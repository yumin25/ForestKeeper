package com.example.gateway;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class JwtAuthenticationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    @Value("${jwt.secret}")
    private String secretKey;

    public JwtAuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(response, "missing authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorization = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String token = authorization.replace("Bearer ", "").trim();
            if(!isValid(token)){
                return onError(response, "missing authorization header", HttpStatus.UNAUTHORIZED);
            }

            String user = getUserAccount(token);
            addAuthorizationHeaders(request, user);
            return chain.filter(exchange);
        };
    }

    // 토큰에서 회원 정보 추출
    public String getUserAccount(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private void addAuthorizationHeaders(ServerHttpRequest request, String user) {
        request.mutate()
                .header("Authorization-Id", user)
                .build();
    }

    private Mono<Void> onError(ServerHttpResponse response, String message, HttpStatus status) {
        response.setStatusCode(status);
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    private boolean isValid(String token){
        try {

            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token : " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT is expired : " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT is unsupported : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty : " + e.getMessage());
        }
        return false;
    }

    public static class Config { }
}
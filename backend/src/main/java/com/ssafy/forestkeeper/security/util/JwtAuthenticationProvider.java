package com.ssafy.forestkeeper.security.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long tokenValidTime;

    private static final String AUTHORITIES_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_ISSUER = "Forest Keeper";

    private final UserDetailsService userDetailsService;

    // JWT 토큰 생성
    public String createToken(String userAccount) {

        Claims claims = Jwts.claims().setSubject(userAccount); // JWT payload 에 저장되는 정보 단위
        claims.put("roles", "ROLE_USER"); // 정보는 key / value 쌍으로 저장됨

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuer(TOKEN_ISSUER) // 토큰 발급자
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS512, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();

    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserAccount(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }

    // 토큰에서 회원 정보 추출
    public String getUserAccount(String token) {

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

    }

    // Request의 Header에서 token 값을 가져옴 "Authorization" : "Bearer TOKEN값'
    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        } else {
            return "No JWT found in request headers";
        }

    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature : " + e.getMessage());
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
}
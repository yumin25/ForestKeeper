package com.ssafy.api.api.controller;

import com.ssafy.api.application.dto.response.BaseResponseDTO;
import com.ssafy.api.exception.*;
import com.ssafy.api.util.mattermost.NotificationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final NotificationManager notificationManager;

    @ExceptionHandler({
            UserNotFoundException.class,
            MountainNotFoundException.class,
            PloggingNotFoundException.class,
            CommunityNotFoundException.class,
            CommentNotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException(RuntimeException e, HttpServletRequest req) {

        notify(e, e.getMessage(), req);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseDTO.of(e.getMessage(), 404));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req) {

        System.out.println(req.getParameterMap().keySet());
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder
                    .append("[")
                    .append(fieldError.getField())
                    .append("](은)는 ")
                    .append(fieldError.getDefaultMessage())
                    .append(". ");
        }

        notify(e, stringBuilder.toString(), req);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of(stringBuilder.toString(), 409));

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e, HttpServletRequest req) {

        String message = "회원 정보가 없습니다.";

        notify(e, message, req);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponseDTO.of(message, 401));

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest req) {

        String message = "권한이 없습니다.";

        notify(e, message, req);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BaseResponseDTO.of(message, 403));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e, HttpServletRequest req) {

        notify(e, e.getMessage(), req);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDTO.of(e.getMessage(), 409));

    }

    private void notify(Exception e, String message, HttpServletRequest req) {

        e.printStackTrace();

        notificationManager.sendNotification(e, message, "**" + req.getMethod() + "** : " + req.getRequestURI(), getParams(req));

    }

    private String getParams(HttpServletRequest req) {

        StringBuilder params = new StringBuilder();
        Enumeration<String> keys = req.getParameterNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.append("- ").append(key).append(" : ").append(req.getParameter(key)).append("\n");
        }

        return params.toString();

    }

}

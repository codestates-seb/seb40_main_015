package com.dongnebook.global.config.security.auth.handler;

import com.dongnebook.global.error.ErrorResponse;
import com.dongnebook.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
        log.warn("Forbidden error happened: {}", accessDeniedException.getMessage());
    }
}

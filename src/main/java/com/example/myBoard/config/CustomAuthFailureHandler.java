package com.example.myBoard.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {
    private static final String DEFAULT_FAIL_URL = "/user/login";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = null;
        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errorMessage = "Username과 Password가 맞지 않습니다. 다시 확인해 주십시오";
        }else if(exception instanceof DisabledException){
            errorMessage = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
        }else if(exception instanceof CredentialsExpiredException){
            errorMessage = "비밀번호 유효기간이 만료 되었습니다. 관리자에게 문의하세요.";
        }else{
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher(DEFAULT_FAIL_URL).forward(request,response);
    }
}

package com.example.member.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 로그인 여부를 확인하고 로그인 상태라면 사용자가 요청한 주소로 보내고
// 로그인하지 않은 상태라면 컨트롤러의 로그인 요청 주소로 넘김
public class LoginCheckInterceptor implements HandlerInterceptor {
    //prehandle이 결국 재정의를 하는 것이어서 세션을 매개변수로 받으면 안되고 동작 부분에서 따로 세션을 가져온다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        // 사용자가 요청한 주소 확인
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        // 세션객체 생성
        HttpSession session = request.getSession();
        // 세션에 저장된 로그인 정보 확인
        if (session.getAttribute("loginEmail") == null) {
            // 로그인하지 않았다면 로그인페이지로 사용자를 보내면서
            // 처음에 사용자가 요청한 주소값도 같이 보냄
            response.sendRedirect("/member/login?redirectURI=" + requestURI);
            return false;
        } else {
            // 로그인 상태라면 요청한 페이지로 보냄(거르지 않음)
            return true;
        }
    }
}

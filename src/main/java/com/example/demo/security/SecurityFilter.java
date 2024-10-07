package com.example.demo.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
//스프링 시큐리티는 사이트 이동간에 필터를 설정할 수 있다.
//이번 프로젝트에서는 시큐리티 필터중 OncePerRequestFilter를 상속받아 doFilterChain 메소드를 오버라이딩 하였다.
//이를통해 이미 인증되어있는 요청은 인증을 하지않음으로써 불필요한 재인증이 발생하지 않도록 했다. 
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	log.info("doFilterInternal 진입");
        try {
            filterChain.doFilter(request, response);
            return;
            
        } catch (Exception e) {

            //사용자 찾을 수 없음
            log.error("사용자를 찾을 수 없습니다.");
        }
    } // doFilterInternal
}


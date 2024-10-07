package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.model.MemberBean;
import com.example.demo.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SecurityProvider implements AuthenticationProvider{
//스프링 시큐리티는 로그인 요청이 들어오면 
//AuthenticationProvider상속 클래스의 authenticate함수를 호출한다.
//오버라이딩된 authenticate함수는 User객체를 만들고 토큰을 발행할지 말지를 결정한다.
	
	//UserDetailsServiceImpl - UserDetailsService를 상속받아 loadUserByUsername을 오버라이딩한 클래스
	private final UserDetailsServiceImpl us;
    
    @Autowired
    MemberBean member;
    @Autowired
    MemberService ms;

    public SecurityProvider(UserDetailsServiceImpl us) {
        this.us = us;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();		//SecurityFilterChain의 username 
        String password = (String) authentication.getCredentials();	//SecurityFilterChain의 password
        //1 . User 객체 만들기
        // loadUserByUsername을 통해 스프링 시큐리티의 UserDetails(User의 인터페이스) 객체를 만들어준다.
        // UserDetails의 기본 함수 목록은 다음과 같다.
        // getAuthorities() : 계정의 권한 목록을 리턴한다.
        // getPassword() : 계정의 비밀번호를 리턴한다.
        // getUsername() : 계정의 고유한 값을 리턴한다.
        // isAccountNonExpired() : 계정의 만료 여부를 리턴한다.
        // isAccountNonLocked() : 계정의 잠김 여부를 리턴한다.
        // isEnabled() : 계정의 활성화 여부를 리턴한다.
        UserDetails user = us.loadUserByUsername(username);
        member = ms.checkMemberId(username);
        //user를 못찾은경우
        if (user == null) {
        	log.info("SP username '"+username+"' is not found.");
            throw new BadCredentialsException("SP username '"+username+"' is not found.");
        }
        //user를 찾은경우
        else { //2. 비밀번호 일치 확인하고 토큰 발행하기.
        	// mem_active 가 2면 탈퇴한 회원
        	if (member.getMem_active().equals("2")) {
        		throw new BadCredentialsException("The corresponding ID has been withdrawn from membership");
        	}
        	//비밀번호 비교
        	if ( !password.equals(user.getPassword()) ) {
        		//비밀번호 불일치시
        		throw new BadCredentialsException("password is not matched");
        	}
    	// 비밀번호 일치시
        // 아이디와 비밀번호 대조에 성공했을경우 인증토큰을 발행해준다.
        // 스프링 시큐리티는 권한별로도 접근가능한 사이트와 아닌 사이트로 나눌 수 있기 때문에
        // 그 아이디가 가진 권한을 가져와 토큰을 만든다. (권한이 사장/이사/일반사원/개발자처럼 여러개인경우 더 효과적으로 작용)
        // 예를들면 내가 일반사원으로 로그인을 했다면 개발자만 접근할수 있는 경로엔 접근할수 없다.
        //( 현재 프로젝트에선 모든 사이트에 대해 permitAll을 해줬기 때문에 큰 의미는 없다)
        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

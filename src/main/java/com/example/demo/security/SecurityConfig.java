package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
//스프링 시큐리티(세션)
// : 스프링부트에서 사이트 로그인시 필요한 기능.
// : gradle에 시큐리티를 추가하면 SecurityFilterChain 함수를 구현해 스프링 시큐리티의 configuration을 정한다.
// : 스프링 시큐리티는 대표적으로 User, UserDetails, OncePerRequestFilter, GenericFilterBean같은 디폴트 클래스들과,
// : AuthenticationProvider와 UserDetailsService같은 디폴트 인터페이스들을 가지고 있다.
// : 사용자들은 이 디폴트 클래스와 인터페이스들을 상속받아 자신만의 시큐리티를 구현할수 있다.

		// 암호 복호화 저장
	    @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    //시큐리티의 필터체인
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	http
	    		.csrf().disable()
	    		.formLogin(form -> form
    				//로그인 페이지를 사용자 지정해서 쓸수 있다.
	    			//로그인페이지를 설정하지않으면 스프링 시큐리티가 제공하는 디폴트 로그인페이지를 사용한다.
	        		.loginPage("/member/login")
	        		//view에서 loginProcessingUrl의 주소로 요청시 Controller로 가지않고
	        		//필터를 거쳐 AuthenticationProvider의 구현클래스의 authenticate함수가 이를 가로챈다.
	        		.loginProcessingUrl("/public/loginAttemptSecurity")
	        		//username과 password는 authenticate함수의 매개변수 Authentication 객체의 username, Credentials과 연동된다.
	        		.usernameParameter("id")	//username을 form으로 넘어온 데이터들중 어떤것으로 할지 정한다. 
	        		.passwordParameter("pwd")	//password도 마찬가지로 form으로 넘어온 것들중 장한다. 
	        		.defaultSuccessUrl("/main")	//AuthenticationProvider에서 토큰 생성시 성공url로 이동.
	        		//로그인 실패시 failureHandler가 실행된다.
	        		.failureHandler(new LoginFailHandler())
	        		);
	    	http
    			.logout()	
    			.logoutUrl("/logout")
    			.logoutSuccessUrl("/main");
	    	http
	        	.authorizeRequests((authorize) -> authorize
        			// 이곳에서 권한별로 사이트 접속을 설정할 수 있다.
	        		// 현재 설정 :모든 사이트에 대해 어떤 권한을 가진 사용자던(비로그인 제외) 접속 가능하다.
	        		// (현재 프로젝트는 시큐리티에서 권한을 검사하지 않고 프론트엔드 단계에서 권한을 검사한다.)
	        		//.requestMatchers("/", "/login", "/public/**", "/loginAttemptSecurity")
	        		//.permitAll()
	        		//.anyRequest().authenticated()
	        		.anyRequest().permitAll()
	        		);
	    	http.exceptionHandling().accessDeniedPage("/public/AccessDenied");
	    	return http.getOrBuild();	
	    }
	    /*
	    @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> web.ignoring().requestMatchers("favicon.ico", "/login", "/loginAttemptSecurity");
	    }
	    */
	}
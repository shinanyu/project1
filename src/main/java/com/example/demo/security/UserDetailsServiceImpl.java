package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.MemberBean;
import com.example.demo.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private MemberService ms;
	
	// SecurityProvider는 1. User를 만들고 2. 비밀번호 일치를 검사한다. 
	// loadByUsername함수를 통해 1번과정을 진행한다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			MemberBean member = ms.checkMemberId(username);
			if ( member == null ) {
				throw new UsernameNotFoundException(username);
			}
			else {
				//DB에 아이디가 존재한다면 User를 상속받은 CustomUser를 만들어준다.
				//CustomUser의 생성자는 User 객체를 만들어서 리턴해주는 역할을 한다.
				List<GrantedAuthority> authorities = new ArrayList<>();
				return new CustomUser(new MemberBean(member.getMem_id(), member.getMem_pw()) );
			
			}
		} catch(Exception e) {
			log.info("err:"+e);
			throw new UsernameNotFoundException(username);
		}	
	} //userdetails
	
}
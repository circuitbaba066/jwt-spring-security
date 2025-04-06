package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.config.UserInfoUserDetails;
import com.example.entity.UserInfo;
import com.example.repository.UserInfoRepository;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserInfoRepository infoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo=	infoRepository.findByName(username);
		System.out.println(userInfo.get());
		return userInfo.map(UserInfoUserDetails::new ).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
		
	}

}

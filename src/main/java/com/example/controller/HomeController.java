package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.UserInfo;
import com.example.repository.UserInfoRepository;



@RestController
@RequestMapping("/v1")
public class HomeController {
	
	@Autowired
	private UserInfoRepository infoRepository;
	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to you! ";
	}
	@GetMapping("/user/getAll")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getAll() {
		return "all employee list";
	}
	@GetMapping("/user/getById/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String getById(@PathVariable int id) {
		return " employee";
	}

	@PostMapping("/save")
	public String addUser(@RequestBody UserInfo user) {
		user.setPassword(encoder.encode(user.getPassword()));
		infoRepository.save(user);
		return "User added";
	}

}


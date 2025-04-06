package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.service.UserInfoUserDetailsService;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Bean
	public UserDetailsService detailsService() {
//		UserDetails admin=User.withUsername("satya").password(encoder.encode("123")).roles("ADMIN").build();
//		UserDetails user=User.withUsername("user").password(encoder.encode("1234")).roles("USER",ADMIN).build();
//		return new InMemoryUserDetailsManager(admin,user);
		return new UserInfoUserDetailsService();
	}
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		return http.csrf().disable()
//				.authorizeHttpRequests().requestMatchers("/v1/welcome","/v1/save").permitAll()
//				.and()
//				.authorizeHttpRequests().requestMatchers("/v1/user/**").authenticated()
//				.and()
//				.formLogin().and().build();
//	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf(AbstractHttpConfigurer::disable)
	            .authorizeHttpRequests(auth ->
	                    auth.requestMatchers("/v1/welcome","/v1/save").permitAll()
	                            .requestMatchers("/v1/user/**")
	                            .authenticated()
	            )
	            .authenticationProvider(authenticationProvider()) 
	            .httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationProvider  authenticationProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(detailsService());
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
		return config.getAuthenticationManager(); 
	} 
}

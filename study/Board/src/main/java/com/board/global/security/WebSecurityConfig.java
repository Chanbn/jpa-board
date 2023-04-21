package com.board.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.board.global.security.handler.CustomAccessDeniedHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web
		.ignoring()
		.antMatchers("/resources/**","/css/**");
	}
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
		.antMatchers("/board/list","/category/**").permitAll()
		.antMatchers("/member/profile","/member/postinfo","/member/commentInfo","/myPage/home","/board/write").authenticated()
		.and()
		.formLogin()
		.loginPage("/member/login")
		.loginProcessingUrl("/member/loginProc") // 추가
		.usernameParameter("username")
		.passwordParameter("password")
		.defaultSuccessUrl("/board/list")
		.failureForwardUrl("/board/list") 
		.and()
		.logout()
		.logoutUrl("/view/logoutProc")
		.logoutSuccessUrl("/board/list")
		.and()
		.exceptionHandling()
		.accessDeniedHandler(new CustomAccessDeniedHandler())
		.and()
		.csrf();
	}
	
	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
}

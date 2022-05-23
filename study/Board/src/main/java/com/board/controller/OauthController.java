package com.board.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.board.helper.constants.SocialLoginType;
import com.board.service.OauthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/login/oauth2/code")
@Slf4j
public class OauthController {
	
	private final OauthService oauthService;

	@GetMapping("/{socialLoginType}")
	public void socialLoginType(@PathVariable(name="socialLoginType") SocialLoginType socialLoginType){
		log.info(">>사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login",socialLoginType);

		oauthService.request(socialLoginType);
		
	}
	
	@GetMapping("/{socialLoginType}/callback")
	public String callback(@PathVariable(name="socialLoginType") SocialLoginType socialLoginType,@RequestParam(name="code") String code) {
		log.info(">>사용자로부터 받은 CODE :: {}",code);
		return oauthService.requestAccessToken(socialLoginType, code);
		
	}
	
}

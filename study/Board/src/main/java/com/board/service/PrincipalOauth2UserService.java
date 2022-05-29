package com.board.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.board.configruation.auth.OAuthAttributes;
import com.board.domain.user.User;
import com.board.mapper.UserMapper;
import com.board.security.config.PrincipalDetails;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	private final UserMapper userMapper;
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// TODO Auto-generated method stub
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		return processOAuth2User(userRequest, oAuth2User);
	}
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		oAuth2User = delegate.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getClientId();
		System.out.println(registrationId);
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().
				getUserInfoEndpoint().getUserNameAttributeName();
		System.out.println(userNameAttributeName);
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,oAuth2User.getAttributes());
		User user = saveOrupdate(attributes,oAuth2User);
		System.out.println("username principalDetails :: "+user.getUsername());
		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
	
	private User saveOrupdate(OAuthAttributes attributes,OAuth2User oAuth2User) {
		User user;

		if(userMapper.findByEmail(attributes.getEmail())!=null){
			user = userMapper.findByEmail(attributes.getEmail());
		}else {
			user = attributes.toEntity();
			userMapper.save(user);
			user = userMapper.findByEmail(attributes.getEmail());
		}
		return user;
	}
}

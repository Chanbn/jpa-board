package com.board.domain.member.service;

import java.util.List;

import com.board.domain.member.dto.MemberInfoDto;
import com.board.domain.member.dto.MemberProfileDto;
import com.board.domain.member.dto.MemberSignUpDto;
import com.board.domain.member.dto.MemberUpdateDto;

public interface MemberService {
	void signup(MemberSignUpDto memberSignUpDto);
	int existCheck(String word,int type);
	MemberInfoDto currentMember(String username);
	MemberProfileDto getProfile(String username);
	void updateProfile(MemberUpdateDto memberUpdateDto);
	List<MemberProfileDto> getList();
	void deleteMember(String username);
}

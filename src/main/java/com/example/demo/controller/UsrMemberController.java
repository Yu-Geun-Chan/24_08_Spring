package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrMemberController {

	@Autowired // 알아서 연결
	private MemberService memberService;

	// 액션 메서드
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		if (Ut.isEmptyOrNull(loginId)) {
			return "아이디를 올바르게 입력해주세요.";
		}

		if (Ut.isEmptyOrNull(loginPw)) {
			return "비밀번호를 올바르게 입력해주세요.";
		}

		if (Ut.isEmptyOrNull(name)) {
			return "이름을 올바르게 입력해주세요.";
		}

		if (Ut.isEmptyOrNull(nickname)) {
			return "닉네임을 올바르게 입력해주세요.";
		}

		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return "휴대폰 번호를 올바르게 입력해주세요.";
		}

		if (Ut.isEmptyOrNull(email)) {
			return "이메일을 올바르게 입력해주세요.";
		}

		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		// MemberService를 통해 id에 -1을 리턴 받았다면 페이지에 이미 사용중인 아이디입니다. 라고 띄우기
		if (id == -1) {
			return Ut.f("이미 사용중인 아이디(%s)입니다.", loginId);
		}

		// MemberService를 통해 id에 -2를 리턴 받았다면 페이지에 이미 사용중인 이름과 이메일입니다. 라고 띄우기
		if (id == -2) {
			return Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다.", name, email);
		}

		Member member = memberService.getMemberById(id);

		return member;
	}
}

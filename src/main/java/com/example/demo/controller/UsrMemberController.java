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
import com.example.demo.vo.ResultData;

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
			return ResultData.from("F-1", "아이디를 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(loginPw)) {
			return ResultData.from("F-1", "비밀번호를 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(name)) {
			return ResultData.from("F-1", "이름을 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(nickname)) {
			return ResultData.from("F-1", "닉네임을 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return ResultData.from("F-1", "휴대폰 번호를 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(email)) {
			return ResultData.from("F-1", "이메일을 올바르게 입력해주세요.");
		}

		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		// MemberService를 통해 id에 -1을 리턴 받았다면 페이지에 
		// 이미 사용중인 아이디(저장되어 있는 loginId)입니다. 라고 띄우기
		if (id == -1) {
			return ResultData.from("F-1", Ut.f("이미 사용중인(%s) 아이디 입니다.", loginId), loginId);
		}

		// MemberService를 통해 id에 -2를 리턴 받았다면 페이지에 
		// 이미 사용중인 이름(저장되어 있는 name)과 이메일(저장되어 있는 email)입니다. 라고 띄우기
		if (id == -2) {
			return ResultData.from("F-1",Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다.", name, email), name, email);
		}

		Member member = memberService.getMemberById(id);

		return member;
	}
}

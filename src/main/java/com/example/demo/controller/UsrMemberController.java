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
import jakarta.servlet.http.HttpSession;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrMemberController {

	@Autowired // 알아서 연결
	private MemberService memberService;

	// 액션 메서드
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(HttpSession httpSession, String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		
		boolean isLogined = false;
		
		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}
		
		// 보통 이런 경우의 실패(로그인 유무)는 "F-A"라고 표시한다.
		if(isLogined) {
			return ResultData.from("F-A", "로그아웃 해주세요.");
		}

		if (Ut.isEmptyOrNull(loginId)) {
			return ResultData.from("F-1", "아이디를 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(name)) {
			return ResultData.from("F-3", "이름을 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(nickname)) {
			return ResultData.from("F-4", "닉네임을 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return ResultData.from("F-5", "휴대폰 번호를 올바르게 입력해주세요.");
		}

		if (Ut.isEmptyOrNull(email)) {
			return ResultData.from("F-6", "이메일을 올바르게 입력해주세요.");
		}

		ResultData doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (doJoinRd.isFail()) {
			return doJoinRd;
		}

		Member member = memberService.getMemberById((int)doJoinRd.getData1());

		return ResultData.newData(doJoinRd, "새로 생성된 member",member);
	}
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData doLogin(HttpSession httpSession, String loginId, String loginPw) {
		
		boolean isLogined = false;
		
		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}
		
		// 보통 이런 경우의 실패(로그인 유무)는 "F-A"라고 표시한다.
		if(isLogined) {
			return ResultData.from("F-A", "로그아웃 해주세요.");
		}
		
		if (Ut.isEmptyOrNull(loginId)) {
			return ResultData.from("F-1", "아이디를 올바르게 입력해주세요.");
		}
		
		if (Ut.isEmptyOrNull(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 올바르게 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			return ResultData.from("F-3", Ut.f("[%s]은(는) 없는 아이디입니다.", loginId), "입력한 로그인 아이디",loginId);
		}
		
		if (!member.getLoginPw().equals(loginPw)) {
			return ResultData.from("F-4","비밀번호가 일치하지 않습니다.");
		}
		
		httpSession.setAttribute("loginedMemberId", member.getId());
		
		return ResultData.from("S-1",Ut.f("[%s]님 환영합니다.", member.getNickname()), "member 1개", member);
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession httpSession) {
		
		if (httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A","로그인 해주세요.");
		}
		
		httpSession.removeAttribute("loginedMemberId");
		
		return ResultData.from("S-1","로그아웃 되었습니다.");
	}
}

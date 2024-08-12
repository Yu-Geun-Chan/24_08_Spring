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
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

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

		return ResultData.newData(doJoinRd, member);
	}
}

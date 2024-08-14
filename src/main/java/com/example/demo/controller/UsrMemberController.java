package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrMemberController {

	@Autowired // 알아서 연결
	private MemberService memberService;

	// 액션 메서드
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(HttpSession httpSession, String loginId, String loginPw, String name,
			String nickname, String cellphoneNum, String email) {

		boolean isLogined = false;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}

		// 보통 이런 경우의 실패(로그인 유무)는 "F-A"라고 표시한다.
		if (isLogined) {
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

		Member member = memberService.getMemberById((int) doJoinRd.getData1());

		return ResultData.newData(doJoinRd, "새로 생성된 member", member);
	}

	@RequestMapping("/usr/member/login")
	public String login(HttpSession httpSession, String loginId, String loginPw, Model model) {

		boolean isLogined = false;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}

		if (isLogined) {
			model.addAttribute("msg", "로그아웃 해주세요.");
			model.addAttribute("replaceUri", "/usr/home/main");
			return "/usr/home/alert";
		}

		return "/usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	public String doLogin(HttpSession httpSession, String loginId, String loginPw, Model model) {
		
		if (Ut.isEmptyOrNull(loginId)) {
			model.addAttribute("msg", "아이디를 입력해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		if (Ut.isEmptyOrNull(loginPw)) {
			model.addAttribute("msg", "비밀번호를 입력해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			model.addAttribute("msg", String.format("[%s]은(는) 없는 아이디 입니다.", loginId));
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		if (!member.getLoginPw().equals(loginPw)) {
			model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		model.addAttribute("member", member);

		Member loginedMember = memberService.getMemberByLoginId(loginId);

		httpSession.setAttribute("loginedMemberId", loginedMember.getId());

		model.addAttribute("loginedMemberId", loginedMember.getId());
		return "/usr/home/main";
	}

	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpSession httpSession, Model model) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			model.addAttribute("msg","로그인 해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		httpSession.removeAttribute("loginedMemberId");

		model.addAttribute("msg","로그아웃 되었습니다.");
		model.addAttribute("replaceUri", "/usr/home/main");
		return "/usr/home/alert";
	}
}

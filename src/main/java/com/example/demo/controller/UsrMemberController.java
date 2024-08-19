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
	@RequestMapping("/usr/member/join")
	public String join(HttpSession httpSession, Model model) {

		boolean isLogined = false;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}

		if (isLogined) {
			model.addAttribute("msg", "로그아웃 해주세요.");
			model.addAttribute("replaceUri", "/usr/home/main");
			return "/usr/home/alert";
		}
		return "/usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, Model model) {

		ResultData doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (doJoinRd.isFail()) {
			return doJoinRd;
		}

		Member member = memberService.getMemberById((int) doJoinRd.getData1());

		model.addAttribute("member", member);

		model.addAttribute("msg", String.format("[%s]님 회원가입을 환영합니다.", member.getNickname()));
		model.addAttribute("replaceUri", "/usr/home/main");
		return "/usr/home/alert";

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
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		model.addAttribute("member", member);

		Member loginedMember = memberService.getMemberByLoginId(loginId);

		httpSession.setAttribute("loginedMemberId", loginedMember.getId());
		httpSession.setAttribute("loginedMemberNickname", loginedMember.getNickname());

		model.addAttribute("loginedMemberId", loginedMember.getId());

		model.addAttribute("msg", String.format("[%s]님 환영합니다.", member.getNickname()));
		model.addAttribute("replaceUri", "/usr/home/main");
		return "/usr/home/alert";
	}

	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpSession httpSession, Model model) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			model.addAttribute("msg", "로그인 해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		model.addAttribute("msg", String.format("[%s]님 로그아웃 되었습니다.", httpSession.getAttribute("loginedMemberNickname")));
		httpSession.removeAttribute("loginedMemberId");
		httpSession.removeAttribute("loginedMemberNickname");
		model.addAttribute("replaceUri", "/usr/home/main");
		return "/usr/home/alert";
	}
}

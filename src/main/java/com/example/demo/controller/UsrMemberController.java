package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrMemberController {

	@Autowired
	private Rq rq;

	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {

		rq.logout();

		return Ut.jsReplace("S-1", Ut.f("로그아웃 되었습니다."), "/");
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req) {

		return "/usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw, String afterLoginUri) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.isEmptyOrNull(loginId)) {
			return Ut.jsHistoryBack("F-1", "로그인 아이디를 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요.");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("[%s]는(은) 존재하지 않는 아이디 입니다.", loginId));
		}

		System.err.println(Ut.sha256(loginPw));

		if (member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다!!!!!"));
		}
		
		if (member.isDelStatus() == true) {
			return Ut.jsReplace("사용정지된 계정이야", "/");
		}

		// 로그인 처리
		rq.login(member);

		// 원래 페이지가 있으면 로그인하고 그쪽으로 보내주는 로직.
		if (afterLoginUri.length() > 0) {
			return Ut.jsReplace("S-1", Ut.f("[%s]님 환영합니다", member.getNickname()), afterLoginUri);
		}

		return Ut.jsReplace("S-1", Ut.f("[%s]님 환영합니다", member.getNickname()), "/");
	}

	@RequestMapping("/usr/member/join")
	public String showJoin(HttpServletRequest req) {

		return "/usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name, String nickname,
			String cellphoneNum, String email) {

		if (Ut.isEmptyOrNull(loginId)) {
			return Ut.jsHistoryBack("F-1", "로그인 아이디를 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-2", "바밀번호를 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(name)) {
			return Ut.jsHistoryBack("F-3", "이름을 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return Ut.jsHistoryBack("F-4", "닉네임을 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", "휴대폰 번호를 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(email)) {
			return Ut.jsHistoryBack("F-6", "이메일을 입력해주세요.");
		}

		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return Ut.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		Member member = memberService.getMemberById((int) joinRd.getData1());

		return Ut.jsReplace(joinRd.getResultCode(), joinRd.getMsg(), "../member/login");
	}

	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {
		return "usr/member/myPage";
	}

	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {
		return "usr/member/checkPw";
	}

	@RequestMapping("/usr/member/doCheckPw")
	public String doCheckPw(String loginPw) {

		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-1", "비밀번호를 입력해주세요");
		}

		if (rq.getLoginedMember().getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return Ut.jsHistoryBack("F-2", "비밀번호가 일치하지 않습니다.");
		}

		return Ut.jsReplace("S-1", "비밀번호 확인 성공", "modify");
	}

	@RequestMapping("/usr/member/modify")
	public String showMymodify() {
		return "usr/member/modify";

	}

	@RequestMapping("/usr/member/doModify")
	public String doModify(HttpServletRequest req, String loginPw, String email, String cellphoneNum, String nickname,
			String name, String loginId) {

		// 비밀번호 입력하지 않아도 회원정보 수정이 가능하게끔 해야한다. -> 비밀번호 null 체크는 X
		if (Ut.isEmptyOrNull(name)) {
			return Ut.jsHistoryBack("F-1", "이름을 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return Ut.jsHistoryBack("F-2", "닉네임을 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return Ut.jsHistoryBack("F-3", "휴대폰 번호를 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(email)) {
			return Ut.jsHistoryBack("F-4", "이메일을 입력해주세요.");
		}

		ResultData modifyRd;

		if (Ut.isEmptyOrNull(loginPw)) {
			modifyRd = memberService.modifyWithoutPwMember(rq.getLoginedMemberId(), email, cellphoneNum, nickname,
					name);
		} else
			modifyRd = memberService.modifyMember(rq.getLoginedMemberId(), loginPw, email, cellphoneNum, nickname,
					name);

		return Ut.jsReplace(modifyRd.getResultCode(), modifyRd.getMsg(), "../member/myPage");
	}
	
	// 회원가입시 아이디 중복체크
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {

		if (Ut.isEmpty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}

		Member existsMember = memberService.getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-2", "해당 아이디는 이미 사용중이야", "loginId", loginId);
		}

		return ResultData.from("S-1", "사용 가능!", "loginId", loginId);
		
	}
	
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId() {

		return "usr/member/findLoginId";
	}

	@RequestMapping("/usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(@RequestParam(defaultValue = "/") String afterFindLoginIdUri, String name,
			String email) {

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			return Ut.jsHistoryBack("F-1", "너는 없는 사람이야");
		}

		return Ut.jsReplace("S-1", Ut.f("너의 아이디는 [ %s ] 야", member.getLoginId()), afterFindLoginIdUri);
	}
}
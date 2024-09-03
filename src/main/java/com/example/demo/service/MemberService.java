package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다.", loginId));
		}

		existsMember = getMemberByNameAndEmail(name, email);

		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다.", name, email));
		}
		
		loginPw = Ut.sha256(loginPw);

		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("[%s]님 회원가입 되었습니다.", nickname), "생성된 회원 id", id);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public ResultData modifyMember(int loginemMemberId, String loginPw, String email, String cellphoneNum,
			String nickname, String name) {
		
		loginPw = Ut.sha256(loginPw);

		memberRepository.modifyMember(loginemMemberId, loginPw, email, cellphoneNum, nickname, name);
		
		return ResultData.from("S-1", "회원정보 수정 완료");
	}

	public ResultData modifyWithoutPwMember(int loginedMemberId, String email, String cellphoneNum, String nickname,
			String name) {
		memberRepository.modifyWithoutPwMember(loginedMemberId, email, cellphoneNum, nickname, name);
		
		return ResultData.from("S-1", "회원정보 수정 완료");
	}

}

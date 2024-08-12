package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.vo.Article;
import com.example.demo.vo.Member;

@Service // 프로젝트 내에서 얘가 서비스라는걸 인식하게끔 하는 것
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	// 실행 순서 때문에 controller와는 다르게 service에서는 repository를 받아야한다.
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {

		// Member타입만을 저장할 수 있는 existsMember 라는 변수에 MemberRepository 클래스를 통해 
		// loginId로 가져온 회원정보를 저장
		Member existsMember = getMemberByLoginId(loginId);

		// 만약 회원정보가 이미 있다면 MemberController 클래스의 id라는 변수에 -1을 리턴
		if (existsMember != null) {
			return -1;
		}

		// Member타입만을 저장할 수 있는 existsMember 라는 변수에 MemberRepository 클래스를 통해
		// name, email로 가져온 회원정보를 저장
		existsMember = getMemberByNameAndEmail(name, email);

		// 만약 회원정보가 이미 있다면 MemberController 클래스의 id라는 변수에 -2를 리턴
		if (existsMember != null) {
			return -2;
		}

		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		return memberRepository.getLastInsertId();
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public List<Member> getArticles() {
		return memberRepository.getMembers();
	}

}

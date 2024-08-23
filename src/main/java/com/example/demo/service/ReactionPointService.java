package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ReactionPointRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;

	public int userCanReaction(int loginedMemberId, String relTypeCode, int relId) {

		if (loginedMemberId == 0) {
			return -2; // 줄 수 없는 숫자(로그인 안했다는 뜻, -1 / 0 / 1만 나오니까)
		}

		return reactionPointRepository.getSumReactionPoint(loginedMemberId, relTypeCode, relId);

	}
}
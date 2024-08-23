package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ReactionPointRepository;
import com.example.demo.vo.ResultData;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;

	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}

	public int userCanReaction(int loginedMemberId, String relTypeCode, int relId) {

		// 로그인 x
		if (loginedMemberId == 0) {
			return -2; // 줄 수 없는 숫자(-1 / 0 / 1만 나오니까)
		}

		return reactionPointRepository.getSumReactionPoint(loginedMemberId, relTypeCode, relId);
	}

	public ResultData increaseReactionPoint(int loginedMemberId, String relTypeCode, int relId) {
		
		int affectRow = reactionPointRepository.increaseReactionPoint(loginedMemberId, relTypeCode, relId);
		if (affectRow != 1) {
			return ResultData.from("F-1", "좋아요 실패!");
		}
		
		return ResultData.from("S-1", "좋아요!");
	}

}
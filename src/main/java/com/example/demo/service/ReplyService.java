package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	
	ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}
	
	// 작성되어있는 댓글들을 가져오기 위한 로직
	public List<Reply> getForPrintReplies(String relTypeCode, int id) {
		return replyRepository.getForPrintReplies(relTypeCode, id);
	}

	// 댓글을 작성하기 위한 로직
	public ResultData writeReply(int loginedMemberId, String relTypeCode, int relId, String body) {
		replyRepository.writeReply(loginedMemberId, relTypeCode, relId, body);
		
		int id = replyRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 댓글이 등록되었습니다", id), "등록 된 댓글의 id", id);
	}
	
    public int getRepliesCount(int articleId) {
        return replyRepository.getRepliesCount(articleId);
    }
}


package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
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
	public List<Reply> getForPrintReplies(int loginMemberId, String relTypeCode, int id) {
		
		List<Reply> replies = replyRepository.getForPrintReplies(loginMemberId, relTypeCode, id);
		
		for(Reply reply : replies) {
			controlForPrintData(loginMemberId, reply);
		}
		
		return replies;
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
    
	private void controlForPrintData(int loginedMemberId, Reply reply) {
		if (reply == null) {
			return;
		}
		ResultData userCanModifyRd = userCanModify(loginedMemberId, reply);
		reply.setUserCanModify(userCanModifyRd.isSuccess());

		ResultData userCanDeleteRd = userCanDelete(loginedMemberId, reply);
		reply.setUserCanDelete(userCanModifyRd.isSuccess());
	}

	public ResultData userCanDelete(int loginedMemberId, Reply reply) {
		if (reply.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 댓글에 대한 삭제 권한이 없습니다", reply.getId()));
		}
		return ResultData.from("S-1", Ut.f("%d번 댓글을 삭제했습니다", reply.getId()));
	}

	public ResultData userCanModify(int loginedMemberId, Reply reply) {
		if (reply.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 댓글에 대한 수정 권한이 없습니다", reply.getId()));
		}
		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정했습니다", reply.getId()), "수정된 댓글", reply);
	}
}


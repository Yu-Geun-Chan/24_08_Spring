package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.service.ReactionPointService;
import com.example.demo.service.ReplyService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrReplyController {

	@Autowired
	private Rq rq;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private ReactionPointService reactionPointService;

	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String relTypeCode, int relId, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.isEmptyOrNull(body)) {
			return Ut.jsHistoryBack("F-1", "내용을 입력해주세요");
		}

		ResultData writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		int id = (int) writeReplyRd.getData1();

		return Ut.jsReplace(writeReplyRd.getResultCode(), writeReplyRd.getMsg(), "../article/detail?id=" + relId);
	}
	
	// 로그인 체크 -> 유무 체크 -> 권한 체크 -> 수정
	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		Reply reply = replyService.getReplyById(id);

		if (reply == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 댓글은 없습니다", id));
		}

		ResultData userCanModifyRd = replyService.userCanModify(rq.getLoginedMemberId(), reply);

		if (userCanModifyRd.isFail()) {
			return Ut.jsHistoryBack(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg());
		}

		if (userCanModifyRd.isSuccess()) {
			replyService.modifyReply(id, body);
		}

		reply = replyService.getReplyById(id);

		return reply.getBody();
	}
	
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		Reply reply = replyService.getReplyById(id);

		if (reply == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 댓글은 없습니다", id));
		}

		ResultData userCanModifyRd = replyService.userCanModify(rq.getLoginedMemberId(), reply);

		if (userCanModifyRd.isFail()) {
			return Ut.jsHistoryBack(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg());
		}

		if (userCanModifyRd.isSuccess()) {
			replyService.deleteReply(id);
		}

		reply = replyService.getReplyById(id);

		return reply.getBody();
	}
	
}

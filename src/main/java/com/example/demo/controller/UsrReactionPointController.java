package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReactionPointService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrReactionPointController {

	@Autowired // 알아서 연결
	private Rq rq;

	@Autowired
	private ReactionPointService reactionPointService;

	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	public String doGoodReaction(HttpServletRequest req, String relTypeCode, int relId, String replaceUri) {

		Rq rq = (Rq) req.getAttribute("rq");

		int usersReaction = reactionPointService.usersReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (usersReaction == 1) {
			return Ut.jsHistoryBack("F-1", "이미 좋아요를 눌렀습니다.");
		}

		ResultData reaticonRd = reactionPointService.increaseReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		return Ut.jsReplace(reaticonRd.getResultCode(), reaticonRd.getMsg(), replaceUri);
	}

}

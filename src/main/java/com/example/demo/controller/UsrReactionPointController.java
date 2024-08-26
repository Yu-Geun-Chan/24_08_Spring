package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.service.ReactionPointService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

@Controller
public class UsrReactionPointController {

	@Autowired
	private Rq rq;

	@Autowired
	private ReactionPointService reactionPointService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public ResultData doGoodReaction(String relTypeCode, int relId) {
		ResultData usersReactionRd = reactionPointService.usersReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		int usersReaction = (int) usersReactionRd.getData1();

		if (usersReaction == 1) {
			reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		} else if (usersReaction == -1) {
			reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		} else {
			reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		}

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), relId);
		return ResultData.from("S-1", "좋아요 반영됨", "goodReactionPoint", article.getGoodReactionPoint(),
				"badReactionPoint", article.getBadReactionPoint());
	}

	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public ResultData doBadReaction(String relTypeCode, int relId) {
		ResultData usersReactionRd = reactionPointService.usersReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		int usersReaction = (int) usersReactionRd.getData1();

		if (usersReaction == -1) {
			reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		} else if (usersReaction == 1) {
			reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		} else {
			reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		}

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), relId);
		return ResultData.from("S-1", "싫어요 반영됨", "badReactionPoint", article.getBadReactionPoint(), "goodReactionPoint",
				article.getGoodReactionPoint());
	}
}

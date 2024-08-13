package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrArticleController {

	@Autowired // 알아서 연결
	private ArticleService articleService;

	// 액션 메서드
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	// ResultData<Article> : data1의 타입이 Article이라고 명시
	public ResultData<Article> doWrite(HttpSession httpSession, String title, String body) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "로그인 후에 이용해주세요.");
		}

		int memberId = (int) httpSession.getAttribute("loginedMemberId");

		if (Ut.isEmptyOrNull(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Ut.isEmptyOrNull(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}

		ResultData writeArticleRd = articleService.writeArticle(memberId, title, body);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticleById(id);

		return ResultData.newData(writeArticleRd, "새로 작성된 게시글", article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Article> doDelete(HttpSession httpSession, int id) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "로그인 후에 이용해주세요.");
		}

		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 없습니다", id));
		}

		if (foundArticle.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 게시글에 대한 권한이 없습니다.", id));
		}

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 게시글이 삭제되었습니다.", id), "삭제된 게시글", foundArticle);
	}

	// 로그인 체크 -> 게시글 유무 체크 -> 권한 체크 -> 수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	// object를 쓰는 이유는 어떨때는 return을 String으로 하고 어떨때는 Article 타입으로 하기 위해
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "로그인 후에 이용해주세요.");
		}

		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 없습니다", id));
		}

		if (foundArticle.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 게시글에 대한 권한이 없습니다.", id));
		}

		articleService.modifyArticle(id, title, body);

		// 수정된 후 게시글의 정보를 보기 위해 한번 더 찾아와야한다.
		foundArticle = articleService.getArticleById(id);

		return ResultData.from("S-1", Ut.f("%d번 게시글이 수정되었습니다.", id), "수정된 게시글", foundArticle);
	}

	// 게시글 상세보기
	@RequestMapping("/usr/article/detail")
	public String getArticle(int id, Model model) {

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			return Ut.f("%d번 게시글은 없습니다.", id);
		}
		
		model.addAttribute("article", foundArticle);

		return "/usr/article/detail";
	}

	// 게시글 목록
	@RequestMapping("/usr/article/list")
	public String getArticles(Model model) {

		List<Article> articles = articleService.getArticles();

		model.addAttribute("articles", articles);

		return "/usr/article/list";
	}

}

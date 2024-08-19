package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Board;
import com.example.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrArticleController {

	@Autowired // 알아서 연결
	private ArticleService articleService;

	@Autowired // 알아서 연결
	private BoardService boardService;

	// 액션 메서드
	@RequestMapping("/usr/article/write")
	public String write(HttpSession httpSession, String title, String body, Model model) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			model.addAttribute("msg", "로그인 후에 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		return "/usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	public String doWrite(HttpSession httpSession, String title, String body, Model model, String boardId) {

		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		if (Ut.isEmptyOrNull(title)) {
			model.addAttribute("msg", "제목을 입력해주세요.");
			model.addAttribute("replaceUri", "/usr/article/write");
			return "/usr/home/alert";
		}

		if (Ut.isEmptyOrNull(body)) {
			model.addAttribute("msg", "내용을 입력해주세요.");
			model.addAttribute("replaceUri", "/usr/article/write");
			return "/usr/home/alert";
		}
		if (Ut.isEmptyOrNull(boardId)) {
			model.addAttribute("msg", "게시판을 선택해주세요.");
			model.addAttribute("replaceUri", "/usr/article/write");
			return "/usr/home/alert";
		}

		ResultData writeArticleRd = articleService.writeArticle(loginedMemberId, title, body, boardId);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticleById(id);

		model.addAttribute("article", article);

		model.addAttribute("msg", String.format("%d번 게시글이 작성되었습니다.", id));
		model.addAttribute("replaceUri", "/usr/article/detail?id=" + id);
		return "/usr/home/alert";

	}

	@RequestMapping("/usr/article/doDelete")
	public Object delete(HttpSession httpSession, int id, Model model) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			model.addAttribute("msg", "로그인 후에 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			model.addAttribute("msg", String.format("%d번 게시글은 없습니다.", id));
			model.addAttribute("replaceUri", "/usr/article/list");
			return "/usr/home/alert";
		}

		if (foundArticle.getMemberId() != loginedMemberId) {
			model.addAttribute("msg", String.format("%d번 게시글에 대한 권한이 없습니다.", id));
			model.addAttribute("replaceUri", "/usr/article/list");
			return "/usr/home/alert";
		}

		articleService.deleteArticle(id);

		model.addAttribute("msg", String.format("%d번 게시글이 삭제되었습니다.", id));
		model.addAttribute("replaceUri", "/usr/article/list");
		return "/usr/home/alert";
	}

	// 로그인 체크 -> 게시글 유무 체크 -> 권한 체크 -> 수정
	@RequestMapping("/usr/article/modify")
	public String modify(HttpSession httpSession, int id, String title, String body, Model model) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			model.addAttribute("msg", "로그인 후에 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "/usr/home/alert";
		}

		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			model.addAttribute("msg", String.format("%d번 게시글은 없습니다.", id));
			model.addAttribute("replaceUri", "/usr/article/list");
			return "/usr/home/alert";
		}

		if (foundArticle.getMemberId() != loginedMemberId) {
			model.addAttribute("msg", String.format("%d번 게시글에 대한 권한이 없습니다.", id));
			model.addAttribute("replaceUri", "/usr/article/list");
			return "/usr/home/alert";
		}

		model.addAttribute("article", foundArticle);

		return "/usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	public String doModify(HttpSession httpSession, int id, String title, String body, Model model) {

		articleService.modifyArticle(id, title, body);

		Article modifyArticle = articleService.getArticleById(id);

		model.addAttribute("article", modifyArticle);

		model.addAttribute("msg", String.format("%d번 게시글이 수정되었습니다.", id));
		model.addAttribute("replaceUri", "/usr/article/detail?id=" + id);
		return "/usr/home/alert";
	}

	// 게시글 상세보기
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpSession httpSession, int id, Model model) {

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			return "redirect:/usr/article/list";
		}

		model.addAttribute("article", foundArticle);

		return "/usr/article/detail";
	}

	// 게시글 목록
	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId) {

		Board board = boardService.getBoardById(boardId);

		List<Article> articles = articleService.getForPrintArticles(boardId);
		
		if (board == null) {
			model.addAttribute("msg", "존재하지 않는 게시판입니다.");
			model.addAttribute("replaceUri", "../article/list");
			return "/usr/home/alert";
		}

		model.addAttribute("articles", articles);
		model.addAttribute("board", board);

		return "/usr/article/list";

	}
}

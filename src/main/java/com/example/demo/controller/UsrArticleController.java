package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String doWrite(HttpSession httpSession, String title, String body) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			return Ut.f("<script>alert('로그인 후 이용해주세요.'); location.replace('../home/main');</script>");
		}

		int memberId = (int) httpSession.getAttribute("loginedMemberId");

		if (Ut.isEmptyOrNull(title)) {
			return Ut.f("<script>alert('제목을 입력해주세요.'); location.replace('../home/main');</script>");
		}
		if (Ut.isEmptyOrNull(body)) {
			return Ut.f("<script>alert('내용을 입력해주세요.'); location.replace('../home/main');</script>");
		}

		ResultData writeArticleRd = articleService.writeArticle(memberId, title, body);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticleById(id);

		return "/usr/article/write";
	}

	@RequestMapping("/usr/article/delete")
	public Object delete(HttpSession httpSession, int id, RedirectAttributes redirectAttributes) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			redirectAttributes.addFlashAttribute("alertMsg","로그인 후 이용해주세요.");
			return "redirect:/usr/member/login";
		}

		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
	        redirectAttributes.addFlashAttribute("alertMsg", String.format("%d번 게시글은 없습니다.", id));
	        return "redirect:/usr/article/list";
		}

		if (foundArticle.getMemberId() != loginedMemberId) {
	        redirectAttributes.addFlashAttribute("alertMsg", String.format("%d번 게시글에 대한 권한이 없습니다.", id));
	        return "redirect:/usr/article/list";
		}

		articleService.deleteArticle(id);

		redirectAttributes.addFlashAttribute("alertMsg", String.format("%d번 게시글은 삭제되었습니다.", id));
		return "redirect:/usr/article/list";
	}

	// 로그인 체크 -> 게시글 유무 체크 -> 권한 체크 -> 수정
	@RequestMapping("/usr/article/modify")
	public String modify(HttpSession httpSession, int id, String title, String body, Model model, RedirectAttributes redirectAttributes) {

		if (httpSession.getAttribute("loginedMemberId") == null) {
			redirectAttributes.addFlashAttribute("alertMsg","로그인 후 이용해주세요.");
			return "redirect:/usr/member/login";
		}

		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
	        redirectAttributes.addFlashAttribute("alertMsg", String.format("%d번 게시글은 없습니다.", id));
	        return "redirect:/usr/article/list";
		}

		if (foundArticle.getMemberId() != loginedMemberId) {
	        redirectAttributes.addFlashAttribute("alertMsg", String.format("%d번 게시글에 대한 권한이 없습니다.", id));
	        return "redirect:/usr/article/list";
		}

		model.addAttribute("article", foundArticle);

		return "/usr/article/modify";
	}
	@RequestMapping("/usr/article/doModify")
	public String doModify(int id, String title, String body, Model model, RedirectAttributes redirectAttributes) {
		
		articleService.modifyArticle(id, title, body);
	
		Article modifyArticle = articleService.getArticleById(id);
		
		model.addAttribute("article",modifyArticle);
		
        redirectAttributes.addFlashAttribute("alertMsg", String.format("%d번 게시글이 수정되었습니다.", id));
        return "/usr/article/detail";
	}
	

	// 게시글 상세보기
	@RequestMapping("/usr/article/detail")
	public String getArticle(int id, Model model, RedirectAttributes redirectAttributes) {

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
	        redirectAttributes.addFlashAttribute("alertMsg", String.format("%d번 게시글은 없습니다.", id));
	        return "redirect:/usr/article/list";
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

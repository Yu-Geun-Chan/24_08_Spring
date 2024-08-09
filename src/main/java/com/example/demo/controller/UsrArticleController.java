package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.vo.Article;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrArticleController {

	@Autowired // 알아서 연결
	private ArticleService articleService;

	// 액션 메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = articleService.writeArticle(title, body);
		Article article = articleService.getArticleById(id);

		return article;
	}

	// 게시글 목록
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articleService.getArticles();
	}

	// 게시글 상세보기
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticle(int id) {

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			return String.format("%d번 게시글은 없습니다.", id);
		}

		return foundArticle;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			return String.format("%d번 게시글은 없습니다.", id);
		}

		articleService.deleteArticle(id);

		return String.format("%d번 게시글이 삭제되었습니다.", id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	// object를 쓰는 이유는 어떨때는 return을 String으로 하고 어떨때는 Article 타입으로 하기 위해
	public Object doModify(int id, String title, String body) {

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			return String.format("%d번 게시글은 없습니다.\n", id);
		}

		articleService.modifyArticle(id, title, body);

		return foundArticle + String.format(", %d번 게시글이 수정되었습니다.", id);
	}

}

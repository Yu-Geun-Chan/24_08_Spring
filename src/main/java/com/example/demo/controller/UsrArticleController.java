package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.vo.Article;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrArticleController {

	int lastArticleId;
	List<Article> articles;

	// 생성자
	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
		makeArticleTestData();
	}

	private void makeArticleTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}

	private Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);

		articles.add(article);

		lastArticleId++;

		return article;
	}

	private Article getArticleById(int id) {

		for (Article article : articles) {

			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	// 액션 메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);

		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			return String.format("%d번 게시글은 없습니다.", id);
		}

		articles.remove(foundArticle);

		return String.format("%d번 게시글이 삭제되었습니다.", id);
	}

}

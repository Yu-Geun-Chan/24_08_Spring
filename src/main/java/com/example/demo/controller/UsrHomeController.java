package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Article;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrHomeController {
	
	@RequestMapping("/usr/home/getString")
	@ResponseBody
	public String getString() {
	
		return "안뇽?";
	}

	@RequestMapping("/usr/home/getDouble")
	@ResponseBody
	public double getDouble() {

		return 1.5123124;
	}

	@RequestMapping("/usr/home/getBoolean")
	@ResponseBody
	public boolean getBoolean() {

		return true;
	}
	
	@RequestMapping("/usr/home/getMap")
	@ResponseBody
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();

		map.put("철수나이", 11);
		map.put("영수나이", 12);

		return map;
	}

	@RequestMapping("/usr/home/getList")
	@ResponseBody
	public List<String> getList() {
		
		List<String> list = new ArrayList<>();
		list.add("영수나이");
		list.add("철수나이");
		
		return list;
	}
	
	@RequestMapping("/usr/home/getArticle")
	@ResponseBody
	public Article getArticle(int id, String title, String body) {
		
		Article article = new Article(id, title, body);
		
		return article;
	}
	
}

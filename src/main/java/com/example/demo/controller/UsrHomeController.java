package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.util.crawlTest;
import com.example.demo.vo.Article;

@Controller // 프로젝트에서 얘는 컨트롤러라고 인식하게끔 하는것
public class UsrHomeController {

	@RequestMapping("/usr/home/main")
	public String showMain() {
		// prefix와 suffix를 설정해놨기 때문에 /WEB-INF/jsp/home/main.jsp 를 /usr/home/main으로 입력해도 된다.
		return "/usr/home/main";
	}
	
	// 주소창에 http://localhost:8081/만 입력해도 http://localhost:8081/usr/home/main 입력한거 처럼 나온다.
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main";
	}
	
	@RequestMapping("/usr/crawl")
	public String doCrawl() {

		crawlTest.crawl();

		return "redirect:/usr/home/main";
	}

}

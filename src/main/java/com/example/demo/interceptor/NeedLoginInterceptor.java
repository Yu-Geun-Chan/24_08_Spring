package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class NeedLoginInterceptor implements HandlerInterceptor {

	@Autowired
	private Rq rq;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		Rq rq = (Rq) req.getAttribute("rq");

		if (!rq.isLogined()) {
			System.err.println("================== 로그인 후 이용해주세요 ====================");

						// 원래 페이지를 저장하기 위한 로직
						String afterLoginUri = rq.getEncodedCurrentUri();
						// 로그인 하지 않았다면 로그인 창으로 보내고 로그인을 한 후에는 원래 페이지로 되돌리기 위한 코드
						rq.printReplace("F-A", "로그인 후 이용해주세요", "../member/login?afterLoginUri=" + afterLoginUri);

						return false;

		}

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}

}
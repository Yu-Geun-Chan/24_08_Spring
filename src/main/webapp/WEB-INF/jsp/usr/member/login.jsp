<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="LOGIN"></c:set>

<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<div class="text-center mt-3">
	<form method="POST" action="doLogin">
		<div>
			아이디 <input autocomplete="off" type="text" placeholder="아이디를 입력해주세요." name="loginId" />
		</div>
		<div>
			비밀번호 <input autocomplete="off" type="password" placeholder="비밀번호를 입력해주세요." name="loginPw" />
		</div>
		<button class="login" type="submit">로그인</button>
	</form>
</div>

<div class="btns text-center mt-8">
	<button class="btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
	<button class="btn btn-outline ml-8" type="button">
		<a href="list">리스트로 돌아가기</a>
	</button>
</div>

<%@ include file="../common/foot.jspf"%>
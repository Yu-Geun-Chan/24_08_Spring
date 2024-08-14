<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Write"></c:set>
<%@ include file="../common/head.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

	<a href="../home/main">메인 페이지로 </a>

	<h2>로그인</h2>


	<form method="POST" action="doLogin">
		<div>
			아이디 : <input autocomplete="off" type="text" placeholder="아이디를 입력해주세요." name="loginId" />
		</div>
		<div>
			비밀번호 : <input autocomplete="off" type="text" placeholder="비밀번호를 입력해주세요." name="loginPw" />
		</div>
		<button type="submit">로그인</button>
	</form>


	<div>
		<a style="color: green" href="list">리스트로 돌아가기</a>
	</div>

</body>
</html>
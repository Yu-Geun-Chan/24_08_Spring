<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세페이지</title>
</head>
<body>

	<a href="../home/main">메인 페이지로 </a>

	<h2>게시물 상세페이지</h2>

	<div>번호 :${article.id}</div>
	<div>날짜 : ${article.regDate}</div>
	<div>작성자 : ${article.nickName}</div>
	<div>제목 : ${article.title}</div>
	<div>내용 : ${article.body}</div>

	<div>
		<a style="color: green" href="list">리스트로 돌아가기</a>
	</div>

</body>
</html>
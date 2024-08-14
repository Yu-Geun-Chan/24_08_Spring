<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${article.id}번 게시물 수정</title>
</head>
<body>

	<a href="../home/main">메인 페이지로 </a>


	<h2>${article.id}번 게시물 수정</h2>


	<form method="POST" action="/usr/article/doModify">
		<input type="hidden" value="${article.id}" name="id" />
		<div>
			새 제목 : <input type="text" placeholder="제목을 입력하세요." name="title" />
		</div>
		<div>
			새 내용 :
			<textarea placeholder="내용을 입력하세요." name="body"></textarea>
		</div>
		<button type="submit">수정</button>
	</form>

	<div>
		<a style="color: green" href="list">리스트로 돌아가기</a>
	</div>

</body>
</html>
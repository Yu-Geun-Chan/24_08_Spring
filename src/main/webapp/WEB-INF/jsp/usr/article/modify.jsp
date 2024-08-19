<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MODIFY"></c:set>
<%@ include file="../common/head.jspf"%>
<%

%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<h2 class="text-center m-8">${article.id}번 게시물 수정</h2>


<form class="mt-3" method="POST" action="/usr/article/doModify">
	<input type="hidden" value="${article.id}" name="id" />
	<div>
		새 제목<input type="text" placeholder="새 제목을 입력하세요." name="title" />
	</div>
	<div>
		새 내용 <br>
		<textarea placeholder="새 내용을 입력하세요." name="body"></textarea>
	</div>
	<button type="submit">수정</button>
</form>

<div class="btns text-center mt-8">
	<button class="btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
	<button class="btn btn-outline ml-8" type="button">
		<a href="list">리스트로 돌아가기</a>
	</button>
</div>

<%@ include file="../common/foot.jspf"%>


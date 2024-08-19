<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="WRITE"></c:set>
<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<form method="POST" action="doWrite">
	<div>
		제목<input type="text" placeholder="제목을 입력하세요." name="title" />
	</div>
	<div>
		내용 <br>
		<textarea type="text" placeholder="내용을 입력하세요." name="body"></textarea>
	</div>
	<button type="submit">작성</button>
</form>


<div class="btns text-center mt-8">
	<button class="hover:underline" type="button" onclick="history.back()">뒤로가기</button>
	<button class="hover:underline ml-8" type="button"><a href="list">리스트로 돌아가기</a></button>
</div>


<%@ include file="../common/foot.jspf"%>
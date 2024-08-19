<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="DETAIL"></c:set>
<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<section class="mt-8 text-xl px-4">
	<div class="mx-auto">
		<table border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">
			<tbody>
				<tr style="text-align: center;">
					<th>게시글 번호</th>
					<td>${article.id}</td>
				</tr>
				<tr style="text-align: center;">
					<th>작성 날짜</th>
					<td>${article.regDate.substring(0,10)}</td>
				</tr>
				<tr style="text-align: center;">
					<th>제목</th>
					<td>${article.title}</td>
				</tr>
				<tr style="text-align: center;">
					<th>내용</th>
					<td>${article.body }</td>
				</tr>
				<tr style="text-align: center;">
					<th>작성자</th>
					<td>${article.extra__Writer}</td>
				</tr>
			</tbody>
		</table>
		<div class="btns text-center mt-8">
			<button class="hover:underline" type="button" onclick="history.back()">뒤로가기</button>
			<c:if test="${article.memberId eq loginedMemberId }">
				<a class="hover:underline ml-8" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.memberId eq loginedMemberId }">
				<a class="hover:underline ml-8" href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	</div>
</section>


<%@ include file="../common/foot.jspf"%>
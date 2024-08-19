<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="LIST"></c:set>
<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>


<hr />


<section class="mt-8 text-xl px-4">
	<div class="mx-auto">
		<table id="list_table" border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">
			<thead>
				<tr style="text-align: center;">
					<th>게시글 번호</th>
					<th>작성 날짜</th>
					<th>제목</th>
					<th>내용</th>
					<th>작성자 닉네임</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${articles}">
					<tr style="text-align: center;">
						<td>${article.id}</td>
						<td>${article.regDate.substring(0,10)}</td>
						<td><a class="hover:underline" href="detail?id=${article.id}">${article.title}</a></td>
						<td>${article.body}</td>
						<td>${article.extra__Writer}</td>
						<td><a href="modify?id=${article.id }">수정</a></td>
						<td><a href="doDelete?id=${article.id }">삭제</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>


<%@ include file="../common/foot.jspf"%>

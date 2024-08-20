<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code} LIST"></c:set>

<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>


<hr />


<section class="mt-8 text-xl px-4">
	<div class="mx-auto">
	<div>총 게시글 수 : ${articlesCount}개</div>
	검색<input type="text" placeholder="검색어를 입력하세요." name="searchWord" />
	
		<table id="list_table" border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">
			<thead>
				<tr style="text-align: center;">
					<th>게시글 번호</th>
					<th>작성 날짜</th>
					<th>제목</th>
					<th>내용</th>
					<th>작성자 닉네임</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${articles}">
					<tr style="text-align: center;">
						<td>${article.id}</td>
						<td>${article.regDate.substring(0,10)}</td>
						<td><a class="hover:underline" href="detail?id=${article.id}">${article.title}</a></td>
						<td>${article.body}</td>
						<td>${article.extra__writer}</td>
					</tr>
				</c:forEach>

				<c:if test="${empty articles}">
					<tr>
						<td colspan="4" style="text-align: center">게시글이 없습니다.</td>
					</tr>
				</c:if>
			</tbody>
		</table>

		<!-- Pagination -->
		<div style="text-align: center; margin-top: 20px;">
			<c:if test="${page > 1}">
				<a href="?boardId=${board.id}&page=${page - 1}&itemsInAPage=${itemsInAPage}">이전</a>
			</c:if>

			<c:forEach begin="1" end="${totalPage}" var="i">
				<c:choose>
					<c:when test="${i == page}">
						<strong>${i}</strong>
					</c:when>
					<c:otherwise>
						<a href="?boardId=${board.id}&page=${i}&itemsInAPage=${itemsInAPage}">${i}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:if test="${page < totalPage}">
				<a href="?boardId=${board.id}&page=${page + 1}&itemsInAPage=${itemsInAPage}">다음</a>
			</c:if>
		</div>
		
	</div>
</section>

<%@ include file="../common/foot.jspf"%>

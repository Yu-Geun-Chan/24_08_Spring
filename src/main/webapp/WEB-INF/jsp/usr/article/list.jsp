<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code} LIST"></c:set>

<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>


<hr />


<section class="mt-8 text-xl px-4">
	<div class="mx-auto">
		<div>해당 게시판 게시글 수 : ${articlesCount}개</div>

		<form class="mb-4" method="POST" action="list?boardId=${boardId}">
			<div>
				<select name="searchKeywordTypeCode" data-value="${searchKeywordTypeCode }">
					<option value="title">제목</option>
					<option value="body">내용</option>
					<option value="title,body">제목 또는 내용</option>
					<option value="nickname">작성자</option>
				</select>
			</div>
			<div>
				<input type="text" autocomplete="off" placeholder="검색어를 입력해주세요." name="searchKeyword" value="${searchKeyword }" />
			</div>
			<button type="submit">검색</button>
		</form>

		<table id="list_table" border="1" cellspacing="0" cellpadding="9" style="width: 100%; border-collapse: collapse;">
			<thead>
				<tr style="text-align: center;">
					<th>게시글 번호</th>
					<th>작성 날짜</th>
					<th>제목</th>
					<th>내용</th>
					<th>작성자 닉네임</th>
					<th>조회수</th>
					<th>좋아요</th>
					<th>싫어요</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${articles}">
					<tr style="text-align: center;">
						<td>${article.id}</td>
						<td>${article.regDate.substring(0,10)}</td>
						<td><a class="hover:underline" href="detail?id=${article.id}">${article.title}</a> [${article.repliesCount}]</td>
						<td>${article.body}</td>
						<td>${article.extra__writer}</td>
						<td>${article.hit}</td>
						<td>${article.goodReactionPoint}</td>
						<td>${article.badReactionPoint}</td>
					</tr>
				</c:forEach>

				<c:if test="${empty articles}">
					<tr>
						<td colspan="5" style="text-align: center">게시글이 없습니다.</td>
					</tr>
				</c:if>
			</tbody>
		</table>

		<!-- 페이징 -->
		<div style="text-align: center; margin-top: 20px;">
			<c:set var="paginationLen" value="3" />
			<c:set var="startPage" value="${page -  paginationLen  >= 1 ? page - paginationLen : 1}" />
			<c:set var="endPage" value="${page +  paginationLen  <= totalPage ? page + paginationLen : totalPage}" />

			<c:set var="baseUri" value="?boardId=${boardId }"></c:set>
			<c:set var="baseUri" value="${baseUri }&searchKeywordTypeCode=${searchKeywordTypeCode }"></c:set>
			<c:set var="baseUri" value="${baseUri }&searchKeyword=${searchKeyword }"></c:set>

			<c:if test="${startPage > 1}">
				<a href="${baseUri}&page=1">1</a>
			</c:if>

			<c:if test="${startPage > 2 }">
				<button>...</button>
			</c:if>

			<c:forEach begin="${startPage}" end="${endPage}" var="i">
				<c:choose>
					<c:when test="${i == page}">
						<strong>${i}</strong>
						<!-- strong 태그 : 굵은 텍스트로 표시 -->
					</c:when>
					<c:otherwise>
						<a href="${baseUri}&page=${i}">${i}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:if test="${endPage < totalPage - 1 }">
				<button>...</button>
			</c:if>

			<c:if test="${endPage < totalPage}">
				<a href="${baseUri}&page=${totalPage}">${totalPage}</a>
			</c:if>
		</div>
		<!-- 여기까지 -->

	</div>
</section>

<%@ include file="../common/foot.jspf"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="DETAIL"></c:set>
<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__doIncreaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyOnView';

		if (localStorage.getItem(localStorageKey)) {
			return;
		}

		localStorage.setItem(localStorageKey, true);

		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			console.log(data);
			console.log(data.data1);
			$('.article-detail__hit').empty().html(data.data1);
		}, 'json')
	}

	$(function() {
		// 		ArticleDetail__doIncreaseHitCount();
		setTimeout(ArticleDetail__doIncreaseHitCount, 2000);
	})
</script>

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
					<th>수정 날짜</th>
					<td>${article.updateDate.substring(0,10)}</td>
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
					<th>게시판</th>
					<td>${article.boardId}</td>
				</tr>
				<tr style="text-align: center;">
					<th>작성자</th>
					<td>${article.extra__writer}</td>
				</tr>
				<tr style="text-align: center;">
					<th>조회수</th>
					<td><span class="article-detail__hit">${article.hit}</span></td>
				</tr>
				<tr style="text-align: center;">
					<th>좋아요</th>
					<td>${article.goodReactionPoint}</td>
				</tr>
				<tr style="text-align: center;">
					<th>싫어요</th>
					<td>${article.badReactionPoint}</td>
				</tr>
				<tr style="text-align: center;">
					<th>좋아요 / 싫어요</th>
					<td><a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.currentUri}" 
					class="btn btn-success">👍 좋아요 ${article.goodReactionPoint}</a> 
					<a href="/usr/reactionPoint/doBadReaction" class="btn btn-error">👎 싫어요 ${article.badReactionPoint}</a></td>
				</tr>
			</tbody>
		</table>

		<div class="btns text-center mt-8">
			<button class="btn" type="button" onclick="history.back()">뒤로가기</button>
			<c:if test="${article.memberId eq loginedMemberId }">
				<a class="btn ml-8" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.memberId eq loginedMemberId }">
				<a class="btn ml-8" href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	</div>
</section>


<%@ include file="../common/foot.jspf"%>
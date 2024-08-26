<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="DETAIL"></c:set>
<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<!-- <iframe src="http://localhost:8080/usr/article/doIncreaseHitCount?id=757" frameborder="0"></iframe> -->

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

<script>
<!-- 좋아요 싫어요 버튼	-->
	function doReaction(reactionType) {
		let url = "";

		if (reactionType === 'good') {
			url = "/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${article.id}";
		} else if (reactionType === 'bad') {
			url = "/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${article.id}";
		}

		$.ajax({
			type : "POST",
			url : url,
			success : function(data) {
				var likeButton = $('.article-detail__good-reaction');
				var dislikeButton = $('.article-detail__bad-reaction');
				console.log(data);

				if (reactionType === 'good') {
					likeButton.text(data.data1);
					dislikeButton.text(data.data2);
				} else if (reactionType === 'bad') {
					dislikeButton.text(data.data1);
					likeButton.text(data.data2);
				}
			},
			error: function(jqXHR,textStatus,errorThrown) {
				alert('좋아요 / 싫어요 오류 발생 : ' + textStatus); <!-- textStatus == 'error' -->
			}
		});
	}
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
					<td>
						<span class="article-detail__hit">${article.hit}</span>
					</td>
				</tr>
				<tr class="reaction" style="text-align: center;">
					<th>좋아요</th>
					<td class="article-detail__good-reaction">${article.goodReactionPoint}</td>
				</tr>
				<tr style="text-align: center;">
					<th>싫어요</th>
					<td class="article-detail__bad-reaction">${article.badReactionPoint}</td>
				</tr>
				<tr style="text-align: center;">
					<th>좋아요 / 싫어요 / ${usersReaction}</th>
					<td>
						<button class="btn btn-success" onclick="doReaction('good')">
							👍 좋아요
							<span class="article-detail__good-reaction">${article.goodReactionPoint}</span>
						</button>
						<button class="btn btn-error" onclick="doReaction('bad')">
							👎 싫어요
							<span class="article-detail__bad-reaction">${article.badReactionPoint}</span>
						</button>
					</td>
				</tr>
			</tbody>
		</table>

		<div class="btns text-center mt-8">
			<button class="btn" type="button" onclick="history.back()">뒤로가기</button>
			<c:if test="${article.memberId eq loginedMemberId }">
				<button class="btn ml-8">
					<a href="../article/modify?id=${article.id }">수정</a>
				</button>
			</c:if>
			<c:if test="${article.memberId eq loginedMemberId }">
				<button class="btn ml-8">
					<a href="../article/doDelete?id=${article.id }">삭제</a>
				</button>
			</c:if>
		</div>
	</div>
</section>


<%@ include file="../common/foot.jspf"%>
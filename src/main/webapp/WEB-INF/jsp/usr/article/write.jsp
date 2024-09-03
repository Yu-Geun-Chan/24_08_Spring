<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="WRITE"></c:set>

<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<!--  -->
<script type="text/javascript">
	function ArticleWrite__submit(form) {
		form.title.value = form.title.value.trim();
		if (form.title.value.length == 0) {
			alert('제목을 입력하세요.');
			return;
		}
		const editor = $(form).find('.toast-ui-editor').data(
				'data-toast-editor');
		const markdown = editor.getMarkdown().trim();
		if (markdown.length == 0) {
			alert('내용을 입력하세요.');
			return;
		}
		form.body.value = markdown;
		form.submit();
	}
</script>

<form class="write_form" onsubmit="ArticleWrite__submit(this); return false;" method="POST" action="doWrite"
	enctype="multipart/form-data">
	<input type="hidden" name="body" />
	<input type="hidden" name=">${currentId }">
	<div class="p-3">
		<select name="board">
			<option value="" selected disabled>게시판을 선택하세요.</option>
			<!-- selected disabled : 나오는 값들 중에서 무조건 고르게 -->
			<option value="1">공지사항</option>
			<option value="2">자유</option>
			<option value="3">질의응답</option>
		</select>
	</div>
	<div>
		제목
		<input type="text" placeholder="제목을 입력하세요." name="title" />
	</div>
	<div>첨부 이미지</div>
	<div>
		<input id="fileInput" placeholder="이미지를 선택해주세요" type="file" />
	</div>
	</div>

	<div>내용</div>
	<div class="toast-ui-editor">
		<script type="text/x-template"></script>
	</div>
	<button type="submit">작성</button>
</form>


<div class="btns text-center mt-8">
	<button class="hover:underline" type="button" onclick="history.back()">뒤로가기</button>
	<button class="hover:underline ml-8" type="button">
		<a href="list">리스트로 돌아가기</a>
	</button>
</div>

<%@ include file="../common/foot.jspf"%>
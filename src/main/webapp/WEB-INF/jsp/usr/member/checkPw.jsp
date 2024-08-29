<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="CHECKPW"></c:set>

<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<div class="text-center mt-3">
	<form method="POST" action="../member/doCheckPw">
		<div>아이디 ${rq.loginedMember.loginId}</div>
		<div>
			비밀번호
			<input autocomplete="off" type="password" placeholder="비밀번호를 입력해주세요." name="loginPw" />
		</div>
		<button type="submit" class="action-btn">확인</button>
	</form>
</div>

<div class="btns text-center mt-8">
	<button class="btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
</div>

<%@ include file="../common/foot.jspf"%>
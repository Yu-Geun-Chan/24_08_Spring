<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MYPAGE"></c:set>

<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<div class="text-center mt-3">
	<form method="POST" action="../member/doCheckPw">
		<div>가입일</div>
		<div>${rq.loginedMember.regDate}</div>
		<div>아이디</div>
		<div>${rq.loginedMember.loginId}</div>
		<div>이름</div>
		<div>${rq.loginedMember.name}</div>
		<div>닉네임</div>
		<div>${rq.loginedMember.nickname}</div>
		<div>이메일</div>
		<div>${rq.loginedMember.email}</div>
		<div>전화번호</div>
		<div>${rq.loginedMember.cellphoneNum}</div>
		<div>
			<a href="../member/checkPw" class="action-btn">수정</a>
		</div>
	</form>
</div>

<div class="btns text-center mt-8">
	<button class="btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
</div>

<%@ include file="../common/foot.jspf"%>
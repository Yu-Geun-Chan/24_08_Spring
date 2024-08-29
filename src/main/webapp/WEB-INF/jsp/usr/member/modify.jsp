<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="LOGIN"></c:set>

<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<div class="text-center mt-3">
	<form method="POST" action="doLogin">
		<div>가입일 ${rq.loginedMember.regDate}</div>
		<div>
			아이디 ${rq.loginedMember.loginId}
		</div>
		<div>
			새 비밀번호 <input autocomplete="off" type="password" placeholder="새 비밀번호를 입력해주세요." name="loginPw" />
		</div>
		<div>
			비밀번호 확인 <input autocomplete="off" type="password" placeholder="비밀번호 확인을 입력해주세요." name="loginPw" />
		</div>
		<div>
			이름 <input autocomplete="off" type="text" placeholder="이름을 입력해주세요." name="name" value="${rq.loginedMember.name}" />
		</div>
		<div>
			닉네임 <input autocomplete="off" type="text" placeholder="닉네임을 입력해주세요." name="nickname" value="${rq.loginedMember.nickname}"/>
		</div>
		<div>
			이메일 <input autocomplete="off" type="text" placeholder="이메일을 입력해주세요." name="email" value="${rq.loginedMember.email}"/>
		</div>
		<div>
			전화번호 <input autocomplete="off" type="text" placeholder="전화번호를 입력해주세요." name="cellphoneNum" value="${rq.loginedMember.cellphoneNum}"/>
		</div>
		<button class="login" type="submit">수정</button>
	</form>
</div>

<div class="btns text-center mt-8">
	<button class="btn btn-outline" type="button" onclick="history.back()">뒤로가기</button>
</div>

<%@ include file="../common/foot.jspf"%>
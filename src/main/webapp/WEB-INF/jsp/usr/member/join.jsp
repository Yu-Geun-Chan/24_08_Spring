<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="JOIN"></c:set>
<%@ include file="../common/head.jspf"%>

<a href="../home/main">메인 페이지로 </a>

<hr />

<script type="text/javascript">
	function JoinForm__submit(form) {

		let loginId = form.loginId.value.trim();
		let loginPw = form.loginPw.value.trim();
		let loginPwConfirm = form.loginPwConfirm.value.trim();
		let name = form.name.value.trim();
		let nickname = form.nickname.value.trim();
		let cellphoneNum = form.cellphoneNum.value.trim();
		let email = form.email.value.trim();

		if (loginId.length == 0) {
			alert('아이디를 입력해주세요.');
			return;
		}
		if (loginPw.length == 0) {
			alert('비밀번호를 입력해주세요.');
			return;
		}
		if (loginPwConfirm.length == 0) {
			alert('비밀번호 확인을 입력해주세요.');
			return;
		}

		if (loginPw != loginPwConfirm) {
			alert('비밀번호가 일치하지 않습니다.');
			form.loginPw.focus();
			return;
		}

		if (name.length == 0) {
			alert('이름을 입력해주세요.');
			return;
		}
		if (nickname.length == 0) {
			alert('닉네임을 입력해주세요.');
			return;
		}
		if (cellphoneNum.length == 0) {
			alert('휴대폰번호를 입력해주세요.');
			return;
		}
		if (email.length == 0) {
			alert('이메일을 입력해주세요.');
			return;
		}

		form.submit();

	}
</script>

<form class="mt-3" method="POST" action="doJoin" onsubmit="JoinForm__submit(this); return false;">
	<div>
		아이디<input autocomplete="off" type="text" placeholder="아이디를 입력해주세요." name="loginId" />
	</div>
	<div>
		비밀번호<input autocomplete="off" type="text" placeholder="비밀번호를 입력해주세요." name="loginPw" />
	</div>
	<div>
		비밀번호 확인<input autocomplete="off" type="text" placeholder="비밀번호 확인을 입력해주세요." name="loginPwConfirm" />
	</div>
	<div>
		이름<input autocomplete="off" type="text" placeholder="이름을 입력해주세요." name="name" />
	</div>
	<div>
		닉네임<input autocomplete="off" type="text" placeholder="닉네임을 입력해주세요." name="nickname" />
	</div>
	<div>
		휴대폰번호<input autocomplete="off" type="text" placeholder="휴대폰번호를 입력해주세요." name="cellphoneNum" />
	</div>
	<div>
		이메일<input autocomplete="off" type="text" placeholder="이메일을 입력해주세요." name="email" />
	</div>
	<button type="submit">회원가입</button>
</form>


<div class="btns text-center mt-8 mb-8">
	<button class="btn" type="button" onclick="history.back()">뒤로가기</button>
	<button class="btn" type="button">
		<a href="list">리스트로 돌아가기</a>
	</button>
</div>

<%@ include file="../common/foot.jspf"%>
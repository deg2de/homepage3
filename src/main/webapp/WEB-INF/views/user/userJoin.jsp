<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script defer type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script defer type="text/javascript" src="js/userJoin.js"></script>
<link rel="stylesheet" type="text/css" href="css/userJoin.css">
<title>LeeSungBok HOMEPAGE</title>
</head>
<body>
<div class="headerDiv">
	<jsp:include page="../header/header.jsp"/>
</div>
<div class="body_main">
	<div class="alert alert-dark main_title" role="alert">
		<span class="text-black h2 menu_login_subinfo">회원 가입</span>
	</div>
	<div class="input-group input-group-lg userJoin_input_check">
		<div class="input-group-prepend">
			<span class="input-group-text inputGroup-sizing-lg">아이디</span>
		</div>
		<input type="text" id="id" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
		<input type="button" id="checkIdBtn" class="btn btn-secondary userJoin_input_check_btn" onclick="idCheckFunction()" value="CK">
		<input type="hidden" id="checkIdF" value="0">
	</div>
	<div class="input-group input-group-lg userJoin_input">
		<div class="input-group-prepend">
			<span class="input-group-text inputGroup-sizing-lg">비밀번호</span>
		</div>
		<input type="password" id="pw" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" readonly="readonly">
	</div>
	<div class="input-group input-group-lg userJoin_input_check">
		<div class="input-group-prepend">
			<span class="input-group-text inputGroup-sizing-lg">비밀번호확인</span>
		</div>
		<input type="password" id="pw2" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" readonly="readonly">
		<input type="button" id="checkPwBtn" class="btn btn-secondary userJoin_input_check_btn" onclick="pwCheckFunction()" value="CK">
		<input type="hidden" id="checkPwF" value="0">
	</div>
	<div class="input-group input-group-lg userJoin_input">
		<div class="input-group-prepend">
			<span class="input-group-text inputGroup-sizing-lg">이름</span>
		</div>
		<input type="text" id="name" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" readonly="readonly">
	</div>
	<div id="mail_input" class="input-group input-group-lg userJoin_input_check">
		<div class="input-group-prepend">
			<span class="input-group-text inputGroup-sizing-lg">메일주소</span>
		</div>
		<input type="text" id="mailAdd" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" readonly="readonly">
		<input type="button" id="checkMailBtn" class="btn btn-secondary userJoin_input_check_btn" onclick="mailCheckFunction()" value="CK">
		<input type="hidden" id="checkMailF" value="0">
	</div>
	<div class="input-group input-group-lg userJoin_input">
		<div class="input-group-prepend">
			<span class="input-group-text inputGroup-sizing-lg">전화번호</span>
		</div>
		<input type="text" id="phoNum" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" readonly="readonly">
	</div>
	<form action="userJoinSuc" method="get">
		<input type="submit" id="userInfoBtn" class="btn btn-secondary btn-lg btn-block userInfo_btn" onclick="return userJoinSucFunction()" value="회원가입"/>
	</form>
</div>
</body>
</html>
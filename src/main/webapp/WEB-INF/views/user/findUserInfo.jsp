<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script defer type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script defer type="text/javascript" src="js/findUserInfo.js"></script>
<link rel="stylesheet" type="text/css" href="css/findUserInfo.css">
<title>LeeSungBok HOMEPAGE</title>
</head>
<body>
<div class="headerDiv">
	<jsp:include page="../header/header.jsp"/>
</div>
<div class="body_main">
	<div class="alert alert-dark main_title" role="alert">
		<span class="text-black h2 menu_login_subinfo">아이디/비밀번호 찾기</span>
	</div>
	<div class="input-group input-group-lg findUserInfo_input">
		<div class="input-group-prepend">
			<span class="input-group-text inputGroup-sizing-lg">메일주소</span>
		</div>
		<input type="text" id="mailAdd" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
	</div>
	<input type="button" id="findUserInfoBtn" class="btn btn-secondary btn-lg btn-block findUserInfo_btn" onclick="return findUserInfoFunction()" value="아이디/비밀번호 찾기">
</div>
</body>
</html>
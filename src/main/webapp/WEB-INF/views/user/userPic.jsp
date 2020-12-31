<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script defer type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script defer src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script defer src="bootstrap/js/bootstrap.js"></script>
<script defer type="text/javascript" src="js/userPic.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/userPic.css">
<meta charset="UTF-8">
<title>LeeSungBok HOMEPAGE</title>
</head>
<body>
<form id="fileForm" action="userPic" method="post" enctype="multipart/form-data">
	<div class="alert alert-dark user_picTitle" role="alert">
		<span class="text-black h2">사용자 이미지 변경</span>
	</div>
	<div id="picDiv" class="picDiv">
		<img id="user_pic" class="user_pic" src="${loginInfo.picAdd}">
	</div>
	<div class="input-group mb-3 fileUploadBtn">
		<div class="custom-file fileUploadInp">
			<input type="file" class="custom-file-input" id="fileUpload" aria-describedby="inputGroupFileAddon01">
			<label class="custom-file-label" for="fileUpload">Choose file</label>
		</div>
	</div>
	<button type="button" class="btn btn-secondary btn-lg btn-block submitBtn" onclick="return changeImgFunction()">변경하기</button>
</form>
</body>
</html>
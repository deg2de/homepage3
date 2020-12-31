<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script defer type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script defer type="text/javascript" src="js/boardManage.js"></script>
<link rel="stylesheet" type="text/css" href="css/boardManage.css">
<title>LeeSungBok HOMEPAGE</title>
</head>
<body>
<div class="headerDiv">
	<jsp:include page="../header/header.jsp"/>
</div>
<div class="body_main">
	<div class="alert alert-dark main_title" role="alert">
		<span class="text-black h2 menu_login_subinfo">게시판 관리</span>
	</div>
	<div id="boardManageDiv" class="p-3">
		<div id="boardDiv" class="p-3">
			<div id="beforeBoardDiv" class="form-group">
				<label for="beforeBoard">게시판</label>
				<select id="beforeBoard" class="form-control boardSelect" size="18">
					<c:forEach var="boardName" items="${boardInfoDtoList}">
						<option value="${boardName.boardCode}">${boardName.boardName}</option>
					</c:forEach>
				</select>
			</div>
			<div id="boardBtnDiv">
				<button id="boardAddBtn" class="btn btn-light boardBtn" onclick="addBoardFuction()">추가</button>
				<button id="boardDeleteBtn" class="btn btn-light boardBtn" onclick="delBoardFuction()">삭제</button>
			</div>
		</div>
		<div id="typeDiv" class="p-3">
			<div id="boardTypeDiv" class="form-group">
				<label id="boardTypeLabel" for="boardType">게시판 종류</label>
				<select id="boardType" class="form-control boardSelect" size="18"></select>
			</div>
			<div id="boardTypeBtnDiv">
				<button id="boardTypeAddBtn" class="btn btn-light boardBtn" onclick="addBoardTypeFuction()" disabled="disabled">추가</button>
				<button id="boardTypeDeleteBtn" class="btn btn-light boardBtn" onclick="delBoardTypeFuction()" disabled="disabled">삭제</button>
			</div>
		</div>
		<div id="BoardInfoTextDiv">
			※변경 내용은 데이터베이스에 바로 반영됩니다. 삭제 및 수정된 정보는 되돌릴 수 없으니 신중하게 입력해 주세요.
		</div>
	</div>
</div>
</body>
</html>
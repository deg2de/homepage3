<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script defer src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<script defer type="text/javascript" src="js/textWrite.js"></script>
<link rel="stylesheet" type="text/css" href="css/textWrite.css">


<title>LeeSungBok HOMEPAGE</title>
</head>
<body>
<div class="headerDiv">
	<jsp:include page="../header/header.jsp"/>
</div>
<div class="body_main">
	<div class="alert alert-dark main_title" role="alert">
		<span class="text-black h2 menu_login_subinfo">글 작성</span>
	</div>
	<div id="textView">
		<form name="writeForm" method="post" action="textWrite">
		<table id="textViewTable">
			<thead id="textViewHead">
				<tr>
					<td id="textViewBoardNameType" colspan="7">
						<div id="textInputBdDiv" class="textInputBTDiv">
							<label id="textInputBdLabel" for="textInputBd">게시판</label>
							<select id="textInputBd" class="textInputSelect">
								<option selected="selected" value="0">선택해주세요</option>
								<c:forEach var="selectBoard" items="${selectBoardInfoList}">
									<option value="${selectBoard.boardCode}">${selectBoard.boardName}</option>
								</c:forEach>
							</select>
						</div>
						<div id="textInputTypeDiv" class="textInputBTDiv">
							<label id="textInputTypeLabel" for="textInputType">종류</label>
							<select id="textInputType" class="textInputSelect" disabled="disabled"></select>
						</div>
					</td>
				</tr>
				<tr>
					<td id="textViewTitle" colspan="7">
						<div id="textViewTitleDiv">
							<label id="textWriteTitleLabel" for="textWriteTitle">제목</label>
							<input type="text" id="textWriteTitle">
						</div>
					</td>
				</tr>
			</thead>
			<tbody id="textViewBody">
				<tr>
					<td id="textViewDes" colspan="7">
						<div id="textViewDesDiv">
							<textarea id="summernote" name="content"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td id="textViewBtn" colspan="7">
						<div id="textViewBtnDiv">
							<input type="button" class="btn btn-dark" onclick="writeFunction()" value="작성">
							<input type="button" class="btn btn-outline-secondary" onclick="cancelFunction()" value="취소">
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>
</div>
</body>
</html>
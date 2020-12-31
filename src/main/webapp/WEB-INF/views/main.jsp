<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script defer type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script defer type="text/javascript" src="js/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>LeeSungBok HOMEPAGE</title>
</head>
<body>
<div class="headerDiv">
	<jsp:include page="header/header.jsp"/>
</div>
<div class="body_main">
	<div class="navbar navbar-expand-lg navbar-light" style="float:right">
		<form class="form-inline my-2 my-lg-0" action="main" method="post">
			<select class="form-control" name="searchObject">
				<option selected value="${searchObject}">
					<c:if test="${searchObject == \"searchTitle\"}">제목</c:if>
					<c:if test="${searchObject == \"searchTitletext\"}">제목+내용</c:if>
					<c:if test="${searchObject == \"searchBoard\"}">게시판</c:if>
					<c:if test="${searchObject == \"searchType\"}">종류</c:if>
					<c:if test="${searchObject == \"searchName\"}">작성자</c:if>
					<c:if test="${searchObject == \"searchBdTp\"}">게시판-종류</c:if>
				</option>
				<option disabled="disabled" value="noSearch">----------</option>
				<option value="searchTitle">제목</option>
				<option value="searchTitletext">제목+내용</option>
				<option value="searchBoard">게시판</option>
				<option value="searchType">종류</option>
				<option value="searchName">작성자</option>
				<option disabled="disabled" value="searchBdTp">게시판-종류</option>
			</select>
			<input class="form-control mr-sm-2" type="text" placeholder="Search" name="searchText" value="${searchText}">
			<input type="hidden" name="pageno" value="${1}">
			<input type="submit" class="btn btn-dark" value="검색">
		</form>
	</div>
	<div id="textInfoListDiv">
		<table id="textInfoListTable">
			<thead class="textInfoListTableHead">
				<tr>
					<td class="textNo">번호</td>
					<td class="boardName">게시판</td>
					<td class="typeName">종류</td>
					<td class="Writeid">작성자</td>
					<td class="textTitle">제목</td>
					<td class="textWriteDate">작성일</td>
					<td class="textCount">조회수</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="textInfo" items="${textInfoList}">
					<tr class="textInfoListTableBody">
						<td class="textNo">${textInfo.textNo}</td>
						<td class="boardName">${textInfo.boardName}</td>
						<td class="typeName">${textInfo.typeName}</td>
						<td class="Writeid">${textInfo.id}</td>
						<td class="textTitle textTitleBody" title="${textInfo.textTitle}">${textInfo.textTitle}</div></td>
						<td class="textWriteDate">${textInfo.textWriteDate}</td>
						<td class="textCount">${textInfo.textCount}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="nowPage" id="nowPage">
			${pageno} / ${totalpagesize} Page
	</div>
	<div class="page_button">
		<div class="listPageNo">
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-center">
				<c:if test="${pageno != 1}">
					<li class="page-item">
						<form action="main" method="post">
							<input type="hidden" name="pageno" value="${1}">
							<input type="hidden" name="searchObject" value="${searchObject}">
							<input type="hidden" name="searchText" value="${searchText}">
							<input type="submit" value="first" class="btn btn-dark">
						</form>
					</li>
					<li class="page-item">
						<form action="main" method="post">
							<input type="hidden" name="pageno" value="${pageno - 1}">
							<input type="hidden" name="searchObject" value="${searchObject}">
							<input type="hidden" name="searchText" value="${searchText}">
							<input type="submit" value="prev" class="btn btn-dark">
						</form>
					</li>
				</c:if>
				<c:forEach var="listno" begin = "${firstlistpage}" end="${lastlistpage}">
					<li class="page-item">
						<form action="main" method="post">
							<input type="hidden" name="pageno" value="${listno}">
							<input type="hidden" name="searchObject" value="${searchObject}">
							<input type="hidden" name="searchText" value="${searchText}">
							<input type="submit" value="${listno}" class="btn btn-light">
						</form>
					</li>
				</c:forEach>
				<c:if test="${pageno != totalpagesize}">
					<li class="page-item">
						<form action="main" method="post">
							<input type="hidden" name="pageno" value="${pageno + 1}">
							<input type="hidden" name="searchObject" value="${searchObject}">
							<input type="hidden" name="searchText" value="${searchText}">
							<input type="submit" value="next" class="btn btn-dark">
						</form>
					</li>
					<li class="page-item">
						<form action="main" method="post">
							<input type="hidden" name="pageno" value="${totalpagesize}">
							<input type="hidden" name="searchObject" value="${searchObject}">
							<input type="hidden" name="searchText" value="${searchText}">
							<input type="submit" value="last" class="btn btn-dark">
						</form>
					</li>
				</c:if>
				</ul>
			</nav>
		</div>
	</div>
	<div id="textView">
		<table id="textViewTable">
			<thead id="textViewHead">
				<tr>
					<td id="textViewBoardNameType" colspan="6">[${textDesInfo.boardName}]-[${textDesInfo.typeName}]</td>
					<td id="textViewBtn">
					<c:if test="${loginInfo.id != null && loginInfo.userType == 2}">
						<form id="updateForm" name="updateForm" method="get" action="textUpdate">
							<input type="button" id="textViewUpdateBtn" class="btn btn-outline-secondary textViewBtn" onclick="textViewUpdateFunction()" value="수정">
						</form>
						<input type="button" id="textViewDeleteBtn" class="btn btn-outline-secondary textViewBtn" onclick="textViewDeleteFunction()" value="삭제">
					</c:if>
					</td>
				</tr>
				<tr>
					<td id="textViewTitle" colspan="7">
						<div id="textViewTitleDiv" title="${textDesInfo.textTitle}">${textDesInfo.textTitle}</div>
					</td>
				</tr>
				<tr>
					<td id="textViewNo">${textDesInfo.textNo}</td>
					<td id="textViewWriter">${textDesInfo.textWriter}</td>
					<td id="textViewCount">조회수 ${textDesInfo.textCount}</td>
					<td id="textViewWriteDate">작성일 ${textDesInfo.textWriteDate}</td>
					<td id="textViewLastUpDateDate">수정일 ${textDesInfo.textUpdateDate}</td>
					<td id="textViewCommentCount" colspan="2">댓글 ${textDesInfo.commentCount}</td>
				</tr>
			</thead>
			<tbody id="textViewBody">
				<tr>
					<td id="textViewDes" colspan="7">
						<div id="textViewDesDiv">${textDesInfo.textDes}</div>
					</td>
				</tr>
			</tbody>
		</table>
		<c:if test="${loginInfo.id != null}">
		<div id="commentWriteDiv">
			<div id="commentWriteInfo">
				<label for="commentWriteDes">댓글 작성</label>
			</div>
			<div id="commentWriteDes" class="form-group">
				<textarea id="commentWriteTA" class="form-control"></textarea>
			</div>
			<div id="commentWriteBtnDiv">
				<button id="commentWriteBtn" class="btn btn-dark" onclick="cmtWriteFunction()">작성</button>
			</div>
		</div>
		</c:if>
	</div>
</div>
</body>
</html>
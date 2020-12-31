<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script defer type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script defer type="text/javascript" src="js/userManage.js"></script>
<link rel="stylesheet" type="text/css" href="css/userManage.css">
<title>LeeSungBok HOMEPAGE</title>
</head>
<body>
<div class="headerDiv">
	<jsp:include page="../header/header.jsp"/>
</div>
<div class="body_main">
	<div class="alert alert-dark main_title" role="alert">
		<span class="text-black h2 menu_login_subinfo">유저 관리</span>
	</div>
	<div id="userInfoListDiv">
		<div class="searchUserManage navbar navbar-expand-lg navbar-light">
			<form class="form-inline my-2 my-lg-0" action="userManage" method="post">
				<select class="form-control" name="search_object">
					<option selected value="search_name">이름</option>
					<option value="search_id">아이디</option>
				</select>
				<input class="form-control mr-sm-2" type="text" placeholder="Search" name="search_text">
				<input type="hidden" name="pageno" value="${1}">
				<input type="submit" class="btn btn-dark" value="검색">
			</form>
		</div>
		<table id="userInfoListTable">
			<thead class="userInfoListTableHead">
				<tr>
					<td class="userNoTd">번호</td>
					<td class="userIdTd">아이디</td>
					<td class="userNameTd">이름</td>
					<td class="userJoindateTd">가입날짜</td>
					<td class="userBtnTd"></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="userInfo" items="${userInfoManageList}" varStatus="status">
					<tr class="userInfoListTableBody">
						<c:if test="${pageno != 1}">
							<td class="userNoTd">${(pageno * 50 - 50) + status.count}</td>
						</c:if>
						<c:if test="${pageno == 1}">
							<td class="userNoTd">${status.count}</td>
						</c:if>
						<td class="userIdTd">${userInfo.userId}</td>
						<td class="userNameTd">${userInfo.userName}</td>
						<td class="userJoindateTd">${userInfo.joinDate}</td>
						<td class="userBtnTd">
							<input type="button" id="userInfoDeleteBtn" class="btn btn-secondary" onclick="userInfoDeleteFunction('${userInfo.userId}')" value="탈퇴">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="page_button">
		<div class="nowPage" id="nowPage">
			${pageno} / ${totalpagesize} Page
		</div>
		<div class="listPageNo">
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-center">
				<c:if test="${pageno != 1}">
					<li class="page-item">
						<form action="userManage" method="post">
							<input type="hidden" name="pageno" value="${1}">
							<input type="hidden" name="search_object" value="${searchobject}">
							<input type="hidden" name="search_text" value="${searchtext}">
							<input type="submit" value="first" class="btn btn-dark">
						</form>
					</li>
					<li class="page-item">
						<form action="userManage" method="post">
							<input type="hidden" name="pageno" value="${pageno - 1}">
							<input type="hidden" name="search_object" value="${searchobject}">
							<input type="hidden" name="search_text" value="${searchtext}">
							<input type="submit" value="prev" class="btn btn-dark">
						</form>
					</li>
				</c:if>
				<c:forEach var="listno" begin = "${firstlistpage}" end="${lastlistpage}">
					<li class="page-item">
						<form action="userManage" method="post">
							<input type="hidden" name="pageno" value="${listno}">
							<input type="hidden" name="search_object" value="${searchobject}">
							<input type="hidden" name="search_text" value="${searchtext}">
							<input type="submit" value="${listno}" class="btn btn-light">
						</form>
					</li>
				</c:forEach>
				<c:if test="${pageno != totalpagesize}">
					<li class="page-item">
						<form action="userManage" method="post">
							<input type="hidden" name="pageno" value="${pageno + 1}">
							<input type="hidden" name="search_object" value="${searchobject}">
							<input type="hidden" name="search_text" value="${searchtext}">
							<input type="submit" value="next" class="btn btn-dark">
						</form>
					</li>
					<li class="page-item">
						<form action="userManage" method="post">
							<input type="hidden" name="pageno" value="${totalpagesize}">
							<input type="hidden" name="search_object" value="${searchobject}">
							<input type="hidden" name="search_text" value="${searchtext}">
							<input type="submit" value="last" class="btn btn-dark">
						</form>
					</li>
				</c:if>
				</ul>
			</nav>
		</div>
	</div>
</div>
</body>
</html>
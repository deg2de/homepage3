<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<script defer type="text/javascript" src="js/header.js"></script>
<link rel="stylesheet" type="text/css" href="css/header.css">
<link rel="stylesheet" type="text/css" href="css/allBody.css">
</head>
<body>
<div class="header_nav bg-dark">
	<div class="dropdown header_menu_fix">
		<div class="pos-f-t">
			<nav class="navbar navbar-dark bg-dark header_navbar">
				<div class="header_menu_btn">
					<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#header_menu" aria-controls="header_menu"
					aria-expanded="false" aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
				</div>

				<h1 id="header_logo" class="h1 header_logo">LEE SUNGBOK</h1>
			</nav>
			<div class="header_menu collapse" id="header_menu">
				<div class="menu_login_info bg-dark">
					<c:if test="${loginInfo.id != null}">
						<img id="login_pic" class="login_pic" src="${loginInfo.picAdd}"/>
							<div class="text-white">${loginInfo.id}</div>
						<div>
							<div class="text-white">${loginInfo.name}님 어서오세요!</div>
						</div>
					<c:if test="${loginInfo.userType == 2}">
						<div class="user_info_div">
							<form method="get" action="boardManage">
								<div class="form-group user_info_div_form_div">
									<button type="submit" class="btn btn-light login_info_div_btn">게시판 관리</button>
								</div>
							</form>
							<form method="get" action="userManage">
								<div class="form-group user_info_div_form_div">
									<input type="hidden" name="pageno" value="${1}">
									<button type="submit" class="btn btn-light login_info_div_btn">유저 관리</button>
								</div>
							</form>
						</div>
						<div class="user_info_div">
							<form method="get" action="textWrite">
								<div class="form-group user_info_div_form_div">
									<button type="submit" class="btn btn-light login_info_div_btn">글쓰기</button>
								</div>
							</form>
						</div>
					</c:if>
						<div class="user_info_div">
							<form method="get" action="infoChange">
								<div class="form-group user_info_div_form_div">
									<button type="submit" class="btn btn-light login_info_div_btn">정보변경</button>
								</div>
							</form>
							<form method="post" action="logout">
								<div class="form-group user_info_div_form_div">
									<button type="submit" class="btn btn-light login_info_div_btn">로그아웃</button>
								</div>
							</form>
						</div>
					</c:if>
					<c:if test="${loginInfo.id == null}">
						<form method="post" action="login">
							<div class="form-group">
								<label class="text-white" for="id">Login ID</label>
								<input type="text" class="form-control menu_login_input" name="id">
							</div>
							<div class="form-group">
								<label class="text-white" for="pw">Login PW</label>
								<input type="password" class="form-control menu_login_input" name="pw">
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-light menu_login_btn">Login</button>
							</div>
						</form>
						<div class="menu_login_sub">
							<form method="get" action="userJoin">
								<div class="form-group">
									<span class="text-muted menu_login_subinfo">아직 회원이 아니신가요?</span>
									<button type="submit" class="btn btn-light userInfoBtn">회원가입</button>
								</div>
							</form>
							<form method="get" action="findUserInfo">
								<div class="form-group">
									<span class="text-muted menu_login_subinfo">회원 정보를 잊으셨나요?</span>
									<button type="submit" class="btn btn-light userInfoBtn">정보찾기</button>
								</div>
							</form>
						</div>
					</c:if>
					<div class="text-muted">${msg}</div>
				</div>
				<div class="menu_board_div p-4">
					<form class="form-inline my-2 my-lg-0" name="searchForm" action="main" method="post">
						<input type="hidden" id="hidPageno" name="pageno" value="${1}">
						<input type="hidden" id="hidSearchObject" name="searchObject">
						<input type="hidden" id="hidSearchText" name="searchText">
						<ul id="board_main_menu_ul" class="board_main_menu_ul">
						</ul>
					</form>
				</div>
				<div class="bg-dark p-4">
					<span class="text-muted">Web Site Manager : Lee SungBok</span>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
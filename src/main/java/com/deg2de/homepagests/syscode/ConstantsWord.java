package com.deg2de.homepagests.syscode;

/**
 * 클래스명(물리) : 상수 문자형
 * 클래스명(논리) : ConstantsWord.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-17
 * 마지막 수정 날짜 : 2020-11-17
 */
public final class ConstantsWord {
	
	/** 널 = null */
	public static final String NULL= null;
	/** 미입력 = "" */
	public static final String NO_WORD= "";
	/** 공백 = " " */
	public static final String SPACE= " ";
	/** 밑선 = "_" */
	public static final String UNDER_BAR= "_";
	
	/** 페이지명 : 에러 페이지 = "error/error" */
	public static final String ERROR_PAGE= "error/error";
	/** 페이지명 : 메인 페이지 = "main" */
	public static final String MAIN_PAGE= "main";
	/** 페이지명 : 유저 회원 가입 페이지 = "user/userJoin" */
	public static final String USERJOIN_PAGE= "user/userJoin";
	/** 페이지명 : 유저 정보 변경 페이지 = "user/infoChange" */
	public static final String INFOCHANGE_PAGE= "user/infoChange";
	/** 페이지명 : 유저 정보 찾기 페이지 = "user/findUserInfo" */
	public static final String FINDUSERINFO_PAGE= "user/findUserInfo";
	/** 페이지명 : 사용자 이미지 페이지 = "user/userPic" */
	public static final String USERPIC_PAGE= "user/userPic";
	/** 페이지명 : 사용자 관리 페이지 = "user/userManage" */
	public static final String USERMANAGE_PAGE= "user/userManage";
	/** 페이지명 : 게시판 관리 페이지 = "board/boardManage" */
	public static final String BOARD_BOARDMANAGE_PAGE= "board/boardManage";
	/** 페이지명 : 게시글 작성 페이지 = "text/textWrite" */
	public static final String TEXTWRITE_PAGE= "text/textWrite";
	/** 페이지명 : 게시글 수정 페이지 = "text/textUpdate" */
	public static final String TEXTUPDATE_PAGE= "text/textUpdate";
	
	/** "text/html;charset=UTF-8" */
	public static final String TEXT_HTML_CHAR_UTF8= "text/html;charset=UTF-8";
	
	/** 경로 : 경로 구분 = "/" */
	public static final String ADD_HYP= "/";
	/** 경로 : 이미지 = "image/" */
	public static final String IMG_ADD= "image/";
	/** 경로 : 유저 사진 저장 경로 = "/img/userPic/" */
	public static final String IMG_USER_PIC= "/img/userPic/";
	/** 경로 : 유저 사진 저장 경로 = "img\\userPic\\" */
	public static final String IMG_USER_PIC_UNBAR= "img\\\\userPic\\\\";
	/** 경로 : 기본 사진 저장 경로 = "/img/UserPicBasic.png" */
	public static final String IMG_USER_PIC_BASIC= "/img/UserPicBasic.png";
	/** 경로 : 이미지 임시 저장 경로 = "/img/userPic/update/" */
	public static final String IMG_USER_PIC_UPDATE= "/img/userPic/update/";
	/** 경로 : 이미지 저장 경로 = "/img/userPic/upload" */
	public static final String IMG_SAVE_ADD= "/img/userPic/upload";
	/** 경로 : 이미지 저장 경로 = "/img/userPic/upload/" */
	public static final String IMG_SAVE_ADD_BAR= "/img/userPic/upload/";
	/** 파일명 : 기본 사진 = "UserPicBasic.png" */
	public static final String USER_PIC_BASIC= "UserPicBasic.png";
	
	/** 날짜 : 날짜 구분 = "-" */
	public static final String DATE_BAR= "-";
	/** 날짜 : 시간 구분 = ":" */
	public static final String TIME_BAR= ":";
	
	/** 메일 내용 : "<p>인증번호는 " */
	public static final String MAIL_DES_FIRST= "<p>인증번호는 ";
	/** 메일 내용 : " 입니다.</p>" */
	public static final String MAIL_DES_LAST= " 입니다.</p>";
	/** 메일 내용 : "<p>ID는" */
	public static final String FIND_USER_MAIL_DES_ID= "<p>ID는";
	/** 메일 내용 : ", PW는 " */
	public static final String FIND_USER_MAIL_DES_PW= ", PW는 ";
	
	
	/** 에러 시작 구분선  = "=======================error=======================" */
	public static final String ERROR_START_POINT= "=======================error=======================";
	/** 에러 마지막 구분선  = "===================================================" */
	public static final String ERROR_END_POINT= "===================================================";
	
	/** 에러 코드 : 로그인시 아이디, 비밀번호 입력정보 없음 = "ERRORCODE100001" */
	public static final String ERRORCODE100001= "ERRORCODE100001";
	
	/** 메세지 내용  = "입력하신 정보가 없거나 일치하는 정보가 없습니다." */
	public static final String MSG100001= "입력하신 정보가 없거나 일치하는 정보가 없습니다.";
	/** 메세지 내용  = "로그인중입니다. 회원가입을 진행할 수 없습니다." */
	public static final String MSG100002= "로그인중입니다. 회원가입을 진행할 수 없습니다.";
	/** 메세지 내용  = "회원가입을 축하합니다!" */
	public static final String MSG100003= "회원가입을 축하합니다!";
	/** 메세지 내용  = "회원가입에 실패했습니다." */
	public static final String MSG100004= "회원가입에 실패했습니다.";
	/** 메세지 내용  = "해당 아이디를 사용 하시겠습니까?(변경불가)" */
	public static final String MSG100005= "해당 아이디를 사용 하시겠습니까?(변경불가)";
	/** 메세지 내용  = "해당 id는 이미 사용중입니다." */
	public static final String MSG100006= "해당 id는 이미 사용중입니다.";
	/** 메세지 내용  = "해당 메일주소는 이미 사용중입니다." */
	public static final String MSG100007= "해당 메일주소는 이미 사용중입니다.";
	/** 메세지 내용  = "해당 메일주소를 사용할 수 있습니다. 사용하시겠습니까?(변경 불가)" */
	public static final String MSG100008= "해당 메일주소를 사용할 수 있습니다. 사용하시겠습니까?(변경 불가)";
	/** 메세지 내용  = "메일주소를 입력해 주세요." */
	public static final String MSG100009= "메일주소를 입력해 주세요.";
	/** 메세지 내용  = "해당 메일로 인증번호가 전송되었습니다. (인증완료까지 페이지를 유지해 주세요.)" */
	public static final String MSG100010= "해당 메일로 인증번호가 전송되었습니다. (인증완료까지 페이지를 유지해 주세요.)";
	/** 메세지 내용  = "인증코드를 입력해 주세요." */
	public static final String MSG100011= "인증코드를 입력해 주세요.";
	/** 메세지 내용  = "인증번호가 맞지 않습니다." */
	public static final String MSG100012= "인증번호가 맞지 않습니다.";
	/** 메세지 내용  = "정상 확인되었습니다." */
	public static final String MSG100013= "정상 확인되었습니다.";
	/** 메세지 내용  = "비로그인중입니다. 회원정보변경을 진행할 수 없습니다." */
	public static final String MSG100014= "비로그인중입니다. 회원정보변경을 진행할 수 없습니다.";
	/** 메세지 내용  = "회원정보 변경에 실패했습니다." */
	public static final String MSG100015= "회원정보 변경에 실패했습니다.";
	/** 메세지 내용  = "회원정보를 변경했습니다." */
	public static final String MSG100016= "회원정보를 변경했습니다.";
	/** 메세지 내용  = "이미 로그인 중입니다." */
	public static final String MSG100017= "이미 로그인 중입니다.";
	/** 메세지 내용  = "해당하는 메일, 또는 비밀번호가 없습니다." */
	public static final String MSG100018= "해당하는 메일, 또는 비밀번호가 없습니다.";
	/** 메세지 내용  = "해당 메일로 정보를 전송하였습니다. 작성하신 메일에서 확인 바랍니다." */
	public static final String MSG100019= "해당 메일로 정보를 전송하였습니다. 작성하신 메일에서 확인 바랍니다.";
	/** 메세지 내용  = "해당 유저의 탈퇴처리가 실패하였습니다." */
	public static final String MSG100020= "해당 유저의 탈퇴처리가 실패하였습니다.";
	/** 메세지 내용  = "탈퇴되었습니다." */
	public static final String MSG100021= "탈퇴되었습니다.";
	/** 메세지 내용  = "로그인을 해주세요." */
	public static final String MSG100022= "로그인을 해주세요.";
	/** 메세지 내용  = "해당 권한이 없습니다.(관리자 전용)" */
	public static final String MSG100023= "해당 권한이 없습니다.(관리자 전용)";
	/** 메세지 내용  = "이미지 파일 저장에 실패하였습니다." */
	public static final String MSG100024= "이미지 파일 저장에 실패하였습니다.";
	/** 메세지 내용  = "프로필 이미지가 변경되었습니다." */
	public static final String MSG100025= "프로필 이미지가 변경되었습니다.";
	/** 메세지 내용  = "해당 게시판 추가에 실패하였습니다." */
	public static final String MSG200001= "해당 게시판 추가에 실패하였습니다.";
	/** 메세지 내용  = "해당 게시판 추가에 성공하였습니다." */
	public static final String MSG200002= "해당 게시판 추가에 성공하였습니다.";
	/** 메세지 내용  = "해당 게시판 삭제에 실패하였습니다." */
	public static final String MSG200003= "해당 게시판 삭제에 실패하였습니다.";
	/** 메세지 내용  = "해당 게시판 삭제에 성공하였습니다." */
	public static final String MSG200004= "해당 게시판 삭제에 성공하였습니다.";
	/** 메세지 내용  = "게시판 종류를 불러왔습니다." */
	public static final String MSG200005= "게시판 종류를 불러왔습니다.";
	/** 메세지 내용  = "해당 게시판 종류를 불러오지 못했습니다." */
	public static final String MSG200006= "해당 게시판 종류를 불러오지 못했습니다.";
	/** 메세지 내용  = "해당 게시판종류 추가에 실패하였습니다." */
	public static final String MSG200007= "해당 게시판종류 추가에 실패하였습니다.";
	/** 메세지 내용  = "해당 게시판종류 추가에 성공하였습니다." */
	public static final String MSG200008= "해당 게시판종류 추가에 성공하였습니다.";
	/** 메세지 내용  = "해당 게시판종류 삭제에 실패하였습니다." */
	public static final String MSG200009= "해당 게시판종류 삭제에 실패하였습니다.";
	/** 메세지 내용  = "해당 게시판종류 삭제에 성공하였습니다." */
	public static final String MSG200010= "해당 게시판종류 삭제에 성공하였습니다.";
	/** 메세지 내용  = "정상적으로 댓글이 작성되었습니다." */
	public static final String MSG300001= "정상적으로 댓글이 작성되었습니다.";
	/** 메세지 내용  = "로그인 후에 댓글을 작성할 수 있습니다." */
	public static final String MSG300002= "로그인 후에 댓글을 작성할 수 있습니다.";
	/** 메세지 내용  = "정상적으로 댓글이 삭제되었습니다." */
	public static final String MSG300003= "정상적으로 댓글이 삭제되었습니다.";
	/** 메세지 내용  = "삭제 권한이 없습니다." */
	public static final String MSG300004= "삭제 권한이 없습니다.";
	/** 메세지 내용  = "해당 게시글이 삭제되었습니다." */
	public static final String MSG300005= "해당 게시글이 삭제되었습니다.";
	/** 메세지 내용  = "이미지 업로드 처리 도중 오류가 발생하였습니다." */
	public static final String MSG300006= "이미지 업로드 처리 도중 오류가 발생하였습니다.";
	/** 메세지 내용  = "글 내용 저장 처리 도중 오류가 발생했습니다." */
	public static final String MSG300007= "글 내용 저장 처리 도중 오류가 발생했습니다.";
	/** 메세지 내용  = "글을 작성하였습니다. (메인페이지로 이동)" */
	public static final String MSG300008= "글을 작성하였습니다. (메인페이지로 이동)";
	/** 메세지 내용  = "로그인중이 아니거나 글 작성 권한이 없습니다." */
	public static final String MSG300009= "로그인중이 아니거나 글 작성 권한이 없습니다.";
	/** 메세지 내용  = "글 작성을 취소합니다." */
	public static final String MSG300010= "글 작성을 취소합니다.";
	/** 메세지 내용  = "글 작성 권한이 없습니다. 상단 로고를 클릭하여 메인페이지로 이동하여 주세요." */
	public static final String MSG300011= "글 작성 권한이 없습니다. 상단 로고를 클릭하여 메인페이지로 이동하여 주세요.";
	/** 메세지 내용  = "이미지 갱신 처리 도중 오류가 발생하였습니다." */
	public static final String MSG300012= "이미지 갱신 처리 도중 오류가 발생하였습니다.";
	/** 메세지 내용  = "글 내용 저장 처리 도중 오류가 발생했습니다." */
	public static final String MSG300013= "글 내용 저장 처리 도중 오류가 발생했습니다.";
	/** 메세지 내용  = "글을 수정하였습니다. (메인페이지로 이동)" */
	public static final String MSG300014= "글을 수정하였습니다. (메인페이지로 이동)";
	/** 메세지 내용  = "로그인중이 아니거나 글 수정 권한이 없습니다." */
	public static final String MSG300015= "로그인중이 아니거나 글 수정 권한이 없습니다.";
	/** 메세지 내용  = "글 수정을 취소합니다." */
	public static final String MSG300016= "글 수정을 취소합니다.";
	/** 메세지 내용  = "글 수정 권한이 없습니다. 상단 로고를 클릭하여 메인페이지로 이동하여 주세요." */
	public static final String MSG300017= "글 수정 권한이 없습니다. 상단 로고를 클릭하여 메인페이지로 이동하여 주세요.";
	
	
	
}

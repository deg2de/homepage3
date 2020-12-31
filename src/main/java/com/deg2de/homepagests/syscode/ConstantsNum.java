package com.deg2de.homepagests.syscode;

/**
 * 클래스명(물리) : 상수 정수형
 * 클래스명(논리) : ConstantsNum.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-17
 * 마지막 수정 날짜 : 2020-11-17
 */
public final class ConstantsNum {
	
	/** 페이지 번호 : 1 */
	public static final int PAGE_NO_ONE = 1;
	/** 페이지당 출력 게시글 수 : 5 */
	public static final int VIEW_TEXT_LIST_SIZE = 5;
	/** 표시할 페이지 버튼 수 : 10 */
	public static final int VIEW_PAGE_BTN_CNT = 10;
	/** 총 페이지 수  : 0 */
	public static final int TOTAL_PAGE_SIZE_ZERO = 0;
	/** 첫 페이지 번호 : 1 */
	public static final int FIRST_LIST_PAGE_ONE = 1;
	/** 마지막 페이지 번호 : 1 */
	public static final int LAST_LIST_PAGE_ONE = 1;
	/** 댓글 개수 : 0 */
	public static final int COMMENT_COUNT_ZERO = 0;
	/** 게시글 번호 : 0 */
	public static final int TEXT_NO_ZERO = 0;
	/** 검색 단어 길이 : 2 */
	public static final int SEARCH_TEXT_LEN_TWO = 2;
	/** 검색 단어의 첫번째 단어 : 0 */
	public static final int SEARCH_TEXT_FST_WORD = 0;
	/** 검색 단어의 두번째 단어 : 1 */
	public static final int SEARCH_TEXT_SEC_WORD = 1;
	/** 검색 게시글의 개수 : 0 */
	public static final int SEARCH_TEXT_SIZE_ZERO = 0;
	
	/** 댓글 페이지 번호 : 1 */
	public static final int COMMENT_PAGE_NO_ONE = 1;
	/** 댓글 수 : 1 */
	public static final int COMMENT_COUNT_ONE = 1;
	/** 댓글 수 : 10 */
	public static final int COMMENT_COUNT_TEN = 10;
	
	/** 유저 관리 페이지당 데이터 갯수 : 50 */
	public static final int USER_MANAGE_DATA_50 = 50;
	
	/** 이미지 개수 : 0 */
	public static final int IMAGE_SIZE_ZERO = 0;
	
	/** 사용자 타입 (일반 : 0) */
	public static final int USER_TYPE_ZERO = 0;
	/** 사용자 타입 (특수 : 1) */
	public static final int USER_TYPE_ONE = 1;
	/** 사용자 타입 (관리자 : 2) */
	public static final int USER_TYPE_TWO = 2;
	
	/** 처리 구분용 플래그 (처리 종료 : 0) */
	public static final int PRO_CHK_FLG_ZERO = 0;
	/** 처리 구분용 플래그 (처리 필요 : 1) */
	public static final int PRO_CHK_FLG_ONE = 1;
	/** 처리 확인용 플래그 (정상 : 0) */
	public static final int PRO_CHK_FLG_SUCESS = 0;
	/** 처리 확인용 플래그 (비정상 : 1) */
	public static final int PRO_CHK_FLG_FAIL = 1;
	/** 일치 확인용 플래그 (일치 : 0) */
	public static final int SAME_CHK_FLG_ZERO = 0;
	/** 일치 확인용 플래그 (비일치 : 1) */
	public static final int SAME_CHK_FLG_ONE = 1;
	/** 삭제 확인용 플래그 (삭제 : 0) */
	public static final int DELETE_CHK_FLG_ZERO = 0;
	/** 삭제 확인용 플래그 (비삭제 : 1) */
	public static final int DELETE_CHK_FLG_ONE = 1;
	/** 추가 댓글 존재 유무 플래그 (있음 : 0) */
	public static final int AFTER_COMMENT_FLG_ZERO = 0;
	/** 추가 댓글 존재 유무 플래그 (없음 : 1) */
	public static final int AFTER_COMMENT_FLG_ONE = 1;
	/** 본인 작성 댓글 확인 플래그 (본인 : 0) */
	public static final int COMMENT_WTR_CHK_FLG_ZERO = 0;
	/** 본인 작성 댓글 확인 유무 플래그 (타인 : 1) */
	public static final int COMMENT_WTR_CHK_FLG_ONE = 1;
	/** 로그인 확인용 플래그 (로그인 : 0) */
	public static final int LOGIN_CHK_FLG_ZERO = 0;
	/** 로그인 확인용 플래그 (비로그인 : 1) */
	public static final int LOGIN_CHK_FLG_ONE = 1;
	/** 관리자 확인용 플래그 (관리자 : 0) */
	public static final int MANAGER_CHK_FLG_ZERO = 0;
	/** 관리자 확인용 플래그 (비관리자 : 1) */
	public static final int MANAGER_CHK_FLG_ONE = 1;
}

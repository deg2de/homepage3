package com.deg2de.homepagests.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deg2de.homepagests.dto.BoardInfoDto;
import com.deg2de.homepagests.dto.BoardTypeInfoDto;

/**
 * 클래스명(물리) : 게시판 정보 서비스
 * 클래스명(논리) : BoardInfoServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-24
 * 마지막 수정 날짜 : 2020-11-24
 * 
 * 게시판 정보 서비스
 */
public interface BoardInfoService {
	
	/**
	 * 메소드명(물리) : 게시판 정보 취득
	 * 메소드명(논리) : selectBoardTitleInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @return List<BoardInfoDto> : 게시판 정보DTO 리스트
	 * 
	 * 모든 게시판의 정보를 취득하여 게시판 정보DTO 리스트로 반환한다.
	 */
	public abstract List<BoardInfoDto> selectBoardTitleInfo() throws Exception ;
	
	/**
	 * 메소드명(물리) : 게시판 정보 조회
	 * 메소드명(논리) : selectBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 게시판 정보를 조회한다.
	 */
	public abstract void selectBoard(HttpServletRequest request) throws Exception;
	
	/**
	 * 메소드명(물리) : 게시판 코드를 조건으로 게시판 취득
	 * 메소드명(논리) : selectBoardToCode
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardCode : 게시판 코드
	 * @return BoardInfoDto : 게시판 정보DTO
	 * @throws Exception 
	 * 
	 * 게시판 코드를 조건으로 게시판을 취득하는 처리
	 */
	public abstract BoardInfoDto selectBoardToCode(int boardCode) throws Exception;
	
	/**
	 * 메소드명(물리) : 게시판 코드를 조건으로 게시판 종류 취득
	 * 메소드명(논리) : selectBoardTypeInfoBc
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardCode : 게시판 코드
	 * @return List<BoardTypeInfoDto> : 게시판 종류 정보DTO 리스트
	 * @throws Exception 
	 * 
	 * 게시판 코드를 조건으로 게시판 종류를 취득하는 처리
	 */
	public abstract List<BoardTypeInfoDto> selectBoardTypeInfoBc(int boardCode) throws Exception;
	
	/**
	 * 메소드명(물리) : 게시판 추가
	 * 메소드명(논리) : createBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param String boardName : 게시판 이름
	 * @return int : 반환값 설정 플래그
	 * 
	 * 게시판을 추가하는 작업
	 */
	public abstract int createBoard(String boardName);
	
	/**
	 * 메소드명(물리) : 게시판 코드를 조건으로 게시판 삭제
	 * 메소드명(논리) : deleteBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardCode : 게시판 코드
	 * @return int : 반환값 설정 플래그
	 * 
	 * 게시판 코드를 조건으로 게시판 삭제하는 처리
	 */
	public abstract int deleteBoard(int boardCode);
	
	/**
	 * 메소드명(물리) : 게시판 종류 생성
	 * 메소드명(논리) : createBoardType
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardCode : 게시판 코드
	 * @param String boardTypeName : 게시판 종류 이름
	 * @return int : 반환값 설정 플래그
	 * 
	 * 게시판 종류를 생성한다.
	 */
	public abstract int createBoardType(int boardCode, String boardTypeName);
	
	/**
	 * 메소드명(물리) : 게시판 종류 삭제
	 * 메소드명(논리) : deleteBoardType
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardTypeCode : 게시판 종류 코드
	 * @return int : 반환값 설정 플래그
	 * 
	 * 게시판 종류를 삭제하는 처리
	 */
	public abstract int deleteBoardType(int boardTypeCode);
	
	/**
	 * 메소드명(물리) : 헤더용 게시판 조회 작업
	 * 메소드명(논리) : headerBoardList
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @return HashMap<String, List<String>> : 출력용 게시판 맵
	 * 
	 * 헤더에 표시될 게시판 메뉴를 조회한다.
	 */
	public abstract HashMap<String, List<String>> headerBoardList() throws Exception;
}

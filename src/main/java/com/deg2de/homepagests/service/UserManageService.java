package com.deg2de.homepagests.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deg2de.homepagests.dto.UserInfoManageDto;
import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 유저 관리 서비스
 * 클래스명(논리) : UserManageService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-21
 * 마지막 수정 날짜 : 2020-11-22
 * 
 * 전체 유저의 정보를 관리하는 서비스
 */
public interface UserManageService {
	/**
	 * 메소드명(물리) : 관리용 유저 정보 취득
	 * 메소드명(논리) : selectAllUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @return List<UserInfoManageDto> : 관리용 유저정보 취득용 DTO 리스트
	 * @throws Exception
	 * 
	 * 관리용 전체 유저 정보를 취득한다.
	 */
	public abstract List<UserInfoManageDto> selectAllUserInfo() throws Exception;
	
	/**
	 * 메소드명(물리) : 관리용 유저 정보 취득 (조건 : 아이디)
	 * 메소드명(논리) : selectUserInfoId
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @return List<UserInfoManageDto> : 관리용 유저정보 취득용 DTO 리스트
	 * @throws Exception
	 * 
	 * 아이디를 조건으로 관리용 유저 정보를 취득한다.
	 */
	public abstract List<UserInfoManageDto> selectUserInfoId(String id) throws Exception ;
	
	/**
	 * 메소드명(물리) : 관리용 유저 정보 취득 (조건 : 작성자)
	 * 메소드명(논리) : selectUserInfoName
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @return List<UserInfoManageDto> : 관리용 유저정보 취득용 DTO 리스트
	 * @throws Exception
	 * 
	 * 작성자를 조건으로 관리용 유저 정보를 취득한다.
	 */
	public abstract List<UserInfoManageDto> selectUserInfoName(String name) throws Exception ;
	
	/**
	 * 메소드명(물리) : 유저 정보 삭제 처리 (조건 : 아이디)
	 * 메소드명(논리) : deleteUserId
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-22
	 * 마지막 수정 날짜 : 2020-11-22
	 * 
	 * @param String id : 아이디
	 * @return JsonObject : 출력용 JSON 데이터
	 * 
	 * 아이디를 조건으로 유저 정보를 검색하여 삭제처리를 진행한다.
	 */
	public abstract JsonObject deleteUserId(String id);
	
	/**
	 * 메소드명(물리) : 유저 정보 페이지 처리
	 * 메소드명(논리) : userInfoPagePro
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param List<UserInfoManageDto> userInfoManageDtoList : 관리용 유저정보 취득용 DTO 리스트
	 * @param int pageNo : 페이지 번호
	 * @param int dataSize : 표시할 데이터 개수
	 * @return String : 사용자 관리 페이지
	 * 
	 * 페이지에 표시할 유저 정보를 가공한다.
	 */
	public abstract String userInfoPagePro(HttpServletRequest request, List<UserInfoManageDto> userInfoManageDtoList,
			int pageNo, int dataSize);
}

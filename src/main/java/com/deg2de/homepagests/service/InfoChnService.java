package com.deg2de.homepagests.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 정보 변경 서비스
 * 클래스명(논리) : InfoChnService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-20
 * 마지막 수정 날짜 : 2020-11-20
 * 
 * 사용자의 개인 정보를 변경하는 처리를 진행하는 서비스
 */
public interface InfoChnService {

	/**
	 * 메소드명(물리) : 유저 정보 취득
	 * 메소드명(논리) : selectUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param String id : 로그인 중인 아이디
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 로그인중인 아이디를 조건으로 유저 정보를 취득한 후 결과 플래그를 반환한다.
	 */
	public abstract int selectUserInfo(String id, HttpServletRequest request) throws Exception;

	/**
	 * 메소드명(물리) : 유저 정보 변경
	 * 메소드명(논리) : updateUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param Map<String, Object> jsonMap : 입력 JSON값 저장 MAP
	 * @return JsonObject : 출력용 JSON 데이터
	 * @throws Exception
	 * 
	 * 입력 받은 유저 정보로 수정하는 작업을 진행한다.
	 */
	public abstract JsonObject updateUserInfo(Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 메소드명(물리) : 사용자 사진 주소 갱신
	 * 메소드명(논리) : updateUserPicAdd
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-23
	 * 마지막 수정 날짜 : 2020-11-23
	 * 
	 * @param String id : 사용자 아이디
	 * @param String fileName : 파일 이름
	 * @return int : 반환값 설정 플래그
	 * 
	 * 사용자의 이미지 파일을 갱신한다.
	 */
	public abstract int updateUserPicAdd(String id, String fileName);
}

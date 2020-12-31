package com.deg2de.homepagests.service;

import java.util.Map;

import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 회원 가입 서비스
 * 클래스명(논리) : UserJoinService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-19
 * 마지막 수정 날짜 : 2020-11-19
 * 
 * 회원가입 관련 처리를 진행하는 서비스
 */
public interface UserJoinService {
	
	/**
	 * 메소드명(물리) : 아이디 중복확인
	 * 메소드명(논리) : selectUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String id : 아이디
	 * @return JsonObject : 출력용 JSON 데이터
	 * @throws Exception
	 * 
	 * ID를 조건으로 유저 정보를 조회하여 ID 중복 여부를 확인한다.
	 */
	public abstract JsonObject selectUserInfo(String id) throws Exception;

	/**
	 * 메소드명(물리) : 메일 중복확인
	 * 메소드명(논리) : selectCheckMail
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String mailAdd : 메일 주소
	 * @return JsonObject : 출력용 JSON 데이터
	 * @throws Exception
	 * 
	 * 메일을 조건으로 유저 정보를 조회하여 메일 중복 여부를 확인한다.
	 */
	public abstract JsonObject selectCheckMail(String mailAdd) throws Exception;

	/**
	 * 
	 * 메소드명(물리) : 유저 정보 입력
	 * 메소드명(논리) : userInfoInsert
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param Map<String, Object> jsonMap : 입력 JSON값 저장 MAP
	 * @return JsonObject : 출력용 JSON 데이터
	 */
	public abstract JsonObject userInfoInsert(Map<String, Object> jsonMap);
	
	/**
	 * 메소드명(물리) : 메일 전송
	 * 메소드명(논리) : mailTrans
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String mailAdd : 메일 주소
	 * @return JsonObject : 출력용 JSON 데이터
	 * @throws Exception
	 * 
	 * 메일 전송처리를 진행한다.
	 */
	public abstract JsonObject mailTrans(String mailAdd) throws Exception;
	
	/**
	 * 메소드명(물리) : 인증 코드 확인 처리
	 * 메소드명(논리) : authCodeChk
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param int authCode : 입력 인증코드
	 * @param int authCode : 확인용 인증코드
	 * @return JsonObject : 출력용 JSON 데이터
	 * @throws Exception
	 * 
	 * 메일을 조건으로 유저 정보를 조회하여 메일 중복 여부를 확인한다.
	 */
	public abstract JsonObject authCodeChk(int authCode, int seAuthCode);
}

package com.deg2de.homepagests.service;

import java.util.Map;

import com.google.gson.JsonObject;

/**
 * 
 * 클래스명(물리) : 유저 정보 찾기 서비스
 * 클래스명(논리) : FindUserInfoService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-20
 * 마지막 수정 날짜 : 2020-11-20
 * 
 * 유저 정보를 찾는 서비스
 */
public interface FindUserInfoService {
	
	/**
	 * 메소드명(물리) : 비밀번호 변경 전송
	 * 메소드명(논리) : pwChangeTrans
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param Map<String, Object> jsonMap : 입력 JSON값 저장 MAP
	 * @return JsonObject : 출력용 JSON 데이터
	 * @throws Exception
	 * 
	 * 메일 주소를 조건으로 유저 정보를 취득 후 비밀번호를 변경하여 해당 유저의 메일로 임시 비밀번호를 전송한다.
	 */
	public abstract JsonObject pwChangeTrans(Map<String, Object> jsonMap) throws Exception;
}
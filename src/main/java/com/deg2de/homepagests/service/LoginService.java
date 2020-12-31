package com.deg2de.homepagests.service;

import com.deg2de.homepagests.dto.SessionInfoDto;

/**
 * 클래스명(물리) : 로그인 서비스
 * 클래스명(논리) : LoginService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-19
 * 마지막 수정 날짜 : 2020-11-19
 * 
 * 로그인 관련 처리를 담당하는 서비스
 */
public interface LoginService {

	/**
	 * 메소드명(물리) : 로그인 체크
	 * 메소드명(논리) : lgnChk
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String id : 아이디
	 * @param String pw : 비밀번호
	 * @param String contextPathStr : 컨테스트 주소
	 * @return SessionInfoDto : 세션 정보DTO
	 * @throws Exception 
	 * 
	 * 입력받은 로그인 정보를 확인 후 로그인에 필요한 처리를 시행한다.
	 */
	public abstract SessionInfoDto lgnChk(String id, String pw, String contextPathStr) throws Exception;

}

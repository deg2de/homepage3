package com.deg2de.homepagests.service;

/**
 * 클래스명(물리) : 로그아웃 서비스
 * 클래스명(논리) : LogoutService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-19
 * 마지막 수정 날짜 : 2020-11-19
 * 
 * 로그아웃 관련 처리를 담당하는 서비스
 */
public interface LogoutService {

	/**
	 * 메소드명(물리) : 이미지 삭제
	 * 메소드명(논리) : imageDelete
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String folderAdd : 폴더 주소
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 이미지가 저장되어 있는 폴더 및 해당 이미지를 삭제한다.
	 */
	public abstract void imageDelete(String folderAdd) throws Exception;
}

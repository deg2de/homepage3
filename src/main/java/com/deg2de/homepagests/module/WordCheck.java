package com.deg2de.homepagests.module;

/**
 * 클래스명(물리) : 문자 체크
 * 클래스명(논리) : WordCheck.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-19
 * 마지막 수정 날짜 : 2020-11-19
 * 
 * 문자 체크에 관한 공통 부품
 */
public class WordCheck {

	/**
	 * 메소드명(물리) : 널 체크 (미입력 확인)
	 * 메소드명(논리) : nullCheck
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String str : 확인할 문자열
	 * @return boolean : 확인 결과 (true : 문자열 없음, false : 문자열 있음)
	 * 
	 * 문자열의 NULL체크를 한다.
	 */
	public boolean nullCheck(String str) {
		if(str == null || str == "" || str.isEmpty() || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}
}

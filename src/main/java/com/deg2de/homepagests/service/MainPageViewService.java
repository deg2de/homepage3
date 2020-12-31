package com.deg2de.homepagests.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 클래스명(물리) : 메인 페이지 뷰 서비스
 * 클래스명(논리) : MainPageViewService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-17
 * 마지막 수정 날짜 : 2020-11-17
 * 
 * 메인 페이지 출력처리를 담당하는 클래스
 */
public interface MainPageViewService {

	/**
	 * 메소드명(물리) : 메인 페이지 뷰 처리 (첫 페이지)
	 * 메소드명(논리) : mainPageViewPro
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-17
	 * 마지막 수정 날짜 : 2020-11-17
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 메인 페이지
	 * @throws Exception 
	 * 
	 * 메인 페이지 출력에 필요한 정보를 설정한 후 메인 페이지로 이동한다.
	 */
	public abstract String mainPageViewPro(HttpServletRequest request) throws Exception;
	
	/**
	 * 메소드명(물리) : 메인 페이지 뷰 처리 (첫 페이지 외)
	 * 메소드명(논리) : mainPageViewProAtr
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * 메인 페이지 출력에 필요한 정보를 설정한 후 메인 페이지로 이동한다.
	 */
	public abstract String mainPageViewProAtr(HttpServletRequest request) throws Exception;
}

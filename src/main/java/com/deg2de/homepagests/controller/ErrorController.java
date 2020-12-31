package com.deg2de.homepagests.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deg2de.homepagests.syscode.ConstantsWord;

/**
 * 클래스명(물리) : 에러 컨트롤러
 * 클래스명(논리) : ErrorController.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-17
 * 마지막 수정 날짜 : 2020-11-17
 * 
 * 에러 페이지에 관한 처리를 담당하는 컨트롤러
 */
@Controller
public class ErrorController {
	
	/**
	 * 메소드명(물리) : GET형 에러 페이지
	 * 메소드명(논리) : getErrorPage
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-17
	 * 마지막 수정 날짜 : 2020-11-17
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @return String : 에러 페이지
	 * 
	 * GET방식 "error"요청에 대해서 에러 페이지를 출력하는 메소드
	 */
	@RequestMapping(value={"/error"}, method = RequestMethod.GET)
	public String getErrorPage(HttpServletRequest request, HttpServletResponse response) {
		
		// 에러 페이지로 이동한다.
		return ConstantsWord.ERROR_PAGE;
	}
}

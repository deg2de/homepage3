package com.deg2de.homepagests.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.deg2de.homepagests.service.MainPageViewServiceImpl;
import com.deg2de.homepagests.syscode.ConstantsWord;

/**
 * 클래스명(물리) : 에러 어드바이스
 * 클래스명(논리) : ErrorAdvice.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-17
 * 마지막 수정 날짜 : 2020-11-17
 * 
 * 에러에 관한 처리를 담당하는 어드바이스
 */
@ControllerAdvice
public class ErrorAdvice {
	
	/** 메인 페이지 뷰 서비스 도구 선언 */
	@Autowired
	private MainPageViewServiceImpl mainPageViewServiceImpl;
	
	/**
	 * 메소드명(물리) : 예외 에러 핸들러
	 * 메소드명(논리) : expErrorHdr
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-17
	 * 마지막 수정 날짜 : 2020-11-17
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param Exception e : Exception 예외
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 예외가 발생할 경우 모두 에러페이지로 이동하도록 처리하는 메소드
	 */
	@ExceptionHandler(Exception.class)
	public String expErrorHdr(HttpServletRequest request, Exception e) throws Exception {
		
		// 에러 메세지 코드
		String errMsgCode = e.getMessage();
		
		// 에러 코드 : 로그인시 아이디, 비밀번호 입력정보 없음 = "ERRORCODE100001"
		if(ConstantsWord.ERRORCODE100001.equals(errMsgCode.toString())) {
			// "입력하신 정보가 없거나 일치하는 정보가 없습니다." 메세지 처리
			request.setAttribute("msg", ConstantsWord.MSG100001);
			
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}
		
		// 에러페이지로 이동한다.
		return ConstantsWord.ERROR_PAGE;
	}
}

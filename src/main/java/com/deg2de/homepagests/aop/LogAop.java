package com.deg2de.homepagests.aop;

///**
// * 클래스명(물리) : 로그 AOP
// * 클래스명(논리) : LogAop.java
// * 
// * 작성자 : 이성복
// * 최초 작성 날짜 : 2020-12-21
// * 마지막 수정 날짜 : 2020-12-21
// * 
// * 기능들에 대한 시작, 종료를 표시하는 로그 공통기능을 가지고 있는 클래스
// */
//@Aspect
public class LogAop {
	
//	// 컨트롤러 로그출력 포인트컷
//	@Pointcut("within(com.deg2de.homepagests.controller.*)")
//	private void pointcutController() {}
	
//	// 서비스 로그출력 포인트컷
//	@Pointcut("within(com.deg2de.homepagests.service.*)")
//	private void pointcutService() {}
	
//	/**
//	 * 메소드명(물리) : 로그 AOP 컨트롤러
//	 * 메소드명(논리) : logAopController
//	 * 
//	 * 작성자 : 이성복
//	 * 최초 작성 날짜 : 2020-12-21
//	 * 마지막 수정 날짜 : 2020-12-21
//	 * 
//	 * @param ProceedingJoinPoint joinPoint : 조인포인트
//	 * @return Object : 핵심기능
//	 * @throws Throwable
//	 * 
//	 * 컨트롤러 시작과 끝의 로그를 표시한다.
//	 */
//	@Around("pointcutController()")
//	public Object logAopController(ProceedingJoinPoint joinPoint) throws Throwable {
//		
//		String signatureStr = joinPoint.getSignature().toShortString();
//		System.out.println("------ Start Controller : " + signatureStr);
//		
//		try {
//			Object obj = joinPoint.proceed();
//			return obj;
//		} finally {
//			System.out.println("------ End Controller : " + signatureStr);
//		}
//		
//	}
	
//	/**
//	 * 메소드명(물리) : 로그 AOP 서비스
//	 * 메소드명(논리) : logAopService
//	 * 
//	 * 작성자 : 이성복
//	 * 최초 작성 날짜 : 2020-12-21
//	 * 마지막 수정 날짜 : 2020-12-21
//	 * 
//	 * @param ProceedingJoinPoint joinPoint : 조인포인트
//	 * @return Object : 핵심기능
//	 * @throws Throwable
//	 * 
//	 * 서비스 시작과 끝의 로그를 표시한다.
//	 */
//	@Around("pointcutService()")
//	public Object logAopService(ProceedingJoinPoint joinPoint) throws Throwable {
//		
//		String signatureStr = joinPoint.getSignature().toShortString();
//		System.out.println("------------ Start Service : " + signatureStr);
//		
//		try {
//			Object obj = joinPoint.proceed();
//			return obj;
//		} finally {
//			System.out.println("------------ End Service : " + signatureStr);
//		}
//		
//	}
}

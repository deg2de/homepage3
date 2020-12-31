package com.deg2de.homepagests.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.deg2de.homepagests.dao.UserInfoIDao;
import com.deg2de.homepagests.dto.SessionInfoDto;
import com.deg2de.homepagests.dto.UserInfoDto;
import com.deg2de.homepagests.module.WordCheck;
import com.deg2de.homepagests.syscode.ConstantsWord;

/**
 * 클래스명(물리) : 로그인 서비스 도구
 * 클래스명(논리) : LoginServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-19
 * 마지막 수정 날짜 : 2020-11-19
 * 
 * 로그인 관련 처리를 담당하는 서비스 클래스
 */
@Service("Login")
public class LoginServiceImpl implements LoginService{

	/** SQL세션 선언 */
	@Autowired
	private SqlSession sqlSession;
	/** 비밀번호 인코더 선언 */
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	/** 공통부품.문자체크 */
	private WordCheck wordCheck = new WordCheck();

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
	@Override
	public SessionInfoDto lgnChk(String id, String pw, String contextPathStr) throws Exception{
		
		// "로그인 정보 확인" 결과 플래그를 "로그인 정보 확인 플래그"에 저장한다.
		UserInfoDto userInfoDto = loginInfoChk(id, pw);
		
		// "유저 정보 설정"으로 로그인에 필요한 유저 정보를 설정한다.
		SessionInfoDto sessionInfoDto = setUserInfo(userInfoDto, contextPathStr);
		
		return sessionInfoDto;
	}
	
	/**
	 * 메소드명(물리) : 로그인 정보 확인
	 * 메소드명(논리) : loginInfoChk
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String id : 아이디
	 * @param String pw : 비밀번호
	 * @return UserInfoDto : 유저 정보DTO
	 * @throws Exception
	 * 
	 * 입력받은 아이디, 비밀번호를 확인한 후 유저 정보DTO를 반환한다.
	 */
	private UserInfoDto loginInfoChk(String id, String pw) throws Exception {
		
		// 유저 정보DTO
		UserInfoDto userInfoDto = new UserInfoDto();
		
		// 입력 아이디, 비밀번호 미입력 체크
		// 아이디, 비밀번호 둘 중 하나라도 문자열이 비어 있는지 확인한다.
		if (wordCheck.nullCheck(id) || wordCheck.nullCheck(pw)) {
			// 아이디, 비밀번호 둘 중 하나라도 문자열이 비어 있을 경우 예외를 발생시킨다.
			// 에러 코드 : 로그인시 아이디, 비밀번호 입력정보 없음 = "ERRORCODE100001" 
			throw new Exception(ConstantsWord.ERRORCODE100001);
		}
		
		// 입력파라미터.아이디를 조건으로 DB로부터 유저 정보를 조회한다.
		// 해당 정보가 조회 될 경우 조회된 정보를"유저 정보DTO"에 저장한다.
		try {
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			userInfoDto = userInfoIDao.selectId(id);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// "유저 정보DTO"에 DB로부터 취득한 정보가 있는 경우
		// "유저 정보DTO.비밀번호" 존재 여부 확인 후 비밀번호 일치 여부를 확인한다.
		if (!(userInfoDto != null && userInfoDto.getPw() != ConstantsWord.NULL)) {
			// "유저 정보DTO"나 "유저 정보DTO.비밀번호"중 하나라도  NULL인 경우 예외를 발생시킨다.
			// 에러 코드 : 로그인시 아이디, 비밀번호 입력정보 없음 = "ERRORCODE100001" 
			throw new Exception(ConstantsWord.ERRORCODE100001);
		}
		
		if (!(pwdEncoder.matches(pw, userInfoDto.getPw()))) {
			// 유저정보의 비밀번호 값과 입력한 비밀번호 값이 일치하지 않는 경우 예외를 발생시킨다.
			// 에러 코드 : 로그인시 아이디, 비밀번호 입력정보 없음 = "ERRORCODE100001" 
			throw new Exception(ConstantsWord.ERRORCODE100001);
		}
		
		// 모든 처리가 정상의 경우
		// 유저정보의 비밀번호 값과 입력한 비밀번호 값이 일치하는 경우
		// "유저 정보DTO"를 반환한다.
		return userInfoDto;
	}
	
	/**
	 * 메소드명(물리) : 유저 정보 설정
	 * 메소드명(논리) : setUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param UserInfoDto userInfoDto : 유저 정보DTO
	 * @param String contextPathStr : 컨테스트 주소
	 * @return SessionInfoDto : 세션 정보DTO
	 * 
	 * 로그인에 필요한 세션정보를 설정한다.
	 */
	private SessionInfoDto setUserInfo(UserInfoDto userInfoDto, String contextPathStr) {
		
		// 세션 정보DTO
		SessionInfoDto sessionInfoDto = new SessionInfoDto();
		
		// 기본 사진 주소
		String basicPicAddStr = null;
		
		// 로그인에 필요한 유저 정보를 "세션 정보DTO"에 저장한다.
		// 세션 아이디
		sessionInfoDto.setId(userInfoDto.getId());
		// 세션 이름
		sessionInfoDto.setName(userInfoDto.getName());
		// 세션 사용자 타입
		sessionInfoDto.setUserType(userInfoDto.getUserType());
		
		// 사진 주소 설정 처리
		StringBuilder basicPicAdd = new StringBuilder();
		basicPicAdd.append(contextPathStr);
		if(!wordCheck.nullCheck(userInfoDto.getPicAdd())) {
			// 사진 주소의 값이 있는 경우 해당 값을 설정해 준다.
			basicPicAdd.append(ConstantsWord.IMG_USER_PIC);
			basicPicAdd.append(userInfoDto.getPicAdd());
			basicPicAddStr = basicPicAdd.toString();
		} else {
			// 사진 주소의 값이 없는 경우 기본 사진 주소값을 설정한다.
			basicPicAdd.append(ConstantsWord.IMG_USER_PIC_BASIC);
			basicPicAddStr = basicPicAdd.toString();
		}
		// 세션 사진 주소
		sessionInfoDto.setPicAdd(basicPicAddStr);
		
		return sessionInfoDto;
		
	}
}

package com.deg2de.homepagests.service;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.deg2de.homepagests.dao.UserInfoIDao;
import com.deg2de.homepagests.dto.UserInfoDto;
import com.deg2de.homepagests.module.WordCheck;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;
import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 회원 가입 서비스 도구
 * 클래스명(논리) : UserJoinServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-19
 * 마지막 수정 날짜 : 2020-11-19
 * 
 * 회원가입 관련 처리를 진행하는 서비스 클래스
 */
@Service("UserJoin")
public class UserJoinServiceImpl implements UserJoinService {

	/** SQL세션 선언 */
	@Autowired
	private SqlSession sqlSession;
	/** 비밀번호 인코더 선언 */
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	/** 공통부품.문자체크 */
	private WordCheck wordCheck = new WordCheck();

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
	@Override
	public JsonObject selectUserInfo(String id) throws Exception {

		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 유저 정보DTO
		UserInfoDto userInfoDto = new UserInfoDto();
		
		// 입력파라미터.ID를 조건으로 DB로부터 유저 정보 취득
		try {
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			userInfoDto = userInfoIDao.selectId(id);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}

		// "유저 정보DTO" 취득 확인
		if (userInfoDto != null) {
			// 해당 유저 정보를 정상적으로 취득한 경우 반환값을 다음과 같이 설정한다.
			// 성공확인플래그 : 처리 확인용 플래그 (비정상 : 1)
			// 메세지 내용 = "해당 id는 이미 사용중입니다."
			json.addProperty("result", ConstantsNum.PRO_CHK_FLG_FAIL);
			json.addProperty("message", ConstantsWord.MSG100006);

		} else {
			// 해당 유저 정보가 없는 경우 반환값을 다음과 같이 설정한다.
			// 성공확인플래그 : 처리 확인용 플래그 (정상 : 0)
			// 메세지 내용 = "해당 아이디를 사용 하시겠습니까?(변경불가)"
			json.addProperty("result", ConstantsNum.PRO_CHK_FLG_SUCESS);
			json.addProperty("message", ConstantsWord.MSG100005);
		}
		
		return json;
	}

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
	@Override
	public JsonObject selectCheckMail(String mailAdd) throws Exception {
		
		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 유저 정보DTO
		UserInfoDto userInfoDto = new UserInfoDto();

		try {
			// 입력파라미터.메일 주소를 조건으로 DB로부터 유저 정보 취득
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			userInfoDto = userInfoIDao.selectMailAdd(mailAdd);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// "유저 정보DTO" 취득 확인
		if (userInfoDto != null) {
			// 해당 유저 정보를 정상적으로 취득한 경우 반환값을 다음과 같이 설정한다.
			// 성공확인플래그 : 처리 확인용 플래그 (비정상 : 1)
			// 메세지 내용 = "해당 메일주소는 이미 사용중입니다."
			json.addProperty("result", ConstantsNum.PRO_CHK_FLG_FAIL);
			json.addProperty("message", ConstantsWord.MSG100007);

		} else {
			// 해당 유저 정보가 없는 경우 반환값을 다음과 같이 설정한다.
			// 성공확인플래그 : 처리 확인용 플래그 (정상 : 0)
			// 메세지 내용 = "해당 메일주소를 사용할 수 있습니다. 사용하시겠습니까?(변경 불가)"
			json.addProperty("result", ConstantsNum.PRO_CHK_FLG_SUCESS);
			json.addProperty("message", ConstantsWord.MSG100008);
		}
		
		return json;
	}

	/**
	 * 메소드명(물리) : 유저 정보 입력
	 * 메소드명(논리) : userInfoInsert
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param Map<String, Object> jsonMap : 입력 JSON값 저장 MAP
	 * @return JsonObject : 출력용 JSON 데이터
	 * 
	 * 입력된 유저 정보를 저장하는 처리를 진행한다.
	 */
	@Override
	public JsonObject userInfoInsert(Map<String, Object> jsonMap) {
		
		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 유저 정보DTO
		UserInfoDto userInfoDto = new UserInfoDto();
		
		// 반환값 설정 플래그 = 초기값 : 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 입력 id 취득
		String id = jsonMap.get("id").toString();
		// 입력 pw 취득
		String pw = jsonMap.get("pw").toString();
		// 입력 name 취득
		String name = jsonMap.get("name").toString();
		// 입력 mailAdd 취득
		String mailAdd = jsonMap.get("mailAdd").toString();
		// 입력 phoNum 취득
		String phoNum = jsonMap.get("phoNum").toString();
		// 사용자 타입(일반 : 0)
		int userType = 0;
		// 가입 날짜
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int mon = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hod = calendar.get(Calendar.HOUR_OF_DAY);
		int mi = calendar.get(Calendar.MINUTE);
		int se = calendar.get(Calendar.SECOND);
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(year));
		sb.append(ConstantsWord.DATE_BAR);
		sb.append(String.valueOf(mon));
		sb.append(ConstantsWord.DATE_BAR);
		sb.append(String.valueOf(day));
		sb.append(ConstantsWord.SPACE);
		sb.append(String.valueOf(hod));
		sb.append(ConstantsWord.TIME_BAR);
		sb.append(String.valueOf(mi));
		sb.append(ConstantsWord.TIME_BAR);
		sb.append(String.valueOf(se));
		String joinDate = sb.toString();
		// 마지막 수정 날짜
		String lastUpdDate = sb.toString();
		
		// 비밀번호 암호화 처리
		String encdPwd = pwdEncoder.encode(pw);
				
		// 입력할 유저 정보 설정
		// 유저 정보DTO.아이디 = 입력 id
		userInfoDto.setId(id);
		// 유저 정보DTO.비밀번호 = 암호화된 비밀번호
		userInfoDto.setPw(encdPwd);
		// 유저 정보DTO.이름 = 입력 이름
		userInfoDto.setName(name);
		// 유저 정보DTO.메일주소 = 입력 메일주소
		userInfoDto.setMailAdd(mailAdd);
		// 유저 정보DTO.연락처 = 입력 연락처
		userInfoDto.setPhoNum(phoNum);
		// 유저 정보DTO.사용자타입 = 입력 사용자타입
		userInfoDto.setUserType(userType);
		// 유저 정보DTO.가입 날짜 = 가입 날짜
		userInfoDto.setJoinDate(joinDate);
		// 유저 정보DTO.마지막 수정 날짜 = 마지막 수정 날짜
		userInfoDto.setLastUpdDate(lastUpdDate);

		try{
			// 입력파라미터.ID를 조건으로 DB로부터 유저 정보 취득
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			userInfoIDao.insertUserInfo(userInfoDto);
			
			// 메세지 내용  = "회원가입을 축하합니다!"
			message = ConstantsWord.MSG100003;
		}catch(Exception e) {
			// 반환값 설정 플래그  = 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "회원가입에 실패했습니다."
			message = ConstantsWord.MSG100004;
		}
		
		// 반환값 설정
		json.addProperty("result", resultFlg);
		json.addProperty("message", message);
		
		return json;
	}

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
	@Override
	public JsonObject mailTrans(String mailAdd) throws Exception {
		
		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		
		// 반환값 설정 플래그 = 초기값 : 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
				
		// 메일 주소 NULL 체크
		if(wordCheck.nullCheck(mailAdd)) {
			// 반환값 설정 플래그  = 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "메일주소를 입력해 주세요."
			message = ConstantsWord.MSG100009;
			
			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 인증번호 생성처리
			int checkNum = new Random().nextInt(900000) + 100000;
			// 인증 코드
			String authCode = String.valueOf(checkNum);

			// 메일 송신자 설정
			String charSet = "utf-8";
			String hostSMTP = "smtp.naver.com";
			String hostSMTPid = "deg2de@naver.com";
			String hostSMTPpwd = "password";

			// 송신자 EMail, 제목, 내용
			String fromEmail = "deg2de@naver.com";
			String fromName = "deg2de운영자";
			String subject = "deg2de사이트 회원가입 인증번호 입니다.";
			
			try {
				HtmlEmail email = new HtmlEmail();
				email.setDebug(true);
				email.setCharset(charSet);
				email.setSSL(true);
				email.setHostName(hostSMTP);
				email.setSmtpPort(465);
				email.setAuthentication(hostSMTPid, hostSMTPpwd);
				email.setTLS(true);
				email.addTo(mailAdd, charSet);
				email.setFrom(fromEmail, fromName, charSet);
				email.setSubject(subject);
				// 메일 내용 설정
				StringBuilder htmlMsgStr = new StringBuilder();
				htmlMsgStr.append(ConstantsWord.MAIL_DES_FIRST);
				htmlMsgStr.append(authCode);
				htmlMsgStr.append(ConstantsWord.MAIL_DES_LAST);
				email.setHtmlMsg(htmlMsgStr.toString());
				email.send();

				// 메세지 내용 = "해당 메일로 인증번호가 전송되었습니다. (인증완료까지 페이지를 유지해 주세요.)"
				message = ConstantsWord.MSG100010;
				
				// 반환값 설정
				json.addProperty("authCode", authCode);
				json.addProperty("result", resultFlg);
				json.addProperty("message", message);

			} catch (Exception e) {
				System.out.println(ConstantsWord.ERROR_START_POINT);
				System.out.println(e);
				System.out.println(ConstantsWord.ERROR_END_POINT);
				throw e;
			}
		}
		
		return json;
	}

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
	@Override
	public JsonObject authCodeChk(int authCode, int seAuthCode) {
		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();

		// 반환값 설정 플래그 = 초기값 : 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		
		// 입력 인증코드 값 설정 여부 확인
		if (authCode == 0) {
			// 입력 인증코드 값이 없을 경우
			// 반환값 설정 플래그  = 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "인증코드를 입력해 주세요."
			message = ConstantsWord.MSG100011;

			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 입력 인증코드와 확인용 인증코드 비교 처리
			if (authCode != seAuthCode) {
				// 반환값 설정 플래그  = 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용 = "인증번호가 맞지 않습니다."
				message = ConstantsWord.MSG100012;
				
				// 반환값 설정
				json.addProperty("result", resultFlg);
				json.addProperty("message", message);
			} else {
				// 비교 후 일치할 경우 아래의 설정을 행한다.
				// 메세지 내용 = "정상 확인되었습니다."
				message = ConstantsWord.MSG100013;
				
				// 반환값 설정
				json.addProperty("authCodeF", ConstantsNum.PRO_CHK_FLG_SUCESS);
				json.addProperty("result", resultFlg);
				json.addProperty("message", message);
			}
		}
		return json;
	}
}
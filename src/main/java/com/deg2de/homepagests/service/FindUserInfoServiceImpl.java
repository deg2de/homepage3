package com.deg2de.homepagests.service;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
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
 * 클래스명(물리) : 유저 정보 찾기 서비스 도구
 * 클래스명(논리) : FindUserInfoServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-20
 * 마지막 수정 날짜 : 2020-11-20
 * 
 * 유저 정보를 찾는 서비스 도구
 */
@Service("FindUserInfo")
public class FindUserInfoServiceImpl implements FindUserInfoService{

	/** SQL세션 선언 */
	@Autowired
	private SqlSession sqlSession;
	/** 비밀번호 인코더 선언 */
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	/** 공통부품.문자체크 */
	private WordCheck wordCheck = new WordCheck();

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
	@Override
	public JsonObject pwChangeTrans(Map<String, Object> jsonMap) throws Exception {
		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 유저 정보DTO
		UserInfoDto userInfoDto = new UserInfoDto();
		
		// 입력 mailAdd 취득
		String mailAdd = jsonMap.get("mailAdd").toString();
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 기존 비밀번호
		String id = ConstantsWord.NULL;
		// 임시 비밀번호
		String newpw = RandomStringUtils.randomAlphabetic(10);
		// DB저장용 비밀번호
		String dbpw = pwdEncoder.encode(newpw);
		// 메일 전송관련
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid = "deg2de@naver.com";
		String hostSMTPpwd = "akskrkEkffu";
		// 보내는사람 EMail, 제목, 내용
		String fromEmail = "deg2de@naver.com";
		String fromName = "deg2de운영자";
		String subject = "deg2de사이트 요청하신 회원정보입니다.";
		
		// 입력받은 메일주소값 NULL체크
		if(wordCheck.nullCheck(mailAdd)) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "메일주소를 입력해 주세요."
			message = ConstantsWord.MSG100009;
			
			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			try {
				// 입력파라미터.메일주소를 조건으로 DB로부터 유저 정보 취득
				UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
				userInfoDto = userInfoIDao.selectMailAdd(mailAdd);
			} catch (Exception e) {
				System.out.println(ConstantsWord.ERROR_START_POINT);
				System.out.println(e);
				System.out.println(ConstantsWord.ERROR_END_POINT);
				throw e;
			}
			
			// "유저 정보DTO" 정상 취득 확인
			if(userInfoDto == null || wordCheck.nullCheck(userInfoDto.getId()) || wordCheck.nullCheck(userInfoDto.getPw())) {
				// 정상 취득 실패의 경우
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용  = "해당하는 메일, 또는 비밀번호가 없습니다."
				message = ConstantsWord.MSG100018;
				
				// 반환값 설정
				json.addProperty("result", resultFlg);
				json.addProperty("message", message);
			} else {
				// 정상 취득의 경우
				// 임시 비밀번호 설정 후 해당 유저의 메일 주소로 비밀번호 전송 처리
				// "유저 정보DTO"로부터 유저 ID 취득
				id = userInfoDto.getId();
				
				try {
					// 입력파라미터.ID를 조건으로 PW정보 갱신
					UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
					userInfoIDao.updateUserPw(id, dbpw);
					
					// 임시 비밀번호 저장 처리 성공시 임시 비밀번호를 해당 유저의 메일주소로 발송
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
					email.setHtmlMsg(ConstantsWord.FIND_USER_MAIL_DES_ID + id + ConstantsWord.FIND_USER_MAIL_DES_PW
							+ newpw + ConstantsWord.MAIL_DES_LAST);
					email.send();
					
					// 메세지 내용  = "해당 메일로 정보를 전송하였습니다. 작성하신 메일에서 확인 바랍니다."
					message = ConstantsWord.MSG100019;
					
					// 반환값 설정
					json.addProperty("result", resultFlg);
					json.addProperty("message", message);
				} catch (Exception e) {
					System.out.println(ConstantsWord.ERROR_START_POINT);
					System.out.println(e);
					System.out.println(ConstantsWord.ERROR_END_POINT);
					throw e;
				}
			}
		}
		return json;
	}
}

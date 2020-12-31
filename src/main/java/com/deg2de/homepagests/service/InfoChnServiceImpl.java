package com.deg2de.homepagests.service;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.deg2de.homepagests.dao.UserInfoIDao;
import com.deg2de.homepagests.dto.UserInfoChangeDto;
import com.deg2de.homepagests.dto.UserInfoDto;
import com.deg2de.homepagests.dto.UserPicAddChangeDto;
import com.deg2de.homepagests.module.WordCheck;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;
import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 정보 변경 서비스 도구
 * 클래스명(논리) : InfoChnServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-20
 * 마지막 수정 날짜 : 2020-11-20
 * 
 * 사용자의 개인 정보를 변경하는 처리를 진행하는 서비스 도구
 */
@Service("InfoChn")
public class InfoChnServiceImpl implements InfoChnService {

	/** SQL세션 선언 */
	@Autowired
	private SqlSession sqlSession;
	/** 비밀번호 인코더 선언 */
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	/** 공통부품.문자체크 */
	private WordCheck wordCheck = new WordCheck();

	/**
	 * 메소드명(물리) : 유저 정보 취득
	 * 메소드명(논리) : selectUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param String id : 로그인 중인 아이디
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 로그인중인 아이디를 조건으로 유저 정보를 취득한 후 결과 플래그를 반환한다.
	 */
	@Override
	public int selectUserInfo(String id, HttpServletRequest request) throws Exception {

		// 유저 정보DTO
		UserInfoDto userInfoDto = new UserInfoDto();
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		
		try {
			// 입력파라미터.아이디를 조건으로 DB로부터 유저 정보 취득
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			userInfoDto = userInfoIDao.selectId(id);
		}  catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// 해당 유저 정보를 취득 여부 확인
		if (userInfoDto != null) {
			// 정상처리의 경우 취득한 "유저 정보DTO"를 출력한다.
			request.setAttribute("userInfo", userInfoDto);
			return resultFlg;
		}

		// 해당 유저 정보가 없는 경우 반환값을 다음과 같이 설정하고 처리를 종료한다.
		// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
		resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
		return resultFlg;
	}

	/**
	 * 메소드명(물리) : 유저 정보 변경
	 * 메소드명(논리) : updateUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param Map<String, Object> jsonMap : 입력 JSON값 저장 MAP
	 * @return JsonObject : 출력용 JSON 데이터
	 * @throws Exception
	 * 
	 * 입력 받은 유저 정보로 수정하는 작업을 진행한다.
	 */
	@Override
	public JsonObject updateUserInfo(Map<String, Object> jsonMap) throws Exception {
		
		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 변경용 데이터 DTO
		UserInfoChangeDto userInfoChangeDto = new UserInfoChangeDto();
				
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 입력 id 취득
		String id = jsonMap.get("id").toString();
		// 입력 pw 취득
		String pw = jsonMap.get("pw").toString();
		// 입력 name 취득
		String name = jsonMap.get("name").toString();
		// 입력 phoNum 취득
		String phoNum = jsonMap.get("phoNum").toString();
		// 마지막 수정 날짜
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int mon = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hod = calendar.get(Calendar.HOUR_OF_DAY);
		int mi = calendar.get(Calendar.MINUTE);
		int se = calendar.get(Calendar.SECOND);
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(year));
		sb.append("-");
		sb.append(String.valueOf(mon));
		sb.append("-");
		sb.append(String.valueOf(day));
		sb.append(" ");
		sb.append(String.valueOf(hod));
		sb.append(":");
		sb.append(String.valueOf(mi));
		sb.append(":");
		sb.append(String.valueOf(se));
		String lastUpdDate = sb.toString();
		
		// 변경할 입력값 값 존재 여부 체크
		if (wordCheck.nullCheck(id) || wordCheck.nullCheck(pw) || wordCheck.nullCheck(name)
				|| wordCheck.nullCheck(phoNum) || wordCheck.nullCheck(lastUpdDate)) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "회원정보 변경에 실패했습니다."
			message = ConstantsWord.MSG100015;
			
			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 비밀번호 암호화 처리
			String encdPwd = pwdEncoder.encode(pw);
			
			// "변경용 데이터 DTO" 설정 처리
			userInfoChangeDto.setId(id);
			userInfoChangeDto.setPw(encdPwd);
			userInfoChangeDto.setName(name);
			userInfoChangeDto.setPhoNum(phoNum);
			userInfoChangeDto.setLastUpdDate(lastUpdDate);
			
			try{
				// "변경용 데이터 DTO"로 DB의 유저 정보 변경 처리
				UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
				userInfoIDao.updateUserInfo(userInfoChangeDto);
				
				// 메세지 내용  = "회원정보를 변경했습니다."
				message = ConstantsWord.MSG100016;
				// 반환값 설정
				json.addProperty("result", resultFlg);
				json.addProperty("message", message);
			}catch(Exception e) {
				System.out.println(ConstantsWord.ERROR_START_POINT);
				System.out.println(e);
				System.out.println(ConstantsWord.ERROR_END_POINT);
				throw e;
			}
		}
		return json;
	}

	/**
	 * 메소드명(물리) : 사용자 사진 주소 갱신
	 * 메소드명(논리) : updateUserPicAdd
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-23
	 * 마지막 수정 날짜 : 2020-11-23
	 * 
	 * @param String id : 사용자 아이디
	 * @param String fileName : 파일 이름
	 * @return int : 반환값 설정 플래그
	 * 
	 * 사용자의 이미지 파일을 갱신한다.
	 */
	@Override
	public int updateUserPicAdd(String id, String fileName) {
		
		// 사용자 사진 주소 변경DTO
		UserPicAddChangeDto userPicAddChangeDto = new UserPicAddChangeDto();
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		
		 // 마지막 수정 날짜
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int mon = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hod = calendar.get(Calendar.HOUR_OF_DAY);
		int mi = calendar.get(Calendar.MINUTE);
		int se = calendar.get(Calendar.SECOND);
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(year));
		sb.append("-");
		sb.append(String.valueOf(mon));
		sb.append("-");
		sb.append(String.valueOf(day));
		sb.append(" ");
		sb.append(String.valueOf(hod));
		sb.append(":");
		sb.append(String.valueOf(mi));
		sb.append(":");
		sb.append(String.valueOf(se));
		String lastUpdDate = sb.toString();
		
		// 사용자 사진 주소 변경DTO.사용자 아이디 = 로그인 아이디
        userPicAddChangeDto.setId(id);
        // 사용자 사진 주소 변경DTO.사진 주소 = 사용자 파일 주소
        userPicAddChangeDto.setPicAdd(fileName);
		// 사용자 사진 주소 변경DTO.마지막 수정 날짜 = 마지막 수정 날짜
        userPicAddChangeDto.setLastUpdDate(lastUpdDate);

		try{
			// 사용자 사진 주소 변경DTO.아이디를 조건으로 DB의 사용자 정보 갱신
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			userInfoIDao.updateUserPicAdd(userPicAddChangeDto);
		}catch(Exception e) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			return resultFlg;
		}
		return resultFlg;
	}
}

package com.deg2de.homepagests.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.deg2de.homepagests.dto.SessionInfoDto;
import com.deg2de.homepagests.dto.UserInfoManageDto;
import com.deg2de.homepagests.module.WordCheck;
import com.deg2de.homepagests.service.FindUserInfoServiceImpl;
import com.deg2de.homepagests.service.InfoChnServiceImpl;
import com.deg2de.homepagests.service.LoginServiceImpl;
import com.deg2de.homepagests.service.LogoutServiceImpl;
import com.deg2de.homepagests.service.MainPageViewServiceImpl;
import com.deg2de.homepagests.service.UserJoinServiceImpl;
import com.deg2de.homepagests.service.UserManageServiceImpl;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 유저 컨트롤러
 * 클래스명(논리) : UserController.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-17
 * 마지막 수정 날짜 : 2020-11-17
 * 
 * 유저 정보에 대한 처리를 담당하는 컨트롤러
 */
@Controller
public class UserController {
	
	/** 로그인 서비스 도구 선언 */
	@Autowired
	private LoginServiceImpl loginServiceImpl;
	/** 정보 변경 서비스 도구 선언 */
	@Autowired
	private InfoChnServiceImpl infoChnServiceImpl;
	/** 회원 가입 서비스 도구 선언 */
	@Autowired
	private UserJoinServiceImpl userJoinServiceImpl;
	/** 유저 정보 찾기 서비스 도구 선언 */
	@Autowired
	private FindUserInfoServiceImpl findUserInfoServiceImpl;
	/** 유저 관리 서비스 도구 선언 */ 
	@Autowired
	private UserManageServiceImpl userManageServiceImpl;
	/** 메인 페이지 뷰 서비스 도구 선언 */
	@Autowired
	private MainPageViewServiceImpl mainPageViewServiceImpl;
	/** 로그아웃 서비스 도구 선언 */
	@Autowired
	private LogoutServiceImpl logoutServiceImpl;

	/** 공통부품.문자체크 */
	private WordCheck wordCheck = new WordCheck();

	/**
	 * 
	 * 메소드명(물리) : GET형 로그인 체크
	 * 메소드명(논리) : getLoginCheck
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-17
	 * 마지막 수정 날짜 : 2020-11-17
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 메인 페이지
	 * @throws Exception 
	 * 
	 * GET방식 "login"요청에 대해서 메인 페이지를 출력하는 메소드
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginCheck(HttpServletRequest request) throws Exception {
		
		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}

	/**
	 * 메소드명(물리) : POST형 로그인 체크
	 * 메소드명(논리) : postLoginCheck
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String id : 아이디
	 * @param String pw : 비밀번호
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * POST방식 "login"요청에 대해서 로그인 정보 확인 처리 후 메인 페이지를 출력하는 메소드
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String postLoginCheck(@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "pw", defaultValue = "") String pw, HttpServletRequest request,
			HttpSession session) throws Exception {

		// 세션 정보DTO
		SessionInfoDto sessionInfoDto = new SessionInfoDto();	
		// 세션 유저정보 저장용MAP
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		
		// 컨테스트 주소
		String contextPathStr = request.getContextPath().toString();

		// "로그인 체크"에서 입력 정보를 확인한 후 정상일 경우 로그인에 필요한 "세션 정보DTO"를 취득한다.
		sessionInfoDto = loginServiceImpl.lgnChk(id, pw, contextPathStr);
		
		// "세션 정보DTO"의 정보를 "세션 유저정보 저장용MAP"에 설정한다.
		sessionMap.put("picAdd", sessionInfoDto.getPicAdd());
		sessionMap.put("id", sessionInfoDto.getId());
		sessionMap.put("name", sessionInfoDto.getName());
		sessionMap.put("userType", sessionInfoDto.getUserType());
		
		// "세션 유저정보 저장용MAP"를 세션에 저장한다.
		session.setAttribute("loginInfo", sessionMap);

		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}

	/**
	 * 메소드명(물리) : GET형 로그아웃
	 * 메소드명(논리) : getLogout
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * 로그아웃 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(HttpServletRequest request, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 세션 정보 취득
		sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
		
		// 저장 경로
		StringBuilder realPath = new StringBuilder();
		realPath.append(ConstantsWord.IMG_USER_PIC_UPDATE);
		realPath.append(sessionMap.get("id").toString());
		realPath.append(ConstantsWord.ADD_HYP);
		// 임시 파일 저장 위치
		String tempFolderAdd = request.getSession().getServletContext().getRealPath(realPath.toString());
		
		// 작성중 이미지 삭제 처리
		logoutServiceImpl.imageDelete(tempFolderAdd);
				
		// 로그인 세션 정보 삭제
		session.removeAttribute("loginInfo");
		// 모든 세션 파괴
		session.invalidate();

		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}

	/**
	 * 메소드명(물리) : POST형 로그아웃
	 * 메소드명(논리) : postLogout
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * 로그아웃 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String postLogout(HttpServletRequest request, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 세션 정보 취득
		sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");

		// 저장 경로
		StringBuilder realPath = new StringBuilder();
		realPath.append(ConstantsWord.IMG_USER_PIC_UPDATE);
		realPath.append(sessionMap.get("id").toString());
		realPath.append(ConstantsWord.ADD_HYP);
		// 임시 파일 저장 위치
		String tempFolderAdd = request.getSession().getServletContext().getRealPath(realPath.toString());
		
		// 작성중 이미지 삭제 처리
		logoutServiceImpl.imageDelete(tempFolderAdd);
				
		// 세션 정보 삭제
		session.removeAttribute("loginInfo");
		// 모든 세션 파괴
		session.invalidate();

		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}

	/**
	 * 메소드명(물리) : GET형 회원 가입
	 * 메소드명(논리) : getUserJoin
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 비로그인에 한해서 회원가입 페이지로 이동한다.
	 */
	@RequestMapping(value = "/userJoin", method = RequestMethod.GET)
	public String getUserJoin(HttpServletRequest request, HttpSession session) throws Exception {

		// 로그인여부 확인
		// 로그인 중일 경우 메인페이지로 이동한다.
		if (session.getAttribute("loginInfo") != null) {
			// 메인페이지로 이동
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}
		
		// 로그인 중이 아닐 경우 회원가입 페이지로 이동한다.
		return ConstantsWord.USERJOIN_PAGE;
	}

	/**
	 * 메소드명(물리) : POST형 회원 가입
	 * 메소드명(논리) : postUserJoin
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * 
	 * 입력받은 데이터로 회원가입을 진행한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/userJoin", method = RequestMethod.POST)
	public void postUserJoin(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) {

		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		};

		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;

		// 로그인여부 확인
		// 로그인 중일 경우 비정상 플래그를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "로그인중입니다. 회원가입을 진행할 수 없습니다."
			message = ConstantsWord.MSG100002;
			
			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 로그인 중이 아닌 경우 아래의 처리를 진행한다.
			// "유저 정보 입력"을 통해 DB에 입력값 저장 후 처리 결과를 반환한다.
			json = userJoinServiceImpl.userInfoInsert(jsonMap);
		}

		try {
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 메소드명(물리) : POST형 ID중복확인 처리
	 * 메소드명(논리) : postUserJoinIdCheck
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception 
	 * 
	 * 입력받은 데이터로 회원가입을 진행한다.
	 */
	@ResponseBody
	@RequestMapping(value = "userJoinIdCheck", method = RequestMethod.POST)
	public void postUserJoinIdCheck(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) throws Exception {

		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 입력 id 취득
		String id = jsonMap.get("id").toString();
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;

		// 로그인여부 확인
		// 로그인 중일 경우 비정상 플래그를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "로그인중입니다. 회원가입을 진행할 수 없습니다."
			message = ConstantsWord.MSG100002;
			
			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// id로 유저 정보 조회 후 중복확인 결과 취득
			json = userJoinServiceImpl.selectUserInfo(id);
		}

		try {
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 메소드명(물리) : POST형 메일중복확인 처리
	 * 메소드명(논리) : postUserJoinMailCheck
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 입력 받은 메일의 중복확인 처리를 행한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/userJoinMailCheck", method = RequestMethod.POST)
	public void postUserJoinMailCheck(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) throws Exception {

		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 입력 메일주소 취득
		String mailAdd = jsonMap.get("mailAdd").toString();
		// 반환 메세지
		String message = ConstantsWord.NULL;
		
		// 로그인여부 확인
		// 로그인 중일 경우 비정상 플래그를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "로그인중입니다. 회원가입을 진행할 수 없습니다."
			message = ConstantsWord.MSG100002;

			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 메일주소로 유저 정보 취득
			json = userJoinServiceImpl.selectCheckMail(mailAdd);
		}

		try {
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 메소드명(물리) : POST형 메일 인증번호 전송 처리
	 * 메소드명(논리) : postUserJoinMailNum
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 메일로 인증번호를 전송하는 처리를 진행한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/userJoinMailNum", method = RequestMethod.POST)
	public void postUserJoinMailNum(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) throws Exception {

		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 수신자 메일 주소 획득
		String mailAdd = jsonMap.get("mailAdd").toString();

		// 로그인여부 확인
		// 로그인 중일 경우 비정상 플래그를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "로그인중입니다. 회원가입을 진행할 수 없습니다."
			message = ConstantsWord.MSG100002;
			
			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 메일 전송 처리
			json = userJoinServiceImpl.mailTrans(mailAdd);
			
			// 메일 전송 처리 결과가 정상일 경우 인증코드를 세션에 설정한다.
			if(!(Integer.parseInt(json.get("result").toString()) != ConstantsNum.PRO_CHK_FLG_SUCESS)) {
				// 인증코드 세션 저장
				session.setAttribute("authCode", json.get("authCode").toString());
			}
		}
		
		try {
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 메소드명(물리) : 인증 코드 확인 처리
	 * 메소드명(논리) : postUserJoinAuthCode
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 입력된 인증번호의 확인처리를 진행한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/userJoinAuthCode", method = RequestMethod.POST)
	public void postUserJoinAuthCode(@RequestBody HashMap<String, Object> jsonData, HttpServletResponse response,
			HttpSession session) {

		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();

		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 입력받은 인증코드
		int authCode = Integer.parseInt(jsonData.get("authCode").toString());

		// 로그인여부 확인
		// 로그인 중일 경우 비정상 플래그를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "로그인중입니다. 회원가입을 진행할 수 없습니다."
			message = ConstantsWord.MSG100002;

			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 세션에 저장된 인증번호 불러오기
			int seAuthCode = Integer.parseInt(session.getAttribute("authCode").toString());
			// 인증번호 비교 처리
			json = userJoinServiceImpl.authCodeChk(authCode, seAuthCode);
		}
		try {
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 메소드명(물리) : GET형 회원가입 완료 처리
	 * 메소드명(논리) : getUserJoinSuc
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * 회원가입 완료 후 메인페이지로 이동하는 처리
	 */
	@RequestMapping(value = "/userJoinSuc", method = RequestMethod.GET)
	public String getUserJoinSuc(HttpServletRequest request) throws Exception {
		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}
	
	/**
	 * 메소드명(물리) : GET형 정보 변경 처리
	 * 메소드명(논리) : getInfoChange
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 정보 변경 처리에 필요한 정보를 취득한다.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/infoChange", method = RequestMethod.GET)
	public String getInfoChange(HttpServletRequest request, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();

		// 로그인여부 확인
		// 로그인중일 경우 처리를 계속 진행하고 비로그인의 경우 메인페이지로 이동한다.
		if (session.getAttribute("loginInfo") == null) {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}

		// 세션 정보 취득
		sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");

		// 세션의 아이디
		String sessionId = sessionMap.get("id").toString();

		// 세션 아이디값 존재 여부 확인
		if (wordCheck.nullCheck(sessionId)) {
			// 존재하지 않을 경우 메인페이지로 이동한다.
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}

		// 유저 정보를 취득 처리를 진행한 후 처리 결과 플래그를 "반환값 설정 플래그"에 저장한다.
		int resultFlg = infoChnServiceImpl.selectUserInfo(sessionId, request);

		// 정상처리가 아닐 경우 메인페이지로 이동한다.
		if (resultFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}

		// 정상처리의 경우 정보 변경 페이지로 이동한다.
		return ConstantsWord.INFOCHANGE_PAGE;
	}

	/**
	 * 메소드명(물리) : POST형 정보 변경 처리
	 * 메소드명(논리) : postInfoChange
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 입력받은 유저 정보를 확인하고 변경하는 처리를 진행한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/infoChange", method = RequestMethod.POST)
	public void postInfoChange(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) throws Exception {

		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

		// 로그인여부 확인
		// 로그인 중일 경우 비정상 플래그를 설정한다.
		if (session.getAttribute("loginInfo") == null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "비로그인중입니다. 회원정보변경을 진행할 수 없습니다."
			message = ConstantsWord.MSG100014;

			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 유저정보 변경
			json = infoChnServiceImpl.updateUserInfo(jsonMap);
		}		

		try {
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 메소드명(물리) : GET형 유저 정보 찾기
	 * 메소드명(논리) : getFindUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 이동 페이지
	 * @throws Exception
	 */
	@RequestMapping(value = "/findUserInfo", method = RequestMethod.GET)
	public String getFindUserInfo(HttpServletRequest request, HttpSession session) throws Exception {

		// 로그인여부 확인
		// 로그인 중일 경우 메인페이지로 이동한다.
		if (session.getAttribute("loginInfo") != null) {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}
		
		// 유저 정보 찾기 페이지로 이동한다.
		return ConstantsWord.FINDUSERINFO_PAGE;
	}

	/**
	 * 메소드명(물리) : POST형 유저 정보 찾기
	 * 메소드명(논리) : postFindUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 입력받은 메일주소를 확인 한 후 해당 메일주소로 임시 비밀번호를 발송한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/findUserInfo", method = RequestMethod.POST)
	public void postFindUserInfo(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) throws Exception {
	
		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 로그인여부 확인
		// 로그인 중일 경우 메인페이지로 이동한다.
		if (session.getAttribute("loginInfo") != null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "이미 로그인 중입니다."
			message = ConstantsWord.MSG100017;

			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			json = findUserInfoServiceImpl.pwChangeTrans(jsonMap);
		}

		try {
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : GET형 사용자 이미지 등록/변경
	 * 메소드명(논리) : getUserPic
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-20
	 * 마지막 수정 날짜 : 2020-11-20
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 로그인 여부를 확인한 후 사용자 이미지 페이지를 로드한다.
	 */
	@RequestMapping(value = "/userPic", method = RequestMethod.GET)
	public String getUserPic(HttpServletRequest request, HttpSession session) throws Exception {
				
		// 로그인여부 확인
		// 로그인 중이 아닐 경우 처리를 중단하고 메인페이지로 이동한다.
		if (session.getAttribute("loginInfo") == null) {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}
		
		// 사용자 이미지 페이지로 이동한다.
		return ConstantsWord.USERPIC_PAGE;
	}
	
	/**
	 * 메소드명(물리) : POST형 사용자 이미지 등록/변경
	 * 메소드명(논리) : postUserPic
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-23
	 * 마지막 수정 날짜 : 2020-11-23
	 * 
	 * @param MultipartHttpServletRequest multi : MultipartHttpServlet 요청
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param  HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 사용자의 이미지 사진을 변경한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/userPic", method = RequestMethod.POST)
	public void postUserPic(MultipartHttpServletRequest multi, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		
		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
		// 세션 새로운 유저정보 저장용 맵
		HashMap<String, Object> newSessionMap = new HashMap<String, Object>();
				
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// DB 업데이트 처리결과 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int updateDbResultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 기본 루트
		String root = multi.getSession().getServletContext().getRealPath(ConstantsWord.ADD_HYP);
		
		// 저장 경로 설정
		StringBuilder pathSb = new StringBuilder();
		pathSb.append(root);
		pathSb.append(ConstantsWord.IMG_USER_PIC_UNBAR);
		String path = pathSb.toString();
		
		OutputStream out = null;
		MultipartFile file = multi.getFile("upload");
		
		// 로그인여부 확인
		// 로그인 중이 아닐 경우 처리를 중단하고 메인페이지로 이동한다.
		if (session.getAttribute("loginInfo") == null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 ="로그인을 해주세요."
			message = ConstantsWord.MSG100022;

			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 파일 획득여부확인
			if(file != null){
				// 파일용량이 있는지 확인한다.
				if(file.getSize() > 0 && file.getName() != null && !file.getName().equals("")){
					// 이미지파일인지 확인한다.
					if(file.getContentType().toLowerCase().startsWith("image/")){
						try{
							String fileName = file.getName();
							byte[] bytes = file.getBytes();
							// 파일을 저장할 주소
							String uploadPath = path;
							File uploadFile = new File(uploadPath);
							// 해당 파일 경로가 없을 경우 파일을 만들어준다.
							if(!uploadFile.exists()){
								uploadFile.mkdirs();
							}
							
							// 이전 이미지명 얻어오기
							String oldImg = sessionMap.get("picAdd").toString();
							String oldImgSp[] = oldImg.split(ConstantsWord.ADD_HYP);
							String oldImgFileName = oldImgSp[oldImgSp.length-1].toString();
							
							// 이미지 삭제 처리
							// 파일리스트 얻어오기
							File[] folder_list = uploadFile.listFiles();
							for (int fileCnt = 0; fileCnt < folder_list.length; fileCnt++) {
								// 기본 이미지의 경우 삭제처리 중단
								if(oldImgFileName.equals(ConstantsWord.USER_PIC_BASIC)) {
									break;
								}
								// 기존 이미지 삭제 처리
								if (folder_list[fileCnt].getName().equals(oldImgFileName)) {
									// 파일 삭제
									folder_list[fileCnt].delete();
								}
							}
							
							// 파일 이름 생성
							fileName = UUID.randomUUID().toString();
							
							// 파일 저장
							uploadPath = uploadPath + fileName;
							out = new FileOutputStream(new File(uploadPath));
	                        out.write(bytes);
	                        
	                        // 사진정보 갱신(정상처리 : 0, 비정상처리 : 1)
	                        updateDbResultFlg = infoChnServiceImpl.updateUserPicAdd(sessionMap.get("id").toString(), fileName);
	            			
	            			// DB 비정상 종료의 경우
	            			if(updateDbResultFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
	            				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
	            				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
	            				// 메세지 내용  = "이미지 파일 저장에 실패하였습니다."
	            				message = ConstantsWord.MSG100024;
	            			} else {
	            				// 정상처리의 경우
	            				// 이미지 파일이 저장된 주소를 세션에 설정
		                        String basicPicAddStr = null;
		                		StringBuilder basicPicAdd = new StringBuilder();
		                		basicPicAdd.append(request.getContextPath().toString());
		                		basicPicAdd.append(ConstantsWord.IMG_USER_PIC);
		                		basicPicAdd.append(fileName);
		                		basicPicAddStr = basicPicAdd.toString();
		                		
		                		// 변경된 사용자 정보를 세션의 사용자 정보로 갱신 처리
		                		newSessionMap.put("id", sessionMap.get("id").toString());
		                		newSessionMap.put("name", sessionMap.get("name").toString());
		                		newSessionMap.put("userType", sessionMap.get("userType").toString());
		                		newSessionMap.put("picAdd", basicPicAddStr);
		                		session.removeAttribute("loginInfo");
		                		session.setAttribute("loginInfo", newSessionMap);
		                		
		                		// 메세지 내용 = "프로필 이미지가 변경되었습니다."
		                		message = ConstantsWord.MSG100025;
	            			}
	                    }catch(Exception e){
	                    	// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
            				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
            				// 메세지 내용  = "이미지 파일 저장에 실패하였습니다."
            				message = ConstantsWord.MSG100024;
	                    }finally{
	                        if(out != null){
	                            out.close();
	                        }
						}
					} else {
						// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
        				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
        				// 메세지 내용  = "이미지 파일 저장에 실패하였습니다."
        				message = ConstantsWord.MSG100024;
					}
				} else {
					// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
    				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
    				// 메세지 내용  = "이미지 파일 저장에 실패하였습니다."
    				message = ConstantsWord.MSG100024;
				}
			} else {
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용  = "이미지 파일 저장에 실패하였습니다."
				message = ConstantsWord.MSG100024;
			}
		}
		
		json.addProperty("result", resultFlg);
		json.addProperty("message", message);
		response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
		response.getWriter().println(json);
	}
	
	/**
	 * 메소드명(물리) : GET형 사용자 관리 처리
	 * 메소드명(논리) : getUserManage
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 사용자 관리 페이지 이동에 필요한 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userManage", method = RequestMethod.GET)
	public String getUserManage(HttpServletRequest request, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 관리용 유저정보 취득용 DTO 리스트
		List<UserInfoManageDto> userInfoManageDtoList = new ArrayList<UserInfoManageDto>();
		
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 페이지 번호
		int pageNo = Integer.parseInt(request.getParameter("pageno"));
		// 페이지당 데이터 갯수
		int dataSize = ConstantsNum.USER_MANAGE_DATA_50;

		// 로그인여부 확인
		// 비로그인 중일 경우 메인페이지로 돌아가도록 설정한다.
		if (session.getAttribute("loginInfo") == null) {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());

			// 관리자 레벨이 아닌 경우 메인페이지로, 관리자의 경우 게시판관리 페이지로 이동한다.
			if (sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
				return mainPageViewServiceImpl.mainPageViewPro(request);
			}
		}
		
		// 전체 유저정보 취득
		userInfoManageDtoList = userManageServiceImpl.selectAllUserInfo();
		
		// 유저 정보 페이지 처리 후 사용자 관리 페이지로 이동한다.
		return userManageServiceImpl.userInfoPagePro(request, userInfoManageDtoList, pageNo, dataSize);
	}
	
	/**
	 * 메소드명(물리) : POST형 사용자 관리 처리 (검색의 경우)
	 * 메소드명(논리) : postUserManage
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 사용자 관리 페이지에서 특정 사용자 데이터를 검색할 경우 진행되는 처리
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userManage", method = RequestMethod.POST)
	public String postUserManage(HttpServletRequest request, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 관리용 유저정보 취득용 DTO
		List<UserInfoManageDto> userInfoManageDtoList = new ArrayList<UserInfoManageDto>();
		
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 현재 페이지
		int pageNo = Integer.parseInt(request.getParameter("pageno"));
		// 페이지당 데이터 갯수
		int dataSize = ConstantsNum.USER_MANAGE_DATA_50;
		// 검색범위
		String searchObject = request.getParameter("search_object");
		// 검색단어
		String searchText = request.getParameter("search_text");
		
		// 로그인여부 확인
		// 비로그인 중일 경우 메인페이지로 돌아가도록 설정한다.
		if (session.getAttribute("loginInfo") == null) {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());

			// 관리자 레벨이 아닌 경우 메인페이지로, 관리자의 경우 게시판관리 페이지로 이동한다.
			if (sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
				return mainPageViewServiceImpl.mainPageViewPro(request);
			}
		}

		// 유저정보 취득
		// 유저관리데이터를 취득한다. 실패시 메인페이지로 이동한다.
		// 검색범위, 검색단어가 있을경우 검색데이터 취득하고 그렇지 않을경우 전체데이터를 취득한다.
		if(!wordCheck.nullCheck(searchObject) && !wordCheck.nullCheck(searchText)) {
			// 유저관리데이터 취득
			if(searchObject.equals("search_name") && !wordCheck.nullCheck(searchText)) {
				// 검색형식이 '이름'이고 검색단어가 NULL이 아닐 경우
				// 유저정보데이터의 이름에서 검색단어가 포함된 데이터를 모두 취득하여 '유저관리데이터 리스트'에 저장한다.
				userInfoManageDtoList = userManageServiceImpl.selectUserInfoName(searchText);
			} else if(searchObject.equals("search_id") && !wordCheck.nullCheck(searchText)) {
				// 검색형식이 '아이디'이고 검색단어가 NULL이 아닐 경우
				// 유저정보데이터의 아이디에서 검색단어가 포함된 데이터를 모두 취득하여 '유저관리데이터 리스트'에 저장한다.
				userInfoManageDtoList = userManageServiceImpl.selectUserInfoId(searchText);
			} else {
				// 이 외의 경우 전체 데이터를 취득하여 ' 유저관리데이터 리스트'에 저장한다.
				userInfoManageDtoList = userManageServiceImpl.selectAllUserInfo();
			}

			// 검색데이터가 없을경우 전체데이터를 취득한다.
			if(userInfoManageDtoList.size() == 0) {
				userInfoManageDtoList = userManageServiceImpl.selectAllUserInfo();
			}
		} else {
			// 검색조건이 없는 경우 전체 데이터를 취득한다.
			userInfoManageDtoList = userManageServiceImpl.selectAllUserInfo();
		}

		// 취득 데이터가 없을 경우 메인페이지로 이동한다.
		if(userInfoManageDtoList.size() == 0) {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}
		
		// 검색 범위
		request.setAttribute("searchobject", searchObject);
		// 검색 내용
		request.setAttribute("searchtext", searchText);

		// 유저 정보 페이지 처리 후 사용자 관리 페이지로 이동한다.
		return userManageServiceImpl.userInfoPagePro(request, userInfoManageDtoList, pageNo, dataSize);
	}
	
	/**
	 * 메소드명(물리) : POST형 사용자 정보 삭제 처리
	 * 메소드명(논리) : postDeleteUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-22
	 * 마지막 수정 날짜 : 2020-11-22
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * 
	 * 입력받은 아이디의 유저 정보를 삭제한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteuserManage", method = RequestMethod.POST)
	public void postDeleteUserInfo(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) {

		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 삭제할 아이디 취득
		String id = jsonMap.get("id").toString();
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
					
		// 로그인여부 확인
		// 비로그인 중일 경우 로그인 메세지를 반환한다.
		if (session.getAttribute("loginInfo") == null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "로그인을 해주세요."
			message = ConstantsWord.MSG100022;
			
			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());
			
			// 해당 사용자가 관리자등급인지 확인한다.
			if(sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// 관리자 레벨이 아닌 경우 권한없음 메세지를 반환한다.
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용  = "해당 권한이 없습니다.(관리자 전용)" 
				message = ConstantsWord.MSG100023;
				
				// 반환값 설정
				json.addProperty("result", resultFlg);
				json.addProperty("message", message);
			} else {
				// 출력용 JSON 데이터  = 유저 정보 삭제 처리
				json = userManageServiceImpl.deleteUserId(id);
			}
		}

		try {
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

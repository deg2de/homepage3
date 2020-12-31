package com.deg2de.homepagests.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deg2de.homepagests.dto.BoardInfoDto;
import com.deg2de.homepagests.dto.BoardTypeInfoDto;
import com.deg2de.homepagests.service.BoardInfoServiceImpl;
import com.deg2de.homepagests.service.MainPageViewServiceImpl;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 게시판 컨트롤러
 * 클래스명(논리) : BoardController.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-24
 * 마지막 수정 날짜 : 2020-11-24
 * 
 * 게시판에 대한 처리를 담당하는 컨트롤러
 */
@Controller
public class BoardController {
	
	/** 메인 페이지 뷰 서비스 도구 선언 */
	@Autowired
	private MainPageViewServiceImpl mainPageViewServiceImpl;
	/** 게시판 정보 서비스 도구 선언 */
	@Autowired
	private BoardInfoServiceImpl boardInfoServiceImpl;
	
	/**
	 * 
	 * 메소드명(물리) : GET형 게시판 관리
	 * 메소드명(논리) : getBoardManage
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 게시판 관리 페이지로 이동하는 처리
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/boardManage", method = RequestMethod.GET)
	public String getBoardManage(HttpServletRequest request, HttpSession session) throws Exception {
		
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 게시판 정보 DTO 리스트
		List<BoardInfoDto> boardInfoDtoList = new ArrayList<BoardInfoDto>();
		
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
				
		// 로그인여부 확인
		// 비로그인 중일 경우 메인페이지로 돌아가도록 설정한다.
		if (session.getAttribute("loginInfo") == null) {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");

			// 세션의 유저레벨
			sessionUserType = Integer.parseUnsignedInt(sessionMap.get("userType").toString());
			
			// 관리자 레벨이 아닌 경우 메인페이지로, 관리자의 경우 게시판관리 페이지로 이동한다.
			if(sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
				return mainPageViewServiceImpl.mainPageViewPro(request);
			}
		}
		
		// 게시판 정보 DTO 리스트  = 게시판 정보 취득
		boardInfoDtoList = boardInfoServiceImpl.selectBoardTitleInfo();
		
		// 출력 정보 설정
		request.setAttribute("boardInfoDtoList", boardInfoDtoList);
		
		// 게시판 관리 페이지로 이동한다.
		return ConstantsWord.BOARD_BOARDMANAGE_PAGE;
	}
	
	/**
	 * 메소드명(물리) : POST형 게시판 생성
	 * 메소드명(논리) : postCreateBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * 
	 * 게시판을 생성하는 처리
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/createBoard", method = RequestMethod.POST)
	public void postCreateBoard(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) {

		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 세션 유저정보 저장용 MAP
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 입력 게시판 이름 취득
		String boardName = jsonMap.get("boardName").toString();
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// DB 게시판 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int dbScsFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
					
		// 로그인여부 확인
		// 비로그인 중일 경우 로그인 메세지를 반환한다.
		if (session.getAttribute("loginInfo") == null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "로그인을 해주세요."
			message = ConstantsWord.MSG100022;
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseUnsignedInt(sessionMap.get("userType").toString());
			
			// 관리자 레벨이 아닌 경우 권한없음 메세지를 반환한다.
			if(sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용 = "해당 권한이 없습니다.(관리자 전용)"
				message = ConstantsWord.MSG100023;
			} else {
				// 게시판 추가작업
				dbScsFlg = boardInfoServiceImpl.createBoard(boardName);
				
				// 게시판 추가작업 정상여부 확인
				if(dbScsFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
					// DB추가 작업 실패의 경우
					// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
					resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					// 메세지 내용  = "해당 게시판 추가에 실패하였습니다."
					message = ConstantsWord.MSG200001;
				} else {
					// DB추가 작업 성공의 경우
					// 메세지 내용  = "해당 게시판 추가에 성공하였습니다."
					message = ConstantsWord.MSG200002;
				}
			}
		}

		try {
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 게시판 삭제
	 * 메소드명(논리) : postDeleteBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * 
	 * 게시판을 삭제하는 처리
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.POST)
	public void postDeleteBoard(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) {

		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 삭제 게시판 코드 취득
		int boardCode = Integer.parseInt(jsonMap.get("boardCode").toString());
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// DB 게시판 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int dbScsFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		
		// 로그인여부 확인
		// 비로그인 중일 경우 로그인 메세지를 반환한다.
		if (session.getAttribute("loginInfo") == null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "로그인을 해주세요."
			message = ConstantsWord.MSG100022;
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			
			// 세션의 유저레벨
			sessionUserType = Integer.parseUnsignedInt(sessionMap.get("userType").toString());
			
			// 관리자 레벨이 아닌 경우 권한없음 메세지를 반환한다.
			if(sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용 = "해당 권한이 없습니다.(관리자 전용)"
				message = ConstantsWord.MSG100023;
			} else {
				// DB 게시판 삭제작업
				dbScsFlg = boardInfoServiceImpl.deleteBoard(boardCode);
				
				if(dbScsFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
					// DB삭제 작업 실패의 경우
					// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
					resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					// 메세지 내용  = "해당 게시판 삭제에 실패하였습니다."
					message = ConstantsWord.MSG200003;
				} else {
					// DB삭제 작업 성공의 경우
					// 메세지 내용  = "해당 게시판 삭제에 성공하였습니다."
					message = ConstantsWord.MSG200004;
				}
			}
		}

		try {
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 게시판 종류 조회
	 * 메소드명(논리) : postSelectBoardType
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * 
	 * 게시판 종류를 조회하는 처리
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/selectBoardType", method = RequestMethod.POST)
	public void postSelectBoardType(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) {

		// 출력용 Gson데이터
		Gson gson = new Gson();
		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 게시판종류 정보 DTO 리스트
		List<BoardTypeInfoDto> boardTypeInfoDtoList = new ArrayList<BoardTypeInfoDto>();
		// 게시판 정보 DTO
		BoardInfoDto boardInfoDto = new BoardInfoDto();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 게시판 코드 취득
		int boardCode = Integer.parseInt(jsonMap.get("boardCode").toString());
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
					
		// 로그인여부 확인
		// 비로그인 중일 경우 로그인 메세지를 반환한다.
		if (session.getAttribute("loginInfo") == null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "로그인을 해주세요."
			message = ConstantsWord.MSG100022;
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseUnsignedInt(sessionMap.get("userType").toString());
			
			// 관리자 레벨이 아닌 경우 권한없음 메세지를 반환한다.
			if(sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용 = "해당 권한이 없습니다.(관리자 전용)"
				message = ConstantsWord.MSG100023;
			} else {
				try {
					// DB 게시판 종류 취득 처리
					boardTypeInfoDtoList = boardInfoServiceImpl.selectBoardTypeInfoBc(boardCode);
					// DB 게시판 취득 처리
					boardInfoDto = boardInfoServiceImpl.selectBoardToCode(boardCode);
					
					// DB조회 작업 성공의 경우
					// 메세지 내용  = "게시판 종류를 불러왔습니다."
					message = ConstantsWord.MSG200005;
				} catch(Exception e) {
					// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
					resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					// 메세지 내용 = "해당 권한이 없습니다.(관리자 전용)"
					message = ConstantsWord.MSG200006;
				}
			}
		}

		try {
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
			json.addProperty("board", gson.toJson(boardInfoDto));
			json.add("boardTypeList", gson.toJsonTree(boardTypeInfoDtoList));
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 게시판 종류 생성
	 * 메소드명(논리) : postCreateBoardType
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * 
	 * 게시판 종류를 생성하는 처리
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/createBoardType", method = RequestMethod.POST)
	public void postCreateBoardType(@RequestBody String jsonData,
			HttpServletResponse response, HttpSession session) {

		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
				
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 게시판 코드 취득
		int boardCode = Integer.parseInt(jsonMap.get("boardCode").toString());
		// 입력 게시판 종류 이름 취득
		String boardTypeName = jsonMap.get("boardTypeName").toString();
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// DB 게시판 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int dbScsFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
					
		// 로그인여부 확인
		// 비로그인 중일 경우 로그인 메세지를 반환한다.
		if (session.getAttribute("loginInfo") == null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "로그인을 해주세요."
			message = ConstantsWord.MSG100022;
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseUnsignedInt(sessionMap.get("userType").toString());
			
			// 관리자 레벨이 아닌 경우 권한없음 메세지를 반환한다.
			if(sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용 = "해당 권한이 없습니다.(관리자 전용)"
				message = ConstantsWord.MSG100023;
			} else {
				// DB 게시판종류 추가작업
				dbScsFlg = boardInfoServiceImpl.createBoardType(boardCode, boardTypeName);
				
				if(dbScsFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
					// DB 게시판종류 추가작업 실패의 경우
					// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
					resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					// 메세지 내용  = "해당 게시판종류 추가에 실패하였습니다."
					message = ConstantsWord.MSG200007;
				} else {
					// DB 게시판종류 추가작업 성공의 경우
					// 메세지 내용  = "해당 게시판종류 추가에 성공하였습니다."
					message = ConstantsWord.MSG200008;
				}
			}
		}
		
		try {
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 게시판 종류 삭제
	 * 메소드명(논리) : postDeleteBoardType
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * 
	 * 게시판 종류를 삭제하는 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteBoardType", method = RequestMethod.POST)
	public void postDeleteBoardType(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) {

		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		// 삭제 게시판 종류 코드 취득
		int boardTypeCode = Integer.parseInt(jsonMap.get("boardTypeCode").toString());
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// DB 게시판 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int dbScsFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
					
		// 로그인여부 확인
		// 비로그인 중일 경우 로그인 메세지를 반환한다.
		if (session.getAttribute("loginInfo") == null) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "로그인을 해주세요."
			message = ConstantsWord.MSG100022;
		} else {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseUnsignedInt(sessionMap.get("userType").toString());
			
			// 관리자 레벨이 아닌 경우 권한없음 메세지를 반환한다.
			if(sessionUserType != ConstantsNum.USER_TYPE_TWO) {
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용 = "해당 권한이 없습니다.(관리자 전용)"
				message = ConstantsWord.MSG100023;
			} else {
				// DB 게시판 종류 삭제작업
				dbScsFlg = boardInfoServiceImpl.deleteBoardType(boardTypeCode);
				
				if(dbScsFlg != 0) {
					// DB 게시판 종류 삭제 작업 실패의 경우
					// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
					resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					// 메세지 내용  = "해당 게시판종류 삭제에 실패하였습니다."
					message = ConstantsWord.MSG200009;
				} else {
					// DB 게시판 종류 삭제 작업 성공의 경우
					// 메세지 내용  = "해당 게시판종류 삭제에 성공하였습니다."
					message = ConstantsWord.MSG200010;
				}
			}
		}
		
		try {
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

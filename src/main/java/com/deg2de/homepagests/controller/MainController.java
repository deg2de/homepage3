package com.deg2de.homepagests.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deg2de.homepagests.dto.BoardTypeInfoDto;
import com.deg2de.homepagests.dto.CommentViewInfoDto;
import com.deg2de.homepagests.dto.ImageFileInfoDto;
import com.deg2de.homepagests.dto.TextDesViewInfoDto;
import com.deg2de.homepagests.dto.UpdateTextDesDto;
import com.deg2de.homepagests.dto.WriteTextDesDto;
import com.deg2de.homepagests.service.BoardInfoServiceImpl;
import com.deg2de.homepagests.service.MainPageViewServiceImpl;
import com.deg2de.homepagests.service.TextInfoServiceImpl;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 메인 컨트롤러
 * 클래스명(논리) : MainController.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 * 
 * 메인 페이지에 관련된 내용을 처리하는 컨트롤러
 */
@Controller
public class MainController {
	
	/** 메인 페이지 뷰 서비스 도구 선언 */
	@Autowired
	private MainPageViewServiceImpl mainPageViewServiceImpl;
	/** 게시판 정보 서비스 도구 선언 */
	@Autowired
	private BoardInfoServiceImpl boardInfoServiceImpl;
	/** 게시글 정보 서비스 도구 선언 */
	@Autowired
	private TextInfoServiceImpl textInfoServiceImpl;
	
	/**
	 * 메소드명(물리) : POST형 헤더 게시판
	 * 메소드명(논리) : postHeaderBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 헤더에 표시되는 게시판 메뉴를 불러온다.
	 */
	@ResponseBody
	@RequestMapping(value = "/headerBoard", method = RequestMethod.POST)
	public void postHeaderBoard(@RequestBody String jsonData, HttpServletResponse response) throws Exception {

		// Gson
		Gson gson = new Gson();
		// 출력용 데이터
		JsonObject json = new JsonObject();
		// DB 조회 결과 MAP
		HashMap<String, List<String>> dbResultMap = new HashMap<String, List<String>>();
		
		// DB 헤더용 게시판 조회 작업
		dbResultMap = boardInfoServiceImpl.headerBoardList();
		
		try {
			json.add("boardList", gson.toJsonTree(dbResultMap).getAsJsonObject());
			json.add("boardName", gson.toJsonTree(dbResultMap.keySet()).getAsJsonArray());
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : GET형 메인페이지 게시판 리스트 (첫페이지, 첫글)
	 * 메소드명(논리) : getMainBoardList
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @return String : 메인페이지
	 * @throws Exception
	 * 
	 * 첫 메인페이지를 표시하는 작업을 진행한다.
	 */
	@RequestMapping(value={"/", "/main"}, method = RequestMethod.GET)
	public String getMainBoardList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}
	
	/**
	 * 메소드명(물리) : POST형 메인페이지 게시판 리스트
	 * 메소드명(논리) : postMainBoardList
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @return String : 메인페이지
	 * @throws Exception
	 * 
	 * 첫 메인페이지 이외 게시글이나 검색된 게시글을 표시하는 처리를 진행한다.
	 */
	@RequestMapping(value={"/", "/main"}, method = RequestMethod.POST)
	public String postMainBoardList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// "메메인 페이지 뷰 처리 (첫 페이지 외)"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewProAtr(request);
	}
	
	/**
	 * 메소드명(물리) : POST형 게시글 내용 읽기
	 * 메소드명(논리) : postReadTextDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 게시글의 내용과 댓글 내용을 호출한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/readTextDes", method = RequestMethod.POST)
	public void postReadTextDes(@RequestBody String jsonData, HttpServletResponse response, HttpSession session)
			throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 댓글 취득용 맵
		Map<String, Object> commentMap = new HashMap<String, Object>();
		// 반환용 페이지 출력용 게시글 정보
		TextDesViewInfoDto textDesViewInfoDto = new TextDesViewInfoDto();
		// 게시글 댓글 정보DTO 리스트
		List<CommentViewInfoDto> commentViewInfoDtoList = new ArrayList<CommentViewInfoDto>();
		// 출력용 데이터
		Gson gson = new Gson();
		// 출력용 데이터
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
		
		// 게시글 번호
		int textNo = Integer.parseInt(jsonMap.get("textNo").toString());
		// 댓글 페이지 번호 : 1
		int commentPageNo = ConstantsNum.COMMENT_PAGE_NO_ONE;
		// 추가 댓글 존재 유무 플래그(초기치 : 1(없음))
		int afterCmtFlg = ConstantsNum.AFTER_COMMENT_FLG_ONE;
		// 세션의 로그인 유저 아이디
		String sessionUserId = ConstantsWord.NULL;
		
		// 세션 정보 취득
		sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
		// 로그인 중이라면 로그인 아이디를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 세션의 로그인 유저 아이디
			sessionUserId = sessionMap.get("id").toString();
		}
		
		try {
			// 게시글 내용 호출
			textDesViewInfoDto = textInfoServiceImpl.readTextDes(textNo);
			// 댓글 내용 호출 (최대 10개)
			commentMap = textInfoServiceImpl.readCommentDes(textNo, sessionUserId, commentPageNo);
			
			// 게시글 댓글 정보DTO 리스트 설정
			commentViewInfoDtoList = (List<CommentViewInfoDto>) commentMap.get("commentViewInfoDtoList");
			// 추가 댓글 존재 유무 플래그 설정
			afterCmtFlg = Integer.parseInt(commentMap.get("afterCmtFlg").toString());
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		

		try {
			json.addProperty("textDesInfo", gson.toJson(textDesViewInfoDto));
			json.addProperty("afterCmtFlg", afterCmtFlg);
			json.addProperty("commentInfoList", gson.toJson(commentViewInfoDtoList));
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 댓글 읽기(첫 페이지)
	 * 메소드명(논리) : postFirstCommentDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 첫페이지 댓글을 출력한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/firstCommentDes", method = RequestMethod.POST)
	public void postFirstCommentDes(@RequestBody String jsonData, HttpServletResponse response, HttpSession session)
			throws Exception {

		// 세션 유저정보 저장용MAP
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 댓글 취득용MAP
		Map<String, Object> commentMap = new HashMap<String, Object>();
		// 게시글 댓글 정보DTO 리스트
		List<CommentViewInfoDto> commentViewInfoDtoList = new ArrayList<CommentViewInfoDto>();
		// 출력용 데이터
		Gson gson = new Gson();
		// 출력용 데이터
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
		
		// 게시글 번호
		int textNo = Integer.parseInt(jsonMap.get("textNo").toString());
		// 댓글 페이지 번호 : 1
		int commentPageNo = ConstantsNum.COMMENT_PAGE_NO_ONE;
		// 추가 댓글 존재 유무 플래그(초기치 : 1(없음))
		int afterCmtFlg = ConstantsNum.AFTER_COMMENT_FLG_ONE;
		// 세션의 로그인 유저 아이디
		String sessionUserId = ConstantsWord.NULL;
		
		// 세션 정보 취득
		sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
		// 로그인 중이라면 로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 세션의 로그인 유저 아이디
			sessionUserId = sessionMap.get("id").toString();
		}
		
		try {
			// 댓글 내용 호출
			commentMap = textInfoServiceImpl.readCommentDes(textNo, sessionUserId, commentPageNo);
			
			// 게시글 댓글 정보DTO 리스트 설정
			commentViewInfoDtoList = (List<CommentViewInfoDto>) commentMap.get("commentViewInfoDtoList");
			// 추가 댓글 존재 유무 플래그 설정
			afterCmtFlg = Integer.parseInt(commentMap.get("afterCmtFlg").toString());
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}

		try {
			json.addProperty("afterCmtFlg", afterCmtFlg);
			json.addProperty("commentInfoList", gson.toJson(commentViewInfoDtoList));
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 댓글 읽기(2 페이지 이상)
	 * 메소드명(논리) : postReadCommentDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 첫페이지 외의 댓글을 출력한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/readCommentDes", method = RequestMethod.POST)
	public void postReadCommentDes(@RequestBody String jsonData,
			HttpServletResponse response, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 댓글 취득용 맵
		Map<String, Object> commentMap = new HashMap<String, Object>();
		// 게시글 댓글 정보
		List<CommentViewInfoDto> commentViewInfoDtoList = new ArrayList<CommentViewInfoDto>();
		// 출력용 데이터
		Gson gson = new Gson();
		// 출력용 데이터
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
		
		// 게시글 번호
		int textNo = Integer.parseInt(jsonMap.get("textNo").toString());
		// 댓글 페이지 번호
		int commentPageNo = Integer.parseInt(jsonMap.get("commentPage").toString());
		// 추가 댓글 존재 유무 플래그(초기치 : 1(없음))
		int afterCmtFlg = ConstantsNum.AFTER_COMMENT_FLG_ONE;
		// 세션의 로그인 유저 아이디
		String sessionUserId = ConstantsWord.NULL;
		
		// 세션 정보 취득
		sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
		// 로그인 중이라면  로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 세션의 로그인 유저 아이디
			sessionUserId = sessionMap.get("id").toString();
		}
		
		try {
			// 댓글 내용 호출
			commentMap = textInfoServiceImpl.readCommentDes(textNo, sessionUserId, commentPageNo);
			commentViewInfoDtoList = (List<CommentViewInfoDto>) commentMap.get("commentViewInfoDtoList");
			afterCmtFlg = Integer.parseInt(commentMap.get("afterCmtFlg").toString());
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}

		try {
			json.addProperty("afterCmtFlg", afterCmtFlg);
			json.addProperty("commentInfoList", gson.toJson(commentViewInfoDtoList));
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 댓글 작성
	 * 메소드명(논리) : postWriteComment
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 댓글을 작성하는 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/writeComment", method = RequestMethod.POST)
	public void postWriteComment(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 출력용 데이터
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
				
		// 세션의 로그인 유저 아이디
		String sessionUserId = ConstantsWord.NULL;
		// 세션 정보 취득
		sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 게시글 번호
		int textNo = Integer.parseInt(jsonMap.get("textNo").toString());
		// 댓글 내용
		String textArea = jsonMap.get("textArea").toString();

		// 로그인 중이라면  로그인 아이디를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 세션의 로그인 유저 아이디
			sessionUserId = sessionMap.get("id").toString();
			
			try {
				// 댓글 작성 처리
				textInfoServiceImpl.writeCommentDes(textNo, sessionUserId, textArea);
			} catch (Exception e) {
				System.out.println(ConstantsWord.ERROR_START_POINT);
				System.out.println(e);
				System.out.println(ConstantsWord.ERROR_END_POINT);
				throw e;
			}
			
			// 메세지 내용 = "정상적으로 댓글이 작성되었습니다." 
			message = ConstantsWord.MSG300001;
		} else {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "로그인 후에 댓글을 작성할 수 있습니다."
			message = ConstantsWord.MSG300002;
		}

		try {
			json.addProperty("resultFlg", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 댓글 삭제
	 * 메소드명(논리) : postDeleteComment
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 댓글을 삭제하는 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
	public void postDeleteComment(@RequestBody String jsonData, HttpServletResponse response, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 출력용 데이터
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
		
		// 댓글 번호
		int commentNo = Integer.parseInt(jsonMap.get("commentNo").toString());
		// 세션의 로그인 유저 아이디
		String sessionUserId = ConstantsWord.NULL;
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		
		// 로그인 중이라면  로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 로그인 유저 아이디
			sessionUserId = sessionMap.get("id").toString();
			
			// 댓글 삭제 처리
			textInfoServiceImpl.deleteCommentDes(commentNo, sessionUserId);
			
			// 메세지 내용 = "정상적으로 댓글이 삭제되었습니다." 
			message = ConstantsWord.MSG300003;
		} else {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "삭제 권한이 없습니다."
			message = ConstantsWord.MSG300004;
		}

		try {
			json.addProperty("resultFlg", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 메인페이지 게시글 삭제
	 * 메소드명(논리) : postTextDelete
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 게시글 삭제처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/textDelete", method = RequestMethod.POST)
	public void postTextDelete(@RequestBody String jsonData, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 삭제처리후 반환 맵
		Map<String, Object> aftDeleteMap = new HashMap<String, Object>();
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
		
		// 게시글 번호
		int textNo = Integer.parseInt(jsonMap.get("textNo").toString());
		// 게시글 작성자
		String textWriter = jsonMap.get("textWriter").toString();
		// 이미지 파일 저장 경로
		String realFolder = ConstantsWord.NULL;
		// 로그인 상태 확인 플래그 (비 로그인 : 1)
		int loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ONE;
		// 관리자 등급 확인 플래그 (비 관리자 : 1)
		int managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ONE;
		// 로그인 세션정보와 게시글 작성자 일치 확인 플래그 (비일치 : 1)
		int sessionTxtIdChkFlg = ConstantsNum.SAME_CHK_FLG_ONE;
		// 세션의 유저레벨  (일반 : 0)
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 세션의 로그인 유저 아이디
		String sessionUserId = ConstantsWord.NULL;
		
		// 삭제권한 확인
		// 로그인 중이라면  로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 로그인 상태 확인 플래그 (로그인 : 0)
			loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ZERO;
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());
			// 세션의 로그인 유저 아이디
			sessionUserId = sessionMap.get("id").toString();
			
			// 관리자 레벨 확인, 관리자의 경우 반환 플래그를 관리자(0)로 설정한다.
			if (sessionUserType == ConstantsNum.USER_TYPE_TWO) {
				managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ZERO;
			}
			
			// 로그인 유저 아이디와 게시글 작성자 아이디 일치 비교
			if(sessionUserId.equals(textWriter)) {
				// 일치할 경우 반환 플래그에 일치(0)을 설정
				sessionTxtIdChkFlg = ConstantsNum.SAME_CHK_FLG_ZERO;
			}
		}
		
		// 삭제 권한이 있을 경우 삭제 처리 진행
		if (loginCheckFlg == ConstantsNum.LOGIN_CHK_FLG_ZERO && (managerCheckFlg == ConstantsNum.MANAGER_CHK_FLG_ZERO
				|| sessionTxtIdChkFlg == ConstantsNum.SAME_CHK_FLG_ZERO)) {
			// 이미지 저장경로 취득
			realFolder = request.getSession().getServletContext().getRealPath(ConstantsWord.IMG_SAVE_ADD).toString();
			
			// 게시글 삭제 (관리자, 작성자만 삭제 가능)
			aftDeleteMap = textInfoServiceImpl.deleteText(textNo, textWriter, loginCheckFlg, managerCheckFlg,
					sessionTxtIdChkFlg, realFolder);
		} else {
			// 삭제권한이 없는 경우
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			aftDeleteMap.put("resultFlg", ConstantsNum.PRO_CHK_FLG_FAIL);
			// 메세지 내용  = "삭제 권한이 없습니다."
			aftDeleteMap.put("resultMessage", ConstantsWord.MSG300004);
		}
		try {
			json.addProperty("resultFlg", aftDeleteMap.get("resultFlg").toString());
			json.addProperty("message", aftDeleteMap.get("resultMessage").toString());
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : GET형 게시판 작성
	 * 메소드명(논리) : getTextWrite
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 게시판 작성에 필요한 처리를 진행한 후 게시판 작성 페이지로 이동한다.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/textWrite"}, method = RequestMethod.GET)
	public String getTextWrite(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
			
		// 세션의 유저레벨 (일반 : 0)
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 로그인 상태 확인 플래그 (비 로그인 : 1)
		int loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ONE;
		// 관리자 등급 확인 플래그 (비 관리자 : 1)
		int managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ONE;
		// 임시 파일 저장 위치
		String tempFolderAdd = ConstantsWord.NULL;

		// 로그인 중이라면  로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 로그인 상태 확인 플래그 (로그인 : 0)
			loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ZERO;
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());
			
			// 관리자 레벨 확인, 관리자의 경우 반환 플래그를 관리자(0)로 설정한다.
			if (sessionUserType == ConstantsNum.USER_TYPE_TWO) {
				managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ZERO;
			}
		}

		// 로그인 상태, 관리자등급일 경우 작성페이지로 이동, 그렇지 않을 경우 메인페이지로 이동한다.
		if (loginCheckFlg == ConstantsNum.LOGIN_CHK_FLG_ZERO && managerCheckFlg == ConstantsNum.MANAGER_CHK_FLG_ZERO) {
			
			// 임시 저장경로
			tempFolderAdd = request.getSession().getServletContext()
					.getRealPath(ConstantsWord.IMG_USER_PIC_UPDATE + sessionMap.get("id").toString() + ConstantsWord.ADD_HYP);
			// 이전 이미지 삭제 처리
			textInfoServiceImpl.imageDelete(tempFolderAdd);
			// 게시판 정보 조회
			boardInfoServiceImpl.selectBoard(request);
			
			// 게시판 작성 페이지로 이동한다.
			return ConstantsWord.TEXTWRITE_PAGE;
		}
		
		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}
	
	/**
	 * 메소드명(물리) : POST형 작성용 게시판 종류 정보 취득
	 * 메소드명(논리) : postSelectTypeName
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 작성용 게시판 종류 정보 취득한다.
	 */
	@ResponseBody
	@RequestMapping(value = "/selectTypeName", method = RequestMethod.POST)
	public void postSelectTypeName(@RequestBody String jsonData, HttpServletResponse response) throws Exception {

		// Gson
		Gson gson = new Gson();
		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 게시판종류 정보DTO 리스트
		List<BoardTypeInfoDto> boardTypeInfoDtoList = new ArrayList<BoardTypeInfoDto>();
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
		
		// 게시판 코드
		int boardCode = Integer.parseInt(jsonMap.get("boardCode").toString());
		
		// 게시판 코드를 조건으로 게시판 종류 취득
		boardTypeInfoDtoList = boardInfoServiceImpl.selectBoardTypeInfoBc(boardCode);
		
		try {
			json.add("typeInfoList", gson.toJsonTree(boardTypeInfoDtoList));
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 작성용 게시판 이미지 임시 업로드
	 * 메소드명(논리) : textWriteImgUpload
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param MultipartFile file
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 작성용 게시판 이미지의 임시 업로드 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
	public void textWriteImgUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		
		// 출력 설정
		response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
		PrintWriter outPrint = response.getWriter();
		OutputStream out = null;

		// 현재시간을 가져와 Date형으로 저장한다
		Date date_now = new Date(System.currentTimeMillis());
		// 년월일시분초 14자리 포멧
		SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyyMMddHHmmss"); 
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		
		// 세션의 유저레벨 (일반 : 0)
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 로그인 상태 확인 플래그 (비 로그인 : 1)
		int loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ONE;
		// 관리자 등급 확인 플래그 (비 관리자 : 1)
		int managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ONE;
		// 로그인 아이디
		String loginId = ConstantsWord.NULL;
		// 현재시간 14자리
		String realTime = fourteen_format.format(date_now).toString();
		// 랜덤 이름 생성
		String uuid = ConstantsWord.NULL;
		// 파일 주소
		String filepath = ConstantsWord.NULL;
		// 임시저장 경로
		String realFolder = ConstantsWord.NULL;

		// 로그인 중이라면 로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 로그인 상태 확인 플래그 (로그인 : 0)
			loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ZERO;
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());

			// 관리자 레벨 확인, 관리자의 경우 반환 플래그를 관리자(0)로 설정한다.
			if (sessionUserType == ConstantsNum.USER_TYPE_TWO) {
				managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ZERO;
			}
		}

		// 로그인 상태, 관리자의 경우 처리를 실행한다.
		if (loginCheckFlg == ConstantsNum.LOGIN_CHK_FLG_ZERO && managerCheckFlg == ConstantsNum.MANAGER_CHK_FLG_ZERO) {
			// 정상의 경우 이미지 임시 업로드 처리를 시작한다.
			// 로그인 아이디 취득
			loginId = sessionMap.get("id").toString();

			// 파일 획득여부확인
			if (file != null) {
				// 파일용량이 있는지 확인한다.
				if (file.getSize() > 0 && file.getName() != null && !file.getName().equals(ConstantsWord.NO_WORD)) {
					// 이미지파일인지 확인한다.
					if (file.getContentType().toLowerCase().startsWith(ConstantsWord.IMG_ADD)) {
						// 바이트형식으로 파일 저장
						byte[] bytes = file.getBytes();

						// 임시 저장할 폴더 경로
						realFolder = request.getSession().getServletContext()
								.getRealPath(ConstantsWord.IMG_USER_PIC_UPDATE + loginId);

						// 파일 경로가 없을 경우 파일을 만들어 준다.
						File f = new File(realFolder);
						if (!f.exists()) {
							f.mkdirs();
						}

						// 랜덤 이름 생성
						uuid = UUID.randomUUID().toString();
						// 파일 주소
						filepath = realFolder + ConstantsWord.ADD_HYP + uuid + realTime;
						// 파일 저장
						out = new FileOutputStream(new File(filepath));
						out.write(bytes);

						outPrint.printf(request.getContextPath() + ConstantsWord.IMG_USER_PIC_UPDATE + loginId
								+ ConstantsWord.ADD_HYP + uuid + realTime);
						outPrint.close();
					}
				}
			}
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 작성용 게시판 작성정보 업로드
	 * 메소드명(논리) : postWriteTextUpload
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * 
	 * 작성된 게시글을 저장 하는 처리
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/writeTextUpload", method = RequestMethod.POST)
	public void postWriteTextUpload(@RequestBody String jsonData, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		// 이미지 주소
		ArrayList<String> imgAdds = new ArrayList<String>();
		// 게시글 작성용 DTO
		WriteTextDesDto writeTextDesDto = new WriteTextDesDto();
		// 이미지 업로드 처리 확인 맵
		Map<String, Object> imgUploadChkMap = new HashMap<String, Object>();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 저장 파일명 저장용 리스트
		List<String> saveFileNameList = new ArrayList<String>();
		// 출력용 데이터
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
		// 이미지 주소 설정
		imgAdds = (ArrayList<String>) jsonMap.get("imgAdd");
		
		// 게시판 코드
		int boardCode = Integer.parseInt(jsonMap.get("textInputBd").toString());
		// 게시판 종류 코드
		int typeCode = Integer.parseInt(jsonMap.get("textInputTy").toString());
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 이미지 갯수
		int imgSize = imgAdds.size();
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 로그인 상태 확인 플래그 (비 로그인 : 1)
		int loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ONE;
		// 관리자 등급 확인 플래그 (비 관리자 : 1)
		int managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ONE;
		// 이미지 업로드 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int imgUploadChkFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// DB 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int dbInsertChkFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 임시 저장 폴더 경로
		String tempFolderAdd = ConstantsWord.NULL;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 실제 저장 폴더 경로
		String realFolderAdd = request.getSession().getServletContext().getRealPath(ConstantsWord.IMG_SAVE_ADD_BAR);
		// 작성글 제목
		String textTitle = jsonMap.get("textWriteTitle").toString();
		// 작성글 내용
		String textDes = jsonMap.get("textDes").toString();
		
		

		// 로그인 중이라면 로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ZERO;
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());

			// 관리자 레벨 확인, 관리자의 경우 반환 플래그를 관리자(0)로 설정한다.
			if (sessionUserType == ConstantsNum.USER_TYPE_TWO) {
				managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ZERO;
			}
		}

		// 로그인 상태, 관리자등급일 경우 작성 작업을 진행, 그렇지 않을 경우 처리를 하지 않고 비권한 메세지를 처리한다.
		if (loginCheckFlg == ConstantsNum.LOGIN_CHK_FLG_ZERO && managerCheckFlg == ConstantsNum.MANAGER_CHK_FLG_ZERO) {
			
			// 임시 저장경로
			tempFolderAdd = request.getSession().getServletContext().getRealPath(
					ConstantsWord.IMG_USER_PIC_UPDATE + sessionMap.get("id").toString() + ConstantsWord.ADD_HYP);
			// 이미지 업로드 처리
			imgUploadChkMap = textInfoServiceImpl.imageSave(imgAdds, imgSize, tempFolderAdd, realFolderAdd);
			
			// 이미지 업로드 반환 플래그
			imgUploadChkFlg = Integer.parseInt(imgUploadChkMap.get("resultFlg").toString());
			
			if(imgUploadChkFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
				// 이미지 업로드 처리결과가 정상이 아닐 경우
				resultFlg = imgUploadChkFlg;
				// 메세지 내용  = "이미지 업로드 처리 도중 오류가 발생하였습니다."
				message = ConstantsWord.MSG300006;
			} else {
				// 정상적으로 이미지 업로드 처리가 완료 되었을 경우
				// DB에 저장할 정보 설정
				// 현재 시간 취득
				// 현재시간을 가져와 Date형으로 저장한다
				Date date_now = new Date(System.currentTimeMillis());
				// 년월일시분초 14자리 포멧
				SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				// 현재시간 14자리
				String realTime = fourteen_format.format(date_now).toString();
				
				writeTextDesDto.setTextBoardCode(boardCode);
				writeTextDesDto.setTextTypeCode(typeCode);
				writeTextDesDto.setTextId(sessionMap.get("id").toString());
				writeTextDesDto.setTextTitle(textTitle);
				writeTextDesDto.setTextDes(textDes);
				writeTextDesDto.setTextWriteDate(realTime);
				writeTextDesDto.setTextLastUpdDate(realTime);
				writeTextDesDto.setTextCount(1);
				
				// DB저장 처리
				// 저장 이미지 파일명
				if((List<String>) imgUploadChkMap.get("saveFileNameList") != null) {
					saveFileNameList = (List<String>) imgUploadChkMap.get("saveFileNameList");
				}
				
				// 게시글 저장 처리
				dbInsertChkFlg = textInfoServiceImpl.writeTextDes(writeTextDesDto, saveFileNameList, tempFolderAdd, realFolderAdd);
				
				// 게시글 저장 처리 정상 여부 확인
				if(dbInsertChkFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
					// DB처리가 비정상의 경우
					// 반환 플래그(비정상 : 1) 및 오류 메세지 설정
					resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					// 메세지 내용  = "글 내용 저장 처리 도중 오류가 발생했습니다."
					message = ConstantsWord.MSG300007;
				} else {
					// 정상 처리의 경우
					// 반환 플래그(정상 : 0) 및 작성 완료 메세지 설정
					// 메세지 내용  = "글을 작성하였습니다. (메인페이지로 이동)"
					message = ConstantsWord.MSG300008;
				}
			}
			
		} else {
			// 반환 플래그(비정상 : 1) 및 오류 메세지 설정
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "로그인중이 아니거나 글 작성 권한이 없습니다."
			message = ConstantsWord.MSG300009;
		}
		
		try {
			json.addProperty("resultFlg", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 게시글 작성
	 * 메소드명(논리) : postTextWrite
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * 게시글 작성 후 메인페이지로 이동한다.
	 */
	@RequestMapping(value={"/textWrite"}, method = RequestMethod.POST)
	public String postTextWrite(HttpServletRequest request) throws Exception {
		
		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}
	
	/**
	 * 메소드명(물리) : POST형 게시글 작성 취소
	 * 메소드명(논리) : postWriteTextCancel
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 게시글 작성 취소 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/writeTextCancel", method = RequestMethod.POST)
	public void postWriteTextCancel(@RequestBody String jsonData, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 출력용 데이터
		JsonObject json = new JsonObject();
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 로그인 상태 확인 플래그 (비 로그인 : 1)
		int loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ONE;
		// 관리자 등급 확인 플래그 (비 관리자 : 1)
		int managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ONE;
		// 임시 저장 폴더 경로
		String tempFolderAdd = ConstantsWord.NULL;
		// 반환 메세지
		String message = ConstantsWord.NULL;

		// 로그인 중이라면 로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 로그인 확인용 플래그 (로그인 : 0)
			loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ZERO;
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());

			// 관리자 레벨 확인, 관리자의 경우 반환 플래그를 관리자(0)로 설정한다.
			if (sessionUserType == ConstantsNum.USER_TYPE_TWO) {
				managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ZERO;
			}
		}

		// 로그인 상태, 관리자등급일 경우 작성 작업을 진행, 그렇지 않을 경우 처리를 하지 않고 비권한 메세지를 처리한다.
		if (loginCheckFlg == ConstantsNum.LOGIN_CHK_FLG_ZERO && managerCheckFlg == ConstantsNum.MANAGER_CHK_FLG_ZERO) {
			// 임시 저장경로
			tempFolderAdd = request.getSession().getServletContext()
					.getRealPath(ConstantsWord.IMG_USER_PIC_UPDATE + sessionMap.get("id").toString() + ConstantsWord.ADD_HYP);
			// 이미지 삭제 처리
			textInfoServiceImpl.imageDelete(tempFolderAdd);
			// 메세지 내용  = "글 작성을 취소합니다."
			message = ConstantsWord.MSG300010;
		} else {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "글 작성 권한이 없습니다. 상단 로고를 클릭하여 메인페이지로 이동하여 주세요."
			message = ConstantsWord.MSG300011;
		}
		
		try {
			json.addProperty("resultFlg", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : GET형 게시글 내용 업데이트
	 * 메소드명(논리) : getTextUpdate
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpSession session : 세션
	 * @return String : 이동 페이지
	 * @throws Exception
	 * 
	 * 게시글 수정 페이지로 이동하기 위한 처리
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/textUpdate"}, method = RequestMethod.GET)
	public String getTextUpdate(HttpServletRequest request, HttpSession session) throws Exception {
		
		// 글 내용 저장 DTO
		TextDesViewInfoDto textDesViewInfoDto = new TextDesViewInfoDto();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();

		// 게시글 번호
		int textNo = Integer.parseInt(request.getParameter("textNo").toString());
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 로그인 상태 확인 플래그 (비 로그인 : 1)
		int loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ONE;
		// 관리자 등급 확인 플래그 (비 관리자 : 1)
		int managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ONE;
		// 임시 저장 폴더 경로
		String tempFolderAdd = ConstantsWord.NULL;

		// 로그인 중이라면 로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 로그인 확인용 플래그 (로그인 : 0)
			loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ZERO;
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());

			// 관리자 레벨 확인, 관리자의 경우 반환 플래그를 관리자(0)로 설정한다.
			if (sessionUserType == ConstantsNum.USER_TYPE_TWO) {
				managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ZERO;
			}
		}

		// 로그인 상태, 관리자등급일 경우 작성 작업을 진행, 그렇지 않을 경우 처리를 하지 않고 비권한 메세지를 처리한다.
		if (loginCheckFlg == ConstantsNum.LOGIN_CHK_FLG_ZERO) {
			
			// 글 정보 호출
			textDesViewInfoDto = textInfoServiceImpl.readTextDes(textNo);
			
			// 글 작성자이거나 관리자의 경우 수정처리 허가
			if(sessionMap.get("id").toString().equals(textDesViewInfoDto.getTextWriter()) || managerCheckFlg == ConstantsNum.MANAGER_CHK_FLG_ZERO) {
				// 이전 이미지 삭제처리
				// 임시 저장경로
				tempFolderAdd = request.getSession().getServletContext()
						.getRealPath(ConstantsWord.IMG_USER_PIC_UPDATE + sessionMap.get("id").toString() + ConstantsWord.ADD_HYP);
				// 이미지 삭제 처리
				textInfoServiceImpl.imageDelete(tempFolderAdd);
				// 게시판 정보 조회
				boardInfoServiceImpl.selectBoard(request);
			} else {
				// 그 외에는 비허가, 메인페이지 이동 처리
				// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
				return mainPageViewServiceImpl.mainPageViewPro(request);
			}

		} else {
			// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
			return mainPageViewServiceImpl.mainPageViewPro(request);
		}
		
		// 게시글 정보
		request.setAttribute("textDesViewInfo", textDesViewInfoDto);
		
		// 게시글 수정 페이지로 이동한다.
		return ConstantsWord.TEXTUPDATE_PAGE;
	}
	
	/**
	 * 메소드명(물리) : POST형 수정용 게시판 수정정보 업로드
	 * 메소드명(논리) : postUpdateTextUpload
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 수정된 게시글을 저장하는 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateTextUpload", method = RequestMethod.POST)
	public void postUpdateTextUpload(@RequestBody String jsonData, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		// 이미지 파일 취득용 DTO 리스트
		List<ImageFileInfoDto> imageFileInfoDtoList = new ArrayList<ImageFileInfoDto>();
		// 게시글 수정용 DTO
		UpdateTextDesDto updateTextDesDto = new UpdateTextDesDto();
		// 갱신된 파일명 리스트
		List<String> writeImgNameList = new ArrayList<String>();
		// DB에 저장 및 임시저장파일에서 정규저장파일로 이동할 파일명 리스트
		List<String> uploadImgNameList = new ArrayList<String>();
		// DB로부터 삭제 및 정규저장파일에서 삭제할 파일명 리스트
		List<String> deleteImgNameList = new ArrayList<String>();
		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 출력용 데이터
		JsonObject json = new JsonObject();
		// 입력 JSON값 취득
		ObjectMapper mapper = new ObjectMapper();
		// 입력 JSON값 저장 MAP
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 이미지 주소
		ArrayList<String> imgAdds = new ArrayList<String>();
		
		try {
			jsonMap = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		// 이미지 주소 설정
		imgAdds = (ArrayList<String>) jsonMap.get("imgAdd");
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 게시글 번호
		int textNo = Integer.parseInt(jsonMap.get("textNo").toString());
		// 게시판 코드
		int boardCode = Integer.parseInt(jsonMap.get("textInputBd").toString());
		// 게시판 종류 코드
		int typeCode = Integer.parseInt(jsonMap.get("textInputTy").toString());
		// 이미지 갯수
		int imgSize = imgAdds.size();
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 로그인 상태 확인 플래그 (비 로그인 : 1)
		int loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ONE;
		// 관리자 등급 확인 플래그 (비 관리자 : 1)
		int managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ONE;
		// 이미지 업로드 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int imgUploadChkFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 이미지 삭제 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int imgDeleteChkFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// DB 게시글 업데이트 처리 확인 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int dbUpdateChkFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 작성글 제목
		String textTitle = jsonMap.get("textWriteTitle").toString();
		// 작성글 내용
		String textDes = jsonMap.get("textDes").toString();
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 임시 저장 폴더 경로
		String tempFolderAdd = ConstantsWord.NULL;
		// 실제 저장 폴더 경로
		String realFolderAdd = request.getSession().getServletContext().getRealPath(ConstantsWord.IMG_SAVE_ADD_BAR);
		
		// 로그인 중이라면 로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 로그인 확인용 플래그 (로그인 : 0)
			loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ZERO;
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());

			// 관리자 레벨 확인, 관리자의 경우 반환 플래그를 관리자(0)로 설정한다.
			if (sessionUserType == ConstantsNum.USER_TYPE_TWO) {
				managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ZERO;
			}
		}

		// 로그인 상태, 관리자등급일 경우 작성 작업을 진행, 그렇지 않을 경우 처리를 하지 않고 비권한 메세지를 처리한다.
		if (loginCheckFlg == ConstantsNum.LOGIN_CHK_FLG_ZERO && managerCheckFlg == ConstantsNum.MANAGER_CHK_FLG_ZERO) {

			// DB에 저장된 기존 이미지 파일 이름 취득
			imageFileInfoDtoList = textInfoServiceImpl.textImageSelect(textNo);
			
			// 임시 저장경로
			tempFolderAdd = request.getSession().getServletContext()
					.getRealPath(ConstantsWord.IMG_USER_PIC_UPDATE + sessionMap.get("id").toString() + ConstantsWord.ADD_HYP);
			
			// 업로드 폴더에 저장되어 있는 파일을 임시 저장 폴더로 이동한다.
			// 업데이트 될 이미지 이름 추출
			for(int imageCount = 0; imageCount < imgSize; imageCount++) {
				String[] imgAdd = imgAdds.get(imageCount).toString().split(ConstantsWord.ADD_HYP);
				writeImgNameList.add(imgAdd[imgAdd.length - 1]);
			}
			
			if(imageFileInfoDtoList.size() != ConstantsNum.IMAGE_SIZE_ZERO) {
				// 기존 이미지가 있을 경우 새로 작성된 이미지 제목과 비교하여 업로드 될 이미지와 삭제될 이미지를 구분 처리
				for(int dbImgCount = 0; dbImgCount < imageFileInfoDtoList.size(); dbImgCount++) {
					String dbImgName = imageFileInfoDtoList.get(dbImgCount).getImageFileName();
					// DB 게시글 업데이트 처리 확인 플래그 = 처리 확인용 플래그 (비정상 : 1)
					int dbImgDelFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					if(writeImgNameList.size() != ConstantsNum.IMAGE_SIZE_ZERO) {
						// 작성된 이미지가 있을 경우 구분 처리 시행
						for(int writeImgCount = 0; writeImgCount < writeImgNameList.size(); writeImgCount++) {
							String writeImgName = writeImgNameList.get(writeImgCount).toString();
							if(dbImgName.equals(writeImgName)) {
								// 작성된 이미지가 DB에 이미 있을 경우
								// DB 게시글 업데이트 처리 확인 플래그 = 처리 확인용 플래그 (비정상 : 0)
								dbImgDelFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
								writeImgNameList.remove(writeImgCount);
								writeImgCount = writeImgCount - 1;
							}
						}
						if(dbImgDelFlg == ConstantsNum.PRO_CHK_FLG_FAIL) {
							// 기존 DB에 저장된 이미지이름과 같은 작성 이미지이름이 없을 경우 DB파일이름을 삭제리스트로 처리
							deleteImgNameList.add(imageFileInfoDtoList.get(dbImgCount).getImageFileName());
						}
					} else {
						// 새로 작성된 이미지가 없을 경우 기존 DB에 저장된 이미지를 전부 삭제리스트로 구분 처리
						deleteImgNameList.add(imageFileInfoDtoList.get(dbImgCount).getImageFileName());
					}
				}
				// DB에 없는 작성이미지를 업로드리스트에 저장
				for(int writeImgCount = 0; writeImgCount < writeImgNameList.size(); writeImgCount++) {
					uploadImgNameList.add(writeImgNameList.get(writeImgCount).toString());
				}
			} else {
				// 기존 이미지가 없을 경우 새로 작성된 이미지를 전부 업로드될 이미지리스트에 저장
				for(int writeImgCount = 0; writeImgCount < writeImgNameList.size(); writeImgCount++) {
					uploadImgNameList.add(writeImgNameList.get(writeImgCount).toString());
				}
			}
			// 필요 없는 기존 이미지 삭제 처리
			imgDeleteChkFlg = textInfoServiceImpl.deleteImgFile(textNo, realFolderAdd, deleteImgNameList);
			// 이미지 수정 저장 처리
			imgUploadChkFlg = textInfoServiceImpl.updateImgFile(textNo, tempFolderAdd, realFolderAdd, uploadImgNameList);
			
			// 처리 정상 확인
			if (imgUploadChkFlg != ConstantsNum.PRO_CHK_FLG_SUCESS
					|| imgDeleteChkFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
				// 이미지 업로드 처리결과가 정상이 아닐 경우
				// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용  = "이미지 갱신 처리 도중 오류가 발생하였습니다."
				message = ConstantsWord.MSG300012;
			} else {
				// 정상적으로 이미지 업로드 처리가 완료 되었을 경우
				// DB에 저장할 정보 설정
				// 현재 시간 취득
				// 현재시간을 가져와 Date형으로 저장한다
				Date date_now = new Date(System.currentTimeMillis());
				// 년월일시분초 14자리 포멧
				SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				// 현재시간 14자리
				String realTime = fourteen_format.format(date_now).toString();
				
				updateTextDesDto.setTextNo(textNo);
				updateTextDesDto.setTextBoardCode(boardCode);
				updateTextDesDto.setTextTypeCode(typeCode);
				updateTextDesDto.setTextTitle(textTitle);
				updateTextDesDto.setTextDes(textDes);
				updateTextDesDto.setTextLastUpdDate(realTime);
				
				// DB저장 처리
				// 업데이트 게시글 저장 처리
				dbUpdateChkFlg = textInfoServiceImpl.updateTextDes(updateTextDesDto);
				
				// DB저장 처리 결과 확인
				if(dbUpdateChkFlg != ConstantsNum.PRO_CHK_FLG_SUCESS) {
					// DB처리가 비정상의 경우
					// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 1)
					resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					// 메세지 내용  = "글 내용 저장 처리 도중 오류가 발생했습니다."
					message = ConstantsWord.MSG300013;
				} else {
					// 정상 처리의 경우
					// 반환 플래그(정상 : 0) 및 작성 완료 메세지 설정
					// 메세지 내용  = "글을 수정하였습니다. (메인페이지로 이동)"
					message = ConstantsWord.MSG300014;
				}
			}
			
		} else {
			// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "로그인중이 아니거나 글 수정 권한이 없습니다."
			message = ConstantsWord.MSG300015;
		}
		
		try {
			json.addProperty("resultFlg", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 메소드명(물리) : POST형 게시글 수정
	 * 메소드명(논리) : postTextUpdToMain
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * 게시글 수정 후 메인 페이지로 이동하는 작업을 하기 위한 처리
	 */
	@RequestMapping(value={"/textUpdate"}, method = RequestMethod.POST)
	public String postTextUpdToMain(HttpServletRequest request) throws Exception {
		
		// "메인 페이지 뷰 처리"후 메인 페이지를 출력한다.
		return mainPageViewServiceImpl.mainPageViewPro(request);
	}
	
	/**
	 * 메소드명(물리) : POST형 게시글 수정 취소
	 * 메소드명(논리) : postUpdateTextCancel
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param String jsonData : JSON 데이터
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param HttpServletResponse response : HttpServlet 응답
	 * @param HttpSession session : 세션
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 게시글 수정 취소 처리를 진행한다.
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateTextCancel", method = RequestMethod.POST)
	public void postUpdateTextCancel(@RequestBody String jsonData, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		// 세션 유저정보 저장용 맵
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		// 출력용 데이터
		JsonObject json = new JsonObject();
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 세션의 유저 레벨 (초기치 : 0(일반))
		int sessionUserType = ConstantsNum.USER_TYPE_ZERO;
		// 로그인 상태 확인 플래그 (비 로그인 : 1)
		int loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ONE;
		// 관리자 등급 확인 플래그 (비 관리자 : 1)
		int managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ONE;
		// 임시 저장 폴더 경로
		String tempFolderAdd = null;

		// 로그인 중이라면 로그인플래그(0)를 설정한다.
		if (session.getAttribute("loginInfo") != null) {
			// 로그인 확인용 플래그 (로그인 : 0)
			loginCheckFlg = ConstantsNum.LOGIN_CHK_FLG_ZERO;
			// 세션 정보 취득
			sessionMap = (HashMap<String, Object>) session.getAttribute("loginInfo");
			// 세션의 유저레벨
			sessionUserType = Integer.parseInt(sessionMap.get("userType").toString());

			// 관리자 레벨 확인, 관리자의 경우 반환 플래그를 관리자(0)로 설정한다.
			if (sessionUserType == ConstantsNum.USER_TYPE_TWO) {
				managerCheckFlg = ConstantsNum.MANAGER_CHK_FLG_ZERO;
			}
		}

		// 로그인 상태, 관리자등급일 경우 작성 작업을 진행, 그렇지 않을 경우 처리를 하지 않고 비권한 메세지를 처리한다.
		if (loginCheckFlg == ConstantsNum.LOGIN_CHK_FLG_ZERO && managerCheckFlg == ConstantsNum.MANAGER_CHK_FLG_ZERO) {
			// 임시 저장경로
			tempFolderAdd = request.getSession().getServletContext()
					.getRealPath(ConstantsWord.IMG_USER_PIC_UPDATE + sessionMap.get("id").toString() + ConstantsWord.ADD_HYP);
			// 이미지 삭제 처리
			textInfoServiceImpl.imageDelete(tempFolderAdd);
			
			// 메세지 내용  = "글 수정을 취소합니다."
			message = ConstantsWord.MSG300016;
		} else {
			// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "글 수정 권한이 없습니다. 상단 로고를 클릭하여 메인페이지로 이동하여 주세요."
			message = ConstantsWord.MSG300017;
		}
		
		try {
			json.addProperty("resultFlg", resultFlg);
			json.addProperty("message", message);
			response.setContentType(ConstantsWord.TEXT_HTML_CHAR_UTF8);
			response.getWriter().println(json);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
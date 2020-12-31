package com.deg2de.homepagests.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deg2de.homepagests.dao.UserInfoIDao;
import com.deg2de.homepagests.dto.UserInfoManageDto;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;
import com.google.gson.JsonObject;

/**
 * 클래스명(물리) : 유저 관리 서비스 도구
 * 클래스명(논리) : UserManageService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-21
 * 마지막 수정 날짜 : 2020-11-22
 * 
 * 전체 유저의 정보를 관리하는 서비스 도구
 */
@Service("UserManage")
public class UserManageServiceImpl implements UserManageService{
	
	/** SQL세션 선언 */
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 메소드명(물리) : 관리용 유저 정보 취득
	 * 메소드명(논리) : selectAllUserInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @return List<UserInfoManageDto> : 관리용 유저정보 취득용 DTO 리스트
	 * @throws Exception
	 * 
	 * 관리용 전체 유저 정보를 취득한다.
	 */
	@Override
	public List<UserInfoManageDto> selectAllUserInfo() throws Exception {
		
		// 관리용 유저 정보 DTO 리스트
		List<UserInfoManageDto> userInfoManageDtoList = new ArrayList<UserInfoManageDto>();
		
		try {
			// 게시판 데이터베이스 취득
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			// 취득한 정보를 "관리용 유저 정보 DTO 리스트"에 저장한다.
			userInfoManageDtoList = userInfoIDao.selsetAllUserInfo();
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		return userInfoManageDtoList;
	}
	
	/**
	 * 메소드명(물리) : 관리용 유저 정보 취득 (조건 : 아이디)
	 * 메소드명(논리) : selectUserInfoId
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @return List<UserInfoManageDto> : 관리용 유저정보 취득용 DTO 리스트
	 * @throws Exception
	 * 
	 * 아이디를 조건으로 관리용 유저 정보를 취득한다.
	 */
	@Override
	public List<UserInfoManageDto> selectUserInfoId(String id) throws Exception {

		// 관리용 유저 정보 DTO 리스트
		List<UserInfoManageDto> userInfoManageDtoList = new ArrayList<UserInfoManageDto>();

		try {
			// 입력파라미터.ID를 조건으로 DB로부터 유저 정보 취득
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			// 취득한 정보를 "관리용 유저 정보 DTO 리스트"에 저장한다.
			userInfoManageDtoList = userInfoIDao.selectmanageUserId(id);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}

		return userInfoManageDtoList;
	}
	
	/**
	 * 메소드명(물리) : 관리용 유저 정보 취득 (조건 : 작성자)
	 * 메소드명(논리) : selectUserInfoName
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @return List<UserInfoManageDto> : 관리용 유저정보 취득용 DTO 리스트
	 * @throws Exception
	 * 
	 * 작성자를 조건으로 관리용 유저 정보를 취득한다.
	 */
	@Override
	public List<UserInfoManageDto> selectUserInfoName(String name) throws Exception {

		// 관리용 유저 정보 DTO 리스트
		List<UserInfoManageDto> userInfoManageDtoList = new ArrayList<UserInfoManageDto>();

		try {
			// 입력파라미터.작성자를 조건으로 DB로부터 유저 정보 취득
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			// 취득한 정보를 "관리용 유저 정보 DTO 리스트"에 저장한다.
			userInfoManageDtoList = userInfoIDao.selectmanageUserName(name);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		return userInfoManageDtoList;
	}

	/**
	 * 메소드명(물리) : 유저 정보 삭제 처리 (조건 : 아이디)
	 * 메소드명(논리) : deleteUserId
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-22
	 * 마지막 수정 날짜 : 2020-11-22
	 * 
	 * @param String id : 아이디
	 * @return JsonObject : 출력용 JSON 데이터
	 * 
	 * 아이디를 조건으로 유저 정보를 검색하여 삭제처리를 진행한다.
	 */
	@Override
	public JsonObject deleteUserId(String id) {
		
		// 출력용 JSON 데이터
		JsonObject json = new JsonObject();
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;

				
		try {
			// DB에서 탈퇴되는 유저 정보 삭제
			UserInfoIDao userInfoIDao = sqlSession.getMapper(UserInfoIDao.class);
			userInfoIDao.deleteUserId(id);
		} catch(Exception e) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용 = "해당 유저의 탈퇴처리가 실패하였습니다."
			message = ConstantsWord.MSG100020;

			// 반환값 설정
			json.addProperty("result", resultFlg);
			json.addProperty("message", message);
		}
		
		// 정상 처리의 경우
		// 메세지 내용 = "탈퇴되었습니다."
		message = ConstantsWord.MSG100021;
		// 반환값 설정
		json.addProperty("result", resultFlg);
		json.addProperty("message", message);
		
		return json;
	}
	
	/**
	 * 메소드명(물리) : 유저 정보 페이지 처리
	 * 메소드명(논리) : userInfoPagePro
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param List<UserInfoManageDto> userInfoManageDtoList : 관리용 유저정보 취득용 DTO 리스트
	 * @param int pageNo : 페이지 번호
	 * @param int dataSize : 표시할 데이터 개수
	 * @return String : 사용자 관리 페이지
	 * 
	 * 페이지에 표시할 유저 정보를 가공한다.
	 */
	@Override
	public String userInfoPagePro(HttpServletRequest request, List<UserInfoManageDto> userInfoManageDtoList, int pageNo,
			int dataSize) {

		// 출력 페이지리스트 = "페이지별 데이터 출력용 유저관리데이터 가공기능" 메소드
		userPageListPro(request, userInfoManageDtoList, pageNo, dataSize);
		// 페이지 번호 정보Map = "페이지 번호 출력용 기능" 메소드
		pageNoHandle(request, userInfoManageDtoList, pageNo, dataSize);
		
		// 사용자 관리 페이지로 이동한다.
		return ConstantsWord.USERMANAGE_PAGE;
		
	}
	
	/**
	 * 메소드명(물리) : 페이지별 데이터 출력용 유저관리데이터 가공기능
	 * 메소드명(논리) : userPageListPro
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param List<UserInfoManageDto> userInfoManageDtoList : 관리용 유저정보 취득용 DTO 리스트
	 * @param int pageNo : 페이지 번호
	 * @param int dataSize : 표시할 데이터 개수
	 * @return void : 없음
	 * 
	 * 입력받은 페이지 번호에 들어갈 데이터를 저장한다.
	 */
	private void userPageListPro(HttpServletRequest request,
			List<UserInfoManageDto> userInfoManageList, int pageNo, int dataSize) {
		// 출력 페이지리스트
		List<UserInfoManageDto> pagelist = new ArrayList<UserInfoManageDto>();

		// 게시물 출력 가공처리
		// '페이지리스트'의 데이터를 dataSize개씩 출력할 수 있도록 가공처리를 한다.
		if (pageNo == 1) {
			// 페이지 번호가 '1'의 경우 UserInfoManageDto리스트의 데이터를 dataSize개 취득하여 '페이지리스트'에 저장한다.
			for (int pagesize = 0; pagesize < dataSize; pagesize++) {
				// 해당 UserInfoManageDto리스트의 데이터가 50개 미만일 경우 해당 갯수만큼 취득하도록 한다.
				if(userInfoManageList.size() - 1 < pagesize){
					break;
				}
				pagelist.add(userInfoManageList.get(pageNo * pagesize));
			}
		} else {
			// 페이지 번호가 '1'이 아닌 경우 페이지 번호에 맞는 위치로부터 UserInfoManageDto리스트의 데이터를 50개 취득하여 '페이지리스트'에 저장한다.
			int startpoint = pageNo * dataSize - dataSize;
			for (int pagesize = 0; pagesize < dataSize; pagesize++) {
				// 남은 UserInfoManageDto리스트의 데이터가 50개 미만일 경우 해당 갯수만큼 취득하도록 한다.
				if(userInfoManageList.size() - 1 < startpoint + pagesize){
					break;
				}
				pagelist.add(userInfoManageList.get(startpoint + pagesize));
			}
		}
		
		// Attribute정보 저장
		// 현재 페이지 번호 = 현재 페이지
		request.setAttribute("pageno", pageNo);
		// 리스트 내용 = 페이지리스트
		request.setAttribute("userInfoManageList", pagelist);
	}
	
	/**
	 * 메소드명(물리) : 페이지 번호 출력용 기능
	 * 메소드명(논리) : pageNoHandle
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-21
	 * 마지막 수정 날짜 : 2020-11-21
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @param List<UserInfoManageDto> userInfoManageDtoList : 관리용 유저정보 취득용 DTO 리스트
	 * @param int pageNo : 페이지 번호
	 * @param int dataSize : 표시할 데이터 개수
	 * @return void : 없음
	 * 
	 * 페이지 번호 출력 처리를 진행하는 기능
	 */
	private void pageNoHandle(HttpServletRequest request,
			List<UserInfoManageDto> userInfoManageDtoList, int pageNo, int dataSize) {

		// 총 데이터의 사이즈 = "관리용 유저정보 취득용 DTO 리스트" 사이즈
		int dbsize = userInfoManageDtoList.size();
		// 총 페이지수 = 총 데이터의 사이즈 / 표시할 데이터 개수
		int totalpagesize = dbsize / dataSize;
		// 첫 페이지 번호
		int firstlistpage = 1;
		// 마지막 페이지 번호
		int lastlistpage = 10;
		// 페이지 체크용 플래그
		boolean listpagecheckflg = false;
		
		// 페이지 번호 리스트 출력 처리
		// 표시할 데이터 개수의 잔여에 따른 총 페이지 수 계산
		if((dbsize % dataSize) != 0) {
			// 남은 데이터가 있는 경우
			// 총 페이지 수 = 총 페이지수 + 1
			totalpagesize = totalpagesize + 1;
		}

		// 페이지번호리스트를 10개씩 출력하도록 한다.
		// 마지막 리스트의 갯수가 10개 미만일 경우 남은 리스트만 출력한다.
		while(listpagecheckflg == false) {
			if(totalpagesize == 0) {
				lastlistpage = 1;
				listpagecheckflg = true;
			}
			if(lastlistpage > totalpagesize) {
				lastlistpage = totalpagesize;
			}
			if(pageNo >= firstlistpage && pageNo <= lastlistpage) {
				listpagecheckflg = true;
			} else {
				firstlistpage += 10;
				lastlistpage += 10;
			}
		}

		// 총 페이지 수 = 총 페이지 수
		request.setAttribute("totalpagesize", totalpagesize);
		// 페이지 번호 리스트 (전) = 첫 페이지 번호
		request.setAttribute("firstlistpage", firstlistpage);
		// 페이지 번호 리스트 (후) = 마지막 페이지 번호
		request.setAttribute("lastlistpage", lastlistpage);
	}

}

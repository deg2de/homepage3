package com.deg2de.homepagests.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deg2de.homepagests.dao.BoardInfoIDao;
import com.deg2de.homepagests.dto.BoardInfoDto;
import com.deg2de.homepagests.dto.BoardNameDto;
import com.deg2de.homepagests.dto.BoardTypeInfoDto;
import com.deg2de.homepagests.dto.BoardTypeNameDto;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;

/**
 * 클래스명(물리) : 게시판 정보 서비스 도구
 * 클래스명(논리) : BoardInfoServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-24
 * 마지막 수정 날짜 : 2020-11-24
 * 
 * 게시판 정보 서비스 도구
 */
@Service("BoardInfo")
public class BoardInfoServiceImpl implements BoardInfoService {

	/** SQL세션 선언 */
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 메소드명(물리) : 게시판 정보 취득
	 * 메소드명(논리) : selectBoardTitleInfo
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @return List<BoardInfoDto> : 게시판 정보DTO 리스트
	 * 
	 * 모든 게시판의 정보를 취득하여 게시판 정보DTO 리스트로 반환한다.
	 * @throws Exception 
	 */
	@Override
	public List<BoardInfoDto> selectBoardTitleInfo() throws Exception {
		
		// 게시판 정보 DTO 리스트
		List<BoardInfoDto> boardInfoDtoList = new ArrayList<BoardInfoDto>();
		
		try {
			// 게시판 데이터베이스 취득
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardInfoDtoList = boardInfoIDao.selectBoardInfo();
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// 게시판 정보 DTO 리스트 반환
		return boardInfoDtoList;
	}
	
	/**
	 * 메소드명(물리) : 게시판 정보 조회
	 * 메소드명(논리) : selectBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 게시판 정보를 조회한다.
	 */
	@Override
	public void selectBoard(HttpServletRequest request) throws Exception {
		
		// 게시판 정보 DTO 리스트
		List<BoardInfoDto> boardInfoDtoList = new ArrayList<BoardInfoDto>();
		
		try {
			// 게시판 데이터베이스 취득
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardInfoDtoList = boardInfoIDao.selectBoardInfo();
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		request.setAttribute("selectBoardInfoList", boardInfoDtoList);
	}

	/**
	 * 메소드명(물리) : 게시판 추가
	 * 메소드명(논리) : createBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param String boardName : 게시판 이름
	 * @return int : 반환값 설정 플래그
	 * 
	 * 게시판을 추가하는 작업
	 */
	@Override
	public int createBoard(String boardName) {
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		
		try {
			// 게시판 생성
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardInfoIDao.createBoard(boardName);
		} catch(Exception e) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
		}
		
		// 반환값 설정 플래그를 반환한다.
		return resultFlg;
	}

	/**
	 * 메소드명(물리) : 게시판 코드를 조건으로 게시판 삭제
	 * 메소드명(논리) : deleteBoard
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardCode : 게시판 코드
	 * @return int : 반환값 설정 플래그
	 * 
	 * 게시판 코드를 조건으로 게시판 삭제하는 처리
	 */
	@Override
	public int deleteBoard(int boardCode) {
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		
		try {
			// 게시판 삭제
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardInfoIDao.deleteBoard(boardCode);
		} catch(Exception e) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
		}
		
		// 반환값 설정 플래그를 반환한다.
		return resultFlg;
	}

	/**
	 * 메소드명(물리) : 게시판 코드를 조건으로 게시판 종류 취득
	 * 메소드명(논리) : selectBoardTypeInfoBc
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardCode : 게시판 코드
	 * @return List<BoardTypeInfoDto> : 게시판 종류 정보DTO 리스트
	 * @throws Exception 
	 * 
	 * 게시판 코드를 조건으로 게시판 종류를 취득하는 처리
	 */
	@Override
	public List<BoardTypeInfoDto> selectBoardTypeInfoBc(int boardCode) throws Exception {
		// 게시판 종류 정보 DTO 리스트
		List<BoardTypeInfoDto> boardTypeInfoDtoList = new ArrayList<BoardTypeInfoDto>();
		
		try {
			// 게시판 종류 데이터베이스 취득
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardTypeInfoDtoList = boardInfoIDao.selectBoardTypeInfoBc(boardCode);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		// 게시판 종류 정보 DTO 리스트를 반환한다.
		return boardTypeInfoDtoList;
	}

	/**
	 * 메소드명(물리) : 게시판 코드를 조건으로 게시판 취득
	 * 메소드명(논리) : selectBoardToCode
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardCode : 게시판 코드
	 * @return BoardInfoDto : 게시판 정보DTO
	 * @throws Exception 
	 * 
	 * 게시판 코드를 조건으로 게시판을 취득하는 처리
	 */
	@Override
	public BoardInfoDto selectBoardToCode(int boardCode) throws Exception {
		// 게시판 정보DTO
		BoardInfoDto boardInfoDto = new BoardInfoDto();
		
		try {
			// 게시판 데이터베이스 취득
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardInfoDto = boardInfoIDao.selectBoardToCode(boardCode);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// 게시판 정보DTO를 반환한다.
		return boardInfoDto;
	}
	
	/**
	 * 메소드명(물리) : 게시판 종류 생성
	 * 메소드명(논리) : createBoardType
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardCode : 게시판 코드
	 * @param String boardTypeName : 게시판 종류 이름
	 * @return int : 반환값 설정 플래그
	 * 
	 * 게시판 종류를 생성한다.
	 */
	@Override
	public int createBoardType(int boardCode, String boardTypeName) {
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		
		try {
			// 게시판 생성
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardInfoIDao.createBoardType(boardTypeName, boardCode);
		} catch(Exception e) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
		}
		
		// 반환값 설정 플래그를 반환한다.
		return resultFlg;
	}
	
	/**
	 * 메소드명(물리) : 게시판 종류 삭제
	 * 메소드명(논리) : deleteBoardType
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-24
	 * 마지막 수정 날짜 : 2020-11-24
	 * 
	 * @param int boardTypeCode : 게시판 종류 코드
	 * @return int : 반환값 설정 플래그
	 * 
	 * 게시판 종류를 삭제하는 처리
	 */
	@Override
	public int deleteBoardType(int boardTypeCode) {
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		
		try {
			// 게시판 삭제
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardInfoIDao.deleteBoardType(boardTypeCode);
		} catch(Exception e) {
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
		}
		
		// 반환값 설정 플래그를 반환한다.
		return resultFlg;
	}

	/**
	 * 메소드명(물리) : 헤더용 게시판 조회 작업
	 * 메소드명(논리) : headerBoardList
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @return HashMap<String, List<String>> : 출력용 게시판 맵
	 * 
	 * 헤더에 표시될 게시판 메뉴를 조회한다.
	 */
	@Override
	public HashMap<String, List<String>> headerBoardList() throws Exception{
		
		// 출력용 게시판 맵
		HashMap<String, List<String>> resultMap = new HashMap<String, List<String>>();
		// 게시판이름 리스트DTO
		List<BoardNameDto> boardNameList = new ArrayList<BoardNameDto>();
		// 게시판종류 리스트(반복처리용)
		List<String> boardTypeList = new ArrayList<String>();
		// 게시판, 종류이름 리스트DTO
		List<BoardTypeNameDto> boardTypeNameList = new ArrayList<BoardTypeNameDto>();
		
		// 게시판이름(반복처리용)
		String boardNameFor = ConstantsWord.NULL;
		
		try {
			// DB로부터 게시판정보 취득
			BoardInfoIDao boardInfoIDao = sqlSession.getMapper(BoardInfoIDao.class);
			boardNameList = boardInfoIDao.selectBoardName();
			boardTypeNameList = boardInfoIDao.selectBoardTypeName();
			
			// 출력용 게시판이름, 종류 처리작업
			for(int boardNameListCount = 0; boardNameListCount < boardNameList.size(); boardNameListCount++) {
				boardNameFor = boardNameList.get(boardNameListCount).getBoardName().toString();
				for(int boardTypeNameCount = 0; boardTypeNameCount< boardTypeNameList.size(); boardTypeNameCount++) {
					if(boardNameFor.equals(boardTypeNameList.get(boardTypeNameCount).getBoardName().toString())) {
						boardTypeList.add(boardTypeNameList.get(boardTypeNameCount).getTypeName().toString());
					}
				}
				
				// 출력값 격납
				resultMap.put(boardNameFor, boardTypeList);
				
				// 반복용 변수 초기화
				boardNameFor = null;
				boardTypeList = new ArrayList<String>();
			}
		}  catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// 출력용 게시판 맵 반환
		return resultMap;
	}
}

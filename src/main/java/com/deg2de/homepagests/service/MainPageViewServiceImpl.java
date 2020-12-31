package com.deg2de.homepagests.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deg2de.homepagests.dao.TextInfoIDao;
import com.deg2de.homepagests.dto.CommentInfoDto;
import com.deg2de.homepagests.dto.TextDesInfoDto;
import com.deg2de.homepagests.dto.TextDesViewInfoDto;
import com.deg2de.homepagests.dto.TextListInfoDto;
import com.deg2de.homepagests.module.WordCheck;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;

/**
 * 클래스명(물리) : 메인 페이지 뷰 서비스 도구
 * 클래스명(논리) : MainPageViewServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-17
 * 마지막 수정 날짜 : 2020-11-17
 * 
 * 메인 페이지 뷰 서비스 도구
 */
@Service("main")
public class MainPageViewServiceImpl implements MainPageViewService{

	/** SQL세션 선언 */
	@Autowired
	private SqlSession sqlSession;
	
	/** 공통부품.문자체크 */
	WordCheck wordCheck = new WordCheck();
	
	/**
	 * 메소드명(물리) : 메인 페이지 뷰 처리 (첫 페이지)
	 * 메소드명(논리) : mainPageViewPro
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-17
	 * 마지막 수정 날짜 : 2020-11-17
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * 메인 페이지 출력에 필요한 정보를 설정한 후 메인 페이지로 이동한다.
	 */
	@Override
	public String mainPageViewPro(HttpServletRequest request) throws Exception {
		
		// 최근 게시글 5개 조회 (첫 페이지)
		rctText5SelectFst(request);
		// 최근 게시글 내용 검색
		newTextDesSelect(request);
		
		// 메인 페이지를 반환한다.
		return ConstantsWord.MAIN_PAGE;
	}
	
	/**
	 * 메소드명(물리) : 메인 페이지 뷰 처리 (첫 페이지 외)
	 * 메소드명(논리) : mainPageViewProAtr
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return String : 메인 페이지
	 * @throws Exception
	 * 
	 * 메인 페이지 출력에 필요한 정보를 설정한 후 메인 페이지로 이동한다.
	 */
	@Override
	public String mainPageViewProAtr(HttpServletRequest request) throws Exception {
		
		// 게시글 5개 조회 (입력받은 페이지 페이지)
		rctText5SelectAtr(request);
		// 최근 게시글 내용 검색
		newTextDesSelect(request);
		
		// 메인 페이지를 반환한다.
		return ConstantsWord.MAIN_PAGE;
	}
	
	/**
	 * 메소드명(물리) : 최근 게시글 5개 조회 (첫 페이지)
	 * 메소드명(논리) : rctText5SelectFst
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-17
	 * 마지막 수정 날짜 : 2020-11-17
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 최근 게시글 5개를 조회한다.
	 */
	private void rctText5SelectFst(HttpServletRequest request) throws Exception {
		
		// 게시글 5개의 정보DTO 리스트
		List<TextListInfoDto> textList5InfoDtoList = new ArrayList<TextListInfoDto>();
		// 게시글의 정보DTO 리스트
		List<TextListInfoDto> textListInfoDtoList = new ArrayList<TextListInfoDto>();

		// 총 페이지 수 (초기치 : 0)
		int totalpagesize = ConstantsNum.TOTAL_PAGE_SIZE_ZERO;
		// 첫 페이지 번호 (초기치 : 1)
		int firstlistpage = ConstantsNum.FIRST_LIST_PAGE_ONE;
		// 마지막 페이지 번호 (초기치 : "표시할 페이지 버튼 수 : 10")
		int lastlistpage = ConstantsNum.VIEW_PAGE_BTN_CNT;
		// 페이지 체크용 플래그 (초기치 : 1)(처리 종료 : 0, 처리 필요 : 1)
		int listpagecheckflg = ConstantsNum.PRO_CHK_FLG_ONE;
				
		// 데이터베이스로부터 모든 게시글 조회 처리
		// 데이터베이스로부터 모든 게시글을 조회하여 "게시글의 정보DTO 리스트"에 저장한다.
		try {
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			textListInfoDtoList = textInfoIDao.selectAllTextList();
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// 페이지별 게시글 5개 설정 처리
		if (textListInfoDtoList.size() < ConstantsNum.VIEW_TEXT_LIST_SIZE) {
			// '게시글의 정보DTO 리스트'가 '페이지당 출력 게시글 수 : 5'보다 작을 경우
			// '게시글의 정보DTO 리스트' 크기만큼 '게시글 5개의 정보DTO 리스트'에 저장
			for (int listCount = 0; listCount < textListInfoDtoList.size(); listCount++) {
				textList5InfoDtoList.add(textListInfoDtoList.get(listCount));
			}
		} else {
			// '게시글의 정보DTO 리스트'가 '페이지당 출력 게시글 수 : 5'보다 클 경우
			// '페이지당 출력 게시글 수 : 5'만큼 '게시글 5개의 정보DTO 리스트'에 저장
			for (int listCount = 0; listCount < ConstantsNum.VIEW_TEXT_LIST_SIZE; listCount++) {
				textList5InfoDtoList.add(textListInfoDtoList.get(listCount));
			}
		}
		
		// 페이지 번호 정보 설정 처리
		// 페이지 크기 계산
		if ((textListInfoDtoList.size() % ConstantsNum.VIEW_TEXT_LIST_SIZE) != 0) {
			// "게시글의 정보DTO 리스트"의 크기를 "페이지당 출력 게시글 수 : 5"만큼 나눈 후
			// 잔여 "게시글의 정보DTO 리스트"가 있는 경우 페이지 "총 페이지 수"를 1페이지 추가하여 설정한다.
			totalpagesize = (textListInfoDtoList.size() / ConstantsNum.VIEW_TEXT_LIST_SIZE) + 1;
		} else {
			// "게시글의 정보DTO 리스트"의 크기를 "페이지당 출력 게시글 수 : 5"만큼 나눈 후
			// 잔여 "게시글의 정보DTO 리스트"가 없는 경우 나눠진 결과 페이지 수를 설정한다.
			totalpagesize = textListInfoDtoList.size() / ConstantsNum.VIEW_TEXT_LIST_SIZE;
		}

		// 페이지 번호를 "표시할 페이지 버튼 수 : 10"개씩 출력하도록 한다.
		// 마지막 페이지가 '표시할 페이지 버튼 수 : 10'개 미만일 경우 남은 리스트만 출력한다.
		while (listpagecheckflg == ConstantsNum.PRO_CHK_FLG_ONE) {
			if (totalpagesize == ConstantsNum.TOTAL_PAGE_SIZE_ZERO) {
				lastlistpage = ConstantsNum.LAST_LIST_PAGE_ONE;
				listpagecheckflg = ConstantsNum.PRO_CHK_FLG_ZERO;
			}
			if (lastlistpage > totalpagesize) {
				lastlistpage = totalpagesize;
			}
			if (ConstantsNum.PAGE_NO_ONE >= firstlistpage && ConstantsNum.PAGE_NO_ONE <= lastlistpage) {
				listpagecheckflg = ConstantsNum.PRO_CHK_FLG_ZERO;
			} else {
				firstlistpage += ConstantsNum.VIEW_PAGE_BTN_CNT;
				lastlistpage += ConstantsNum.VIEW_PAGE_BTN_CNT;
			}
		}

		// 출력 Attribute정보 저장 처리
		// 현재 페이지 번호
		request.setAttribute("pageno", ConstantsNum.PAGE_NO_ONE);
		// 총 페이지 수
		request.setAttribute("totalpagesize", totalpagesize);
		// 첫 페이지 번호
		request.setAttribute("firstlistpage", firstlistpage);
		// 마지막 페이지 번호
		request.setAttribute("lastlistpage", lastlistpage);
		// 게시글 5개 정보 리스트
		request.setAttribute("textInfoList", textList5InfoDtoList);
	}
	
	/**
	 * 메소드명(물리) : 게시글 5개 조회 (입력받은 페이지)
	 * 메소드명(논리) : rctText5SelectAtr
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 게시글 5개를 조회한다.
	 */
	private void rctText5SelectAtr(HttpServletRequest request) throws Exception {
		
		// 게시글의 정보DTO 리스트
		List<TextListInfoDto> textListInfoDtoList = new ArrayList<TextListInfoDto>();
		// 게시글 5개 리스트DTO
		List<TextListInfoDto> textList5InfoDtoList = new ArrayList<TextListInfoDto>();

		// 페이지 번호 : 입력받은 페이지
		int pageNo = Integer.parseInt(request.getParameter("pageno").toString());
		// 검색범위 : 입력받은 검색 종류
		String searchType = request.getParameter("searchObject").toString();
		// 검색단어 : 입력받은 검색 단어
		String searchText = request.getParameter("searchText").toString();
		// 페이지당 출력 게시글 수 : 5
		int textListSize = ConstantsNum.VIEW_TEXT_LIST_SIZE;
		// 표시할 페이지 버튼 개수 : 10
		int pageBtnCount = ConstantsNum.VIEW_PAGE_BTN_CNT;
		// 총 페이지 수 (초기치 : 0)
		int totalpagesize = ConstantsNum.TOTAL_PAGE_SIZE_ZERO;
		// 첫 페이지 번호 (초기치 : 1)
		int firstlistpage = ConstantsNum.FIRST_LIST_PAGE_ONE;
		// 마지막 페이지 번호 (초기치 : "표시할 페이지 버튼 수 : 10")
		int lastlistpage = pageBtnCount;
		// 페이지 체크용 플래그 (초기치 : 1)(처리 종료 : 0, 처리 필요 : 1)
		int listpagecheckflg = ConstantsNum.PRO_CHK_FLG_ONE;

		try {
			// DB로부터 검색 데이터 취득
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			
			// 검색범위, 검색단어가 있을경우 검색데이터 취득하고 그렇지 않을경우 전체데이터를 취득한다.
			if (!wordCheck.nullCheck(searchType) && !wordCheck.nullCheck(searchText)) {
				// 조건별 데이터 취득
				if(searchType.equals("searchTitle") && !wordCheck.nullCheck(searchText)) {
					// 검색형식이 '타이틀'이고 검색단어가 NULL이 아닐 경우
					// 데이터의 타이틀에서 검색단어가 포함된 데이터를 모두 취득하여 'TextListInfoDto리스트'에 저장한다.
					textListInfoDtoList = textInfoIDao.allSearchTitle(searchText);
				} else if(searchType.equals("searchTitletext") && !wordCheck.nullCheck(searchText)) {
					// 검색형식이 '타이틀' 혹은 '내용'이고 검색단어가 NULL이 아닐 경우
					// 데이터의 타이틀 혹은 내용에서 검색단어가 포함된 데이터를 모두 취득하여 'TextListInfoDto리스트'에 저장한다.
					textListInfoDtoList = textInfoIDao.allSearchTitleText(searchText);
				} else if(searchType.equals("searchBoard") && !wordCheck.nullCheck(searchText)) {
					// 검색형식이 '게시판'이고 검색단어가 NULL이 아닐 경우
					// 데이터의 타이틀 혹은 내용에서 검색단어가 포함된 데이터를 모두 취득하여 'TextListInfoDto리스트'에 저장한다.
					textListInfoDtoList = textInfoIDao.allSearchBoard(searchText);
				} else if(searchType.equals("searchType") && !wordCheck.nullCheck(searchText)) {
					// 검색형식이 '종류'이고 검색단어가 NULL이 아닐 경우
					// 데이터의 종류에서 검색단어가 포함된 데이터를 모두 취득하여 'TextListInfoDto리스트'에 저장한다.
					textListInfoDtoList = textInfoIDao.allSearchType(searchText);
				} else if(searchType.equals("searchName") && !wordCheck.nullCheck(searchText)) {
					// 검색형식이 '작성자'이고 검색단어가 NULL이 아닐 경우
					// 데이터의 작성자에서 검색단어가 포함된 데이터를 모두 취득하여 'TextListInfoDto리스트'에 저장한다.
					textListInfoDtoList = textInfoIDao.allSearchUserid(searchText);
				} else if(searchType.equals("searchBdTp") && !wordCheck.nullCheck(searchText)) {
					// 검색형식이 '게시판-종류'이고 검색단어가 NULL이 아닐 경우
					// 검색단어가 형식대로 설정되어 있는지 확인한다. ("게시판_종류")
					String[] searchTextSp = searchText.split(ConstantsWord.UNDER_BAR);
					if(searchTextSp.length != ConstantsNum.SEARCH_TEXT_LEN_TWO) {
						// 형식에 맞지 않을 경우 전체 데이터를 취득하여 'TextListInfoDto리스트'에 저장한다.
						textListInfoDtoList = textInfoIDao.selectAllTextList();
					} else {
						// 형식에 맞을 경우 해당 데이터를 취득한다.
						textListInfoDtoList = textInfoIDao.allSearchBdTp(
								searchTextSp[ConstantsNum.SEARCH_TEXT_FST_WORD].toString(),
								searchTextSp[ConstantsNum.SEARCH_TEXT_SEC_WORD].toString());
					}
				} else {
					// 이 외의 경우 전체 데이터를 취득하여 'TextListInfoDto리스트'에 저장한다.
					textListInfoDtoList = textInfoIDao.selectAllTextList();
				}
				
				// 검색데이터가 없을경우 전체데이터를 취득한다.
				if (textListInfoDtoList.size() == ConstantsNum.SEARCH_TEXT_SIZE_ZERO) {
					// 데이터베이스로부터 모든 게시글 조회
					textListInfoDtoList = textInfoIDao.selectAllTextList();
				}
			} else {
				// 데이터베이스로부터 모든 게시글 조회
				textListInfoDtoList = textInfoIDao.selectAllTextList();
			}
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		

		// 페이지별 게시글 5개 추출
		if (pageNo == ConstantsNum.PAGE_NO_ONE) {
			// 페이지 번호가 1인 경우
			if (textListInfoDtoList.size() < textListSize) {
				// '전체 데이터 수'가 '페이지당 게시글 수'보다 작을 경우 게시글 리스트 크기만큼 '반환용 게시글 리스트DTO'에 저장
				for (int listCount = 0; listCount < textListInfoDtoList.size(); listCount++) {
					textList5InfoDtoList.add(textListInfoDtoList.get(listCount));
				}
			} else {
				// '전체 데이터 수'가 '페이지당 게시글 수'보다 클 경우 '페이지당 게시글 수'크기만큼 '반환용 게시글 리스트DTO'에 저장
				for (int listCount = 0; listCount < textListSize; listCount++) {
					textList5InfoDtoList.add(textListInfoDtoList.get(listCount));
				}
			}
		} else {
			// 페이지 번호가 1이 아닌 경우
			// 잔여 리스트 크기 확인
			// 리스트 시작 인덱스
			int listStartIndex = (pageNo * textListSize) - textListSize;
			// 잔여 리스트 크기
			int restListSize = (textListInfoDtoList.size() - listStartIndex) + 1;

			if (restListSize < textListSize) {
				// '잔여 리스트 크기'가 '페이지당 게시글 수'보다 작을 경우 '잔여 리스트 크기'만큼 '반환용 게시글 리스트DTO'에 저장
				for (int listCount = listStartIndex; listCount < textListInfoDtoList.size(); listCount++) {
					textList5InfoDtoList.add(textListInfoDtoList.get(listCount));
				}
			} else {
				// '잔여 리스트 크기'가 '페이지당 게시글 수'보다 클 경우 '페이지당 게시글 수'만큼 '반환용 게시글 리스트DTO'에 저장
				for (int listCount = 0; listCount < textListSize; listCount++) {
					textList5InfoDtoList.add(textListInfoDtoList.get(listStartIndex + listCount));
				}
			}
		}
		
		// 페이지 번호 정보
		// 페이지 크기 계산
		if ((textListInfoDtoList.size() % textListSize) != 0) {
			// 페이지별 게시글 크기만큼 나눠서 잔여 게시글이 있는 경우 페이지 크기를 1개 추가하여 설정한다.
			totalpagesize = (textListInfoDtoList.size() / textListSize) + 1;
		} else {
			// 페이지별 게시글 크기만큼 나눠서 잔여 게시글이 없는 경우 나눈 페이지 수를 설정한다.
			totalpagesize = textListInfoDtoList.size() / textListSize;
		}

		// 페이지번호리스트를 '표시할 페이지 버튼 개수'개씩 출력하도록 한다.
		// 마지막 리스트의 갯수가 '표시할 페이지 버튼 개수'개 미만일 경우 남은 리스트만 출력한다.
		while (listpagecheckflg == ConstantsNum.PRO_CHK_FLG_ONE) {
			if (totalpagesize == ConstantsNum.TOTAL_PAGE_SIZE_ZERO) {
				lastlistpage = ConstantsNum.LAST_LIST_PAGE_ONE;
				listpagecheckflg = ConstantsNum.PRO_CHK_FLG_ZERO;
			}
			if (lastlistpage > totalpagesize) {
				lastlistpage = totalpagesize;
			}
			if (pageNo >= firstlistpage && pageNo <= lastlistpage) {
				listpagecheckflg = ConstantsNum.PRO_CHK_FLG_ZERO;
			} else {
				firstlistpage += pageBtnCount;
				lastlistpage += pageBtnCount;
			}
		}
		
		// Attribute정보 저장
		// 현재 페이지 번호
		request.setAttribute("pageno", pageNo);
		// 총 페이지 수
		request.setAttribute("totalpagesize", totalpagesize);
		// 페이지 번호 리스트 (전)
		request.setAttribute("firstlistpage", firstlistpage);
		// 페이지 번호 리스트 (후)
		request.setAttribute("lastlistpage", lastlistpage);
		// 검색 범위
		request.setAttribute("searchObject", searchType);
		// 검색 단어
		request.setAttribute("searchText", searchText);
		// 게시글 리스트
		request.setAttribute("textInfoList", textList5InfoDtoList);
	}
	
	/**
	 * 메소드명(물리) : 최근 게시글 내용 검색
	 * 메소드명(논리) : newTextDesSelect
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-18
	 * 마지막 수정 날짜 : 2020-11-18
	 * 
	 * @param HttpServletRequest request : HttpServlet 요청
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 가장 최근 작성된 게시글의 내용과 댓글정보를 조회한다.
	 */
	private void newTextDesSelect(HttpServletRequest request) throws Exception {

		// 댓글 내용 정보DTO 리스트
		List<CommentInfoDto> commentInfoDtoList = new ArrayList<CommentInfoDto>();
		// 게시글 내용 정보DTO
		TextDesInfoDto textDesInfoDto = new TextDesInfoDto();
		// 게시글 내용 표시용 정보DTO
		TextDesViewInfoDto textDesViewInfoDto = new TextDesViewInfoDto();
		
		// 댓글 개수 (초기치 : 0)
		int commentCount = ConstantsNum.COMMENT_COUNT_ZERO;
		// 게시글 번호 (초기치 : 0)
		int textNo = ConstantsNum.TEXT_NO_ZERO;

		try {
			// DB로부터 가장 최신 게시글 정보 조회
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			textDesInfoDto = textInfoIDao.selectNewTextInfo();
			// DB로부터 취득한 게시글 정보의 "게시글 번호"를 조건으로 해당 게시글의 댓글 개수 조회하여 "댓글 개수"에 저장한다.
			commentCount = textInfoIDao.selectTextCommentCount(textDesInfoDto.getTextNo());

			// 표시용 게시글 내용 정보 DTO에 정보 설정
			// 게시글 내용 표시용 정보DTO.게시판 이름 = 게시글 내용 정보DTO.게시판 이름
			textDesViewInfoDto.setBoardName(textDesInfoDto.getBoardName());
			// 게시글 내용 표시용 정보DTO.게시판 종류 = 게시글 내용 정보DTO.게시판 종류
			textDesViewInfoDto.setTypeName(textDesInfoDto.getTypeName());
			// 게시글 내용 표시용 정보DTO.게시글 제목 = 게시글 내용 정보DTO.게시글 제목
			textDesViewInfoDto.setTextTitle(textDesInfoDto.getTextTitle());
			// 게시글 내용 표시용 정보DTO.게시글 번호 = 게시글 내용 정보DTO.게시글 번호
			textDesViewInfoDto.setTextNo(textDesInfoDto.getTextNo());
			// 게시글 내용 표시용 정보DTO.게시글 작성자 = 게시글 내용 정보DTO.게시글 작성자
			textDesViewInfoDto.setTextWriter(textDesInfoDto.getTextWriter());
			// 게시글 내용 표시용 정보DTO.게시글 조회수 = 게시글 내용 정보DTO.게시글 조회수
			textDesViewInfoDto.setTextCount(textDesInfoDto.getTextCount());
			// 게시글 내용 표시용 정보DTO.게시글 작성일 = 게시글 내용 정보DTO.게시글 작성일
			textDesViewInfoDto.setTextWriteDate(textDesInfoDto.getTextWriteDate());
			// 게시글 내용 표시용 정보DTO.게시글 수정일 = 게시글 내용 정보DTO.게시글 수정일
			textDesViewInfoDto.setTextUpdateDate(textDesInfoDto.getTextUpdateDate());
			// 게시글 내용 표시용 정보DTO.게시글 댓글수 = 댓글 개수
			textDesViewInfoDto.setCommentCount(commentCount);
			// 게시글 내용 표시용 정보DTO.게시글 내용 = 게시글 내용 정보DTO.게시글 내용
			textDesViewInfoDto.setTextDes(textDesInfoDto.getTextDes());

			// 취득한 게시글 번호 설정
			// 게시글 번호 = 게시글 내용 정보DTO.게시글 번호
			textNo = textDesInfoDto.getTextNo();
			// "게시글 번호"를 조건으로 댓글 정보 취득하여 "댓글 정보DTO 리스트"에 저장한다.
			commentInfoDtoList = textInfoIDao.selectComInfo(textNo);

		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}

		// 출력 Attribute정보 저장 처리
		// 게시글 내용 표시용 정보DTO
		request.setAttribute("textDesInfo", textDesViewInfoDto);
		// 댓글 정보DTO 리스트
		request.setAttribute("commentInfoList", commentInfoDtoList);
	}

}

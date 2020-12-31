package com.deg2de.homepagests.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deg2de.homepagests.dao.TextInfoIDao;
import com.deg2de.homepagests.dto.CommentInfoDto;
import com.deg2de.homepagests.dto.CommentViewInfoDto;
import com.deg2de.homepagests.dto.CommentWriteDto;
import com.deg2de.homepagests.dto.ImageFileInfoDto;
import com.deg2de.homepagests.dto.TextDesInfoDto;
import com.deg2de.homepagests.dto.TextDesViewInfoDto;
import com.deg2de.homepagests.dto.UpdateTextDesDto;
import com.deg2de.homepagests.dto.WriteTextDesDto;
import com.deg2de.homepagests.module.FolderModule;
import com.deg2de.homepagests.module.WordCheck;
import com.deg2de.homepagests.syscode.ConstantsNum;
import com.deg2de.homepagests.syscode.ConstantsWord;

/**
 * 클래스명(물리) : 게시글 정보 서비스 도구
 * 클래스명(논리) : TextInfoServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-25
 * 마지막 수정 날짜 : 2020-11-25
 * 
 * 게시글에 관한 정보 서비스 도구
 */
@Service("TextInfo")
public class TextInfoServiceImpl implements TextInfoService{
	
	/** SQL세션 선언 */
	@Autowired
	private SqlSession sqlSession;
	
	/** 공통부품.문자체크 */
	WordCheck wordCheck = new WordCheck();
	/** 공통부품.폴더 모듈 */
	private FolderModule folderModule = new FolderModule();
	
	/**
	 * 메소드명(물리) : 게시글 내용 조회
	 * 메소드명(논리) : readTextDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param int textNo : 게시글 번호
	 * @return TextDesViewInfoDto : 출력용 게시글 정보DTO
	 * @throws Exception
	 * 
	 * 게시글 번호를 조건으로 게시글 내용 출력
	 */
	@Override
	public TextDesViewInfoDto readTextDes(int textNo) throws Exception {
		
		// 게시글 정보DTO
		TextDesInfoDto textDesInfoDto = new TextDesInfoDto();
		// 반환용 페이지 출력용 게시글 정보DTO
		TextDesViewInfoDto textDesViewInfoDto = new TextDesViewInfoDto();
		
		// 댓글 수 : 1
		int commentCount = ConstantsNum.COMMENT_COUNT_ONE;
		
		try {
			// DB로부터 게시글 정보 조회(조건 : 게시글 번호)
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			textDesInfoDto = textInfoIDao.selectTextInfo(textNo);
			commentCount = textInfoIDao.selectTextCommentCount(textDesInfoDto.getTextNo());
			
			// DB에 해당 게시글의 조회수 1 증가
			textInfoIDao.updateTextViewCount(textNo);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// 표시용 게시글 내용 정보 DTO에 정보 설정
		// 게시판 이름
		textDesViewInfoDto.setBoardName(textDesInfoDto.getBoardName());
		// 게시판 종류
		textDesViewInfoDto.setTypeName(textDesInfoDto.getTypeName());
		// 게시글 제목
		textDesViewInfoDto.setTextTitle(textDesInfoDto.getTextTitle());
		// 게시글 번호
		textDesViewInfoDto.setTextNo(textDesInfoDto.getTextNo());
		// 게시글 작성자
		textDesViewInfoDto.setTextWriter(textDesInfoDto.getTextWriter());
		// 게시글 조회수
		textDesViewInfoDto.setTextCount(textDesInfoDto.getTextCount());
		// 게시글 작성일
		textDesViewInfoDto.setTextWriteDate(textDesInfoDto.getTextWriteDate());
		// 게시글 수정일
		textDesViewInfoDto.setTextUpdateDate(textDesInfoDto.getTextUpdateDate());
		// 게시글 댓글수
		textDesViewInfoDto.setCommentCount(commentCount);
		// 게시글 내용
		textDesViewInfoDto.setTextDes(textDesInfoDto.getTextDes());

		// 반환용 페이지 출력용 게시글 정보 반환
		return textDesViewInfoDto;
		
	}
	
	/**
	 * 메소드명(물리) : 게시글 삭제 (관리자, 작성자만 삭제 가능)
	 * 메소드명(논리) : deleteText
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param int textNo : 게시글 번호
	 * @param String textWriter : 게시글 작성자
	 * @param int loginCheckFlg : 로그인 확인용 플래그
	 * @param int managerCheckFlg : 관리자 확인용 플래그
	 * @param int sessionTxtIdChkFlg : 일치 확인용 플래그
	 * @param String imageAdd : 이미지 주소
	 * @return Map<String, Object> : 반환용MAP
	 * @throws Exception
	 * 
	 * 관리자 혹은 게시글 당사자일 경우 게시글 삭제처리를 진행한다.
	 */
	@Override
	public Map<String, Object> deleteText(int textNo, String textWriter, int loginCheckFlg, int managerCheckFlg,
			int sessionTxtIdChkFlg, String imageAdd) throws Exception {

		// 반환용 맵
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 게시글의 이미지 파일정보 취득용 리스트
		List<ImageFileInfoDto> imageFileInfoDtoList = new ArrayList<ImageFileInfoDto>();
		
		// 삭제처리 진행 확인 플래그 : 초기치 = 삭제 확인용 플래그 (비삭제 : 1)
		int deleteCheckFlg = ConstantsNum.DELETE_CHK_FLG_ONE;
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 반환 메세지
		String message = ConstantsWord.NULL;
		// 파일 주소
		String filePath = ConstantsWord.NULL;
		
		// 로그인 확인
		if(loginCheckFlg != ConstantsNum.LOGIN_CHK_FLG_ZERO) {
			// 로그인 중이 아닐 경우
			// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			// 메세지 내용  = "삭제 권한이 없습니다."
			message = ConstantsWord.MSG300004;
			
		} else {
			// 로그인 중일 경우
			// 삭제 권한 관리자 타입 확인
			if (managerCheckFlg != ConstantsNum.MANAGER_CHK_FLG_ZERO) {
				// 관리자 타입이 아닐 경우
				// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
				resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
				// 메세지 내용  = "삭제 권한이 없습니다."
				message = ConstantsWord.MSG300004;
				
				// 해당 게시글 작성자 확인
				if (sessionTxtIdChkFlg != ConstantsNum.SAME_CHK_FLG_ZERO) {
					// 해당 게시글 작성자가 아닌 경우
					// 반환값 설정 플래그 : 처리 확인용 플래그 (비정상 : 1)
					resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
					// 메세지 내용  = "삭제 권한이 없습니다."
					message = ConstantsWord.MSG300004;
				} else {
					// 해당 게시글 작성자인 경우
					// 삭제 확인용 플래그 (삭제 : 0)
					deleteCheckFlg = ConstantsNum.DELETE_CHK_FLG_ZERO;
				}
			} else {
				// 관리자 타입인 경우
				// 삭제 확인용 플래그 (삭제 : 0)
				deleteCheckFlg = ConstantsNum.DELETE_CHK_FLG_ZERO;
			}
		}
		
		// 삭제 처리
		if(deleteCheckFlg != ConstantsNum.DELETE_CHK_FLG_ONE) {
			try {
				// 삭제의 경우 해당 게시글 삭제처리
				// DB의 게시글 삭제(조건 : 게시글 번호)
				TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
				// 게시글의 이미지 파일명 취득
				imageFileInfoDtoList = textInfoIDao.selectImageInfo(textNo);

				// 파일 삭제처리
				if (imageFileInfoDtoList.size() > 0) {
					// 이미지 파일이 존재 할 경우 삭제처리 진행
					filePath = imageAdd.concat(ConstantsWord.ADD_HYP).toString();
					for (int imageCount = 0; imageCount < imageFileInfoDtoList.size(); imageCount++) {
						deleteFile(filePath.concat(imageFileInfoDtoList.get(imageCount).getImageFileName()).toString());
					}
				}

				// 게시글 정보 삭제 처리
				textInfoIDao.deleteTextInfo(textNo);
			} catch (Exception e) {
				System.out.println(ConstantsWord.ERROR_START_POINT);
				System.out.println(e);
				System.out.println(ConstantsWord.ERROR_END_POINT);
				throw e;
			}
			
			// 처리 구분용 플래그 (처리 종료 : 0)
			resultFlg = ConstantsNum.PRO_CHK_FLG_ZERO;
			// "해당 게시글이 삭제되었습니다."
			message = ConstantsWord.MSG300005;
		}
		
		// 반환값 설정
		resultMap.put("resultFlg", resultFlg);
		resultMap.put("resultMessage", message);
		
		return resultMap;
	}
	
	/**
	 * 메소드명(물리) : 이미지 삭제
	 * 메소드명(논리) : imageDelete
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String folderAdd : 폴더 주소
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 작성중인 이미지를 삭제한다.
	 */
	@Override
	public void imageDelete(String folderAdd) throws Exception {
		// 이미지가 저장되어 있는 폴더 및 이미지를 삭제한다.
		folderModule.folderDelete(folderAdd);
	}
	
	/**
	 * 메소드명(물리) : 이미지 업로드
	 * 메소드명(논리) : imageSave
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param ArrayList<String> imgAdds : 저장 이미지들 파일 이름
	 * @param int imgCount : 이미지 개수
	 * @param String tempFolderAdd : 임시 저장폴더 주소
	 * @param String realFolderAdd : 저장폴더 주소
	 * @return Map<String, Object> : 반환용 맵
	 * 
	 * 임시 저장폴더에 저장되있는 이미지를 실제 저장장소로 이동 저장하는 처리를 시행한다.
	 */
	@Override
	public Map<String, Object> imageSave(ArrayList<String> imgAdds, int imgCount, String tempFolderAdd,
			String realFolderAdd) {

		// 저장 파일명 저장용 리스트
		List<String> saveFileNameList = new ArrayList<String>();
		// 반환용 맵
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;

		try {
			// 이동할 이미지가 있는지 확인처리
			if (!(imgCount > ConstantsNum.IMAGE_SIZE_ZERO)) {
				// 처리할 이미지가 없는 경우
				// 기존 임시폴더가 있는지 확인, 있는 경우 폴더 삭제처리후 정상종료
				folderModule.folderDelete(tempFolderAdd);
				
				// 반환값 설정(정상 : 0)
				resultMap.put("resultFlg", resultFlg);
				
				return resultMap;
			}

			// 파일 이동처리(임시저장 -> 정규저장)
			for (int tempImgCount = 0; tempImgCount < imgCount; tempImgCount++) {
				// 임시저장된 파일 이름 취득
				String[] imgAdd = imgAdds.get(tempImgCount).toString().split(ConstantsWord.ADD_HYP);
				String tempImgName = tempFolderAdd.concat(imgAdd[imgAdd.length - 1].toString());
				String newImgName = realFolderAdd.concat(imgAdd[imgAdd.length - 1].toString());
				// 파일명 추출 저장
				saveFileNameList.add(imgAdd[imgAdd.length - 1].toString());
				
				// 파일 이동
				renameFile(tempImgName, newImgName, realFolderAdd);
			}

			// 임시저장파일 삭제처리
			folderModule.folderDelete(tempFolderAdd);
		} catch (Exception e) {
			// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
		}
		
		// 반환값 설정
		resultMap.put("resultFlg", resultFlg);
		resultMap.put("saveFileNameList", saveFileNameList);

		return resultMap;
	}
	
	/**
	 * 메소드명(물리) : 게시글 작성 처리
	 * 메소드명(논리) : writeTextDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param WriteTextDesDto writeTextDesDto : 작성 게시글 내용DTO
	 * @param List<String> saveFileNameList 저장 파일 이름 리스트
	 * @param String tempFolderAdd : 임시 폴더 주소
	 * @param String realFolderAdd : 실 폴더 주소
	 * @return int : 저리 결과 반환 플래그
	 * 
	 * 게시글 작성처리를 진행한다.
	 */
	@Override
	public int writeTextDes(WriteTextDesDto writeTextDesDto, List<String> saveFileNameList, String tempFolderAdd,
			String realFolderAdd) {

		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		// 글 번호 취득 : 초기치 = 게시글 번호 : 0
		int textNo = ConstantsNum.TEXT_NO_ZERO;

		try {
			// DB에 게시글 내용 저장
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			// 게시글 내용 저장
			textInfoIDao.insertTextDes(writeTextDesDto);
			// 게시글 번호 취득
			textNo = textInfoIDao.selectWriteTextNo(writeTextDesDto.getTextId(), writeTextDesDto.getTextWriteDate());
			
			// 게시글 번호 취득 여부 확인
			if(textNo != ConstantsNum.TEXT_NO_ZERO) {
				// 글 번호 정상 취득시 DB에 이미지 저장 처리
				if(saveFileNameList.size() > ConstantsNum.IMAGE_SIZE_ZERO) {
					// 이미지가 존재 하는 경우 저장처리
					// DB에 이미지 저장
					for (int imageFileCount = 0; imageFileCount < saveFileNameList.size(); imageFileCount++) {
						textInfoIDao.insertImageInfo(textNo, saveFileNameList.get(imageFileCount).toString());
					}
				}
			}
		} catch (Exception e) {
			// DB에 저장 게시글 삭제
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			// 게시글 내용 삭제
			textInfoIDao.deleteTextInfo(textNo);
			
			// 데이터 저장 도중 에러 발생 시 저장파일로 이동한 이미지 파일들을 전부 임시파일로 이동
			if(saveFileNameList.size() > ConstantsNum.IMAGE_SIZE_ZERO) {
				for(int imgCount = 0; imgCount < saveFileNameList.size(); imgCount++) {
					renameFile(realFolderAdd.concat(saveFileNameList.get(imgCount).toString()), tempFolderAdd.concat(saveFileNameList.get(imgCount).toString()).toString() , tempFolderAdd);
				}
			}
			// 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
		}

		return resultFlg;
	}
	
	/**
	 * 메소드명(물리) : 게시글 수정 처리
	 * 메소드명(논리) : updateTextDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param UpdateTextDesDto updateTextDesDto : 수정 게시글 내용DTO
	 * @return int : 반환 플래그
	 * 
	 * 수정된 게시글의 내용을 저장한다.
	 */
	@Override
	public int updateTextDes(UpdateTextDesDto updateTextDesDto) {
		
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		
		try {
			// DB에 게시글 업데이트 처리
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			textInfoIDao.updateTextDes(updateTextDesDto);
			
		} catch(Exception e) {
			// 처리 확인용 플래그 (비정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			return resultFlg;
		}
		return resultFlg;
	}

	/**
	 * 메소드명(물리) : 이미지 이름 조회
	 * 메소드명(논리) : textImageSelect
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param int textNo : 게시판 번호
	 * @return List<ImageFileInfoDto> : 게시글의 이미지파일이름 저장용DTO 리스트
	 * @throws Exception
	 * 
	 * 게시글의 이미지 이름을 조회한다. 
	 */
	@Override
	public List<ImageFileInfoDto> textImageSelect(int textNo) throws Exception {
		
		// 게시글의 이미지파일이름 저장용DTO 리스트
		List<ImageFileInfoDto> imageFileInfoDtoList = new ArrayList<ImageFileInfoDto>();
		try {
			// DB로부터 게시글의 이미지 파일 검색 (조건 : 게시글 번호)
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			imageFileInfoDtoList = textInfoIDao.selectTextImage(textNo);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		return imageFileInfoDtoList;
	}

	/**
	 * 메소드명(물리) : 이미지 파일 삭제처리
	 * 메소드명(논리) : deleteImgFile
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param int textNo : 게시글 번호
	 * @param String realFolderAdd : 저장 폴더 주소
	 * @param List<String> imgFileNameList : 이미지 파일명 리스트
	 * @return int : 처리 결과 플래그
	 * 
	 * 이미지 파일 삭제처리를 진행한다.
	 */
	@Override
	public int deleteImgFile(int textNo, String realFolderAdd, List<String> imgFileNameList) {
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;
		
		try {
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			for(int fileCount = 0; fileCount < imgFileNameList.size(); fileCount++) {
				String fileAdd = realFolderAdd.concat(imgFileNameList.get(fileCount).toString());
				// 파일 삭제 처리
				deleteFile(fileAdd);
				// DB 이미지파일 삭제 처리
				textInfoIDao.deleteImageInfo(textNo, imgFileNameList.get(fileCount).toString());
			}
		} catch(Exception e) {
			// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			return resultFlg;
		}
		return resultFlg;
	}
	
	/**
	 * 메소드명(물리) : 이미지 갱신 처리
	 * 메소드명(논리) : updateImgFile
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param int textNo : 게시글 번호
	 * @param String tempFolderAdd : 임피 폴더 주소
	 * @param String realFolderAdd : 저장 폴더 주소
	 * @param List<String> imgFileNameList : 이미지 파일명 리스트
	 * @return int : 처리 결과 플래그
	 * 
	 * 게시글의 이미지 갱신 처리를 진행한다.
	 */
	@Override
	public int updateImgFile(int textNo, String tempFolderAdd, String realFolderAdd, List<String> imgFileNameList) {
		// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 0)
		int resultFlg = ConstantsNum.PRO_CHK_FLG_SUCESS;

		try {
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			for (int fileCount = 0; fileCount < imgFileNameList.size(); fileCount++) {
				String tempFileAdd = tempFolderAdd.concat(imgFileNameList.get(fileCount).toString());
				String realFileAdd = realFolderAdd.concat(imgFileNameList.get(fileCount).toString());
				// 파일 이동 처리(임시 -> 정규)
				renameFile(tempFileAdd, realFileAdd, realFolderAdd);
				// DB 이미지파일 저장 처리
				textInfoIDao.insertImageInfo(textNo, imgFileNameList.get(fileCount).toString());
			}
		} catch (Exception e) {
			// 반환값 설정 플래그 : 초기치 = 처리 확인용 플래그 (정상 : 1)
			resultFlg = ConstantsNum.PRO_CHK_FLG_FAIL;
			return resultFlg;
		}
		return resultFlg;
	}
	
	/**
	 * 메소드명(물리) : 댓글 내용 호출 (최대 10개)
	 * 메소드명(논리) : readCommentDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-25
	 * 마지막 수정 날짜 : 2020-11-25
	 * 
	 * @param int textNo : 게시글 번호
	 * @param String userId : 로그인 유저 아이디
	 * @param int commentPageNo : 댓글 페이지 번호
	 * @return Map<String, Object> : 출력용 맵
	 * @throws Exception
	 * 
	 * 댓글 내용을 최대 10개까지 호출한다.
	 */
	@Override
	public Map<String, Object> readCommentDes(int textNo, String userId, int commentPageNo) throws Exception {
		// 출력용 맵
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 출력용 댓글 내용 정보DTO
		CommentViewInfoDto commentViewInfoDto = null;
		// 댓글 내용 정보DTO 리스트
		List<CommentInfoDto> commentInfoDtoList = new ArrayList<CommentInfoDto>();
		// 출력용 댓글 내용 정보DTO 리스트
		List<CommentViewInfoDto> commentViewInfoDtoList = new ArrayList<CommentViewInfoDto>();
		
		// 추가 댓글 존재 유무 플래그(초기치 : 1(없음))
		int afterCmtFlg = ConstantsNum.AFTER_COMMENT_FLG_ONE;
		
		try {
			// DB로부터 검색 데이터 취득
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			// 댓글 정보 취득
			commentInfoDtoList = textInfoIDao.selectComInfo(textNo);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
		// 취득한 댓글 정보가 10개 이상의 경우
		if (commentInfoDtoList.size() > ConstantsNum.COMMENT_COUNT_TEN) {
			// 첫페이지의 경우 10개만 취득
			if(commentPageNo == ConstantsNum.COMMENT_PAGE_NO_ONE) {
				// 추가 댓글 존재 유무 플래그(0 : 있음) 
				afterCmtFlg = ConstantsNum.AFTER_COMMENT_FLG_ZERO;
				for(int listCount = 0; listCount < 10; listCount++) {
					commentViewInfoDto = new CommentViewInfoDto();
					commentViewInfoDto.setCommentNo(commentInfoDtoList.get(listCount).getCommentNo());
					commentViewInfoDto.setCommentTextNo(commentInfoDtoList.get(listCount).getCommentTextNo());
					commentViewInfoDto.setCommentId(commentInfoDtoList.get(listCount).getCommentId());
					commentViewInfoDto.setCommentDes(commentInfoDtoList.get(listCount).getCommentDes());
					commentViewInfoDto.setCommentWriteDate(commentInfoDtoList.get(listCount).getCommentWriteDate());
					
					// 본인 작성 댓글인지 확인하는 플래그 설정
					if(userId != null) {
						if(userId.equals(commentInfoDtoList.get(listCount).getCommentId())) {
							// 일치할 경우 0 설정
							commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ZERO);
						} else {
							// 비일치의 경우 1 설정
							commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ONE);
						}
					} else {
						// 비로그인의 경우 1 설정
						commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ONE);
					}
					commentViewInfoDtoList.add(listCount, commentViewInfoDto);
				}
			} else {
				// 첫페이지가 아닐 경우
				// 해당 페이지 외에 데이터가 더 있을 경우
				if(commentInfoDtoList.size() > (commentPageNo * ConstantsNum.COMMENT_COUNT_TEN)) {
					afterCmtFlg = ConstantsNum.AFTER_COMMENT_FLG_ZERO;
					// 페이지번호 X 10개만큼 데이터 취득
					for(int listCount = 0; listCount < (commentPageNo * ConstantsNum.COMMENT_COUNT_TEN); listCount++) {
						commentViewInfoDto = new CommentViewInfoDto();
						commentViewInfoDto.setCommentNo(commentInfoDtoList.get(listCount).getCommentNo());
						commentViewInfoDto.setCommentTextNo(commentInfoDtoList.get(listCount).getCommentTextNo());
						commentViewInfoDto.setCommentId(commentInfoDtoList.get(listCount).getCommentId());
						commentViewInfoDto.setCommentDes(commentInfoDtoList.get(listCount).getCommentDes());
						commentViewInfoDto.setCommentWriteDate(commentInfoDtoList.get(listCount).getCommentWriteDate());
						
						// 본인 작성 댓글인지 확인하는 플래그 설정
						if(userId != null) {
							if(userId.equals(commentInfoDtoList.get(listCount).getCommentId())) {
								// 일치할 경우 0 설정
								commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ZERO);
							} else {
								// 비일치의 경우 1 설정
								commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ONE);
							}
						} else {
							// 비로그인의 경우 1 설정
							commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ONE);
						}
						commentViewInfoDtoList.add(listCount, commentViewInfoDto);
					}
				} else {
					// 해당 페이지에 마지막 데이터가 있을 경우
					for(int listCount = 0; listCount < commentInfoDtoList.size(); listCount++) {
						commentViewInfoDto = new CommentViewInfoDto();
						commentViewInfoDto.setCommentNo(commentInfoDtoList.get(listCount).getCommentNo());
						commentViewInfoDto.setCommentTextNo(commentInfoDtoList.get(listCount).getCommentTextNo());
						commentViewInfoDto.setCommentId(commentInfoDtoList.get(listCount).getCommentId());
						commentViewInfoDto.setCommentDes(commentInfoDtoList.get(listCount).getCommentDes());
						commentViewInfoDto.setCommentWriteDate(commentInfoDtoList.get(listCount).getCommentWriteDate());
						
						// 본인 작성 댓글인지 확인하는 플래그 설정
						if(userId != null) {
							if(userId.equals(commentInfoDtoList.get(listCount).getCommentId())) {
								// 일치할 경우 0 설정
								commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ZERO);
							} else {
								// 비일치의 경우 1 설정
								commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ONE);
							}
						} else {
							// 비로그인의 경우 1 설정
							commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ONE);
						}
						commentViewInfoDtoList.add(listCount, commentViewInfoDto);
					}
				}
			}
			
		} else {
			// 총 데이터가 10개 미만일 경우 (첫페이지 처리)
			for(int listCount = 0; listCount < commentInfoDtoList.size(); listCount++) {
				commentViewInfoDto = new CommentViewInfoDto();
				commentViewInfoDto.setCommentNo(commentInfoDtoList.get(listCount).getCommentNo());
				commentViewInfoDto.setCommentTextNo(commentInfoDtoList.get(listCount).getCommentTextNo());
				commentViewInfoDto.setCommentId(commentInfoDtoList.get(listCount).getCommentId());
				commentViewInfoDto.setCommentDes(commentInfoDtoList.get(listCount).getCommentDes());
				commentViewInfoDto.setCommentWriteDate(commentInfoDtoList.get(listCount).getCommentWriteDate());
				
				// 본인 작성 댓글인지 확인하는 플래그 설정
				if(userId != null) {
					if(userId.equals(commentInfoDtoList.get(listCount).getCommentId())) {
						// 일치할 경우 0 설정
						commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ZERO);
					} else {
						// 비일치의 경우 1 설정
						commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ONE);
					}
				} else {
					// 비로그인의 경우 1 설정
					commentViewInfoDto.setCommentIdFlg(ConstantsNum.COMMENT_WTR_CHK_FLG_ONE);
				}
				commentViewInfoDtoList.add(listCount, commentViewInfoDto);
			}
		}
		
		// 반환MAP 설정
		resultMap.put("commentViewInfoDtoList", commentViewInfoDtoList);
		resultMap.put("afterCmtFlg", afterCmtFlg);
		return resultMap;
	}

	/**
	 * 메소드명(물리) : 댓글 작성
	 * 메소드명(논리) : writeCommentDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param int textNo : 게시글 번호
	 * @param String userId : 로그인 아이디
	 * @param String commentDes : 댓글 정보
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 댓글 내용을 저장하는 처리
	 */
	@Override
	public void writeCommentDes(int textNo, String userId, String commentDes) throws Exception {
		
		// 댓글 작성 정보DTO
		CommentWriteDto commentWriteDto = new CommentWriteDto();
		
		// 현재날짜 설정
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
		String writeDate = sb.toString();
		
		// 댓글 작성 정보DTO.게시글 번호 = 게시글 번호
		commentWriteDto.setCommentTextNo(textNo);
		// 댓글 작성 정보DTO.아이디 = 로그인 아이디
		commentWriteDto.setCommentId(userId);
		// 댓글 작성 정보DTO.내용 = 댓글 내용
		commentWriteDto.setCommentDes(commentDes);
		// 댓글 작성 정보DTO.작성날짜 = 현재날짜
		commentWriteDto.setCommentWriteDate(writeDate);
		
		try {
			// DB에 댓글내용 저장
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			textInfoIDao.insertCmtInfo(commentWriteDto);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
		
	}

	/**
	 * 메소드명(물리) : 댓글 삭제
	 * 메소드명(논리) : deleteCommentDes
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-26
	 * 마지막 수정 날짜 : 2020-11-26
	 * 
	 * @param int commentNo : 댓글 번호
	 * @param String userId : 로그인 아이디
	 * @throws Exception
	 * 
	 * 댓글 번호, 아이디에 맞는 댓글을 삭제한다.
	 */
	@Override
	public void deleteCommentDes(int commentNo, String userId) throws Exception {
		try {
			// DB에 댓글내용 삭제 (조건 : 댓글 번호, 아이디)
			TextInfoIDao textInfoIDao = sqlSession.getMapper(TextInfoIDao.class);
			textInfoIDao.deleteCmtInfo(commentNo, userId);
		} catch (Exception e) {
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
	}
	
	/**
	 * 메소드명(물리) : 파일 삭제
	 * 메소드명(논리) : deleteFile
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param String filePath : 파일 주소
	 * @return void : 없음
	 * 
	 * 파일 삭제용 메소드
	 */
	private void deleteFile(String filePath) {
		File fileTemp = new File(filePath);
		try {
			// 해당 파일이 있을 경우 파일 삭제처리 진행
			if (fileTemp.exists()) {
				fileTemp.delete();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * 메소드명(물리) : 파일 이동
	 * 메소드명(논리) : renameFile
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-27
	 * 마지막 수정 날짜 : 2020-11-27
	 * 
	 * @param String tempFilename : 기존 파일
	 * @param String newFilename : 갱신 파일
	 * @param String realFolderAdd : 저장 파일 주소
	 * @return void : 없음
	 */
	private void renameFile(String tempFilename, String newFilename, String realFolderAdd) {
		File fileTemp = new File(tempFilename);
		File fileNew = new File(newFilename);

		// 파일을 임시로 저장할 주소
		File uploadFile = new File(realFolderAdd);
		// 해당 파일 경로가 없을 경우 파일을 만들어준다.
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		if (fileTemp.exists()) {
			fileTemp.renameTo(fileNew);
		}
	}
}

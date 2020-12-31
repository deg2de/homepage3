package com.deg2de.homepagests.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deg2de.homepagests.dto.ImageFileInfoDto;
import com.deg2de.homepagests.dto.TextDesViewInfoDto;
import com.deg2de.homepagests.dto.UpdateTextDesDto;
import com.deg2de.homepagests.dto.WriteTextDesDto;

/**
 * 클래스명(물리) : 게시글 정보 서비스
 * 클래스명(논리) : TextInfoService.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-25
 * 마지막 수정 날짜 : 2020-11-25
 * 
 * 게시글에 관한 정보 서비스
 */
public interface TextInfoService {
	
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
	public abstract TextDesViewInfoDto readTextDes(int textNo) throws Exception;
	
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
	public abstract Map<String, Object> deleteText(int textNo, String textWriter, int loginCheckFlg,
			int managerCheckFlg, int sessionTxtIdChkFlg, String imageAdd) throws Exception;
	
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
	 * 임시 저장폴더에 저장되있는 이미지를 실제 저장장소로 이동 저장하는 처리ㅡㄹㄹ 시행한다.
	 */
	public abstract Map<String, Object> imageSave(ArrayList<String> imgAdds, int imgCount, String tempFolderAdd, String realFolderAdd);
	
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
	public abstract int writeTextDes(WriteTextDesDto writeTextDesDto, List<String> saveFileNameList, String tempFolderAdd, String realFolderAdd);
	
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
	public abstract List<ImageFileInfoDto> textImageSelect(int textNo) throws Exception;
	
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
	public abstract int updateTextDes(UpdateTextDesDto updateTextDesDto);
	
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
	public abstract int deleteImgFile(int textNo, String realFolderAdd, List<String> imgFileNameList);
	
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
	public abstract int updateImgFile(int textNo,String tempFolderAdd ,String realFolderAdd, List<String> imgFileNameList);
	
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
	public abstract Map<String, Object> readCommentDes(int textNo, String userId, int commentPageNo) throws Exception;
	
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
	public abstract void writeCommentDes(int textNo, String userId, String commentDes) throws Exception;
	
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
	public abstract void deleteCommentDes(int commentNo, String userId) throws Exception;
	
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
	public abstract void imageDelete(String folderAdd) throws Exception;
	
}

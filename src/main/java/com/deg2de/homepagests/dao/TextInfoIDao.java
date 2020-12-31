package com.deg2de.homepagests.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deg2de.homepagests.dto.CommentInfoDto;
import com.deg2de.homepagests.dto.CommentWriteDto;
import com.deg2de.homepagests.dto.ImageFileInfoDto;
import com.deg2de.homepagests.dto.TextDesInfoDto;
import com.deg2de.homepagests.dto.TextListInfoDto;
import com.deg2de.homepagests.dto.UpdateTextDesDto;
import com.deg2de.homepagests.dto.WriteTextDesDto;

public interface TextInfoIDao {
	// 리스트용 게시글 정보 취득
	public List<TextListInfoDto> selectAllTextList();
	
	// 최근 게시글 정보 취득
	public TextDesInfoDto selectNewTextInfo();
	
	// 게시글 정보 취득(조건 : 게시글 번호)
	public TextDesInfoDto selectTextInfo(@Param("textNo") int textNo);
	
	// 게시글 삭제(조건 : 게시글 번호)
	public void deleteTextInfo(@Param("textNo") int textNo);
	
	// 게시글의 댓글 갯수
	public int selectTextCommentCount(@Param("textNo") int textNo);
	
	// 작성 게시글 내용 저장
	public int insertTextDes(WriteTextDesDto writeTextDesDto);
	
	// 게시글의 조회수 증가(조건 : 게시글 번호)
	public int updateTextViewCount(@Param("textNo") int textNo);
	
	// 게시글의 이미지 파일 이름 취득(조건 : 게시글 번호)
	public List<ImageFileInfoDto> selectImageInfo(@Param("textNo") int textNo);
	
	// 이미지 파일 저장용 게시글 번호 취득(조건 : 작성자, 작성일)
	public int selectWriteTextNo(@Param("textId") String textId, @Param("textWriteDate") String textWriteDate);
	
	// 이미지 저장
	public void insertImageInfo(@Param("imageTextNo") int imageTextNo, @Param("imageFileName") String imageFileName);
	
	// 작성 게시글 업데이트 처리
	public void updateTextDes(UpdateTextDesDto updateTextDesDto);
	
	// 작성 게시글 업데이트 처리
	public List<ImageFileInfoDto> selectTextImage(@Param("textNo") int textNo);
	
	// 이미지 파일 삭제 처리
	public void deleteImageInfo(@Param("imageTextNo") int imageTextNo, @Param("imageFileName") String imageFileName);
	
	// 게시물 검색 (조건 : 제목)
	public List<TextListInfoDto> allSearchTitle(@Param("searchText") String searchText);
	
	// 게시물 검색 (조건 : 제목 or 내용)
	public List<TextListInfoDto> allSearchTitleText(@Param("searchText") String searchText);
	
	// 게시물 검색 (조건 : 게시판)
	public List<TextListInfoDto> allSearchBoard(@Param("searchText") String searchText);
	
	// 게시물 검색 (조건 : 종류)
	public List<TextListInfoDto> allSearchType(@Param("searchText") String searchText);
	
	// 게시물 검색 (조건 : 작성자)
	public List<TextListInfoDto> allSearchUserid(@Param("searchText") String searchText);
	
	// 게시물 검색 (조건 : 게시판, 종류)
	public List<TextListInfoDto> allSearchBdTp(@Param("searchText1") String searchText1, @Param("searchText2") String searchText2);
	
	// 댓글 정보 취득 (조건 : 게시글 번호)
	public List<CommentInfoDto> selectComInfo(@Param("commentTextNo") int commentTextNo);
	
	// 댓글 정보 작성
	public void insertCmtInfo(CommentWriteDto commentWriteDto);
	
	// 댓글 정보 삭제
	public void deleteCmtInfo(@Param("commentTextNo") int commentTextNo, @Param("commentId") String commentId);
}

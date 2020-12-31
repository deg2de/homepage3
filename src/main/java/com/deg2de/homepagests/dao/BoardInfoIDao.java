package com.deg2de.homepagests.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deg2de.homepagests.dto.BoardInfoDto;
import com.deg2de.homepagests.dto.BoardNameDto;
import com.deg2de.homepagests.dto.BoardTypeInfoDto;
import com.deg2de.homepagests.dto.BoardTypeNameDto;

public interface BoardInfoIDao {
	// 모든 게시판 정보 취득
	public List<BoardInfoDto> selectBoardInfo();
	
	// 모든 게시판 종류 정보 취득
	public List<BoardTypeInfoDto> selectTypeInfo();
	
	// 게시판 정보 취득 (조건 : 게시판 코드)
	public BoardInfoDto selectBoardToCode(@Param("boardCode") int boardCode);
	
	// 모든 게시판 종류 정보 취득
	public List<BoardTypeInfoDto> selectBoardTypeInfoBc(@Param("boardCode") int boardCode);
	
	// 게시판 추가
	public void createBoard(@Param("boardName") String boardName);
	
	// 게시판 삭제
	public void deleteBoard(@Param("boardCode") int boardCode);
	
	// 게시판종류 추가
	public void createBoardType(@Param("boardTypeName") String boardTypeName, @Param("boardCode") int boardCode);
	
	// 게시판종류 삭제
	public void deleteBoardType(@Param("boardTypeCode") int boardTypeCode);
	
	// 헤더 게시판 리스트용 게시판이름 검색
	public List<BoardNameDto> selectBoardName();
	
	// 헤더 게시판 리스트용 게시판이름, 종류 검색
	public List<BoardTypeNameDto> selectBoardTypeName();
}

package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 게시판 정보DTO
 * 클래스명(논리) : BoardInfoDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class BoardInfoDto {
	/** 게시판 정보.게시판 코드 */
	private int boardCode;
	/** 게시판 정보.게시판 이름 */
	private String boardName;
	
	public int getBoardCode() {
		return boardCode;
	}
	public void setBoardCode(int boardCode) {
		this.boardCode = boardCode;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
}

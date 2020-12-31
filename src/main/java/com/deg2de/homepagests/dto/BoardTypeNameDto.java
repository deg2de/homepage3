package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 게시판 종류 이름DTO
 * 클래스명(논리) : BoardTypeNameDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class BoardTypeNameDto {
	
	/** 게시판 정보.게시판 이름 */
	private String boardName;
	/** 게시판 종류 정보.게시판 종류 이름 */
	private String typeName;
	
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}

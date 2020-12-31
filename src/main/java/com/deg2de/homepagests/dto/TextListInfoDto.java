package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 게시글 리스트 정보DTO
 * 클래스명(논리) : TextListInfoDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class TextListInfoDto {
	
	/** 게시글 정보.게시글 번호 */
	private int textNo;
	/** 게시판 정보.게시판 이름 */
	private String boardName;
	/** 게시판 종류 정보.게시판 종류 이름 */
	private String typeName;
	/** 사용자 정보.아이디 */
	private String id;
	/** 게시글 정보.게시글 제목 */
	private String textTitle;
	/** 게시글 정보.작성 날짜 */
	private String textWriteDate;
	/** 게시글 정보.조회수 */
	private int textCount;
	
	public int getTextNo() {
		return textNo;
	}
	public void setTextNo(int textNo) {
		this.textNo = textNo;
	}
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTextTitle() {
		return textTitle;
	}
	public void setTextTitle(String textTitle) {
		this.textTitle = textTitle;
	}
	public String getTextWriteDate() {
		return textWriteDate;
	}
	public void setTextWriteDate(String textWriteDate) {
		this.textWriteDate = textWriteDate;
	}
	public int getTextCount() {
		return textCount;
	}
	public void setTextCount(int textCount) {
		this.textCount = textCount;
	}
	
	
}

package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 작성용 게시글 내용DTO
 * 클래스명(논리) : WriteTextDesDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class WriteTextDesDto {
	
	/** 게시글 정보.게시글 게시판 코드 */
	private int textBoardCode;
	/** 게시글 정보.게시글 게시판종류 코드 */
	private int textTypeCode;
	/** 게시글 정보.게시글 작성자 */
	private String textId;
	/** 게시글 정보.게시글 제목 */
	private String textTitle;
	/** 게시글 정보.게시글 내용 */
	private String textDes;
	/** 게시글 정보.게시글 작성일 */
	private String textWriteDate;
	/** 게시글 정보.게시글 마지막 수정일 */
	private String textLastUpdDate;
	/** 게시글 정보.게시글 조회수 */
	private int textCount;
	
	public int getTextBoardCode() {
		return textBoardCode;
	}
	public void setTextBoardCode(int textBoardCode) {
		this.textBoardCode = textBoardCode;
	}
	public int getTextTypeCode() {
		return textTypeCode;
	}
	public void setTextTypeCode(int textTypeCode) {
		this.textTypeCode = textTypeCode;
	}
	public String getTextId() {
		return textId;
	}
	public void setTextId(String textId) {
		this.textId = textId;
	}
	public String getTextTitle() {
		return textTitle;
	}
	public void setTextTitle(String textTitle) {
		this.textTitle = textTitle;
	}
	public String getTextDes() {
		return textDes;
	}
	public void setTextDes(String textDes) {
		this.textDes = textDes;
	}
	public String getTextWriteDate() {
		return textWriteDate;
	}
	public void setTextWriteDate(String textWriteDate) {
		this.textWriteDate = textWriteDate;
	}
	public String getTextLastUpdDate() {
		return textLastUpdDate;
	}
	public void setTextLastUpdDate(String textLastUpdDate) {
		this.textLastUpdDate = textLastUpdDate;
	}
	public int getTextCount() {
		return textCount;
	}
	public void setTextCount(int textCount) {
		this.textCount = textCount;
	}

}

package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 수정용 게시글 내용DTO
 * 클래스명(논리) : UpdateTextDesDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class UpdateTextDesDto {
	/** 게시글 정보.게시글 번호 */
	private int textNo;
	/** 게시글 정보.게시판 코드 */
	private int textBoardCode;
	/** 게시글 정보.종류 코드 */
	private int textTypeCode;
	/** 게시글 정보.게시글 제목 */
	private String textTitle;
	/** 게시글 정보.게시글 내용 */
	private String textDes;
	/** 게시글 정보.게시글 수정일 */
	private String textLastUpdDate;
	
	public int getTextNo() {
		return textNo;
	}
	public void setTextNo(int textNo) {
		this.textNo = textNo;
	}
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
	public String getTextLastUpdDate() {
		return textLastUpdDate;
	}
	public void setTextLastUpdDate(String textLastUpdDate) {
		this.textLastUpdDate = textLastUpdDate;
	}
	
}

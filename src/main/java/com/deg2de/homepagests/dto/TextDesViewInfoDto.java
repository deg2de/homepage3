package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 출력용 게시글 내용 정보DTO
 * 클래스명(논리) : TextDesViewInfoDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class TextDesViewInfoDto {
	/** 게시판 정보.게시판 이름 */
	private String boardName;
	/** 게시판 종류 정보.종류 이름 */
	private String typeName;
	/** 게시글 정보.게시글 제목 */
	private String textTitle;
	/** 게시글 정보.게시글 번호 */
	private int textNo;
	/** 게시글 정보.게시글 작성자 */
	private String textWriter;
	/** 게시글 정보.게시글 조회수 */
	private int textCount;
	/** 게시글 정보.게시글 작성일 */
	private String textWriteDate;
	/** 게시글 정보.게시글 수정일 */
	private String textUpdateDate;
	/** 댓글 갯수 */
	private int commentCount;
	/** 게시글 정보.게시글 내용 */
	private String textDes;
	
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
	public String getTextTitle() {
		return textTitle;
	}
	public void setTextTitle(String textTitle) {
		this.textTitle = textTitle;
	}
	public int getTextNo() {
		return textNo;
	}
	public void setTextNo(int textNo) {
		this.textNo = textNo;
	}
	public String getTextWriter() {
		return textWriter;
	}
	public void setTextWriter(String textWriter) {
		this.textWriter = textWriter;
	}
	public int getTextCount() {
		return textCount;
	}
	public void setTextCount(int textCount) {
		this.textCount = textCount;
	}
	public String getTextWriteDate() {
		return textWriteDate;
	}
	public void setTextWriteDate(String textWriteDate) {
		this.textWriteDate = textWriteDate;
	}
	public String getTextUpdateDate() {
		return textUpdateDate;
	}
	public void setTextUpdateDate(String textUpdateDate) {
		this.textUpdateDate = textUpdateDate;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getTextDes() {
		return textDes;
	}
	public void setTextDes(String textDes) {
		this.textDes = textDes;
	}
}

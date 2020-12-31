package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 게시판 종류 정보DTO
 * 클래스명(논리) : BoardTypeInfoDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class BoardTypeInfoDto {
	/** 게시판 종류 정보.게시판 종류 코드 */
	private int typeCode;
	/** 게시판 종류 정보.게시판 종류 이름 */
	private String typeName;
	/** 게시판 종류 정보.게시판 코드 */
	private String typeBoardCode;
	
	public int getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeBoardCode() {
		return typeBoardCode;
	}
	public void setTypeBoardCode(String typeBoardCode) {
		this.typeBoardCode = typeBoardCode;
	}
	
	
}

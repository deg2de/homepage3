package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 세션 정보DTO
 * 클래스명(논리) : SessionInfoDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class SessionInfoDto {

	/** 세션 정보.아이디 */
	private String id;
	/** 세션 정보.이름 */
	private String name;
	/** 세션 정보.사용자 타입 */
	private int userType;
	/** 세션 정보.사진 주소 */
	private String picAdd;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getPicAdd() {
		return picAdd;
	}
	public void setPicAdd(String picAdd) {
		this.picAdd = picAdd;
	}
	
	
	
	
}

package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 유저 사진 주소 변경DTO
 * 클래스명(논리) : UserPicAddChangeDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class UserPicAddChangeDto {
	
	/** 사용자 정보.아이디 */
	private String id;
	/** 사용자 정보.사진 주소 */
	private String picAdd;
	/** 사용자 정보.마지막수정날짜 */
	private String lastUpdDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPicAdd() {
		return picAdd;
	}
	public void setPicAdd(String picAdd) {
		this.picAdd = picAdd;
	}
	public String getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
}

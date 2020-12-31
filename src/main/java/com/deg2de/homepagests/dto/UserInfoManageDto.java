package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 유저 정보 관리용DTO
 * 클래스명(논리) : UserInfoManageDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class UserInfoManageDto {
	
	/** 사용자 정보.아이디 */
	private String userId;
	/** 사용자 정보.이름 */
	private String userName;
	/** 사용자 정보.가입 날짜 */
	private String joinDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
}

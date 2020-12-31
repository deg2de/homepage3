package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 유저 정보DTO
 * 클래스명(논리) : UserInfoDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class UserInfoDto {

	/** 사용자 정보.아이디 */
	private String id;
	/** 사용자 정보.비밀번호 */
	private String pw;
	/** 사용자 정보.이름 */
	private String name;
	/** 사용자 정보.메일주소 */
	private String mailAdd;
	/** 사용자 정보.전화번호 */
	private String phoNum;
	/** 사용자 정보.사용자 종류 */
	private int userType;
	/** 사용자 정보.가입 날짜 */
	private String joinDate;
	/** 사용자 정보.마지막 수정 날짜 */
	private String lastUpdDate;
	/** 사용자 정보.사진 주소 */
	private String picAdd;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAdd() {
		return mailAdd;
	}
	public void setMailAdd(String mailAdd) {
		this.mailAdd = mailAdd;
	}
	public String getPhoNum() {
		return phoNum;
	}
	public void setPhoNum(String phoNum) {
		this.phoNum = phoNum;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getPicAdd() {
		return picAdd;
	}
	public void setPicAdd(String picAdd) {
		this.picAdd = picAdd;
	}

}
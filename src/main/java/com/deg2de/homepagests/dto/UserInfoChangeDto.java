package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 유저 정보 변경DTO
 * 클래스명(논리) : UserInfoChangeDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class UserInfoChangeDto {
	
	/** 사용자 정보.비밀번호 */
	private String pw;
	/** 사용자 정보.이름 */
	private String name;
	/** 사용자 정보.전화번호 */
	private String phoNum;
	/** 사용자 정보.마지막 수정 날짜 */
	private String lastUpdDate;
	/** 사용자 정보.아이디 */
	private String id;

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
	public String getPhoNum() {
		return phoNum;
	}
	public void setPhoNum(String phoNum) {
		this.phoNum = phoNum;
	}
	public String getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

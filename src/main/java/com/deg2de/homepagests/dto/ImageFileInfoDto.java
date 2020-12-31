package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 이미지 파일 정보DTO
 * 클래스명(논리) : ImageFileInfoDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class ImageFileInfoDto {
	
	/** 이미지 정보.이미지 번호 */
	private int imageNo;
	/** 이미지 정보.이미지 게시글 번호 */
	private int imageTextNo;
	/** 이미지 정보.이미지 파일 이름 */
	private String imageFileName;
	
	public int getImageNo() {
		return imageNo;
	}
	public void setImageNo(int imageNo) {
		this.imageNo = imageNo;
	}
	public int getImageTextNo() {
		return imageTextNo;
	}
	public void setImageTextNo(int imageTextNo) {
		this.imageTextNo = imageTextNo;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}

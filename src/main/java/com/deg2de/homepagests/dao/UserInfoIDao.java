package com.deg2de.homepagests.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deg2de.homepagests.dto.UserInfoChangeDto;
import com.deg2de.homepagests.dto.UserInfoDto;
import com.deg2de.homepagests.dto.UserInfoManageDto;
import com.deg2de.homepagests.dto.UserPicAddChangeDto;

public interface UserInfoIDao {

	// 유저 정보 조회 (조건 : 아이디)
	public UserInfoDto selectId(String id);
	
	// 유저 정보 조회 (조건 : 메일 주소)
	public UserInfoDto selectMailAdd(String mailAdd);

	// 유저 정보 입력
	public void insertUserInfo(UserInfoDto userInfoDto);

	// 유저 정보 수정
	public void updateUserInfo(UserInfoChangeDto userInfoChangeDto);
	
	// 유저 비밀번호 수정 (조건 : 아이디)
	public void updateUserPw(@Param("id") String id, @Param("pw") String pw);
	
	// 유저 이미지 파일 주소 수정
	public void updateUserPicAdd(UserPicAddChangeDto userPicAddChangeDto);
	
	// 유저 관리용 유저 모든 유저 정보 조회
	public List<UserInfoManageDto> selsetAllUserInfo();
	
	// 유저 관리용 유저 정보 조회 (조건 : 아이디)
	public List<UserInfoManageDto> selectmanageUserId(@Param("id") String id);
	
	// 유저 관리용 유저 정보 조회 (조건 : 이름)
	public List<UserInfoManageDto> selectmanageUserName(@Param("name") String name);
	
	// 유저 정보 삭제 (조건 : 아이디)
	public void deleteUserId(@Param("id") String id);
}

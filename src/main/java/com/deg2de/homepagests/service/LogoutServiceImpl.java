package com.deg2de.homepagests.service;

import org.springframework.stereotype.Service;

import com.deg2de.homepagests.module.FolderModule;

/**
 * 클래스명(물리) : 로그아웃 서비스 도구
 * 클래스명(논리) : LogoutServiceImpl.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-19
 * 마지막 수정 날짜 : 2020-11-19
 * 
 * 로그아웃 관련 처리를 담당하는 서비스 클래스
 */
@Service("Logout")
public class LogoutServiceImpl implements LogoutService{
	
	/** 공통부품.폴더 모듈 */
	private FolderModule folderModule = new FolderModule();

	/**
	 * 메소드명(물리) : 이미지 삭제
	 * 메소드명(논리) : imageDelete
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String folderAdd : 폴더 주소
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 작성중인 이미지를 삭제한다.
	 */
	@Override
	public void imageDelete(String folderAdd) throws Exception {
		// 이미지가 저장되어 있는 폴더 및 이미지를 삭제한다.
		folderModule.folderDelete(folderAdd);
	}

}

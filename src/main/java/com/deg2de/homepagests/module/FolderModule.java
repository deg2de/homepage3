package com.deg2de.homepagests.module;

import java.io.File;

import com.deg2de.homepagests.syscode.ConstantsWord;

/**
 * 클래스명(물리) : 폴더 모듈
 * 클래스명(논리) : FolderModule.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-19
 * 마지막 수정 날짜 : 2020-11-19
 * 
 * 폴더 및 파일에 관한 처리를 모아둔 공통 부품
 */
public class FolderModule {

	/**
	 * 메소드명(물리) : 폴더 삭제
	 * 메소드명(논리) : folderDelete
	 * 
	 * 작성자 : 이성복
	 * 최초 작성 날짜 : 2020-11-19
	 * 마지막 수정 날짜 : 2020-11-19
	 * 
	 * @param String folderPath : 폴더 주소
	 * @return void : 없음
	 * @throws Exception
	 * 
	 * 지정 폴더 및 하위 파일까지 모두 삭제한다.
	 */
	public void folderDelete(String folderPath) throws Exception {
		
		File folder = new File(folderPath);
		try {
			// 폴더가 존재할 경우 삭제 처리
			while (folder.exists()) {
				// 폴더의 하위 파일리스트 얻어오기
				File[] folder_list = folder.listFiles();

				for (int j = 0; j < folder_list.length; j++) {
					// 파일 삭제
					folder_list[j].delete();
				}
				// 하위 파일이 모두 삭제 되었다면 대상 폴더를 삭제한다.
				if (folder_list.length == 0 && folder.isDirectory()) {
					folder.delete(); // 대상폴더 삭제
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(ConstantsWord.ERROR_START_POINT);
			System.out.println(e);
			System.out.println(ConstantsWord.ERROR_END_POINT);
			throw e;
		}
	}
}

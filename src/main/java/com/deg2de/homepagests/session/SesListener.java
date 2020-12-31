package com.deg2de.homepagests.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SesListener implements HttpSessionListener{

	// 세션 생성시
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

	// 세션 종료시
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
	}

}

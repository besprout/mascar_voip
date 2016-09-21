package com.besprout.voip.listener;

import javax.servlet.ServletContextEvent;

import com.besprout.voip.common.spring.ApplicationContextUtilsInitListener;

public class SpringListener extends ApplicationContextUtilsInitListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
	
	
}

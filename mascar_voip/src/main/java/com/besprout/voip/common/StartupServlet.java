package com.besprout.voip.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.besprout.voip.common.spring.ServiceLocator;
import com.besprout.voip.service.DialogService;

public class StartupServlet extends HttpServlet{

	@Override
	public void init() throws ServletException {
		loadSystemPropertis();
		loadSystemParam();
	}
	
	private void loadSystemParam(){
		DialogService dialogService = ServiceLocator.getBean(DialogService.class);
		dialogService.loadParam();
		dialogService.initDialog();
	}
	
	private void loadSystemPropertis(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("config/properties/system.properties");
	    Properties p = new Properties();
	    try {
			p.load(in);
			Extra.rest_voip = p.getProperty("rest.voip");
			Extra.dialogTemplateFile = p.getProperty("dialog.template.file");
			Extra.dialogTempDir = p.getProperty("dialog.temp.dir");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(in != null){
					in.close();
				}
			} catch (Exception e2) {
			}
		}
	    
	}
	
}

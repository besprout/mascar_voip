package com.besprout.voip.common.spring;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:描述
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * <p>
 * Company:besprout
 * </p>
 * 
 * @author wander
 * 
 * @version 1.0
 * 
 * @createtime Apr 10, 2012-4:17:02 PM
 * 
 * @修改历史
 * 
 * <li>版本号 修改日期 修改人 修改说明
 * 
 * <li>
 * 
 * <li>
 */
public class ApplicationContextUtilsInitListener extends ContextLoaderListener {
	
	private Logger logger =Logger.getLogger(ApplicationContextUtilsInitListener.class);
	/**
	 * Method contextInitialized.
	 * 
	 * @param event
	 *            ServletContextEvent
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		ServiceLocator.setApplicationContext(context);
	}
	
   @Override
   public void contextDestroyed(ServletContextEvent event) {
	    super.contextDestroyed(event);
	    logger.info("--销毁");
    }
}

package com.besprout.voip.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p/>
 * Title: ServiceLocator
 * </p>
 * <p/>
 * <p/>
 * Description: spring应用上下文获取工具类，该类在非web环境下可直接使用，但需要修改需要加载的配置文件路径
 * ，在web环境中配合com.besprout.springframework.web.context.ApplicationContextUtilsInitListener
 * 使用而将会加载配置web.xml中的配置文件
 * </p>
 * <p/>
 * <p/>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p/>
 * <p/>
 * Company:
 * </p>
 *
 * @author wuqj
 * @version 1.0
 */
public class ServiceLocator {
	/**
	 * spring应用上下文
	 */
	private static ApplicationContext CONTEXT;

	/**
	 * Method getApplicationContext.
	 *
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		if (CONTEXT == null) {
			CONTEXT = new ClassPathXmlApplicationContext(
					new String[] { "classpath:spring.xml" });
		}
		return CONTEXT;

	}

	/**
	 * Method setApplicationContext.
	 *
	 * @param outcontext ApplicationContext
	 */
	public static void setApplicationContext(ApplicationContext outcontext) {
		CONTEXT = outcontext;
	}

	/**
	 * 获得一个Spring的Bean对象
	 *
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * 通过calss 获得一个Spring的Bean对象
	 *
	 * @param T
	 * @return
	 */
	public static <T> T getBean(Class<T> T) {
		return getApplicationContext().getBean(T);
	}
	
	/**
	 * 
	 * @param key ：properties定义的值
	 * @param defaultMessage：取不到值,默认的值
	 * @return
	 */
	public static String getMessage(String key,String defaultMessage){
		return CONTEXT.getMessage(key, null, defaultMessage,null);
	}
	
	/**
	 * 
	 * @param key ：properties定义的值
	 * @return  如果key值 没有，将返回"",
	 */
	public static String getMessage(String key){
		return CONTEXT.getMessage(key, null,"",null);
	}
	
	
}

package com.ecloud.framework.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorCodeUtil {
	private static Logger LOG = LoggerFactory.getLogger(ErrorCodeUtil.class);
	private static Properties properties = new Properties();
	static {
		InputStream is = null;
		try {
			is = ErrorCodeUtil.class.getClassLoader().getResourceAsStream("code.properties");
			properties.load(new InputStreamReader(is, "UTF-8"));
		} catch (IOException e) {
			LOG.error("{}", e);
		}finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOG.error("{}", e);
				}
			}
		}
	}
	
	/**
	 * 根据错误码获取错误描述信息
	 * @param errorCode	错误码
	 * @return	错误描述信息
	 */
	public static String getErrorDesc(String errorCode) {
		return properties.getProperty(errorCode);
	}
	
	
	public static void main(String[] args) {
		System.out.println(ErrorCodeUtil.getErrorDesc("err-001"));
	}
}

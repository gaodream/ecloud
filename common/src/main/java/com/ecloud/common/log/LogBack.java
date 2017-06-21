package com.ecloud.common.log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogBack {
	
	/**
     * 初始化agent的logback
     * @param logbackConf logback配置的路径
     */
    public static void init(String logbackConf) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            configurator.doConfigure(new FileInputStream(logbackConf));
        } catch (JoranException | FileNotFoundException e) {
        	e.printStackTrace();
        }
    }

}

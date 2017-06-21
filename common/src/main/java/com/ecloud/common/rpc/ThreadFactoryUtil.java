package com.ecloud.common.rpc;

import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ThreadFactoryUtil {

	private static final String SERVER_THREAD_PREFIX = "ecloud-"; 
    private ThreadFactoryUtil() {} 
 
    public static ThreadFactory create(String format) { 
    	return new ThreadFactoryBuilder().setThreadFactory(new DefaultThreadFactory()).setDaemon(false).setNameFormat(SERVER_THREAD_PREFIX + format).build();
    } 
    
    public static ThreadFactory create(boolean isDaemon, String format) { 
    	return new ThreadFactoryBuilder().setDaemon(isDaemon).setNameFormat(SERVER_THREAD_PREFIX + format).build();
    } 
}

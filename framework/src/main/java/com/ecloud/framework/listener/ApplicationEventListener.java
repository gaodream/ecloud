package com.ecloud.framework.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {

	/**
	 * 需要早application中设置
	 * context.listener.classes=com.ecloud.framework.listener.ApplicationEventListener
	 */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 在这里可以监听到Spring Boot的生命周期
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
        	System.err.println("*****初始化环境变量*****");
        }else if (event instanceof ApplicationPreparedEvent) { 
        	System.err.println("*****初始化完成*****");
        }else if (event instanceof ContextRefreshedEvent) { 
        	System.err.println("*****应用刷新*****");
        }else if (event instanceof ApplicationReadyEvent) {
        	System.err.println("*****应用已启动完成 *****");
        }else if (event instanceof ContextStartedEvent) { 
        	System.err.println("*****应用启动，需要在代码动态添加监听器才可捕获 *****");
        }else if (event instanceof ContextStoppedEvent) { 
        	System.err.println("*****应用停止*****");
        }else if (event instanceof ContextClosedEvent) { 
        	System.err.println("*****应用关闭 *****");
        }else {
        	System.err.println("*****其他*****"+event.getClass());
        	
        }
		handleThrowable();
    }

    /*处理异常*/
    private void handleThrowable() {
		/*	 if (this.shutdownHook == null) {
            // No shutdown hook registered yet.
            this.shutdownHook = new Thread() {
                @Override
                public void run() {
                    doClose();
                }
            };
            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        }*/
    }

}

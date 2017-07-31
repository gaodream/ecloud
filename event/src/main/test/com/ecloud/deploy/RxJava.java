package com.ecloud.deploy;

import java.io.File;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.methods.HttpAsyncMethods;

import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

public class RxJava {
	
    static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).build();
    static CloseableHttpAsyncClient httpclient = null;
    static HttpClientContext httpContext = HttpClientContext.create();
    static HttpGet hettpGet = null;
    private static volatile boolean isMaster = true;
    private final static Integer DATA_INDEX = 6; 
    private final static Integer EVENT_INDEX = 7; 
	
	public static void main(String[] args) {
		File file = new File("F://event.txt");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials("ips", "ips"));
		httpContext.setCredentialsProvider(credsProvider);
		httpclient = HttpAsyncClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setMaxConnPerRoute(10)
				.setMaxConnTotal(20)
				.build();
		httpclient.start();
		hettpGet = new HttpGet(URI.create("http://10.20.16.227:8080/v2/events"));
		hettpGet.setHeader("Accept", "text/event-stream");
		/**
		 * 需要处理的事件
		 * deployment_info: 事件开始标志
		 * instance_changed_event | status_update_event: 容器运行状态
		 * health_status_changed_event | instance_health_changed_event: 容器健康状态
		 * deployment_success: 事件最终完成状态
		 */
		ObservableHttp<ObservableHttpResponse> result = ObservableHttp.createRequest(HttpAsyncMethods.create(hettpGet), httpclient,httpContext);
		result.toObservable()
        .flatMap((ObservableHttpResponse response) ->{
        	EventBean eventBean = new EventBean();
            return response.getContent().map(( byte[] bb) ->{
            	String info = new String(bb);
            	if(info.startsWith("event:")){
            		eventBean.setEventName(info.substring(EVENT_INDEX));
            	}else if(info.startsWith("data:")){
            		eventBean.setEventContent(info.substring(DATA_INDEX));
            	}else{
            		System.out.println("do nothing.");
            	}
            	//存储事件到消息队列中
            	//message.put(eventBean);
            	return new String(bb);
            });
        })
        .forEach((String resp) -> {
        	if(isMaster){
        		try {
					//FileUtils.write(file, resp+"\n", "UTF-8",true);
				} catch (Exception e) {
					e.printStackTrace();
				}
        		//System.out.println(resp);
        	}
        });
	}

}

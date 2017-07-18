package com.ecloud.deploy.listener;


import java.io.IOException;
import java.net.URI;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.ecloud.common.constant.EConstants;
import com.ecloud.common.marathon.MarathonClient;
import com.ecloud.common.zookeeper.PathListener;
import com.ecloud.common.zookeeper.ZKClient;

import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

public class MarathonElectionListener extends PathListener {

    private static final Logger LOG = LoggerFactory.getLogger(MarathonElectionListener.class);

    @Value("${server.port:8094}")
    private int jettyPort;
    @Value("${server.contextPath:/}")
    private String context ;
    @Value("${ecloud.marathon.credentials}")
    private String needAuth ;
    @Value("${ecloud.marathon.username}")
    private String marathonName ;
    @Value("${ecloud.marathon.password}")
    private String marathonPass ;
    private RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).build();
    private CloseableHttpAsyncClient httpclient = null;
    private HttpClientContext httpContext = HttpClientContext.create();
    private HttpGet hettpGet = null;
    private volatile boolean isStarted = false;//是否启动了Stream接收器
    
    public MarathonElectionListener(Environment env) {
    	this.jettyPort = env.getProperty("server.port", Integer.class, 8094);
    	this.context = env.getProperty("server.contextPath");
    	this.needAuth = env.getProperty("ecloud.marathon.credentials");
    	if("on".equals(needAuth)){
    		this.marathonName = env.getProperty("ecloud.marathon.username");
    		this.marathonPass = env.getProperty("ecloud.marathon.password");
    		CredentialsProvider credsProvider = new BasicCredentialsProvider();
    		credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
    				new UsernamePasswordCredentials(marathonName, marathonPass));
    		httpContext.setCredentialsProvider(credsProvider);
    	}
    }
    /**
     * 检查并且更新marathon的地址相关
     */
    @Override
    public synchronized void changeEvent() {
        String data = "";
		try {
			data = ZKClient.getMinSeqNode(EConstants.MARATHON_ZK_PATH, EConstants.MARATHON_NAME);
			if (!StringUtils.isEmpty(data)) {
	            // 拼接成marathon地址后进行对比
	            String newAddress = "http://" + data;
	            String oldAddress = MarathonClient.marathonUrl;
	            if (newAddress.equals(oldAddress)) {
	                LOG.info("Current marathon address {} not change .", newAddress);
	            } else {
	            	if(isStarted){
	            		//停止之前的线程，重置URL并重启
	            		closeStream();
	            	}
	            	startStream(newAddress+"/v2/event", httpContext);
	            }
	        }
		} catch (Exception e) {
			LOG.error(" Get marathon error : {}",e.getMessage());
		}
        
    }
    
    /**
     * This method is deprecated, see method startStream.
     * @param host
     * @param oldAddress
     * @param newAddress
     */
    @Deprecated
    public void registerAndhandle(String host,String oldAddress,String newAddress){
    	//"http://192.168.6.110:8080/v2/events
    	//marathon地址发生改变
    	//1.移除事件回调监听
    	String callbackUrl = "http://"+ host+ ":8094/"+context + "/event/process";
    	//等于说明是第一次，之前的值为空不需要做unRegister
    	if(!StringUtils.isEmpty(oldAddress)){
    		MarathonClient.unRegisterEventSubscription(oldAddress,marathonName,marathonPass,callbackUrl);
    	}
    	//2.更新新的marathon地址
    	MarathonClient.marathonUrl = newAddress;
    	//3.重新注册事件回调
    	MarathonClient.registerEventSubscription(newAddress,marathonName,marathonPass,callbackUrl);
    }
    
    
    /**
     * 启动事件流接收
     * @param url
     * @param context
     */
    private void startStream(String url,HttpClientContext context){
		httpclient = HttpAsyncClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setMaxConnPerRoute(10)
				.setMaxConnTotal(20)
				.build();
		httpclient.start();
		hettpGet = new HttpGet(URI.create(url));
		hettpGet.setHeader("Accept", "text/event-stream");
		ObservableHttp<ObservableHttpResponse> result = ObservableHttp.createRequest(HttpAsyncMethods.create(hettpGet), httpclient,context);
		result.toObservable()
        .flatMap((ObservableHttpResponse response) ->{
            return response.getContent().map(( byte[] bb) ->{
            	return new String(bb);
            });
        })
        .forEach((String resp) -> {
            System.out.println(resp);
        });
		isStarted = true;
	}
	
	public void closeStream(){
		try {
			hettpGet.releaseConnection();
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isStarted = false;
	}
	

}

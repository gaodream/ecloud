package com.ecloud.deploy.master.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ecloud.common.info.AgentNode;
import com.ecloud.deploy.master.event.AppInfo;

public final class MessageCache {

	private static final Logger LOG = LoggerFactory.getLogger(MessageCache.class);

	public static volatile boolean isMaster = false;
	public static volatile String host ;
	// 默认事件队列的大小
	private static final int DEFAULT_E_QUEUE_SIZE = 100000;
	// 事件队列
	private static BlockingQueue<Map<String, Object>> eventQueue = new ArrayBlockingQueue<Map<String, Object>>(
			DEFAULT_E_QUEUE_SIZE);
	//存储运行的容器信息
	private static Map<String, AppInfo> appMap = new ConcurrentHashMap<String, AppInfo>();
	
	//上报的Agent相关信息
	private static Map<String, AgentNode> addressToAgent = new ConcurrentHashMap<String, AgentNode>();

	//容器状态信息，用于判断HA事件
	private static Map<String, JSONObject> ctnStatusMapping = new ConcurrentHashMap<String, JSONObject>();
	
	//主要存储从zookeeper上同步下来的每个应用信息
	private static Map<String, String> appScale = new ConcurrentHashMap<String, String>();

	public static void putWorker(String workerIP, AgentNode worker) {
		addressToAgent.put(workerIP, worker);
	}

	public static AgentNode getWorker(String workerIP) {
		return addressToAgent.get(workerIP);
	}

	public static AgentNode removeWorker(String workerIP) {
		return addressToAgent.remove(workerIP);
	}

	public static void cleanWorker() {
		addressToAgent.clear();
	}

	public static boolean containsWorker(String workerIP) {
		return addressToAgent.containsKey(workerIP);
	}

	public static Map<String, AgentNode> getAddressToAgent() {
		return addressToAgent;
	}

	public static void setAddressToAgent(Map<String, AgentNode> addressToAgent) {
		MessageCache.addressToAgent = addressToAgent;
	}

	public synchronized static void addEventToQueue(Map<String, Object> event) {
		if (!eventQueue.add(event)) {
			LOG.error(
					"[ERROR] : add event failed, current eventQueue size is {}, default queue size is {}, event is {}",
					eventQueue.size(), DEFAULT_E_QUEUE_SIZE, event);
		}
	}

	public static boolean containsApp(String marathonAppId) {
		return appMap.containsKey(marathonAppId);
	}
	public static void removeApp(String marathonAppId) {
		appMap.remove(marathonAppId);
	}
	public static AppInfo getApp(String marathonAppId) {
		return appMap.get(marathonAppId);
	}
	public static void putApp(String marathonAppId,AppInfo info) {
		 appMap.put(marathonAppId,info);
	}
	
	public static Map<String, AppInfo> getAppMap() {
		return appMap;
	}

	public static BlockingQueue<Map<String, Object>> getEventQueue() {
		return eventQueue;
	}
	
	
	public static void putTask(String taskId,JSONObject ctnObj){
		ctnStatusMapping.put(taskId, ctnObj);
	}
	public static JSONObject getTask(String taskId){
		return ctnStatusMapping.get(taskId);
	}

	
	public static Map<String, JSONObject> getCtnStatusMapping() {
		return ctnStatusMapping;
	}

	public static void putAppScale(String key,String value) {
		appScale.put(key, value);
	}
	public static Map<String, String> getAppScale() {
		return appScale;
	}
	public static void removeAppScale(String key) {
		if(appScale.containsKey(key))
		 appScale.remove(key);
	}

	public static String getHost() {
		if(StringUtils.isNotEmpty(host)) return host;
		InetAddress inet = null;
		try {
			inet = InetAddress.getLocalHost();
			host = inet.getHostAddress();
			return host;
			//result.setHostName(inet.getHostName());
		} catch (UnknownHostException e) {
			LOG.error("GET LOCAL HOST ERROR : {}", e.getMessage());
		}
		return "";
	}

}

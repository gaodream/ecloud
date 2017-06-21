package com.ecloud.deploy.master.event;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ecloud.common.constant.EConstants;
import com.ecloud.common.model.Pair;
import com.ecloud.deploy.master.common.MessageCache;
import com.ecloud.deploy.master.common.model.ContainerVO;
import com.ecloud.deploy.master.common.service.ManagerService;

@Component
public class EventHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(EventHandler.class);
	
	@Autowired
	private ManagerService managerService;
	
	public @PostConstruct void init() {
		LOG.info("start event task......");
		Runnable task = () -> {
			handleEvent();
		};
		task.run();
	}

	public void handleEvent() {
		while (true) {
			try {
				Map<String, Object> event = MessageCache.getEventQueue().poll(5, TimeUnit.SECONDS);
				if (null != event) {
					String eventType = (String) event.get("eventType");
					String eventContent = JSON.toJSONString(event);
					switch (eventType) {
					case EConstants.E_DEPLOY_INFO:
						LOG.info("E_DEPLOY_INFO ");
						//updateAppInfo(eventContent,"INFO");
						break;
					case EConstants.E_GROUP_CHANGE_SUCCESS:
						LOG.info("E_GROUP_CHANGE_SUCCESS");
						break;
					case EConstants.E_API_POST:
						LOG.info("E_API_POST");
						break;
					case EConstants.E_DEPLOY_STEP_SUCCESS:
						LOG.info("E_DEPLOY_STEP_SUCCESS ");
						break;
					case EConstants.E_DEPLOY_SUCCESS:
						updateAppInfo(eventContent,"SUCCESS");
						sendEventToPlugin(eventContent);
						break;
					case EConstants.E_DEPLOY_FAILED:
						LOG.info("E_DEPLOY_STEP_SUCCESS");
						break;
					case EConstants.E_STATUS_UPDATE:
						handle(eventContent);
						LOG.info("E_STATUS_UPDATE");
						break;
					case EConstants.E_APP_TERMINATED:
						LOG.info("E_APP_TERMINATED");
						break;
					default:
						LOG.info("unknown eventType[{}]", eventType);
					}
				}
			} catch (InterruptedException e) {
				LOG.error("poll event error : {}", e.getMessage());
			}
		}
	}

	/**
	 * 给插件发送相应的事件
	 * 
	 * @param event
	 */
	private void sendEventToPlugin(String content) {
		JSONObject plan = JSON.parseObject(content).getJSONObject("plan");
		JSONObject original = plan.getJSONObject("original");
		JSONObject target = plan.getJSONObject("target");
		JSONArray steps = plan.getJSONArray("steps");
		Integer size = steps.size();
		StringBuffer stepsBuffer = new StringBuffer();
		String appId = "";
		for (int i = 0; i < size; i++) {
			JSONObject step = steps.getJSONObject(i);
			stepsBuffer.append(step.getString("type"));
			if (size - 1 != i)
				stepsBuffer.append(",");
			appId = step.getString("app");
		}
		String stepsStr = stepsBuffer.toString();
		// TODO:执行插件的start事件
		if (stepsStr.contains("StartApplication") && stepsStr.contains("ScaleApplication")) {
			// TODO:start
		} else if ("StopApplication".equals(stepsStr)) {
			// TODO:执行插件的stop事件
			if (MessageCache.containsApp(appId)){
				MessageCache.removeApp(appId);
			}
		} else if ("RestartApplication".equals(stepsStr)) {
			// TODO:执行插件的restart事件
			if (MessageCache.containsApp(appId))
				MessageCache.removeApp(appId);
		} else if ("ScaleApplication".equals(stepsStr)) {
			// TODO:执行插件的扩缩容事件
			Pair<String, Integer> originalPair = getAppVersion(appId, original);
			Pair<String, Integer> targetPair = getAppVersion(appId, target);
			if (originalPair.getSecond() > targetPair.getSecond()) {
				// TODO:执行插件的缩容事件
			}
			if (originalPair.getSecond() < targetPair.getSecond()) {
				// TODO:执行插件的扩容事件
			}

		}

		// HA事件：本质属于逻辑上的事件 在E_STATUS_UPDATE事件中抽象 handle()

	}

	/**
	 * 处理容器事件
	 * 
	 * @param content
	 */
	private void handle(String content) {
		JSONObject ctnObj = JSON.parseObject(content);
		String marathonAppId = ctnObj.getString("appId");
		String taskStatus = ctnObj.getString("taskStatus");
		String taskId = ctnObj.getString("taskId");
		String version = ctnObj.getString("version");
		switch (taskStatus) {
		case "TASK_RUNNING":// 更新到内存
			AppInfo info = null;
			if (MessageCache.containsApp(marathonAppId)) {
				info = MessageCache.getApp(marathonAppId);
			} else {
				info = new AppInfo(marathonAppId);
			}
			ContainerInfo ctn = new ContainerInfo();
			ctn.setHost(marathonAppId);
			ctn.setTaskId(taskId);
			ctn.setVersion(version);
			ctn.setTimestamp(ctnObj.getString("timestamp"));
			ctn.setTaskStatus(taskStatus);
			info.addCtns(ctn);
			MessageCache.putApp(marathonAppId, info);
			// 判断是否是HA;SUCCESS表示服务不在扩缩容状态
			if("SUCCESS".equals(info.getStatus())){
				JSONObject task = MessageCache.getTask(taskId);
				//String preTaskStatus = task.getString("taskStatus");
				String preVersion = task.getString("version");
				//"TASK_RUNNING".equals(preTaskStatus)&&
				if(version.equals(preVersion)){
					//TODO:执行HA
				}
			}
			ContainerVO vo = new ContainerVO();
			String[] ids = marathonAppId.split("/");
			vo.setAppId(ids[1]);
			vo.setServiceId(ids[2]);
			vo.setTaskId(taskId);
			vo.setMarathonAppId(marathonAppId);
			vo.setUpgradeId("0");
			vo.setImageName("");
			//运行容器入库
			managerService.doInsertContainer(vo);
			break;
		case "TASK_KILLED":
			MessageCache.putTask(taskId,ctnObj);
			managerService.doDeleteContainer(taskId);//删除死亡容器
			break;
		case "TASK_FAILED":
			managerService.doDeleteContainer(taskId);//删除死亡容器
			break;
		case "TASK_LOST":
			managerService.doDeleteContainer(taskId);//删除死亡容器
			if (MessageCache.containsApp(marathonAppId)) {
				Map<String, ContainerInfo> ctns = MessageCache.getApp(marathonAppId).getCtns();
				if (null != ctns && ctns.containsKey(taskId)) {
					ctns.remove(taskId);
				}
			}
			break;
		default:
			break;
		}


	}

	/**
	 * 处理服务信息
	 * 
	 * @param content
	 */
	private void updateAppInfo(String content,String status) {
		JSONObject root = JSON.parseObject(content);
		JSONObject plan = root.getJSONObject("plan");

		String appId = plan.getJSONObject("currentStep").getJSONArray("actions").getJSONObject(0).getString("app");
		AppInfo info = null;
		if (MessageCache.containsApp(appId)) {
			info = MessageCache.getApp(appId);
		} else {
			info = new AppInfo(appId);
		}

		String timestamp = root.getString("timestamp");
		info.setTimestamp(timestamp);
		// 事件之前的版本和实例数信息
		Pair<String, Integer> originalPair = getAppVersion(appId, plan.getJSONObject("original"));
		info.setlVersion(originalPair.getFirst());
		info.setlInstance(originalPair.getSecond());
		// 当前的版本和实例数信息
		Pair<String, Integer> targetPair = getAppVersion(appId, plan.getJSONObject("target"));
		info.setcVersion(targetPair.getFirst());
		info.setcInstance(targetPair.getSecond());
	}

	/**
	 * 获取版本和实例数信息
	 * 
	 * @param appId
	 * @param parent
	 * @return
	 */
	private Pair<String, Integer> getAppVersion(String appId, JSONObject parent) {
		String appGroup = appId.substring(0, appId.lastIndexOf("/"));
		String groupId = "";
		String version = "";
		Integer instance = 0;
		boolean isFound = false;
		JSONArray groups = parent.getJSONArray("groups");
		while (appGroup.startsWith(groupId)) {
			int size = groups.size();
			for (int i = 0; i < size; i++) {
				JSONObject sub = groups.getJSONObject(i);
				String id = sub.getString("id");
				// 最后一个group
				if (id.equals(appGroup)) {
					JSONArray apps = sub.getJSONArray("apps");
					if (apps.size() > 0) {
						JSONObject app = apps.getJSONObject(0);
						String nodeId = app.getString("id");
						if (nodeId.equals(appId)) {
							version = app.getString("version");
							instance = app.getInteger("instances");
						}
					}
					isFound = true;
					break;
				} else if (appGroup.startsWith(id)) {
					groups = sub.getJSONArray("groups");
					groupId = id;
					break;
				}

				if (size - 1 == i) {
					version = sub.getString("version");
					isFound = true;
				}
			}
			if (isFound)
				break;
		}

		return new Pair<String, Integer>(version, instance);
	}

}

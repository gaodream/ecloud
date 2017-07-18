package com.ecloud.deploy.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecloud.deploy.common.model.ContainerMetricBean;
import com.ecloud.deploy.common.model.ContainerVO;
import com.ecloud.deploy.common.model.HostMetricBean;

public interface ManagerService {
	
	static final Logger LOG = LoggerFactory.getLogger(ManagerService.class);
	
	/******************************		指标处理相关	***********************************/
	/**
	 * 批量插入容器指标
	 * @param list
	 * @return
	 */
	public int doBatchInsertCtn(List<ContainerMetricBean> list);
	
	/**
	 * 批量插入主机指标
	 * @param list
	 * @return
	 */
	public int doBatchInsertHost(List<HostMetricBean> list);
	
	/**
	 * 插入容器表
	 * @param vo
	 * @return
	 */
	public int doInsertContainer(ContainerVO vo);
	
	/**
	 * 删除死亡容器
	 * @param taskId
	 * @return
	 */
	public int doDeleteContainer(String taskId);
	
	
}
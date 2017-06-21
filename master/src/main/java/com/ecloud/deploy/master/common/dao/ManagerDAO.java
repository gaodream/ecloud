package com.ecloud.deploy.master.common.dao;

import java.util.List;

import com.ecloud.deploy.master.common.model.ContainerMetricBean;
import com.ecloud.deploy.master.common.model.ContainerVO;
import com.ecloud.deploy.master.common.model.HostMetricBean;

public interface ManagerDAO {
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
	
	
	/******************************		数据加载相关	***********************************/
	/**
	 * Manager变为Active时，从zookeeper中加载恢复数据
	 * 1.恢复ZK中的Agent数据
	 * 2.恢复ZK中的Observer数据
	 * 3.恢复ZK中的Deployment状态的容器数据
	 */
	public void initLoadDataFromZookeeper();

	/**
	 * Manage内存中的数据存留到一点时间后需要定时清理
	 * 1.Agent数据定时清理，放到Managerhandler
	 * 2.Observer数据 暂时未改造，仍然采用从zookeeper中获取Manager
	 * 3.Deployment状态的容器数据清理
	 */
	public void listenData();
	
	public void listenObserver();
}
package com.ecloud.deploy.master.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecloud.deploy.master.common.dao.ManagerDAO;
import com.ecloud.deploy.master.common.model.ContainerMetricBean;
import com.ecloud.deploy.master.common.model.ContainerVO;
import com.ecloud.deploy.master.common.model.HostMetricBean;
import com.ecloud.deploy.master.common.service.ManagerService;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private ManagerDAO managerDAO;

	@Override
	public int doBatchInsertCtn(List<ContainerMetricBean> list) {
		return managerDAO.doBatchInsertCtn(list);
	}

	@Override
	public int doBatchInsertHost(List<HostMetricBean> list) {
		return managerDAO.doBatchInsertHost(list);
	}
	


	@Override
	public int doInsertContainer(ContainerVO vo) {
		return managerDAO.doInsertContainer(vo);
	}

	@Override
	public int doDeleteContainer(String taskId) {
		return managerDAO.doDeleteContainer(taskId);
	}

}
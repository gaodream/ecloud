package com.ecloud.common.constant;

public final class EConstants {
	
	public final static String MASTER_NAME = "master";
	public final static String MARATHON_NAME = "marathon";
	public final static String MASTER_ZK_PATH = "/ecloud/master";
	public final static String MARATHON_ZK_PATH = "/ecloud/marathon";
	public final static String APP_ZK_PATH = "/ecloud/app";
	public static final String AGENT_ZK_PATH = "/ecloud/agents/";
	
	//Marathon变量相关
	public final static String MARATHON_APP_ID = "MARATHON_APP_ID";
	public static final String MESOS_TASK_ID = "MESOS_TASK_ID";
	
	//采集指标相关
	public static final String CONTAINER_MEMORY_LIMIT = "limit";
	public static final String CONTAINER_MEMORY_USAGE = "usage";
	public static final String CONTAINER_BLK_IO = "io_serviced_recursive";
	public static final String CONTAINER_CPU_USAGE = "cpu_usage";
	public static final String CONTAINER_TOTAL_USAGE = "total_usage";
	public static final String CONTAINER_SYSTEM_CPU_USAGE = "system_cpu_usage";
	public static final String CONTAINER_PERCPU_USAGE = "percpu_usage";

    //Netty消息通信类型
    public enum CTN_TYPE{
    	;
    	public final static String DOCKER = "docker";  // 组件间的心跳
    	public final static String MESOS = "mesos";  // 组件间的心跳
    }
    
    //Netty消息通信类型
    public enum MsgType{
    	;
    	public final static int RPC_HEARTBEAT = 0x10000000;  // 组件间的心跳
    	public final static int RPC_REG_RESP = 0x10000001; //agent注册响应
    	
    	public final static int RPC_AM_REGISTER = 0x20000000; //agent注册
    	public final static int RPC_AM_METRICS = 0x20000001; //agent指标上报
    	public final static int RPC_AM_CTN_STATUS = 0x20000002; //agent容器状态上报
    	public final static int RPC_MA_MASTER_CHG = 0x20000003; //Master主切换
    	
    	public final static int RPC_OM_REGISTER = 0x30000001;
    	public final static int RPC_OM_HEARTBEAT = 0x30000002;
    	public final static int RPC_MO_REG_RESP = 0x30000003;
    }
    
	// 容器状态
	public enum COMP_TYPE {
		;
		public static final String AGENT = "Agent";// AGENT
		public static final String MASTER = "Master";// MASTER
		public static final String OBSERVER = "";// OBSERVER
		public static final String ZOOKEEPER = "Zookeeper";// ZK
		public static final String MESOS_MASTER = "MesosMaster";// MESOS_MASTER
		public static final String MESOS_SLAVE = "MesosSlave";// MESOS_SLAVE
		public static final String DOCKER = "docker";// MESOS_SLAVE
	}
	
	public static final String E_DEPLOY_INFO = "deployment_info";
	public static final String E_GROUP_CHANGE_SUCCESS = "group_change_success";
	public static final String E_API_POST = "api_post_event";
	public static final String E_DEPLOY_STEP_SUCCESS = "deployment_step_success";
	public static final String E_DEPLOY_SUCCESS = "deployment_success";
	public static final String E_DEPLOY_FAILED = "deployment_failed";
	public static final String E_STATUS_UPDATE = "status_update_event";
	public static final String E_APP_TERMINATED = "app_terminated_event";
    
}

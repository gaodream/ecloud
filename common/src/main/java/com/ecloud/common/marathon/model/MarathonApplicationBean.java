package com.ecloud.common.marathon.model;

import java.io.Serializable;
import java.util.List;

public class MarathonApplicationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; 
	private int instances;//实例数目
	private String cmd; //命令sleep 100
	private double cpus;//CPU ：0.1
	private double disk; //磁盘
	private double mem; //内存
	
	private List<String> acceptedresourceroles;//资源角色
	
	private List<String> args; //  "args": ["sleep", "100"]
	
	private double backofffactor; //1.15
	private int backoffseconds; //1
	private List<String> constraints;//约束 [ [ "hostname", "LIKE", "srv2.*"]],
	private Container container; //容器信息
	private List<String> dependencies;
	private String env; //JSON格式:{"XPS1": "Test","XPS2": "Rest","PASSWORD": {"secret": "/db/password" }}
	private String executor;
	private List<Healthchecks> healthchecks;
	private List<Readinesschecks> readinesschecks;
	private String labels;//JSON格式   {  "owner": "zeus","note": "Away from olympus"}
	private int maxlaunchdelayseconds;
	/**
	 * 实例
	 * {
    "discovery": {
      "ports": [
        {
          "number": 8080,
          "name": "rest-endpoint",
          "protocol": "tcp"
        }
      ]
    },
    "groups": [
      "dev"
    ],
    "labels": {
      "environment": "dev"
    }
  }
	 */
	private String ipaddress;
	/**
	 * 实例
	 * [
         {
             "port": 0,
             "protocol": "tcp",
             "name": "http",
             "labels": {
               "vip": "192.168.0.1:80"
             }
           }],
	 */
	private List<String> portdefinitions;//"portDefinitions": 
	private boolean requireports;
	
	
	private String upgradestrategy;//{ "maximumOverCapacity": 1,"minimumHealthCapacity": 1},
	
	/**
	 * 实例
	 * [
    {
      "uri": "https://foo.com/setup.py"
    },
    {
      "uri": "https://foo.com/archive.zip",
      "executable": false,
      "extract": true,
      "cache": true,
      "outputFile": "newname.zip"
    }
  ],
	 */
	private List<String> fetch;
	private String user;
	
	/**
	 * 实例
	 * {
    "secret1": {
      "source": "/db/password"
    },
    "secret3": {
      "source": "/foo2"
    }
  }
	 * 
	 */
	private String secrets;
	private int taskkillgraceperiodseconds;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getInstances() {
		return instances;
	}
	public void setInstances(int instances) {
		this.instances = instances;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public double getCpus() {
		return cpus;
	}
	public void setCpus(double cpus) {
		this.cpus = cpus;
	}
	public double getDisk() {
		return disk;
	}
	public void setDisk(double disk) {
		this.disk = disk;
	}
	public double getMem() {
		return mem;
	}
	public void setMem(double mem) {
		this.mem = mem;
	}
	public List<String> getAcceptedresourceroles() {
		return acceptedresourceroles;
	}
	public void setAcceptedresourceroles(List<String> acceptedresourceroles) {
		this.acceptedresourceroles = acceptedresourceroles;
	}
	public List<String> getArgs() {
		return args;
	}
	public void setArgs(List<String> args) {
		this.args = args;
	}
	public double getBackofffactor() {
		return backofffactor;
	}
	public void setBackofffactor(double backofffactor) {
		this.backofffactor = backofffactor;
	}
	public int getBackoffseconds() {
		return backoffseconds;
	}
	public void setBackoffseconds(int backoffseconds) {
		this.backoffseconds = backoffseconds;
	}

	
	public List<String> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}
	public Container getContainer() {
		return container;
	}
	public void setContainer(Container container) {
		this.container = container;
	}
	public List<String> getDependencies() {
		return dependencies;
	}
	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
	public List<Healthchecks> getHealthchecks() {
		return healthchecks;
	}
	public void setHealthchecks(List<Healthchecks> healthchecks) {
		this.healthchecks = healthchecks;
	}
	public List<Readinesschecks> getReadinesschecks() {
		return readinesschecks;
	}
	public void setReadinesschecks(List<Readinesschecks> readinesschecks) {
		this.readinesschecks = readinesschecks;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public int getMaxlaunchdelayseconds() {
		return maxlaunchdelayseconds;
	}
	public void setMaxlaunchdelayseconds(int maxlaunchdelayseconds) {
		this.maxlaunchdelayseconds = maxlaunchdelayseconds;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public List<String> getPortdefinitions() {
		return portdefinitions;
	}
	public void setPortdefinitions(List<String> portdefinitions) {
		this.portdefinitions = portdefinitions;
	}
	public boolean isRequireports() {
		return requireports;
	}
	public void setRequireports(boolean requireports) {
		this.requireports = requireports;
	}
	public String getUpgradestrategy() {
		return upgradestrategy;
	}
	public void setUpgradestrategy(String upgradestrategy) {
		this.upgradestrategy = upgradestrategy;
	}
	public List<String> getFetch() {
		return fetch;
	}
	public void setFetch(List<String> fetch) {
		this.fetch = fetch;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSecrets() {
		return secrets;
	}
	public void setSecrets(String secrets) {
		this.secrets = secrets;
	}
	public int getTaskkillgraceperiodseconds() {
		return taskkillgraceperiodseconds;
	}
	public void setTaskkillgraceperiodseconds(int taskkillgraceperiodseconds) {
		this.taskkillgraceperiodseconds = taskkillgraceperiodseconds;
	}
	
}

package com.ecloud.deploy.common.model;

/**
 * 
 * @author gaogao
 *
 */
public class RedisContainerMetrics {

    private String marathonId; //marathon应用id
    private String taskId; //taskId
    private String ctnId; //容器id
    private float cpuUsage; //cpu使用率
    private float memUsage; //内存使用率
    private float totalMem; //总内存
    private double discUsage; //磁盘使用率
    private double netUpIO; //上行带宽
    private double netDownIO; //下行带宽

    private String hostIp;
    private String hostPort;
    private Long insertTime;
    private Long collectTime;

    private String hystrix;

    public RedisContainerMetrics() {
    }

    public RedisContainerMetrics(String marathonId, String taskId, String ctnId, 
    		float cpuUsage, float memUsage, float totalMem, double discUsage, 
    		double netUpIO, double netDownIO, String hostIp, String hostPort, 
    		Long insertTime, Long collectTime, String hystrixInfo) {
        this.marathonId = marathonId;
        this.taskId = taskId;
        this.ctnId = ctnId;
        this.cpuUsage = cpuUsage;
        this.memUsage = memUsage;
        this.totalMem = totalMem;
        this.discUsage = discUsage;
        this.netUpIO = netUpIO;
        this.netDownIO = netDownIO;
        this.hostIp = hostIp;
        this.hostPort = hostPort;
        this.insertTime = insertTime;
        this.collectTime = collectTime;
        this.hystrix = hystrixInfo;
    }

    public String getMarathonId() {
        return marathonId;
    }

    public void setMarathonId(String marathonId) {
        this.marathonId = marathonId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCtnId() {
        return ctnId;
    }

    public void setCtnId(String ctnId) {
        this.ctnId = ctnId;
    }

    public float getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(float cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public float getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(float memUsage) {
        this.memUsage = memUsage;
    }

    public float getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(float totalMem) {
        this.totalMem = totalMem;
    }

    public double getDiscUsage() {
        return discUsage;
    }

    public void setDiscUsage(double discUsage) {
        this.discUsage = discUsage;
    }

    public double getNetUpIO() {
        return netUpIO;
    }

    public void setNetUpIO(double netUpIO) {
        this.netUpIO = netUpIO;
    }

    public double getNetDownIO() {
        return netDownIO;
    }

    public void setNetDownIO(double netDownIO) {
        this.netDownIO = netDownIO;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public Long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Long insertTime) {
        this.insertTime = insertTime;
    }

    public Long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Long collectTime) {
        this.collectTime = collectTime;
    }

 
    

	public String getHystrix() {
		return hystrix;
	}

	public void setHystrix(String hystrix) {
		this.hystrix = hystrix;
	}

	@Override
    public String toString() {
        return "RedisContainerMetrics{" +
                "marathonId='" + marathonId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", ctnId='" + ctnId + '\'' +
                ", cpuUsage=" + cpuUsage +
                ", memUsage=" + memUsage +
                ", totalMem=" + totalMem +
                ", discUsage=" + discUsage +
                ", netUpIO=" + netUpIO +
                ", netDownIO=" + netDownIO +
                ", hostIp='" + hostIp + '\'' +
                ", hostPort='" + hostPort + '\'' +
                ", insertTime=" + insertTime +
                ", collectTime=" + collectTime +
                ", hystrixInfo='" + hystrix.toString() + '\'' +
                '}';
    }
}

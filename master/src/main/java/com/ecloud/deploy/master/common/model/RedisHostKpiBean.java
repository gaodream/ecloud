package com.ecloud.deploy.master.common.model;

/**
 * 
 * @author gaogao
 *
 */
public class RedisHostKpiBean {
    private String hostName;
    private String hostIp;
    private double cpuUsage; //cpu使用率
    private double memUsage; //内存使用率
    private double totalMem; //总内存
    private double discUsage; //磁盘使用率
    private double netUpIO; //上行带宽
    private double netDownIO; //下行带宽
    private double load;

    private Long insertTime;
    private Long collectTime;

    public RedisHostKpiBean() {
    }

    public RedisHostKpiBean(String hostName, String hostIp, double cpuUsage, double memUsage, double totalMem, double discUsage, double netUpIO, double netDownIO, double load, Long insertTime, Long collectTime) {
        this.hostName = hostName;
        this.hostIp = hostIp;
        this.cpuUsage = cpuUsage;
        this.memUsage = memUsage;
        this.totalMem = totalMem;
        this.discUsage = discUsage;
        this.netUpIO = netUpIO;
        this.netDownIO = netDownIO;
        this.load = load;
        this.insertTime = insertTime;
        this.collectTime = collectTime;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(double memUsage) {
        this.memUsage = memUsage;
    }

    public double getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(double totalMem) {
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

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
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

    @Override
    public String toString() {
        return "RedisHostKpiBean{" +
                "hostName='" + hostName + '\'' +
                ", hostIp='" + hostIp + '\'' +
                ", cpuUsage=" + cpuUsage +
                ", memUsage=" + memUsage +
                ", totalMem=" + totalMem +
                ", discUsage=" + discUsage +
                ", netUpIO=" + netUpIO +
                ", netDownIO=" + netDownIO +
                ", load=" + load +
                ", insertTime=" + insertTime +
                ", collectTime=" + collectTime +
                '}';
    }
}

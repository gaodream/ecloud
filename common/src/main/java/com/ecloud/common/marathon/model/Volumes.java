package com.ecloud.common.marathon.model;

public class Volumes {

    private String containerpath; //容器路径
    private String hostpath;//主机路径
    private String mode;//读写模式 rw
    
    public Volumes() {
	}

	public Volumes(String containerpath, String hostpath, String mode) {
		this.containerpath = containerpath;
		this.hostpath = hostpath;
		this.mode = mode;
	}
    
	public void setContainerpath(String containerpath) {
         this.containerpath = containerpath;
     }
     public String getContainerpath() {
         return containerpath;
     }

    public void setHostpath(String hostpath) {
         this.hostpath = hostpath;
     }
     public String getHostpath() {
         return hostpath;
     }

    public void setMode(String mode) {
         this.mode = mode;
     }
     public String getMode() {
         return mode;
     }

}
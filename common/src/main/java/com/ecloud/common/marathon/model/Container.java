package com.ecloud.common.marathon.model;

import java.io.Serializable;
import java.util.List;

public class Container implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Docker docker;
	private String type;
    private List<Volumes> volumes;
    
    
	public Docker getDocker() {
		return docker;
	}
	public void setDocker(Docker docker) {
		this.docker = docker;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Volumes> getVolumes() {
		return volumes;
	}
	public void setVolumes(List<Volumes> volumes) {
		this.volumes = volumes;
	}
    
    
    

}

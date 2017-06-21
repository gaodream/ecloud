package com.ecloud.deploy.master.plugin;

public interface ServicePlugin {
	
	public void start();
	public void restart();
	public void ScaleUp();
	public void scaleDown();
	public void ha();
	public void Stop();

}

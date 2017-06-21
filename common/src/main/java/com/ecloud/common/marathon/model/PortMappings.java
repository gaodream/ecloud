package com.ecloud.common.marathon.model;


public class PortMappings {

    private int containerport;
    private int hostport;
    private String protocol;
    private int serviceport;
    private String name;
    private String labels;
    
    
    public PortMappings() {
		super();
	}
	public PortMappings(int containerport, int hostport, String protocol, int serviceport, String name, String labels) {
		super();
		this.containerport = containerport;
		this.hostport = hostport;
		this.protocol = protocol;
		this.serviceport = serviceport;
		this.name = name;
		this.labels = labels;
	}
	public void setContainerport(int containerport) {
         this.containerport = containerport;
     }
     public int getContainerport() {
         return containerport;
     }

    public void setHostport(int hostport) {
         this.hostport = hostport;
     }
     public int getHostport() {
         return hostport;
     }

    public void setProtocol(String protocol) {
         this.protocol = protocol;
     }
     public String getProtocol() {
         return protocol;
     }

    public void setServiceport(int serviceport) {
         this.serviceport = serviceport;
     }
     public int getServiceport() {
         return serviceport;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}

     

}
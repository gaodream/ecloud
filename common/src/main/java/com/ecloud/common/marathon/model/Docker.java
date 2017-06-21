package com.ecloud.common.marathon.model;
import java.util.List;


public class Docker {

    private boolean forcepullimage;
    private String image;
    private String network;
    private List<String> parameters;
    private List<PortMappings> portmappings;
    private boolean privileged;
    
    
    public void setForcepullimage(boolean forcepullimage) {
         this.forcepullimage = forcepullimage;
     }
     public boolean getForcepullimage() {
         return forcepullimage;
     }

    public void setImage(String image) {
         this.image = image;
     }
     public String getImage() {
         return image;
     }

    public void setNetwork(String network) {
         this.network = network;
     }
     public String getNetwork() {
         return network;
     }

    
     

    public List<String> getParameters() {
		return parameters;
	}
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	public void setPortmappings(List<PortMappings> portmappings) {
         this.portmappings = portmappings;
     }
     public List<PortMappings> getPortmappings() {
         return portmappings;
     }

    public void setPrivileged(boolean privileged) {
         this.privileged = privileged;
     }
     public boolean getPrivileged() {
         return privileged;
     }

}
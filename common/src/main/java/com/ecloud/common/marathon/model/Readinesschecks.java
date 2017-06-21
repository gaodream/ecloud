package com.ecloud.common.marathon.model;
import java.util.List;


public class Readinesschecks {

    private String name;
    private String protocol;
    private String path;
    private String portname;
    private int intervalseconds;
    private int timeoutseconds;
    private List<Integer> httpstatuscodesforready;
    private boolean preservelastresponse;
    
    
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setProtocol(String protocol) {
         this.protocol = protocol;
     }
     public String getProtocol() {
         return protocol;
     }

    public void setPath(String path) {
         this.path = path;
     }
     public String getPath() {
         return path;
     }

    public void setPortname(String portname) {
         this.portname = portname;
     }
     public String getPortname() {
         return portname;
     }

    public void setIntervalseconds(int intervalseconds) {
         this.intervalseconds = intervalseconds;
     }
     public int getIntervalseconds() {
         return intervalseconds;
     }

    public void setTimeoutseconds(int timeoutseconds) {
         this.timeoutseconds = timeoutseconds;
     }
     public int getTimeoutseconds() {
         return timeoutseconds;
     }

   
     

    public List<Integer> getHttpstatuscodesforready() {
		return httpstatuscodesforready;
	}
	public void setHttpstatuscodesforready(List<Integer> httpstatuscodesforready) {
		this.httpstatuscodesforready = httpstatuscodesforready;
	}
	public void setPreservelastresponse(boolean preservelastresponse) {
         this.preservelastresponse = preservelastresponse;
     }
     public boolean getPreservelastresponse() {
         return preservelastresponse;
     }

}
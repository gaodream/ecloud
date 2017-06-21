package com.ecloud.common.marathon.model;

public class Healthchecks {

    private int graceperiodseconds;
    private boolean ignorehttp1xx;
    private int intervalseconds;
    private int maxconsecutivefailures;
    private String path;
    private int portindex;
    private String protocol;
    private int timeoutseconds;
    
    
    
    
    public Healthchecks() {
		super();
	}
	public Healthchecks(int graceperiodseconds, boolean ignorehttp1xx, int intervalseconds, int maxconsecutivefailures,
			String path, int portindex, String protocol, int timeoutseconds) {
		super();
		this.graceperiodseconds = graceperiodseconds;
		this.ignorehttp1xx = ignorehttp1xx;
		this.intervalseconds = intervalseconds;
		this.maxconsecutivefailures = maxconsecutivefailures;
		this.path = path;
		this.portindex = portindex;
		this.protocol = protocol;
		this.timeoutseconds = timeoutseconds;
	}
	public void setGraceperiodseconds(int graceperiodseconds) {
         this.graceperiodseconds = graceperiodseconds;
     }
     public int getGraceperiodseconds() {
         return graceperiodseconds;
     }

    public void setIgnorehttp1xx(boolean ignorehttp1xx) {
         this.ignorehttp1xx = ignorehttp1xx;
     }
     public boolean getIgnorehttp1xx() {
         return ignorehttp1xx;
     }

    public void setIntervalseconds(int intervalseconds) {
         this.intervalseconds = intervalseconds;
     }
     public int getIntervalseconds() {
         return intervalseconds;
     }

    public void setMaxconsecutivefailures(int maxconsecutivefailures) {
         this.maxconsecutivefailures = maxconsecutivefailures;
     }
     public int getMaxconsecutivefailures() {
         return maxconsecutivefailures;
     }

    public void setPath(String path) {
         this.path = path;
     }
     public String getPath() {
         return path;
     }

    public void setPortindex(int portindex) {
         this.portindex = portindex;
     }
     public int getPortindex() {
         return portindex;
     }

    public void setProtocol(String protocol) {
         this.protocol = protocol;
     }
     public String getProtocol() {
         return protocol;
     }

    public void setTimeoutseconds(int timeoutseconds) {
         this.timeoutseconds = timeoutseconds;
     }
     public int getTimeoutseconds() {
         return timeoutseconds;
     }

}
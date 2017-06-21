package com.ecloud.common.log;

@FunctionalInterface
public interface Logging {
   
/*   Logger log = null;
   
   default void  checkLog(){
	   if(null==log)
	   log = LoggerFactory.getLogger(this.getClass());
   }
   
   public default void  logDebug(String msg){
	   checkLog();
	   if (log.isDebugEnabled()) log.debug(msg);
   }*/
   
   // 抽象方法  
   public void sub();  
 
}

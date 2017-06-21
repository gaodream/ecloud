package com.ecloud.deploy.agent;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecloud.common.lock.InstanceLock;
import com.ecloud.common.log.LogBack;
import com.ecloud.common.rpc.udp.NettyEndpoint;
import com.ecloud.deploy.agent.common.AgentConfig;
import com.ecloud.deploy.agent.handler.AgentHandler;

/**
 * 基于原有的Agent进行改造
 * @author gaogao
 *
 */
public class Agent {
	
    private static final Logger LOG = LoggerFactory.getLogger(Agent.class);
    private static HelpFormatter helpFormatter =  new HelpFormatter();
    private static final String CONF_OPT = "conf";
    

    private static Options opts;  //命令行选项
    private static AgentHandler handler; //agent控制器
    private static InstanceLock instanceLock; //实例锁

    // 静态初始化块
    static {
        opts = new Options();
        opts.addOption(CONF_OPT, true, "agent conf path");
    }
    
    private static String doCheck(String[] args){

    	/*************************2.参数校验*************************************
        |1、解析参数，正确的启动参数应为  --conf ${confPath}
        |3、检查agent的配置目录
        |3、配置目录下agent.properties和agent-logback.xml
        *********************************************************************/
    	String conf = "";
    	CommandLine cliParser = null;
		try {
			cliParser = new GnuParser().parse(opts, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (args.length == 0) {
			helpFormatter.printHelp("Agent", opts);
		} else {
			if (cliParser.hasOption(CONF_OPT)) {
				conf = cliParser.getOptionValue(CONF_OPT);
				LOG.info(CONF_OPT + " is {}", conf);
			} else {
				throw new IllegalArgumentException(CONF_OPT + " is not input, please input again.");
			}
		}
         return conf;
    }


    /**
     * 主函数
     * @param args 命令行参数
     * @throws ParseException 命令行解析异常
     */
    public static void main(String[] args) throws Exception {
       // 1.参数和配置校验
       //String conf = doCheck(args);
       String conf = "F:/gitwork/dev/ecloud/agent/src/main/resources";
       
       if(null==conf||"".equals(conf)){
    	   System.exit(0);
       }
       // 2.加载配置文件
       AgentConfig config = AgentConfig.getInstance(conf);
       
       // 3.初始化logback
       LogBack.init(conf + File.separator +AgentConfig.LOGBACK_CONF);
       
       // 4.进程控制 限制仅能启动一个agent进程实例
       instanceLock = new InstanceLock(new File(conf + File.separator + ".lock"));
       if (instanceLock.getLock() == null) {
           throw new RuntimeException("Can not get lock , Agent has been started.");
       }
       
       Runtime.getRuntime().addShutdownHook(new Thread(()->{  
    	   instanceLock.release();
	       if(handler!=null)
	         handler.stop();
	       }
       ));
       
       // 6.构造agentController 启动
       // 构造适配器相关
       NettyEndpoint endpoint = new NettyEndpoint(config.getString(AgentConfig.CURRENT_HOST_IP), 
				config.getInteger("ecloud.agent.rpc.port",9969), 
				config.getInteger("ecloud.agent.rpc.message.timeOut",20));
        handler = new AgentHandler(endpoint);
        endpoint.registHandler(handler);
		handler.start();
        LOG.info("agent start success");
    }
}

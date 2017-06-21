package com.ecloud.deploy.agent.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecloud.common.http.HttpClient;
import com.ecloud.common.model.Pair;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerConfig;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.DockerCmdExecFactoryImpl;

/**
 * DockerClient工具
 * 封装的docker java客户端
 * （补充目前docker-java做不到的功能）
 * @author gaogao
 */
public final class EDockerClient {
	private static final Logger LOG =  LoggerFactory.getLogger(EDockerClient.class);

    //访问docker服务的客户端
    private DockerClient dockerClient;
    private static int defaultMaxPerRouteConnections = 10;
    private static int defaultMaxTotalConnections = 50;
    private static String defaultDockerServerUrl="unix:///var/run/docker.sock";
    
    private static EDockerClient eDockerClient = null;
    
    public EDockerClient(){
    	this(defaultMaxPerRouteConnections,defaultMaxTotalConnections,defaultDockerServerUrl);
    }
    
    @SuppressWarnings("resource")
	private EDockerClient(int maxPerRouteConnections,
					       int maxTotalConnections,
					       String dockerServerUrl){
    	DockerClientConfig dockerConfig = DockerClientConfig.createDefaultConfigBuilder().withUri(dockerServerUrl).build();
        // 增加最大连接和最小连接的限制
        dockerClient = DockerClientBuilder.getInstance(dockerConfig)
                		.withDockerCmdExecFactory(new DockerCmdExecFactoryImpl()
                		.withMaxPerRouteConnections(maxPerRouteConnections)
                		.withMaxTotalConnections(maxTotalConnections))
                		.build();
        
        if (dockerClient == null) {
            throw new RuntimeException("build dockerClient failed, return null");
        }
    }
    
    
    public static EDockerClient getInstance(int maxPerRouteConnections,
										       int maxTotalConnections,
										       String dockerServerUrl){
    	if(null == eDockerClient){
    		return  new EDockerClient(maxPerRouteConnections,maxTotalConnections,dockerServerUrl);
    	}
    	return eDockerClient;
    }
    
    
    /**
     * 通过inspect命令获取容器信息
     *
     * @param dockerDaemonAddr 要连接访问的远程docker-daemon地址，格式如http://10.1.245.21:2375
     * @param ctnId            容器id
     * @return 返回map存放的容器信息
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> inspectContainer(String dockerDaemonAddr, String ctnId) {
        String url = String.format("%s/containers/%s/json", dockerDaemonAddr, ctnId);
        Pair<Integer, String> result = HttpClient.doGet(url);
        if (result.getFirst()!= 200) {
            return Collections.emptyMap();
        } else {
            String json = result.getSecond();
            Map<String, Object> map = toObject(json, Map.class);
            return map == null ? Collections.EMPTY_MAP : map;
        }
    }
    
    public static <T> T toObject(String json, Class<T> clazz) {
    	ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			LOG.error(json, e);
			return null;
		}
	}
    
    
    /**
     * 调用docker的inspect命令同时获取对应的env参数
     *
     * @param containerId 容器id
     * @param envName     环境变量名称
     * @return 返回对应的环境变量值
     */
    public String inspectAndGetEnv(String containerId, String envName) {
        try {
            InspectContainerResponse ics = dockerClient.inspectContainerCmd(containerId).exec();
            ContainerConfig config = ics.getConfig();
            String[] envs = config.getEnv();
            for (String env : envs) {
                if (env.startsWith(envName)) {
                    String[] kv = env.split("=");
                    if (kv.length != 2) {
                        LOG.info("can not get container : {}'s {} env via inspect cmd.", containerId, envName);
                        return "";
                    }
                    return kv[1];
                }
            }
        } catch (Exception e) {
            LOG.warn("{}", e);
        }
        return "";
    }
 
    
    /**
     * 执行dockerClient的list命令
     * @return 返回Container的list
     */
    public List<Container> getContainerList() {
        List<Container> ctnList = new ArrayList<Container>();
        try {
        	ctnList = dockerClient.listContainersCmd().exec();
        } catch (Exception e) {
            LOG.warn("when exec list cmd exception : {}", e);
        }
        return ctnList;
    }
    
    
    
    /**
     * 执行dockerClient的stats命令,并添加回掉处理方法
     * @return ResultCallback
     */
    public ResultCallback<Statistics> execStats(String containerId,ResultCallback<Statistics> callback ) {
    	
    	return dockerClient.statsCmd().withContainerId(containerId).exec(callback);
    }
    
    public void removeContainerCmd(String containerId){
    	dockerClient.removeContainerCmd(containerId).exec();
    }
    
    public void close(){
	   if (dockerClient != null) {
           try {
               dockerClient.close();
               dockerClient = null;
           } catch (IOException e) {
               LOG.warn("dockerClient close exception : {}", e);
           }
       }
    }
    
    public static void main(String[] args) {
    	  /* DockerClientConfig dockerConfig = DockerClientConfig.createDefaultConfigBuilder().withUri("http://10.1.245.102:2375").build();
           // 增加最大连接和最小连接的限制
    	   DockerClient dockerClient = dockerClient = DockerClientBuilder.getInstance(dockerConfig)
                   .withDockerCmdExecFactory(new DockerCmdExecFactoryImpl()
                           .withMaxPerRouteConnections(10)
                           .withMaxTotalConnections(10)).build();
    	   
    	   List<Container> cList = dockerClient.listContainersCmd().exec();
    	   Container ctn = cList.get(2);
    	   System.err.println("---"+JSON.toJSONString(ctn.getPorts()));
    	   InspectContainerResponse ics = dockerClient.inspectContainerCmd(ctn.getId()).exec();
           Map<ExposedPort, Binding[]> bindings = ics.getNetworkSettings().getPorts().getBindings();
           
           Map<String,String> portMapping = new HashMap<String,String>();
           Iterator<Entry<ExposedPort, Binding[]>> itor = bindings.entrySet().iterator();
           //System.err.println(JSON.toJSONString(ics.getNetworkSettings().getPorts()));
           while(itor.hasNext()){
        	   Entry<ExposedPort, Binding[]> entry = itor.next();
        	   String protocol = entry.getKey().getProtocol().name();
        	   int ctnPort = entry.getKey().getPort();
        	   Binding[] binds = entry.getValue();
        	   int hostPort = 0;
        	   if(binds.length>0){
        		   hostPort = binds[0].getHostPort();
        	   }
        	   portMapping.put(protocol, hostPort+":"+ctnPort);
           }*/
          // System.err.println(JSON.toJSONString(portMapping));
    }
}

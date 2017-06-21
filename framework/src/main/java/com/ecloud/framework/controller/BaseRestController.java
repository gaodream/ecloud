package com.ecloud.framework.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecloud.framework.common.EConstants;
import com.ecloud.framework.common.EResponse;
import com.ecloud.framework.model.ValueObject;
import com.ecloud.framework.service.BaseService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 * @author gaogao
 *
 * 最佳实践：
 * 1、不要在controller中定义成员变量。
 * 2、万一必须要定义一个非静态成员变量时候，则通过注解@Scope("prototype")，将其设置为多例模式
 * 备注：
 * 单例是不安全的，会导致属性重复使用。如果不使用静态变量就没有关系，不会导致线程不安全，当然单例性能高于多例
 */
///@Scope("prototype")
public abstract class BaseRestController<T extends ValueObject> {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseRestController.class);
    
	@SuppressWarnings("rawtypes")
	private Class clz ;
	@SuppressWarnings("unused")
	private T instance ;//用于多步骤操作
	private Type type ;

	public BaseRestController() {
		initialize();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize(){
		 type = this.getClass().getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			clz = Object.class;
		}
		Type param = ((ParameterizedType) type).getActualTypeArguments()[0];
		clz = (Class)param;
		try {
			 instance = (T)clz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Spring提供的方式，待测试
	/*	ResolvableType resolvableType = ResolvableType.forClass(BaseController.class);  
		Class<?> tpe = resolvableType.as(Service.class).getGeneric(1).resolve();
		Class<?> tpe1 = resolvableType.getInterfaces()[0].getGeneric(1).resolve();*/
	}

	
	@ApiOperation(value="根据ID查询",notes="注意事项")
	@RequestMapping(value ="/doFind/{id}",method=RequestMethod.GET)
	@ApiImplicitParam(name = "id",value = "业务ID",dataType="String",paramType = "path")
	public EResponse doFind(@PathVariable int id){
		EResponse response = EResponse.build();
		try {
			response.setResult(this.getBaseService().doFindById(id));
		}catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return response;
	}
	
	
	@ApiOperation(value="查询列表",notes="列表信息")
	@RequestMapping(value ="/doSearch",method=RequestMethod.GET)
	@ApiImplicitParam(name="vo",value = "业务实体")
	public  EResponse doSearch(@ModelAttribute(value="searchVO") T vo ,HttpServletRequest request) {
		EResponse response = EResponse.build();
		try {
			response.setResult(this.getBaseService().doSearchListByVO(vo));
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return response;
	}
	

	@ApiOperation(value="根据VO保存",notes="注意事项")
	@RequestMapping(value ="/doSave",method=RequestMethod.POST)
	@ApiImplicitParam(name="vo",value = "业务实体")
	public EResponse doSave(@ModelAttribute T vo) throws Exception{
		EResponse response = EResponse.build();
		try {
			this.getBaseService().doInsertByVO(vo);
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
			response.setRespCode(EConstants.HTTP.FAILED);
		}
		
		return 	response;
	}
	
	@ApiOperation(value="根据VO更新",notes="注意事项")
	@RequestMapping(value ="/doUpdate",method=RequestMethod.PUT)
	@ApiImplicitParam(name="vo",value = "业务实体")
	public EResponse doUpdate(@ModelAttribute T vo) throws Exception{
		EResponse response = EResponse.build();
		try {
			if(validateParam(vo)){
				this.getBaseService().doUpdateByVO(vo);
			}
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
			response.setRespCode(EConstants.HTTP.FAILED);
		}
		return 	response;
	}
	
	@ApiOperation(value="根据VO删除",notes="注意事项")
	@RequestMapping(value ="/doDelete",method=RequestMethod.DELETE)
	public EResponse  doDelete(@ModelAttribute T vo) throws Exception{
		EResponse response = EResponse.build();
		try {
			this.getBaseService().doDeleteByVO(vo);
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
			response.setRespCode(EConstants.HTTP.FAILED);
		}
		return 	response;
	}
	
	@ApiOperation(value="根据ID删除",notes="注意事项")
	@RequestMapping(value ="/doDelete/{id}",method=RequestMethod.DELETE)
	@ApiImplicitParam(name="id",value = "业务ID",dataType="String",paramType="path")
	public EResponse  doDelete(@PathVariable long id) throws Exception{
		EResponse response = EResponse.build();
		try {
			this.getBaseService().doDeleteById(id);
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
			response.setRespCode(EConstants.HTTP.FAILED);
		}
		return 	response;
	}
	
	
	
	//增加令牌
	public void doAddTokenValid()  throws Exception{
	  
	}

	//编辑令牌
	public void doEditTokenValid()  throws Exception{
	    
	}

	//删除令牌
	public void doDeleteTokenValid()  throws Exception{
	    
	}

	//增加明细行
	public void doAddDetailRow()  throws Exception{
	    
	}

	//日志处理
	public String addOpLog(){
	    return "";
	}

	public void doExportExcelInit() throws Exception{
	
	}

	public void doPrintInit()  throws Exception{
	   
	}
	
	public void doExportExcel() throws Exception{
	   
	}
	
	public void doPrint() throws Exception{
	    
	}
	
	protected void clearSession() throws Exception{
	}


	/**
	 * 对VO中数据的验证
	 * @param vo
	 * @return
	 */
	public boolean validateParam(ValueObject vo){
		return true;
	/*	Set<ConstraintViolation<ValueObject>> constraints = validator.validate(vo, First.class);
		if(constraints.size() > 0){
			Iterator<ConstraintViolation<ValueObject>> it = constraints.iterator();
			while(it.hasNext()){
				System.out.println(it.next().getMessage());
			}
			return false;
		}else{
			return true;
		}*/
	}

	public abstract BaseService<T> getBaseService();
	
	
}

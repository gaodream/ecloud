package com.ecloud.framework.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.PostConstruct;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.ecloud.framework.common.EConstants;
import com.ecloud.framework.model.EResponse;
import com.ecloud.framework.model.ValueObject;
import com.ecloud.framework.service.BaseService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


public abstract class BaseController<T extends ValueObject> {

	protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

	protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	protected static final String RESULT = "result";

    @SuppressWarnings("rawtypes")
	private Class clz ;
	private T instance ;//用于多步骤操作
	private Type type ;

	public BaseController() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
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
	@ApiImplicitParam(name = "id",value = "业务ID",dataType="String",paramType = "path")
	@GetMapping("{id}")
	public ModelAndView find(@PathVariable long id) {
		EResponse response = EResponse.build();
		try {
			response.setResult(this.getBaseService().doFindById(id));
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return new ModelAndView(this.getPath()+"Detail","result",response);
	}

	@ApiOperation(value="查询列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value = "业务实体")
	@PostMapping("list")
	public ModelAndView list(@RequestBody T vo ) {
		EResponse response = EResponse.build();
		try {
			response.setResult(this.getBaseService().doSearchListByVO(vo));
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return new ModelAndView(this.getPath()+"List","result",response);
	}


	@ApiOperation(value="分页查询列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value = "业务实体")
	@PostMapping("page")
	public ModelAndView page(@RequestBody T vo ) {
		EResponse response = EResponse.build();
		try {
			response.setResult(this.getBaseService().doSearchPage(vo));
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return new ModelAndView(this.getPath()+"List","result",response);
	}


	@ApiOperation(value="根据VO保存",notes="注意事项")
	@ApiImplicitParam(name="vo",value = "业务实体")
	@PostMapping("")
	public ModelAndView save(@RequestBody T vo) throws Exception{
		EResponse response = EResponse.build();
		try {
			this.getBaseService().doInsertByVO(vo);
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
			response.setRespCode(EConstants.HTTP.FAILED);
		}
		return 	page(instance);
	}

	@ApiOperation(value="根据VO更新",notes="注意事项")
	@ApiImplicitParam(name="vo",value = "业务实体")
	@PutMapping("")
	public ModelAndView update(@RequestBody T vo) throws Exception{
		EResponse response = EResponse.build();
		try {
			if(validateParam(vo)){
				this.getBaseService().doUpdateByVO(vo);
			}
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
			response.setRespCode(EConstants.HTTP.FAILED);
		}
		return new ModelAndView(this.getPath()+"Edit","result",response);
	}

	@ApiOperation(value="根据VO删除",notes="注意事项")
	@DeleteMapping("")
	public ModelAndView  delete(@RequestBody T vo) throws Exception{
		EResponse response = EResponse.build();
		try {
			this.getBaseService().doDeleteByVO(vo);
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
			response.setRespCode(EConstants.HTTP.FAILED);
		}
		return 	page(instance);
	}

	@ApiOperation(value="根据ID删除",notes="注意事项")
	@ApiImplicitParam(name="id",value = "业务ID",dataType="String",paramType="path")
	@DeleteMapping("{id}")
	public ModelAndView  delete(@PathVariable long id) throws Exception{
		EResponse response = EResponse.build();
		try {
			this.getBaseService().doDeleteById(id);
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
			response.setRespCode(EConstants.HTTP.FAILED);
		}
		return 	page(instance);
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

	public abstract String getPath();

}

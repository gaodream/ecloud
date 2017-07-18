package com.ecloud.generator.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ecloud.generator.common.bean.GeneratorBean;
import com.ecloud.generator.common.service.GeneratorService;


@Controller
@RequestMapping
public class GeneratorController {
	
	@Autowired
	public GeneratorService generatorService;
	
	@Value("${ecloud.project.name}")
	private String projectName;
	@Value("${ecloud.model.name}")
	private String modelName;
	@Value("${ecloud.generator.path}")
	private String genPath;
	
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/doGetTables")
	public @ResponseBody Map<String,Object> doGetTables(){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("projectName", projectName);
		result.put("modelName", modelName);
		result.put("genPath", genPath);
		result.put("list", generatorService.getTables());
		return result;
	}
	
	@RequestMapping(value="/doGetColumns", produces = "application/json;charset=utf-8")
	public @ResponseBody String doGetColumns(@RequestParam String tableName){
		return JSON.toJSONString(generatorService.getTableCloumn(tableName));
	}
	
	
	@RequestMapping("/doGenerate")
	public @ResponseBody String doGenerate(GeneratorBean bean){
		generatorService.generate(bean);
		return "";
	}
	 

}

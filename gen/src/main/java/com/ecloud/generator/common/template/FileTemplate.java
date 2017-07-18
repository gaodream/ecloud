package com.ecloud.generator.common.template;

import java.util.HashMap;
import java.util.Map;

import com.ecloud.generator.common.bean.Cloumn;

public class FileTemplate {
    /******************************   公用  SQL 模板   ************************************/
	//1.库中的表查询
	public final static String SQL_TABLE =" SELECT TABLE_NAME,TABLE_COMMENT FROM  information_schema.TABLES  WHERE 1=1  AND table_schema = ? ";
	
	//2.表中的列查询
	public final static String SQL_COLUMN ="SELECT TABLE_CATALOG,TABLE_SCHEMA,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE, "
			+" CHARACTER_MAXIMUM_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_COMMENT "
			+" FROM INFORMATION_SCHEMA.COLUMNS  "
			+" WHERE 1=1  AND TABLE_NAME = ? and table_schema =  ?  ORDER BY ordinal_position" ;
	

	//3.查询记录数SQL
	public final static String SQL_COUNT = "\t<select id=\"doSearchCount\" parameterType=\"$_voName$\" resultType=\"java.lang.Integer\">\n"
										   +"	\tSELECT COUNT(0) AS _COUNT FROM $TABLE_NAME$ $PREFIX$ WHERE 1=1 AND DELETED_FLAG = 'N'\n"
										   +"	\t<include refid=\"SQL_CONDITION\"/>\n"
										   +"\t</select>\n";
	
	
	 /******************************   公用  文件 模板   ************************************/
	//1.Mapper文件模板
	public static  final String MAPPER_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+
			  "<!DOCTYPE mapper  PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">"+
			   "\n<mapper namespace=\"$packagePath$.dao.$_daoName$\">\n";

	//2.DAO文件模板
	public static  final String DAO_TEMPLATE = "package $packagePath$.dao;\n\n"+
						"import com.ecloud.framework.dao.BaseDAO;\n"+
						"import $packagePath$.model.$_voName$;\n\n"+
						"public interface $_daoName$ extends BaseDAO<$_voName$>{\n"+
						"}";
	
	//3.Service文件模板
	public static  final String SERVICE_TEMPLATE = "package $packagePath$.service;\n\n"+
						"import com.ecloud.framework.service.BaseService;\n"+
						"import $packagePath$.model.$_voName$;\n\n"+
						"public interface $_serviceName$  extends BaseService<$_voName$>{\n"+
						"}";
	//4.ServiceImpl文件模板
	public static  final String SERVICE_IMPL_TEMPLATE = "package $packagePath$.service.impl;\n\n"+
							"import org.springframework.beans.factory.annotation.Autowired;\n"+
							"import org.springframework.stereotype.Service;\n\n"+
							"import com.ecloud.framework.service.impl.BaseServiceImpl;\n"+
							"import $packagePath$.dao.$_daoName$;\n"+
							"import $packagePath$.model.$_voName$;\n"+
							"import $packagePath$.service.$_serviceName$;\n\n"+
							"@Service(\"$serviceName$\")\n"+
							"public class $_serviceName$Impl extends BaseServiceImpl<$_voName$> implements $_serviceName$ {\n\n"+
							"\t@Autowired\n"+
							"\tprivate $_daoName$ $daoName$;\n\n"+
							"\t@Override\n"+
							"\tpublic $_daoName$ getBaseDAO() {\n"+
							"\t\treturn $daoName$;\n"+
							"\t}\n"+
							"}";
	
	//5.controller文件模板
	public static  final String CONTROLLER_TEMPLATE = "package $packagePath$.controller;\n\n"+
								"import org.springframework.beans.factory.annotation.Autowired;\n\n"+
								"import org.springframework.web.bind.annotation.RestController;\n"+
								"import org.springframework.web.bind.annotation.RequestMapping;\n\n"+
								"import $packagePath$.model.$_voName$;\n"+
								"import $packagePath$.service.$_serviceName$;\n"+
								"import com.ecloud.framework.controller.BaseRestController;\n\n"+
								
								"@RestController\n"+
								"@RequestMapping(\"$requestMapping$\")\n"+
								"public class $_controllerName$  extends BaseRestController<$_voName$>{\n\n"+
								"\t@Autowired\n"+
								"\tprivate $_serviceName$ $serviceName$;\n\n"+
								"\t@Override\n"+
								"\tpublic $_serviceName$ getBaseService() {\n"+
								"\t\treturn this.$serviceName$;\n"+
								"\t}\n\n"+
								"}\n";
	//6.Bean文件模板
	public static  final String VO_TEMPLATE = "package $packagePath$.model;\n\n"+
					"import java.io.Serializable;\n\n"+
					"import com.ecloud.framework.model.ValueObject;\n\n"+
					"public class $_voName$ extends ValueObject implements Serializable{\n\n"+
					"\tprivate static final long serialVersionUID = 1L;\n\n"+
					"";
	
	public static Map<String,String> dataMap = new HashMap<String,String>();
	
	static{
		//MYSQL和JAVA的数据类型对应
		dataMap.put("varchar", "String");
		dataMap.put("int", "int");
		dataMap.put("float", "float");
		dataMap.put("double", "double");
		dataMap.put("decimal", "long");
	//	dataMap.put("timestamp", "Timestamp");
		dataMap.put("datetime", "String");
	}
	/**
	 * 
	 * @param dataType 数据类型
	 * @param length 长度
	 * @param scale 精度
	 * @return
	 */
	public static String getType(String dataType,String length,String scale){
		if(scale==null||scale.equals("0")||scale.equals("null")){
			return dataMap.get(dataType)==null?"String":dataMap.get(dataType).toString();
		}else{
			return "double";
		}
	}
	
	/**
	 * 特殊字段处理
	 * @param columnVO
	 * @param prefix
	 * @return
	 */
	public static String handerColumn(Cloumn columnVO,String prefix,String operation){
		String result = "";
		String cname = columnVO.getColumnName(); 
		String dataType = columnVO.getDataType();
		
		String newName = handerVOColumn(prefix+"_"+cname);
		//1.查询
		if("SELECT".equals(operation.toUpperCase())){
			result = prefix +"."+cname +" "+newName;
			if("timestamp".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%Y-%m-%d %H:%i:%s') " +newName;
			}
			if("data".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%Y-%m-%d') " +newName;
			}
			if("time".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%H:%i:%s') " +newName;
			}
		}
		//2.插入
		if("INSERT".equals(operation.toUpperCase())){
			result = prefix +"."+cname +" "+newName;
			if("timestamp".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%Y-%m-%d %H:%i:%s')" + newName;
			}
			if("data".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%Y-%m-%d')" + newName;
			}
			if("time".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%H:%i:%s')" + newName;
			}
		}
		
		return result;
	}
	
	public static String handerPeculiarColumn(String cname,String prefix){
		String newName = handerVOColumn(prefix+"_"+cname);
		String result = "#{"+newName+"}";
	   if(cname.endsWith("CREATED_DATE")||cname.endsWith("LAST_UPD_DATE")){
		   result = "NOW()";
	   }else if(cname.endsWith("MODIFICATION_NUM")||cname.endsWith("DELETED_FLAG")){
		   result = "0";
	   }else if(cname.equals("ROW_ID")&&prefix.contains("item")){
		   result = "DEFAULT_SEQ('DEFAULT')";
		   //result = "#{"+prefix+"_"+cname+"}";
	   }else{
		   result = "#{"+newName+"}";
	   }
	   
	   return result;
	}
	public static String handerVOColumn(String cname){
		cname = cname.toLowerCase();
		String[] parts = cname.split("_");
		String result = "";
		for(int i=0;i<parts.length;i++){
			String part = parts[i];
			if(i==0){
				result += part.toLowerCase();
			}else{
				result += part.substring(0, 1).toUpperCase()+part.substring(1);
			}
		}
		
		return result;
	}
	
}

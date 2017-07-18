package com.ecloud.generator.common.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ecloud.generator.common.bean.Cloumn;
import com.ecloud.generator.common.bean.GeneratorBean;
import com.ecloud.generator.common.repositry.GeneratorRepositry;
import com.ecloud.generator.common.template.FileTemplate;

@Service("generatorService")
public class GeneratorService {

	@Autowired
	private Environment env;

	@Autowired
	private GeneratorRepositry generatorRepositry;

	/**
	 * 检查数据库联通性
	 * 
	 * @return 1:OK
	 */
	public int doTestConnection() {
		return generatorRepositry.doTestConnection();
	}

	/**
	 * 获取表中所有库中所有表
	 * 
	 * @param tableBName
	 * @return
	 */
	public List<String> getTables() {
		String dbName = env.getProperty("spring.datasource.url");
		dbName = dbName.substring(dbName.lastIndexOf("/") + 1, dbName.indexOf("?"));
		return generatorRepositry.doGetTables(dbName);
	}

	/**
	 * 获取表中所有字段
	 * 
	 * @param tableBName
	 * @return
	 */
	public List<Cloumn> getTableCloumn(String tableName) {
		String dbName = env.getProperty("spring.datasource.url");
		dbName = dbName.substring(dbName.lastIndexOf("/") + 1, dbName.indexOf("?"));
		return generatorRepositry.doGetColumns(dbName, tableName, "default");
	}

	/*************************************** 文件生成 ***********************************************/

	String modelName = "";// program"; //模块名称(小写)
	String tableName = "";// "sm_program"; //表名称 --需要改
	String prefix = "";// "smp"; //前缀 备用--可以为空
	String genPath = "";// "D:/gen";//生成路径
	String projectName = "";// "ecloud"; //
	String basePath = "";// "com.ecloud.ecloud.common";

	List<Cloumn> list = new ArrayList<Cloumn>();// 表中的列信息
	StringBuffer buffer = new StringBuffer();// 临时变量
	StringBuffer iColumnBuffer = new StringBuffer(); // 插入时字段
	StringBuffer iPrefixColumnBuffer = new StringBuffer();// 单次插入时字段值
	StringBuffer iBatchPrefixColumnBuffer = new StringBuffer();// 批量插入时字段值
	StringBuffer uColumnBuffer = new StringBuffer();// 更新时时字段
	StringBuffer uPrefixColumnBuffer = new StringBuffer();// 更新时时字段值

	String packagePath = "";// basePath+"."+modelName; //项目包路径
	String _modelName = "";// modelName.substring(0, 1).toUpperCase() +
							// modelName.substring(1); //模块名称
	String voName = "";// modelName+"Bean";//小写开头Bean
	String _voName = "";// _modelName+"Bean";//大写开头Bean
	String controllerName = "";// modelName+"Controller";//小写开头Controller
	String _controllerName = "";// _modelName+"Controller";//大写开头Controller
	String serviceName = "";// modelName+"Service";//小写开头Service
	String _serviceName = "";// _modelName+"Service";//大写开头Service
	String daoName = "";// modelName+"Repository";//小写开头Repository
	String _daoName = "";// _modelName+"Repository";//大写开头Repository
	int size = 0;

	public String generate(GeneratorBean bean) {
		// 1.数据准备
		initialize(bean);
		try {
			genMapper();
			genRepository();
			genService();
			genController();
			genBean();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			buffer=null;
			iColumnBuffer=null;
			iPrefixColumnBuffer=null;
			iBatchPrefixColumnBuffer=null;
			uColumnBuffer=null;
			uPrefixColumnBuffer=null;
		}
		return "0";
	}

	private void initialize(GeneratorBean bean) {
		buffer = new StringBuffer();// 临时变量
		iColumnBuffer = new StringBuffer(); // 插入时字段
		iPrefixColumnBuffer = new StringBuffer();// 单次插入时字段值
		iBatchPrefixColumnBuffer = new StringBuffer();// 批量插入时字段值
		uColumnBuffer = new StringBuffer();// 更新时时字段
		uPrefixColumnBuffer = new StringBuffer();// 更新时时字段值
		
		
		this.projectName = bean.getProjectName().toLowerCase();
		this.basePath = bean.getBasePath().toLowerCase();
		this.modelName = bean.getModelName().toLowerCase();
		this.tableName = bean.getTableName();
		this.prefix = bean.getPrefix().toLowerCase();
		this.genPath = bean.getGenPath();

		packagePath = basePath + "." +projectName+"."+modelName; // 项目包路径
		_modelName = modelName.substring(0, 1).toUpperCase() + modelName.substring(1); // 模块名称
		voName = modelName + "VO";// 小写开头Bean
		_voName = _modelName + "VO";// 大写开头Bean
		controllerName = modelName + "Controller";// 小写开头Controller
		_controllerName = _modelName + "Controller";// 大写开头Controller
		serviceName = modelName + "Service";// 小写开头Service
		_serviceName = _modelName + "Service";// 大写开头Service
		daoName = modelName + "DAO";// 小写开头Repository
		_daoName = _modelName + "DAO";// 大写开头Repository

		list = bean.getCloumnList();
		this.size = list.size();

		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				iColumnBuffer.append(list.get(i).getColumnName() + ")\n");
			} else {
				iColumnBuffer.append(list.get(i).getColumnName() + ",");
			}
		}
		iPrefixColumnBuffer.append("(");
		iBatchPrefixColumnBuffer.append("(");
		for (int i = 0; i < size; i++) {
			String beanName = list.get(i).getBeanName();
			String name = list.get(i).getColumnName().toUpperCase();
			if (name.endsWith("LAST_UPD_DATE")){
				iPrefixColumnBuffer.append("NOW()");
				iBatchPrefixColumnBuffer.append("NOW()");
			}else if(name.endsWith("CREATE_TIME")){
				iPrefixColumnBuffer.append("NOW()");
				iBatchPrefixColumnBuffer.append("NOW()");
			}else if(name.endsWith("MODIFICATION_NUM")){
				iPrefixColumnBuffer.append("1");
				iBatchPrefixColumnBuffer.append("1");
			}else{
				iPrefixColumnBuffer.append("#{"+beanName+"}");
				iBatchPrefixColumnBuffer.append("#{item."+beanName+"}");
			}
			
			
			if (i == size - 1) {
				iPrefixColumnBuffer.append(")\n");
				iBatchPrefixColumnBuffer.append(")\n");
			} else {
				iPrefixColumnBuffer.append(",");
				iBatchPrefixColumnBuffer.append(",");
			}
		}

		for (int i = 0; i < size; i++) {
			String name = list.get(i).getColumnName();
			String beanName = list.get(i).getBeanName();
			String separator = ",";
			if(i==size-1){
				separator = "";
			}
			if (name.endsWith("LAST_UPD_DATE")){
				uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + name + "=NOW()"+separator+"</if>\n");
			}else if(name.endsWith("MODIFICATION_NUM")){
				uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + name + "= "+beanName +  " + 1 "+separator+"</if>\n");
			}else if(name.endsWith("ROW_ID")) {
				uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + name + "=#{" + beanName + "}"+separator+"</if>\n");
			}else{
				uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + name + "=#{" + beanName + "}"+separator+"</if>\n");
			}

		}

	}

	private void genMapper() throws IOException {
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(
				genPath + "/" + projectName + "/src/" + modelName + "/config/" + modelName + "-mapper.xml");
		buffer.append(FileTemplate.MAPPER_TEMPLATE.replace("$packagePath$", packagePath).replace("$_daoName$", _daoName)
				.replace("$_voName$", _voName));

		StringBuffer tempbuffer = new StringBuffer();

		System.out.println(
				"************************************1.查询条件******************************************************");
		tempbuffer.append("\t<sql id=\"SQL_CONDITION\">\n");
		for (int i = 0; i < size; i++) {
			Cloumn column = list.get(i);
			String name = column.getColumnName();
			String beanName = column.getBeanName();
			tempbuffer.append("\t\t<if test=\"" + beanName + " != null and " + beanName + " != '' \">\n");
			tempbuffer.append("\t\t and " + prefix + "." + name + " = #{" + beanName + "}\n");
			tempbuffer.append("\t\t</if>\n");
		}
		tempbuffer.append("\t</sql>");

		buffer.append("\t<!--1.查询条件-->\n");
		buffer.append(tempbuffer.toString() + "\n\n");

		System.out.println(
				"************************************2.查询内容******************************************************");
		tempbuffer.delete(0, tempbuffer.length());
		buffer.append("\t<!--2.查询内容-->\n");
		buffer.append("\t<sql id=\"SQL_CONTENT\">\n");
		for (int i = 0; i < size; i++) {
			Cloumn column = list.get(i);
			String dataType = column.getDataType();
			String name = column.getColumnName();
			String beanName = column.getBeanName();
			String result = "";
			if ("timestamp".equals(dataType.toLowerCase())) {
				result = "DATE_FORMAT(" + prefix + "." + name + ",'%Y-%m-%d %H:%i:%s')" + beanName;
			}else if ("data".equals(dataType.toLowerCase())) {
				result = "DATE_FORMAT(" + prefix + "." + name + ",'%Y-%m-%d')" + beanName;
			}else if ("time".equals(dataType.toLowerCase())) {
				result = "DATE_FORMAT(" + prefix + "." + name + ",'%H:%i:%s')" + beanName;
			}else{
				result = prefix + "." + name + " " + beanName;
			}
			if (i == size - 1) {
				buffer.append("\t\t" + result + "\n");
			} else {
				buffer.append("\t\t" + result + ",\n");
			}
		}
		buffer.append("\t</sql>\n\n");

		System.out.println(
				"************************************3.查询记录数******************************************************");
		buffer.append("\t<!--3.查询记录数-->\n");
		String sql_count = FileTemplate.SQL_COUNT.replace("$TABLE_NAME$", tableName).replace("$PREFIX$", prefix)
				.replace("$_voName$", _voName);
		buffer.append(sql_count + "\n");

		System.out.println(
				"************************************4.按条件查询记录******************************************************");
		buffer.append("\t<!--4.按条件查询记录-->\n");
		buffer.append("\t<select id=\"doSearchListByVO\" parameterType=\"" + _voName + "\"  resultType=\"" + _voName
				+ "\"> \n");
		buffer.append("\t\tSELECT \n");
		buffer.append("\t\t\t<include refid=\"SQL_CONTENT\"/>\n");
		buffer.append("\t\tFROM " + tableName + " " + prefix + "  WHERE " + prefix + ".DELETED_FLAG = 'N' \n");
		buffer.append("\t\t\t<include refid=\"SQL_CONDITION\"/>\n");
		buffer.append("\t</select>\n\n");

		System.out.println(
				"************************************5.根据ID查询记录******************************************************");
		buffer.append("\t<!--5.根据ID查询记录-->\n");
		buffer.append(
				"\t<select id=\"doFindById\" parameterType=\"" + _voName + "\"  resultType=\"" + _voName + "\">  \n");
		buffer.append("\t\tSELECT \n");
		buffer.append("\t\t<include refid=\"SQL_CONTENT\"/>\n");
		buffer.append("\t\tFROM " + tableName + " " + prefix + " \n");
		buffer.append("\t\tWHERE " + prefix + ".DELETED_FLAG = 'N' AND  " + prefix + ".ROW_ID = #{value} \n");
		buffer.append("\t</select>\n\n");
		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");

		System.out.println("************************************6.按照VO插入******************************************************");
		buffer.append("\t<!--6.按照VO插入-->\n");
		buffer.append("\t<insert  id=\"doInsertByVO\"  parameterType=\"" + _voName + "\"> \n");
		buffer.append("\t\t<selectKey resultType=\"java.lang.Long\" order=\"BEFORE\" keyProperty=\"rowId\">\n");
		buffer.append("\t\t\tSELECT DEFAULT_SEQ('DEFAULT') AS rowId FROM DUAL\n");
		buffer.append("\t\t</selectKey>\n");
		buffer.append("\t\tINSERT INTO " + tableName + "(");
		// 加入字段名
		buffer.append(iColumnBuffer.toString());
		buffer.append("\t\tVALUES");
		// 加入字段别名
		buffer.append(iPrefixColumnBuffer.toString());
		buffer.append("\t </insert>\n\n");
		// System.out.println(buffer.toString());

		System.out.println(
				"************************************7.批量插入******************************************************");
		buffer.append("\t<!--7.批量插入-->\n");
		buffer.append("\t<insert  id=\"doBatchInsertByList\"  parameterType=\"java.util.List\"> \n");
		buffer.append("\t\tINSERT INTO " + tableName + "(");
		// 加入字段名
		buffer.append(iColumnBuffer.toString());
		buffer.append("\t\tVALUES\n");
		buffer.append("\t\t<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >\n");
		buffer.append("\t\t\t");
		// 加入字段别名
		buffer.append(iBatchPrefixColumnBuffer.toString());

		buffer.append("\t\t</foreach>\n");
		buffer.append("\t</insert>\n\n");

		System.out.println(
				"************************************8.按主键逻辑删除******************************************************");
		buffer.append("\t<!--8.按主键逻辑删除-->\n");
		buffer.append("\t<update id=\"doDeleteById\"  parameterType=\"Long\"> \n");
		// buffer.append("\t\tDELETE FROM "+tableName+" WHERE 1=1 AND ROW_ID =
		// #{value} \n");
		buffer.append("\t\tUPDATE  " + tableName + " " + prefix + " SET " + prefix
				+ ".DELETED_FLAG = 'Y'  WHERE 1=1   AND " + prefix + ".ROW_ID = #{value} \n");
		buffer.append("\t</update>\n\n");
		// System.out.println(buffer.toString());

		System.out.println(
				"************************************9.修改******************************************************");
		buffer.append("\t<!--9.按复合条件逻辑更新-->\n");
		buffer.append("\t<update  id=\"doUpdateByVO\"  parameterType=\"" + _voName + "\"> \n");
		buffer.append("\t\tUPDATE   " + tableName	 + "  \n");
		buffer.append("\t\t<set>\n");
		buffer.append(uColumnBuffer.toString());
		buffer.append("\t\t</set>\n");
		buffer.append("\t\tWHERE  ROW_ID = #{rowId}\n");
		buffer.append("\t</update>\n\n");
		// System.out.println(buffer.toString());

		System.out.println(
				"************************************10.批量修改******************************************************");
		buffer.append("\t<!--10.批量修改-->\n");
		buffer.append("\t<update id=\"doBatchUpdateByList\"  parameterType=\"java.util.List\">\n");
		buffer.append("\t\tINSERT INTO " + tableName + "(");
		buffer.append(iColumnBuffer.toString());
		buffer.append("\t\tVALUES\n");
		buffer.append("\t\t<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >\n");
		buffer.append("\t\t\t");
		// 加入字段别名
		buffer.append(iBatchPrefixColumnBuffer.toString());
		buffer.append("\t\t</foreach>\n");
		buffer.append("\t\tON DUPLICATE KEY UPDATE   \n");
		for (int i = 0; i < size; i++) {
			String cname = list.get(i).getColumnName();
			if (i == size - 1) {
				buffer.append("\t\t\t").append(cname).append(" = VALUES(").append(cname).append(")\n");
			} else {
				buffer.append("\t\t\t").append(cname).append(" = VALUES(").append(cname).append("),\n");
			}
		}
		buffer.append("\t</update>\n");

		buffer.append("</mapper>\n");
		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}

	private void genRepository() throws Exception {
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(genPath + "/" + projectName + "/src/" + modelName + "/dao/" + _daoName + ".java");

		buffer.append(FileTemplate.DAO_TEMPLATE.replace("$packagePath$", packagePath).replace("$basePath$", basePath)
				.replace("$_voName$", _voName).replace("$_daoName$", _daoName));

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}

	private void genService() throws Exception {
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(genPath + "/" + projectName + "/src/" + modelName + "/service/" + _serviceName + ".java");

		buffer.append(FileTemplate.SERVICE_TEMPLATE.replace("$packagePath$", packagePath)
				.replace("$basePath$", basePath).replace("$_voName$", _voName).replace("$_serviceName$", _serviceName));

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");

		file = new File(
				genPath + "/" + projectName + "/src/" + modelName + "/service/impl/" + _serviceName + "Impl.java");

		buffer = null;
		buffer = new StringBuffer();
		buffer.append(FileTemplate.SERVICE_IMPL_TEMPLATE.replace("$packagePath$", packagePath)
				.replace("$basePath$", basePath).replace("$_daoName$", _daoName).replace("$daoName$", daoName)
				.replace("$_serviceName$", _serviceName).replace("$serviceName$", serviceName)
				.replace("$_voName$", _voName));

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}

	private void genController() throws Exception {
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(
				genPath + "/" + projectName + "/src/" + modelName + "/controller/" + _controllerName + ".java");

		buffer.append(FileTemplate.CONTROLLER_TEMPLATE.replace("$packagePath$", packagePath)
				.replace("$_voName$", _voName).replace("$_serviceName$", _serviceName)
				.replace("$serviceName$", serviceName).replace("$_controllerName$", _controllerName)
				.replace("$returnPath$", projectName + "/" + modelName + "/" + modelName)
				.replace("$requestMapping$", modelName).replace("$basePath$", basePath)
				.replace("$packagePath$", packagePath));

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");

	}

	private void genBean() throws Exception {
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(genPath + "/" + projectName + "/src/" + modelName + "/model/" + _voName + ".java");
		Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
		buffer.append(FileTemplate.VO_TEMPLATE.replace("$packagePath$", packagePath).replace("$basePath$", basePath)
				.replace("$_voName$", _voName));
		for (Cloumn field : list) {
			String commnet = field.getBeanComment();
			Matcher m = CRLF.matcher(commnet);
			commnet = m.replaceAll("\t");
			buffer.append("\tprivate " + field.getBeanType() + " " + field.getBeanName() + ";\t//" + commnet
					+ "\n");

		}
		buffer.append("\n");
		for (Cloumn field : list) {
			String name = field.getBeanName();
			// String fullName = prefix+"_"+cName;
			// String fullName = FileTemplate.handerVOColumn(prefix+"_"+cname);
/*			String type = field.getDataType();
			String length = field.getMaxLength();
			String scale = field.getNumeric_scale();*/
			String temp = name.substring(0, 1).toUpperCase() + name.substring(1); // 模块名称
			buffer.append("\tpublic " + field.getBeanType()  + " get" + temp + "(){\n");
			buffer.append("\t\treturn " + name + ";\n");
			buffer.append("\t}\n");
			buffer.append("\tpublic void set" + temp + "(" + field.getBeanType()  + " " + name
					+ "){\n");
			buffer.append("\t\tthis." + name + " = " + name + ";\n");
			buffer.append("\t}\n");

		}
		buffer.append("}");

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}
}

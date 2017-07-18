<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ecloud代码生成器</title>
<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
function init(){
	var t = new Date().valueOf();
 	$.ajax({
  		type : "POST", //用POST方式传输  
  		url : '/doGetTables?now='+t, //目标地址  
  		data : "",
  		async:false,
  		error : function(XMLHttpRequest, textStatus, errorThrown) {
  		},
  		success : function(data) {
  			 for(var i =0;i<data.length;i++){
  				var arr = data[i].split(":");
  				$("#tableName").prepend("<option value="+arr[1]+">"+arr[0]+" ["+arr[1]+"] </option>");
  			 }
  		}
  	}); 
}
function doGetColumn(){
	var t = new Date().valueOf();
	var tableName = $("#tableName").val();
 	 $.ajax({
  		type : "POST", //用POST方式传输  
  		url : '/doGetColumns?now='+t, //目标地址  
  		data : "tableName="+tableName,
  		async:false,
  		error : function(XMLHttpRequest, textStatus, errorThrown) {
  		},
  		success : function(data) {
  			$("#gridtable").append("<tr><th>序号</th><th>列名</th><th>数据库类型</th><th>Bean类型</th><th>精度</th><th>数据库描述</th><th>Bean描述</th></tr>")
  		    for(var i =0;i<data.length;i++){
  		     var columnName = data[i]["columnName"];
  		     var arr = columnName.split("_");
  		     var result = arr[0].toLowerCase();
  		     
  		     for(var j=1;j<arr.length;j++){
  		    	 result += arr[j].substring(0,1).toUpperCase() + arr[j].substring(1).toLowerCase();
  		     }
  		   alert(result);
  			 $("#gridtable").append("<tr>"+
  			 ""+(i+1)+""+
  			 ""+data[i]["columnName"]+""+
  			 ""+data[i]["dataType"]+""+
  			 ""+data[i]["dataType"]+""+
  			 ""+data[i]["numeric_scale"]+""+
  			 ""+data[i]["comment"]+""+
  			 "<input type='text' name='' value=\""+result+"\"></tr>");
   			}
  		}
  	}); 
}
</script>
<style type="text/css">
body{
	background-color: #ececf1;
	text-align: center;
}
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>

</head>
<body onload="init()" >
<h2>Ecloud代码生成器</h2>
<div style="text-align: center;">
		<label>项目名称：</label>
		<input type="text" id="projectName" name="projectName"><br><br>
		<label>模块名称：</label>
		<input type="text" id="modelName" name="modelName"><br><br>
		<label>对 应 表：</label>
		<select id="tableName" name="tableName" onchange="doGetColumn()"></select><br><br>
		<label>包 路 径：</label>
		<input type="text" id="basePath" name="basePath"><br><br>
		<label>前&nbsp;&nbsp;缀：</label>
		<input type="text" id="prefix" name="prefix"><br><br>
		<label>生成路径：</label>
		<input type="text" id="genPath" name="genPath"><br><br>
		<input type="submit" name="" value="Generator">&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</body>
</html>

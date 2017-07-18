<#assign sctx=request.contextPath+"/static/" />
<#assign cctx=request.contextPath+"/static/common/" />
<#compress>
<link href="${cctx}componet/layer/layui/css/layui.css" rel="stylesheet" >
<script type="text/javascript" src="${cctx}componet/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${cctx}componet/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" src="${cctx}componet/ueditor/lang/zh-cn/zh-cn.js"></script>
<style type="text/css">
.btn-group-sm>.btn, .btn-sm {
    border-radius: 0px;
    font-size: 14px;
}
.input-group-addon{
	line-height: 0
}
</style>
</head>
<body>
<div id="page-inner">
   <div class="row">
        <div class="col-lg-12">
           <div class="panel panel-default">
                <div class="panel-heading">写文章</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-6">
                        <form id="artForm" action="/article/add" method="post">
                          <div class="form-group input-group">
                             <span class="input-group-addon">文章标题</span>
                             <input type="text" name="articleName"  id="articleName" class="form-control" placeholder="文章标题">
                          </div>
	                      <div class="form-group input-group">
	                          <span class="input-group-addon" >所属类别</span>
	                          <select class="form-control" name="categoryCode">
	                          <#list clist as category> 
		                              <option value="${ category.categoryCode}">${ category.categoryName}</option>
	                          </#list> 
	                          </select>
	                      </div>
	                      <div id="tagsdiv" class="form-group input-group">
                 	        <span class="input-group-addon" >文章标签</span>
                 	        <input type="hidden" name="tags" id="tags">
		                    <a href="javascript:void(0)" class="btn btn-default btn-sm" >hadoop</a> &nbsp;
		                    <a href="javascript:void(0)" class="btn btn-default btn-sm" >spark</a> &nbsp;
		                    <a href="javascript:void(0)" class="btn btn-default btn-sm" >大数据</a> &nbsp;
		                    <a href="javascript:void(0)" class="btn btn-default btn-sm" >kafka</a> &nbsp;
		                    <a href="javascript:void(0)" class="btn btn-default btn-sm" >flume</a> &nbsp;
		                    <a href="javascript:void(0)" class="btn btn-default btn-sm" >warning</a> &nbsp;
	                      </div>
	                      <div id="top" class="form-group input-group">
                 	        <span class="input-group-addon " style="border: 1px solid #ccc;padding: 16px 12px;">是否置顶</span>
               	            <label class="radio-inline" style="margin-left:20px;margin-top:5px;">
                                 <input type="radio" name="toTop" value="N" checked="checked">非置顶
                            </label>
               	            <label class="radio-inline" style="margin-left:20px;margin-top:5px;">
                                <input type="radio" name="toTop" value="Y">置顶
                            </label>
	                      </div>
	                      <div class="form-group">
	                      	<input type="hidden" name="content" id="content">
	                         <script id="editor"  type="text/plain" style="width:1024px;height:500px;"></script>
	                      </div>
	                      <div class="form-group" style="text-align: right">
	                         <button id="submit" type="button" class="btn btn-success"> 提交  </button>
	                      </div>
	                      </form>
	                     </div>
	                  </div>
	             </div>
	         </div>
        </div>
    </div>
<#include "common/footer.ftl">
</div>
<script src="${cctx}js/jquery-1.10.2.js"></script>
<script src="${sctx}admin/js/menu.js"></script>
<script type="text/javascript">
    var ue = UE.getEditor('editor');
    $(document).ready(function() { 
    	 $("div#tagsdiv > a").bind("click",function(){    
    		 $(this).toggleClass("btn-success");
    	 });
    });
   	$("#submit").on('click', function() { 
	       var content = ue.getContent();
    	   $("#content").val(content);
   		   var tags = "";
	   	   $("div#tagsdiv > a").filter(".btn-success").each(function(i,obj){
	   			tags = tags+$(obj).html() + ",";
	   	   });
	   	   $("#tags").val(tags);
	   	   alert($("#tags").val())
           var url = "/article/add";  
           var data= $('#artForm').serialize();  
            $.ajax({  
                  type: 'POST',  
                  url: url,  
                  data: data,  
                  success:function(result){  
                	  alert(result.respCode);
                	  layer.open({
                		  title: '执行结果'
                		  ,content: '文章保存成功。'
                		});   
                  }
            }); 
      });
</#compress>
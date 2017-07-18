<#assign ctx=request.contextPath+"/" />
<#assign sctx=request.contextPath+"/static/" />
<#assign cctx=request.contextPath+"/static/common/" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>${_tilte}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="description" content="${_tilte}">
  <link rel="stylesheet" href="${cctx}componet/layer/layui/css/layui.css">
  <link rel="stylesheet" href="${sctx}front/css/global.css">
  <link rel="stylesheet" href="${sctx}front/css/article.css">
  <script type="text/javascript" src="${cctx}js/jquery-1.10.2.js"></script>
  <script type="text/javascript" src="${sctx}front/js/navigate.js"></script>
</head>
<body>
<#include "common/header.ftl">
<div class="main layui-clear" >
  <div class="wrap" id="main">
  	<div class="content">
		 <ul class="fly-list" >
				<#list page.list as article>
		          <div class="article">
			            <div class="article-div">
			                <img src="${sctx}images/f19.jpg" alt="" class="article-div-img">
			            </div>
			            <div class="article-info">
			                <h1 class="article-title"><a href="<%=ctx %>article/detail/${article.rowId}">[&nbsp;${article.categoryName}&nbsp;]&nbsp;&nbsp;${article.articleName}</a></h1>
			                <p class="article-content">${article.content}</p>
			                <div class="article-other">
				                <span><a href="">dgao</a></span>
				                <span><a href="">浏览(${article.browseTimes})</a></span>
				                <span><a href="">喜欢(${article.loveTimes})</a></span>
				                <span><a href="">${article.createTime}</a></span>
			                </div>
			            </div>
			      </div>
		         </#list>
		  </ul>
		</div>
	<!-- 导入自定义ftl -->  
	<#import "common/navigate.ftl" as nav/>  
	<!-- 使用该标签 -->    
	<@nav.page pageNum=page.pageNum pageSize=page.pageSize totalPage=page.pages url="list"/>  
  </div>
    <#include "common/right.ftl">
</div>
<#include "common/footer.ftl">
<script src="${cctx}componet/layer/layui/layui.js"></script>
</body>
</html>
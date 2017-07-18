<#assign ctx=request.contextPath+"/" />
<#assign sctx=request.contextPath+"/static/" />
<#assign cctx=request.contextPath+"/static/common/" />
<#compress>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>${_tilte}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="${_tilte}">
  <meta name="description" content="dream blog">
  <link rel="stylesheet" href="${cctx}componet/layer/layui/css/layui.css">
  <link rel="stylesheet" href="${sctx}front/css/global.css">
  <link rel="stylesheet" href="${sctx}front/css/article.css">
  <link rel="stylesheet" href="${cctx}componet/slider/slider.css">
  <script type="text/javascript" src="${cctx}js/jquery-1.10.2.js"></script>
  <script type="text/javascript" src="${cctx}componet/slider/slider.js"></script>
  <script type="text/javascript" src="${sctx}front/js/navigate.js"></script>
</head>
<body>
<#include "common/header.ftl">
<div class="main layui-clear">
  <div class="wrap">
    <div class="content">
      	<div class="slider">
			<ul class="slider-main">
				<li class="slider-panel">
					<a href="" target="_blank"><img src="${sctx}images/1.jpg"></a>
				</li>
				<li class="slider-panel">
					<a href="" target="_blank"><img src="${sctx}images/2.jpg"></a>
				</li>
				<li class="slider-panel">
					<a href="" target="_blank"><img src="${sctx}images/3.jpg"></a>
				</li>
				<li class="slider-panel">
					<a href="" target="_blank"><img src="${sctx}images/4.jpg"></a>
				</li>
			</ul>
			<div class="slider-extra">
				<ul class="slider-nav">
					<li class="slider-item">1</li>
					<li class="slider-item">2</li>
					<li class="slider-item">3</li>
					<li class="slider-item">4</li>
				</ul>
				<div class="slider-page">
					<a class="slider-pre" href="javascript:;">&lt;</a>
					<a class="slider-next" href="javascript:;">&gt;</a>
				</div>
			</div>
		</div>
	<ul class="fly-list fly-list-top">
		<#list topList as article>
        <li class="fly-list-li">
          <a href="${ctx}article/detail/${article.rowId}" class="fly-list-avatar">
            <img src="http://tp4.sinaimg.cn/1345566427/180/5730976522/0" alt="">
          </a>
          <h2 class="fly-tip">
            <a href="${ctx}article/detail/${article.rowId}" title="${article.articleName}">${article.articleName}</a>
            <span class="fly-tip-stick">置顶</span>
            <span class="fly-tip-jing">精帖</span>
          </h2>
          <p>
            <span><a href="user/home.html">贤心</a></span>
            <span>${article.createTime}</span>
            <span>${article.categoryName}</span>
            <span class="fly-list-hint"> 
              <i class="iconfont" title="回答">&#xe60c;</i> 317
              <i class="iconfont" title="人气">&#xe60b;</i> ${article.browseTimes}
            </span>
          </p>
        </li>
        </#list>
      </ul>
      <ul class="fly-list">
      <#list artList as article>
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
  </div>
  <#include "common/right.ftl">
</div>
<#include "common/footer.ftl">
</body>
</html>
</#compress>
<div class="edge">
     <dl class="fly-panel fly-list-one"> 
      <dt class="fly-panel-title">网站统计</dt>
      <dd>
        <a href="javascript:void(0)">文章</a>
        <span><i class="iconfont icon-wenzhang"></i> 96</span>
      </dd>
      <dd>
        <a href="javascript:void(0)">评论</a>
        <span><i class="iconfont icon-pinglun"></i> 96</span>
      </dd>
      <dd>
        <a href="javascript:void(0)">浏览</a>
        <span><i class="iconfont icon-liulan"></i> 96</span>
      </dd>
      <dd>
        <a href="javascript:void(0)">最后更新</a>
        <span><i class="iconfont icon-riqi"></i> 2017-07-10</span>
      </dd>
    </dl>
    <div class="fly-panel leifeng-rank"> 
      <h3 class="fly-panel-title">标签云</h3>
      <div class="right-div" >
        <img src="${sctx}/images/tags.png"  style="width: 310px;">
      </div>
    </div>
    
    <dl class="fly-panel fly-list-one"> 
      <dt class="fly-panel-title">热门推荐</dt>
        <#list hots as article>
	      <dd>
	        <a href="jie/detail.html">${article.articleName}</a>
	        <span><i class="iconfont icon-wenzhang"></i> ${article.browseTimes}</span>
	      </dd>
        </#list>
        <#list hots as article>
	      <dd>
	        <a href="jie/detail.html">${article.articleName}</a>
	        <span><i class="iconfont icon-wenzhang"></i> ${article.browseTimes}</span>
	      </dd>
        </#list>
    </dl>
    
    <dl class="fly-panel fly-list-one"> 
      <dt class="fly-panel-title">最新评论</dt>
      <#list comments as comment>
      <dd>
        <a href="jie/detail.html">${comment.content}</a>
        <span><i class="iconfont icon-pinglun"></i> 96</span>
      </dd>
       </#list>
      <#list comments as comment>
      <dd>
        <a href="jie/detail.html">${comment.content}</a>
        <span><i class="iconfont icon-pinglun" ></i> 96</span>
      </dd>
       </#list>
      <#list comments as comment>
      <dd>
        <a href="jie/detail.html">${comment.content}</a>
        <span><i class="iconfont icon-pinglun"></i> 96</span>
      </dd>
       </#list>
    </dl>
   
    <div class="fly-panel fly-link"> 
      <h3 class="fly-panel-title">友情链接</h3>
      <dl>
      	<#list links as link>
	        <dd style="white-space:nowrap;width:90%;text-overflow:ellipsis;overflow: hidden;">
	          <a href="${link.linkUrl }" target="_blank">${link.linkName }</a>
	          <span ><i class="iconfont icon-web-icon-" ></i> 
	          <a href="${link.linkUrl }" target="_blank">${link.linkUrl }</a>
	          </span>
	        </dd>
        </#list>
      	<#list links as link>
	        <dd style="white-space:nowrap;width:90%;text-overflow:ellipsis;overflow: hidden;">
	          <a href="${link.linkUrl }" target="_blank">${link.linkName }</a>
	          <span ><i class="iconfont icon-web-icon-" ></i> ${link.linkUrl }</span>
	        </dd>
        </#list>
      </dl>
    </div>
  </div>
<#-- 自定义的分页指令 (powered by qiujy)
    属性：
   pageNum      当前页号(int类型)
   pageSize     每页要显示的记录数(int类型)
   totalPage    总页数(int类型)
   url          点击分页标签时要跳转到的目标URL(string类型)
 -->
<#macro page pageNum pageSize totalPage url>  
<#-- 输出分页样式 -->
<style type="text/css">
	.pagination {padding: 5px;font-size:12px;}
	.pagination a, .pagination a:link, .pagination a:visited {padding: 5px 5px;margin: 5px;border: 1px solid #009e94;text-decoration: none;color: #006699;}
	.pagination a:hover, .pagination a:active {border: 1px solid #009e94;color: #FFF;text-decoration: none;background-color: #009e94;}
	.pagination span.current {padding: 5px 5px;margin: 5px;border: 1px solid #009e94;font-weight: bold;background-color: #009e94;color: #FFF;}
	.pagination span.disabled {padding: 5px 5px;margin: 5px;border: 1px solid #849fa9; color: #ddd;}
</style>
<#-- 页号越界处理 -->
  <#if (pageNum > totalPage)>
    <#assign pageNum=totalPage>
  </#if>
  <#if (pageNum < 1)>
    <#assign pageNum=1>
  </#if>
<#-- 输出分页表单 -->
<div class="pagination">
<form method="post" action="${url}" id="_navigateFrm">
	<#-- 把请求中的所有参数当作隐藏表单域(无法解决一个参数对应多个值的情况) -->
	<input type="hidden" id="pageNum" name="pageNum" value=""/>
	<input type="hidden" name="categoryCode" value="${cgCode}"/>
	<#-- 上一页处理 -->
    <#if (pageNum == 1)>
		<span class="disabled">&laquo;&nbsp;上一页</span>
  	<#else>
		<a href="javaScript:navigate(${pageNum - 1})">&laquo;&nbsp;上一页</a>
    </#if>
 	
  	<#list 1..totalPage as i>
	    <#if (pageNum==i)>
			<span class="current">${i}</span>
	  	<#else>
			<a href="javascript:navigate(${i})">${i}</a>      
	    </#if>
  	</#list>
    
	<#-- 下一页处理 -->
    <#if (pageNum == totalPage)>
		<span class="disabled">下一页&nbsp;&raquo;</span>
    <#else>
		<a href="javascript:navigate(${pageNum + 1})">下一页&nbsp;&raquo;</a>
    </#if>
</form>
<script language="text/javascript">
function turnOverPage(no){
    var qForm=document.qPagerForm;
    if(no>${totalPage}){no=${totalPage};}
    if(no<1){no=1;}
    qForm.pageNo.value=no;
    qForm.action="${url}";
    qForm.submit();
}
</script>

</div> 
</#macro>
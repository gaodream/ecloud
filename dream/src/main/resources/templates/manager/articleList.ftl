<#assign sctx=request.contextPath+"/static/" />
<#compress>
<div id="page-inner">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">Messages</div>        
				<div class="panel-body"> 
					<div class="alert alert-success">
						<strong>Well done!</strong> You successfully read this important alert message.
					</div>
				</div>
			</div>
		</div>						
	</div>		
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">列表</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover"
							id="artTable">
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">文章主题</th>
									<th class="center">所属类别</th>
									<th class="center">标签</th>
									<th class="center">创建时间</th>
									<th class="center">发布时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<#list artlist as article> 
								<tr class="odd gradeX">
									<td>${article_index+1}</td>
									<td>${article.articleName}</td>
									<td>${article.categoryName}</td>
									<td>${article.tags?default("")}</td>
									<td class="center">${article.createTime}</td>
									<td class="center">${article.lastUpdTime}</td>
									<td class="center">
									 <a href="#" class="btn btn-success btn-sm">修改</a>
									 <a href="#" class="btn btn-danger btn-sm">删除</a>
									</td>
								</tr>
								</#list>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<#include "common/footer.ftl">
</div>
<script src="${sctx}admin/js/dataTables/jquery.dataTables.js"></script>
<script src="${sctx}admin/js/dataTables/dataTables.bootstrap.js"></script>
<script>
 $(document).ready(function () {
     $('#artTable').dataTable();
 });
</script>
</#compress>
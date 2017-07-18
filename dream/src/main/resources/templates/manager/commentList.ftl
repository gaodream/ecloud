<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String ctx = request.getContextPath() + request.getScheme() + "://" 
			   + request.getServerName() + ":" + request.getServerPort() + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dream后台管理</title>
<link href="<%=ctx %>admin/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=ctx %>admin/css/font-awesome.css" rel="stylesheet" />
<link href="<%=ctx %>admin/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
<link href="<%=ctx %>admin/css/custom-styles.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />
</head>
<body>
	<div id="wrapper">
		<jsp:include page="common/head.jsp" />
		<jsp:include page="common/menu.jsp" />
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">Messages</div>        
							<div class="panel-body"> 
								<div class="alert alert-danger">
									<strong>Oh snap!</strong> Change a few things up and try submitting again.
								</div>
							</div>
						</div>
					</div>						
				</div>		
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">列表
								  <div class="btn-group">
		                              <button data-toggle="dropdown" class="btn btn-warning dropdown-toggle">操作 <span class="caret"></span></button>
			                          <ul class="dropdown-menu">
			                            <li><a href="/article/edit">写文章</a></li>
			                            <li><a href="#">发布</a></li>
			                            <li class="divider"></li>
			                            <li><a href="#">下架</a></li>
			                          </ul>
		                        </div>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-striped table-bordered table-hover"
										id="dataTables-example">
										<thead>
											<tr>
												<th class="center">序号</th>
												<th class="center">文章名称</th>
												<th class="center">类别编码</th>
												<th class="center">类别名称</th>
												<th class="center">评论时间</th>
												<th class="center">操作</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${page.list}" var="comment" varStatus="status" >
											<c:choose>  
												<c:when test="${(status.index+1)/2==0}">
													<tr class="odd gradeX">
												</c:when>
												<c:otherwise>
													<tr class="odd gradeX">
												</c:otherwise>
											</c:choose>
												<td>${status.index+1}</td>
												<td>${comment.articleName}</td>
												<td>${comment.categoryCode}</td>
												<td>${comment.categoryName}</td>
												<td class="center">${comment.createTime}</td>
												<td class="center">
                    							 <a href="#" class="btn btn-danger btn-sm">删除</a>
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<jsp:include page="common/footer.jsp"/>
			</div>
		</div>
	</div>
	<script src="<%=ctx %>admin/js/jquery-1.10.2.js"></script>
	<script src="<%=ctx %>admin/js/bootstrap.min.js"></script>
	<script src="<%=ctx %>admin/js/jquery.metisMenu.js"></script>
	<script src="<%=ctx %>admin/js/dataTables/jquery.dataTables.js"></script>
    <script src="<%=ctx %>admin/js/dataTables/dataTables.bootstrap.js"></script>
    <script>
           $(document).ready(function () {
               $('#dataTables-example').dataTable();
               $('#dataTables-example_filter').css("text-align","right")
           });
    </script>
    <script src="<%=ctx %>admin/js/custom-scripts.js"></script>
	<script src="${sctx}admin/js/menu.js"></script>
</body>
</html>
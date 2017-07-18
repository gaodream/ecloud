<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath() + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() ;
String ctx = path + "/assets/";
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
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
<div id="wrapper">
		<jsp:include page="head.jsp"/>
        <!--/. NAV TOP  -->
   		<jsp:include page="menu.jsp"/>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
              	关于
				<jsp:include page="footer.jsp"/>
            </div>
        </div>
	</div>
    <script src="<%=ctx %>admin/js/jquery-1.10.2.js"></script>
    <script src="<%=ctx %>admin/js/bootstrap.min.js"></script>
    <script src="<%=ctx %>admin/js/jquery.metisMenu.js"></script>
    <script src="<%=ctx %>admin/js/morris/raphael-2.1.0.min.js"></script>
    <script src="<%=ctx %>admin/js/morris/morris.js"></script>
	<script src="<%=ctx %>admin/js/easypiechart.js"></script>
	<script src="<%=ctx %>admin/js/easypiechart-data.js"></script>
    <script src="<%=ctx %>admin/js/custom-scripts.js"></script>
</body>
</html>
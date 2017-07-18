<!DOCTYPE html>
<html>
<head>
<title>Dream</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="stylesheet" type="text/css" href="<%=ctx %>assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=ctx %>assets/css/nprogress.css">
<link rel="stylesheet" type="text/css" href="<%=ctx %>assets/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=ctx %>assets/css/font-awesome.min.css">
<link rel="apple-touch-icon-precomposed" href="<%=ctx %><%=ctx %>assets/images/icon.png">
<script src="<%=ctx %>assets/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="<%=ctx %>assets/js/nprogress.js" type="text/javascript"></script>
<script src="<%=ctx %>assets/js/jquery.lazyload.min.js"></script>
<style type="text/css">
.panel{
		padding: 80px 20px 0px;
		min-height: 400px;
		cursor: default;
	}
.text-center{
		margin: 0 auto;
		text-align: center;
		border-radius: 10px;
		max-width: 900px;
		-moz-box-shadow: 0px 0px 5px rgba(0,0,0,.3);
		-webkit-box-shadow: 0px 0px 5px rgba(0,0,0,.3);
		box-shadow: 0px 0px 5px rgba(0,0,0,.1);
	}
.float-left{
		float: left !important;
	}
.float-right{
		float: right !important;
	}
img{
		border: 0;
		vertical-align: bottom;
	}
h2{
		padding-top: 20px;
		font-size: 20px;
	}
.padding-big{
		padding: 20px;
	}
.alert
	{
		border-radius: 5px;
		padding: 15px;
		border: solid 1px #ddd;
		background-color: #f5f5f5;
	}
</style>
</head>
<body class="user-select">
	<jsp:include page="../common/header.jsp" />
	<section class="container">
	<div class="panel">
	<div class="text-center">
	  <h2><strong>404错误！很抱歉，您要找的页面不存在</strong></h2>
		<div class="padding-big"> <a href="#" class="btn btn-primary" >返回首页</a>
		</div>
		<div class="padding-big"> <a href="#" title="用DTcms做一个独立博客网站（响应式模板）" >用DTcms做一个独立博客网站（响应式模板）</a>
		</div>
		<div class="padding-big"> <a href="#" title="MZ-NetBolg主题" >MZ-NetBolg主题栏目</a>
		</div>
	</div>
	</div>
	</section>
	<jsp:include page="../common/footer.jsp" />
	<script src="<%=ctx %>assets/js/bootstrap.min.js"></script>
	<script src="<%=ctx %>assets/js/jquery.ias.js"></script>
	<script src="<%=ctx %>assets/js/scripts.js"></script>
</body>
</html>
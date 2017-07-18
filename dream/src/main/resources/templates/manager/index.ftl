<#assign ctx=request.contextPath+"/" />
<#assign sctx=request.contextPath+"/static/" />
<#assign cctx=request.contextPath+"/static/common/" />
<#compress>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dream后台管理</title>
<link href="${sctx}admin/css/bootstrap.min.css" rel="stylesheet" />
<link href="${sctx}admin/css/font-awesome.css" rel="stylesheet" />
<link href="${sctx}admin/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
<link href="${sctx}admin/css/custom-styles.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
<style type="text/css">
.panel {
    border-radius: 3px;
}
.panel-heading {
    padding: 10px 15px;
    border-bottom: 1px solid transparent;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
}

.panel-green>.panel-heading {
    color: #fff;
    background-color: #5cb85c;
    border-color: #5cb85c;
}
.panel-green {
    border-color: #5cb85c;
}
.panel-yellow>.panel-heading {
    color: #fff;
    background-color: #f0ad4e;
    border-color: #f0ad4e;
}
.panel-yellow {
    border-color: #f0ad4e;
}
.panel-red>.panel-heading {
    color: #fff;
    background-color: #d9534f;
    border-color: #d9534f;
}
.panel-red {
    border-color: #d9534f;
}
</style>
</head>
<body>
<div id="wrapper">
	<#include "common/header.ftl">
    <!--/. NAV TOP  -->
	<#include "common/menu.ftl">
    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header"> 博客统计 
                    <small>Summary of your App</small>
                    </h1>
                </div>
            </div>
        <div class="row">
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-comments fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">26</div>
                                <div>文章数</div>
                            </div>
                        </div>
                    </div>
                        <div class="panel-footer">
                            <span class="pull-left"></span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-green">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-comments fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">12</div>
                                <div>评论数</div>
                            </div>
                        </div>
                    </div>
                        <div class="panel-footer">
                            <span class="pull-left"></span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-shopping-cart fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">124</div>
                                <div>New Orders!</div>
                            </div>
                        </div>
                    </div>
                        <div class="panel-footer">
                            <span class="pull-left"></span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-red">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-shopping-cart fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">13</div>
                                <div>Support Tickets!</div>
                            </div>
                        </div>
                    </div>
                        <div class="panel-footer">
                            <span class="pull-left"></span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                </div>
            </div>
        </div>    
		<div class="row">
			 <div class="panel panel-default">
                    <div class="panel-heading"> Context Classes </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Username</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="success">
                                        <td>1</td>
                                        <td>Mark</td>
                                        <td>Otto</td>
                                        <td>@mdo</td>
                                    </tr>
                                    <tr class="info">
                                        <td>2</td>
                                        <td>Jacob</td>
                                        <td>Thornton</td>
                                        <td>@fat</td>
                                    </tr>
                                    <tr class="warning">
                                        <td>3</td>
                                        <td>Larry</td>
                                        <td>the Bird</td>
                                        <td>@twitter</td>
                                    </tr>
                                    <tr class="danger">
                                        <td>4</td>
                                        <td>John</td>
                                        <td>Smith</td>
                                        <td>@jsmith</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
			<#include "common/footer.ftl">
        </div>
    </div>
</div>
<script src="${sctx}admin/js/jquery-1.10.2.js"></script>
<script src="${sctx}admin/js/bootstrap.min.js"></script>
<script src="${sctx}admin/js/jquery.metisMenu.js"></script>
<script src="${sctx}admin/js/morris/raphael-2.1.0.min.js"></script>
<script src="${sctx}admin/js/morris/morris.js"></script>
<script src="${sctx}admin/js/easypiechart.js"></script>
<script src="${sctx}admin/js/easypiechart-data.js"></script>
<script src="${sctx}admin/js/custom-scripts.js"></script>
<script src="${sctx}admin/js/menu.js"></script>

</body>
</html>
</#compress>
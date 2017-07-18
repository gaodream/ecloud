<nav class="navbar-default navbar-side" role="navigation">
	<div id="sideNav" >
		<i class="fa fa-caret-right"></i>
	</div>
	<div class="sidebar-collapse">
		<ul class="nav" id="main-menu">
			<li>
				<a class="active-menu" href="/manager">
					<i class="fa fa-dashboard"></i> 首页
				</a>
			</li>
			<li>
				<a href="javascript:void()">
					<i class="fa fa-table"></i>文章管理<span class="fa arrow"></span>
				</a>
				<ul class="nav nav-second-level">
					<li><a href="javascript:_navigate('${ctx}article/gotoEdit','')">发布文章</a></li>
					<li><a href="javascript:_navigate('${ctx}article/list/A','')">文章</a></li>
					<li><a href="javascript:_navigate('${ctx}article/draft','')">草稿箱</a></li>
				</ul>
			</li>
			<li>
				<a href="/comment/list">
					<i class="fa fa-desktop"></i>评论管理
				</a>
			</li>
			<li>
			    <a href="#">
			    	<i class="fa fa-sitemap"></i> 
			   		系统管理<span class="fa arrow"></span>
				</a>
				<ul class="nav nav-second-level">
					<li><a href="#">用户管理</a></li>
					<li><a href="#">导航管理</a></li>
					<li><a href="#">焦点图设置</a></li>
					<li><a href="#">友情链接</a></li>
					<li><a href="#">分类标签</a></li>
					<li><a href="#">其他</a></li>
				</ul>
			</li>
			<li>
				<a href="/article/about"><i class="fa fa-fw fa-file"></i>关于</a>
			</li>
		</ul>
	</div>
</nav>

<div class="header" style="margin-bottom: 90px">
  <div class="main">
  <!--   <a class="logo" href="/" title="Fly">Fly社区</a> -->
    <div class="nav" id="nav">
      <a class="${index!'' }" href="${ctx}">
        <i class="iconfont icon-home" style="top:2px"></i>主页
      </a>
      <a class="${java!'' }" href="${ctx}article/java"  >
        <i class="iconfont icon-java" style="top:2px"></i>JAVA
      </a>
      <a class="${bigdata!'' }" href="${ctx}article/bigdata" >
        <i class="iconfont icon-dashuju"></i>大数据
      </a>
      <a class="${cloud!'' }" href="${ctx}article/cloud" >
        <i class="iconfont icon-yunjisuan"></i>云平台
      </a>
      <a  class="${ms!'' }" href="${ctx}article/ms" >
        <i class="iconfont icon-mianshi"></i>面试
      </a>
      <a  class="${share!'' }"href="${ctx}article/share" >
        <i class="iconfont icon-fenxiang"></i>分享
      </a>
      <a  class="${about!'' }"href="${ctx}article/about" >
        <i class="iconfont icon-guanyu"></i>关于
      </a>
    </div>
    
    <div class="nav-user">
      <!-- 未登入状态 -->
      <a class="unlogin" href="user/login.html"><i class="iconfont icon-touxiang"></i></a>
      <span><a href="user/login.html">登入</a><a href="javascript:doRegister()">注册</a></span>
      <p class="out-login">
        <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
        <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" 
        title="微博登入"></a>
      </p>   
      
      <!-- 登入后的状态 -->
      <!-- 
      <a class="avatar" href="user/index.html">
        <img src="http://tp4.sinaimg.cn/1345566427/180/5730976522/0">
        <cite>贤心</cite>
        <i>VIP2</i>
      </a>
      <div class="nav">
        <a href="/user/set/"><i class="iconfont icon-shezhi"></i>设置</a>
        <a href="/user/logout/"><i class="iconfont icon-tuichu" style="top: 0; font-size: 22px;"></i>退了</a>
      </div> -->  
    </div>
 
    <div class="layui-main" style="width:1200px;top:65px">
	  <blockquote class="layui-elem-quote" style="border-left:1px;font-size: 18px;background-color:#FFF">
	    <i class="layui-icon" style="font-size: 22px; color: #1E9FFF;">&#xe63a;</i> 
	    <marquee direction="left" behavior=scroll scrollamount=5  width="95%" 
	     onmouseover=this.stop() onmouseout=this.start()> 欢迎光临小站 </marquee>
	  </blockquote>
	</div>
 </div>
</div>
  <script src="${cctx}js/jquery-1.10.2.js"></script>
  <script src="${cctx}componet/layer/layer/layer.js"></script>
<script type="text/javascript">
function doRegister(){
	//示范一个公告层
	layer.open({
	  type: 1
	  ,title: "用户注册" //不显示标题栏
	  ,closeBtn: false
	  ,area: ['420px','310px']
	  ,shade: 0.8
	  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
	  ,resize: false
	  ,btn: ['确认', '取消']
	  ,btnAlign: 'c'
	  ,moveType: 0 //拖拽模式，0或者1 
	  ,content: '<div style="padding: 50px; line-height: 22px; background-color: #f8f9f9; color: #fff; font-weight: 300;">'
					+'<div class="layui-form-item">'
						+'<div class="layui-input-inline" style="width:300px">'
							+'<input type="text" name="title" required  lay-verify="required" placeholder="手机/邮箱" autocomplete="off" class="layui-input">'
						+'</div>'
					+'</div>'
					+'<div class="layui-form-item">'
						+'<div class="layui-input-inline" style="width:300px">'
							+'<input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">'
						+'</div>'
					+'</div>'
	 			 +'</div>'
	  ,success: function(layero){
	    var btn = layero.find('.layui-layer-btn');
	    btn.find('.layui-layer-btn0').attr({
	      href: 'http://www.layui.com/'
	      ,target: '_blank'
	    });
	  }
	});
}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<#include "/common/meta.html"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords"
	content="登录">
<meta name="description"
	content="登录">
<meta name="title" content="登录例子">
<script type="text/javascript">
    var conf = {
        isVip:false,
        isMem:false
    };
    var sinaSSOConfig = {
        entry : 'cnmail', // 本产品的标识
        loginType : 0,
        setDomain : true,
        pageCharset :'UTF-8',
        customInit : function() {
            sinaSSOController.setLoginType(3);
        },
        customLoginCallBack : function(status){
            conf.loginCallBack(status);
        }
    };
</script>
<title>新浪邮箱</title>
<link rel="stylesheet"
	href="${ctx}/styles/style_120815.css">
</head>
<body style="height: 100%">
	<div class="mailLoginBox">
		<div class="mailLogin">
			<div class="headerBox">
				<div class="header clearfix">
					<h1>
						<a href="" class="logo">新浪邮箱</a>
					</h1>
					<div class="nav">
						<a target="_blank" href="http://vip.sina.com.cn/">VIP邮箱</a> <span>|</span>
						<a target="_blank" href="http://exmail.sina.com/">免费企业邮箱</a><img
							src="${ctx}/images/new.gif"> <span>|</span>
						<a target="_blank" href="http://mail.sina.net/">专业企业邮箱</a> <span>|</span>
						<a target="_blank" href="http://mail.sina.com.cn/mobile.html">手机邮箱</a>
					</div>
				</div>
			</div>
			<!--背景通过改变class,bg1为荷花背景，bg2为小象背景-->
			<div
				style="background: url(&quot;http://i0.sinaimg.cn/rny/webface/2012login/images/free_03.png&quot;) no-repeat scroll center center rgb(29, 64, 137);"
				class="mainBox  bg1">
				<div class="main">
					<div class="positionBox clearfix">
						<div class="error timeout" style="display: none;">
							<h3>注册激活失败，可能由于以下原因：</h3>
							<p>·您登录后长时间未进行操作</p>
							<p>·您今天注册的邮箱太多，请明天再来</p>
						</div>
						<div class="error locked" style="display: none;">
							<h3>帐号被锁定：</h3>
							<p>·如需解锁，请编辑短信js发送到1069019555605进行解锁。</p>
						</div>
						<div class="error keyerr" style="display: none;">
							<h3>已检测到您的会员帐号没有开通邮箱，请重新登录</h3>
						</div>
						<div class="loginBox">
							<ul class="tit clearfix">
								<!--被选中的情况，在li上加入class为current-->
								<li class="current"><a href="#" onclick="return false;">免费邮箱登录</a>
								</li>
							</ul>
							<!--freeMailbox为免费邮箱，vipMailbox为VIP邮箱,切换时设另一个为display:none-->
							<div class="freeMailbox">
								<!--不显示visibility="hidden",若错误visibility=visible-->
								<span class="loginError tip11" style="visibility: hidden">您输入的邮箱名或密码不正确</span>
								<div class="usernameBox">
									<input tabindex="1" id="freename" class="username focus"
										type="text"> <a href="#" class="clearname"></a> <label
										for="freename" class="placeholder">输入邮箱名</label>
								</div>
								<span class="loginError tip13" style="visibility: hidden">请输入密码</span>
								<div class="passwordBox">
									<input tabindex="2" id="freepassword" class="password" value=""
										type="password"> <label for="freepassword"
										class="placeholder">输入密码</label>
								</div>
								<div class="loginSetting clearfix">
									<a
										href="http://login.sina.com.cn/cgi/getpwd/getpwd0.php?entry=sso"
										target="_blank" class="forgetPas">忘记密码？</a> <input
										tabindex="4" id="store1" type="checkbox"> <label
										for="store1">保持登录</label> <input tabindex="5" id="ssl1"
										checked="checked" type="checkbox"> <label for="ssl1">SSL安全登录</label>
								</div>
								<div style="display: none;" class="safeTip tip14">建议在网吧或公共电脑上取消保持登录选项。</div>
								<!--若没有验证码，设置display:none-->
								<div class="checkcodeBox" style="display: none">
									<div class="clearfix">
										<input id="freecheckcode" name="checkcode" type="text">
										<img src="" alt="" class="checkcode"> <label
											for="freecheckcode" class="placeholder">验证码</label>
									</div>
									<p class="clearfix">
										<a href="">看不清？换一个</a>按右图填写，不区分大小写
									</p>

								</div>
								<div class="loginOrRegister clearfix">
									<a href="#" class="loginBtn" tabindex="3">登录</a> <span>还没有新浪邮箱？<a
										href="http://login.sina.com.cn/signup/signup.php?entry=freemail"
										target="_blank">立即注册</a></span>
									<p>
										<a id="member" href="#">会员名登录&gt;&gt;</a><a
											style="float: right; background: none"
											href="http://mail.sina.com.cn/cgi-bin/speeddetect.php"
											target="_blank">登录太慢？</a>
									</p>
								</div>
							</div>
							<div class="vipMailbox" style="display: none">
								<form name="vip_login" method="post"
									action="http://mail.sina.com.cn/cgi-bin/viplogin.php">
									<!--不显示visibility="hidden",若错误visibility=visible-->
									<span class="loginError tip21" style="visibility: hidden">您输入的邮箱名或密码不正确</span>
									<div class="usernameBox">
										<input tabindex="1" id="vipname" class="username"
											name="username" type="text"><span class="vipDomain">@vip.sina.com</span>
										<a href="#" class="clearname"></a> <label for="vipname"
											class="placeholder">输入邮箱名</label>
									</div>
									<span class="loginError tip23" style="visibility: hidden">请输入密码</span>
									<div class="passwordBox">
										<input tabindex="2" id="vippassword" class="password"
											name="password" value="" type="password"> <label
											for="vippassword" class="placeholder">输入密码</label>
									</div>
									<div class="loginSetting clearfix">
										<a
											href="http://login.sina.com.cn/cgi/getpwd/getpwd0.php?entry=sso"
											target="_blank" class="forgetPas">忘记密码？</a> <input
											tabindex="4" id="store2" type="checkbox"> <label
											for="store2">保持登录</label> <input tabindex="5" id="ssl2"
											checked="checked" type="checkbox"> <label for="ssl2">SSL安全登录</label>
									</div>
									<div style="display: none;" class="safeTip tip24">建议在网吧或公共电脑上取消保持登录选项。</div>
									<!--若没有验证码，设置display:none-->
									<div class="checkcodeBox" style="display: none;">
										<div class="clearfix">
											<input id="vipcheckcode" name="checkcode" value="验证码"
												type="text"> <img src="" alt="" class="checkcode">
											<label for="vipcheckcode" class="placeholder">验证码</label>
										</div>
										<p class="clearfix">
											<a href="">看不清？换一个</a>按右图填写，不区分大小写
										</p>
									</div>
									<div class="loginOrRegister clearfix">
										<a tabindex="3" href="#" class="loginBtn">登录</a> <span>还没有VIP邮箱？<a
											target="_blank"
											href="http://mail.sina.com.cn/register/reg_vipmail.php">立即注册</a></span>
										<p>
											<a target="_blank"
												href="http://pay.mail.sina.com.cn/email_login.php">快捷续费</a><a
												style="float: right; background: none"
												href="http://mail.sina.com.cn/cgi-bin/speeddetect.php?type=vip"
												target="_blank">登录太慢？</a>
										</p>
									</div>
								</form>
							</div>
							<div class="loginFooter">
								<p>
									<a target="_blank"
										href="http://mail.sina.com.cn/anti-fraud.html"
										style="color: red;">谨防诈骗 反虚假有奖信息公告</a><a target="_blank"
										href="http://help.sina.com.cn/index.php?s=comquestiondetail&amp;a=view&amp;id=1300">元旦壁纸-新年快乐</a>
								</p>
								<p>
									<a target="_blank"
										href="http://mail.sina.net/main/entmailad.html">新浪企业邮箱，助您树立企业品牌</a><a
										target="_blank" href="http://exmail.sina.com.cn/">免费企业邮箱公测</a>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="footerBox">
				<div class="footer clearfix">
					<p class="copy">新浪公司&nbsp;版权所
						有&nbsp;&nbsp;&nbsp;&nbsp;Copyright&nbsp;©&nbsp;1996-2012&nbsp;SINA&nbsp;Corporation,&nbsp;All&nbsp;Rights&nbsp;Reserved</p>
					<a target="_blank" href="http://www.sina.com.cn/">新浪首页</a><span>|</span>
					<a target="_blank" href="http://weibo.com/sinamail">官方微博</a><span>|</span>
					<a target="_blank"
						href="http://comment4.news.sina.com.cn/comment/skin/feedback.html?channel=ly&amp;newsid=210">意见反馈</a><span>|</span>
					<a target="_blank" href="http://www.12321.cn/">不良信息举报</a><span>|</span><a
						target="_blank"
						href="http://help.sina.com.cn/index.php?s=commonquestion&amp;a=subview&amp;productid=2">帮助</a>
				</div>
			</div>
		</div>
	</div>
	<noscript>
		<div
			style='position: absolute; top: 0; left: 0; width: 0; height: 0; visibility: hidden'>
			<img width=0 height=0 src='http://beacon.sina.com.cn/a.gif?noScript'
				border='0' alt='' />
		</div>
	</noscript>

</body>
</html>
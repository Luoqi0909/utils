<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆页面</title>
<!-- script写到head中 -->
<script type="text/javascript">
function _change() {
	/*
	1. 得到img元素
	2. 修改其src为/day11_3/VerifyCodeServlet
	*/
	var imgEle = document.getElementById("img");
	imgEle.src = "${pageContext.request.contextPath}/VerifyCodeServlet?a=" + new Date().getTime();
}
</script>
</head>
<body>

<%
String msg1="";
String msg = (String)request.getAttribute("msg");
if(msg == null){
	msg=msg1;
}else{
	msg1=msg;
}

%>
<%
String uname="";
Cookie[] cookies = request.getCookies();
if(cookies != null){
	for(Cookie c : cookies){
		if("name".equals(c.getName())){
			uname=c.getValue();
		}
	}
}



%>
<%=uname %>
<%=msg1 %>
<form action="${pageContext.request.contextPath}/LoginServlet" method="post">
登录名:<input type="text"  name="name" value="<%=uname %>" /><br/>
密码:<input  type="password" name="password" /><br/>
验证码：<input type="text" name="verifycode" >
<img id="img" src="${pageContext.request.contextPath }/VerifyCodeServlet" onclick="javascript:_change()"><!--注意对象写的时候，驼峰写法  -->
点击图片更换验证码<br>
<input type="submit" value="提交" />




</form>
</body>
</html>
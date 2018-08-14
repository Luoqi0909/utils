<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String name1 = "";
		String name = (String) session.getAttribute("name");
		if (name == null) {
			String msg = "请老老实实登陆";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
            name1=name;
		}
	%>
	<%="欢迎" + name1 + "先生成功访问"%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
		String i=request.getParameter("id");
		String p=request.getParameter("pw");
		
		if(i.equals("dong")&& p.equals("123")){
			
			out.println("<h1>로그인 성공</h1>");
			
		}else{
			out.println("<h1>로그인 실패</h1>");
		}
	%>
</body>
</html>
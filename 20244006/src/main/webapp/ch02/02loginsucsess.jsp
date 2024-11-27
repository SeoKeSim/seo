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

	if(i!=null&&p!=null){
		if(i.equals("dong")&& p.equals("123")){
			//out.println("<h1>로그인 성공</h1>");
			response.sendRedirect("02loginsucsess.jsp");
		}else{
			//out.println("<h1>로그인 실패</h1>");
			%>
			<jsp:forward page="02loginfail.jsp"></jsp:forward>
			<%
		}
	}else{
		out.println("<h1>아이디 또는 비밀번호가 입력되지 않았습니다.</h1>");
	}
%>
<h1>****로그인 성공!</h1>
</body>
</html>
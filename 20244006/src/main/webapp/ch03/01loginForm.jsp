<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>request기본객체</title>
</head>
<body>
<%
String check =(String) session.getAttribute("logincheck");
	if(check==null){
		//빈폼
		%>
			<h1>로그인</h1>
			<form action="01loginsucsess.jsp" method="post">
				아이디 : <input type="text" name="id"><br>
    			암호 : <input type="password" name="pw"><br>
    			<input type="submit" value="로그인"><input type="reset" value="취소">
			</form>
		<% 
	} else{
		//로그아웃
		%>
			<form action="01LogoutProcess.jsp" method="post">
				<%= session.getAttribute("loginName") %>님 <input type="submit" value="로그아웃">
			</form>
		<%
	}
%>


<!-- <h1>로그인</h1>
<form action="01loginsucsess.jsp" method="post">
	아이디 : <input type="text" name="id"><br>
    암호 : <input type="password" name="pw"><br>
    <input type="submit" value="로그인"><input type="reset" value="취소">
</form>
 -->
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="top.jsp" %>

<form action="insertProcess.jsp" method="post">
	아이디 : <input type="text" name="memberid"><br>
	암호 : <input type="text" name="password"><br>
	이름 : <input type="text" name="name"><br>
	이메일 : <input type="text" name="email"><br>
	<input type="submit" value="가입">
</form>


</body>
</html>
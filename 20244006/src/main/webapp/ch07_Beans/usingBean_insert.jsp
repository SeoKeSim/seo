<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <jsp:useBean class="ch07.beans.first.MemberDTO" id="regBean" scope="session"></jsp:useBean>
    <jsp:setProperty name="regBean" property="*" /> <!-- *쓸꺼면 java 랑 jsp랑 property 같아야함 -->
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h3>회원정보</h3>
	<jsp:getProperty property="memberID" name="regBean" />
	<jsp:getProperty property="password" name="regBean" />
	
</body>
</html>
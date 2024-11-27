<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 	request.setCharacterEncoding("UTF-8");
 	String memberId = request.getParameter("memberId");
 	String password = request.getParameter("password");
 	String name = request.getParameter("name");
	 String email = request.getParameter("email");
 
//	1단계 드라이버(커넥터) 로딩
	Class.forName("com.mysql.cj.jdbc.Driver");
//	2단계 DB서버 접속 (url, user, password)
	String uurl = "jdbc:mysql://localhost:3306/jspdb?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf-8";
	Connection con = DriverManager.getConnection(uurl,"root","dongyang");
	System.out.println("여기까지 성공");
//	3단계 쿼리문
	Statement stmt = con.createStatement();
	stmt.executeUpdate("insert into memberTbl values ('"+ memberId + "','" + password + "','" + name + "','" + email + "')");
//	stmt.executeQuery("");
//	VALUES ('" + memberId + "', '" + password + "', '" + name + "', '" + email + "')
//	4단계 close
	con.close();
	stmt.close();
	
 %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
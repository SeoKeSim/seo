<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 	request.setCharacterEncoding("UTF-8");
 	String memberId = request.getParameter("memberId");
 	String name = request.getParameter("name");
 	int updateCount = 0;
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection conn = null;
	PreparedStatement stmt = null;
	
	try{
	String uurl = "jdbc:mysql://localhost:3306/jspdb?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf-8";
	Connection con = DriverManager.getConnection(uurl,"root","dongyang");
	System.out.println("여기까지 성공");
	stmt = con.prepareStatement("update memberTbl set NAME=? where MEMBERID=?");
	stmt.setString(1, memberId);
	stmt.setString(2, name);
	updateCount = stmt.executeUpdate();
	}catch(Exception e){
		
	}finally{
	conn.close();
	stmt.close();
	}
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
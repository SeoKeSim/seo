<%-- <%@page import="javax.swing.colorchooser.RecentSwatchPanel"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String id =request.getParameter("id");
String pw =request.getParameter("pw");

if(id.equals("dong")& pw.equals("123")){
	session.setAttribute("logincheck", "ok");
	
	session.setAttribute("loginName", "sks");
}

response.sendRedirect("01loginForm.jsp");
%> 

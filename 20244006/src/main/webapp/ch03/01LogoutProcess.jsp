<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<% 
session.removeAttribute("logincheck");
/* session.invalidate(); */
response.sendRedirect("01loginForm.jsp");
%>
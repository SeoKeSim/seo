<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String mem_id = (String)session.getAttribute("idKey");
	
	String log="";
	if(mem_id == null) log ="<a href="+request.getContextPath()+"/ch07_third/Login.jsp> 로그인 (모델2)</a>";
	else log = "<a href="+ request.getContextPath()+"/logout.do> 로그아웃 (모델2)</a>";

	String mem="";
	if(mem_id == null) mem ="<a href="+request.getContextPath()+"/ch07_third/Register.jsp> 회원 등록 (모델2) </a>";
	else mem = "<a href=#> 회원 수정 </a>";

%>

[<%=log%>]  [ <%=mem%>]
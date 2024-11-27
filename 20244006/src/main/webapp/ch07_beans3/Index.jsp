<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
<title>모델 2 예제 </title>
</head>

<body>

<%@ include file="top.jsp" %>


<h1> MVC의 모델 2 </h1>


	<%
	if(mem_id != null){
	%>
		<%=mem_id%>님 방문해 주셔서 감사합니다. 
	<%}else{%>
		로그인 하신 후 이용해 주세요
	<%}%>



</body>
</html>  
  
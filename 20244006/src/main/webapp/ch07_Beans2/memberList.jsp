<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.* , ch07.beans.second.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="top.jsp" %>

<h2>회원 목록</h2>
<jsp:useBean class="ch07.beans.second.RegisterDAO" id="regMgr2" scope="session" />
<%
	ArrayList<RegisterDTO> vList=regMgr2.selectMemberList();
	for(int i=0; i< vList.size() ;i++ ){
		RegisterDTO regBean=vList.get(i);
		out.println( regBean.getMemberid() + "," );
		out.println( regBean.getPassword() + "," );
		out.println( regBean.getName() + ",");
		out.println( regBean.getEmail() + "<BR>");
	}
%>


</body>
</html>







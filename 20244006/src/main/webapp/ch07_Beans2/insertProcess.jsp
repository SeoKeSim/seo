<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean class="ch07.beans.second.RegisterDTO" id="regBean"  scope="session" />
<jsp:setProperty name="regBean" property="*" />

<jsp:useBean class="ch07.beans.second.RegisterDAO" id="regMgr"  scope="session" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:getProperty name="regBean" property="memberid" />
<%
	out.println( "," + regBean.getPassword() );
	regMgr.insertMember(regBean);
%>

</body>
</html>
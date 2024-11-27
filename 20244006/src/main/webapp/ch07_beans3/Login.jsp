<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
<title>모델 2 예제 (로그인)</title>

</head>
<body >

<%@ include file="top.jsp" %>

<h1> 로그인 </h1>

<br><br>
	
		<form method="get" action="<%= request.getContextPath() %>/login.do">
		
			<table width="50%" border="1" align="center">
				<tr bordercolor="#FFFF66"> 
					<td colspan="2" align="center">로그인</td>
				</tr>
				<tr > 
					<td width="47%" align="center">ID</td>
					<td width="53%" align="center"><input type="text" name="id"></div></td>
				</tr>
				<tr> 
					<td align="center">Password</td>
					<td align="center"><input type="text" name="password"></td>
				</tr>
				<tr> 
					<td colspan="2" align="center">
						<input type="submit" value="login">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						<input type="reset" value="reset">
					</td>
				</tr>
		</table>
		</form>
		

	
</body>
</html>
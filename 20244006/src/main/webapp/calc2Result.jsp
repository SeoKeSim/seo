<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>서비스 처리 결과</title>
	</head>
	<body>
		<h1> 요청하신 10년 후 나이 : <%= request.getAttribute("resultData") %>    입니다!</h1>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 정보 조회</title>
</head>
<body>
    <h1>사용자 정보 조회</h1>
    <form action="<%= request.getContextPath() %>/user" method="get">
        <label for="id">아이디:</label>
        <input type="text" name="id" id="id" required>
        <input type="submit" value="조회">
    </form>
</body>
</html>
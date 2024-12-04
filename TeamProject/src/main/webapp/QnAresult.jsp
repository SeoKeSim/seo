<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="team.beans.UserDAO" %>
<%@ page import="team.beans.UserDTO" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>처리 결과</title>
</head>
<body>
    <script>
        // 서버에서 전달한 메시지를 표시하고, 지정된 URL로 이동
        alert('<%= request.getAttribute("message") %>');
        window.location.href = '<%= request.getAttribute("redirectUrl") %>';
    </script>
</body>
</html>
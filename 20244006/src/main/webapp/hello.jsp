<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>랜덤 숫자 생성기</title>
<script>
    // 버튼 클릭 시 페이지를 새로 고침하여 랜덤 숫자를 업데이트합니다.
    function refreshNumber() {
        // 폼을 새로 고침하여 서버에서 새로운 랜덤 숫자를 생성하도록 합니다.
        document.getElementById("randomForm").submit();
    }
</script>
</head>
<body>
    <h1>dd 히히 성공</h1>
    <%
        // 랜덤 숫자 생성
        java.util.Random random = new java.util.Random();
        int randomNumber = random.nextInt(101); // 0부터 99까지의 랜덤 숫자 생성
    %>
    <form id="randomForm" method="post">
        <p>랜덤 숫자: <%= randomNumber %></p>
        <button type="button" onclick="refreshNumber()">랜덤 숫자 새로 고침</button>
    </form>
</body>
</html>

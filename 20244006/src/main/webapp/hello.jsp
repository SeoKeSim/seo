<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���� ���� ������</title>
<script>
    // ��ư Ŭ�� �� �������� ���� ��ħ�Ͽ� ���� ���ڸ� ������Ʈ�մϴ�.
    function refreshNumber() {
        // ���� ���� ��ħ�Ͽ� �������� ���ο� ���� ���ڸ� �����ϵ��� �մϴ�.
        document.getElementById("randomForm").submit();
    }
</script>
</head>
<body>
    <h1>dd ���� ����</h1>
    <%
        // ���� ���� ����
        java.util.Random random = new java.util.Random();
        int randomNumber = random.nextInt(101); // 0���� 99������ ���� ���� ����
    %>
    <form id="randomForm" method="post">
        <p>���� ����: <%= randomNumber %></p>
        <button type="button" onclick="refreshNumber()">���� ���� ���� ��ħ</button>
    </form>
</body>
</html>

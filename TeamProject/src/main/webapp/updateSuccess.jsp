<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원정보 수정 완료</title>
    <link rel="stylesheet" href="css/update-info.css">
    <link rel="stylesheet" href="css/headermain.css">
</head>
<body>
    <jsp:include page="/module/headermain.jsp" />
    <div class="update-success-container">
        <h2>회원정보 수정이 완료되었습니다</h2>
        <p>변경된 정보는 마이페이지에서 확인하실 수 있습니다.</p>
        <a href="mypage.jsp" class="btn-mypage">마이페이지로 이동</a>
    </div>
</body>
</html>
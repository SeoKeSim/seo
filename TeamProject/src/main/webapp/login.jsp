<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 페이지</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
    <div class="login-page">
        <div class="login-box">
            <div class="logo-section">
                <div class="logo"></div>
                <p>메이플스토리</p> <!-- 메이플스토리 -->
                <p>MapleStory</p>  <!-- MapleStory 추가 -->
            </div>
            <div class="form-section">
                <h2>로그인</h2>
                <form action="loginProcess.jsp" method="post">
                    <div class="input-group">
                        <input type="text" id="userId" name="userId" placeholder="아이디" required>
                    </div>
                    <div class="input-group">
                        <input type="password" id="password" name="password" placeholder="비밀번호" required>
                    </div>
                    <div class="form-footer">
                        <a href="../Signup/signup.jsp">회원가입</a>
                        <button type="submit">로그인</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

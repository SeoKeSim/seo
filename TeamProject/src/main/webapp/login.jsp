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
            <a class="logo-section" href="index.jsp">
                <img alt="홈페이지로고(임시)" src="img/MapleLogo.png">
            </a>
            <div class="form-section">
                <h2>로그인</h2>
                <form method="get" action="<%= request.getContextPath() %>/user">
                    <div class="input-group">
                        <!-- 로그인 실패 시 입력값 초기화 -->
                        <input type="text" id="id" name="id" placeholder="아이디" required value="">
                    </div>
                    <div class="input-group">
                        <!-- 로그인 실패 시 입력값 초기화 -->
                        <input type="password" id="password" name="password" placeholder="비밀번호" required value="">
                    </div>

                    <!-- 로그인 실패 시 오류 메시지 표시 -->
                    <div class="error-message" style="color: red;">
                        <%= request.getAttribute("loginError") != null ? request.getAttribute("loginError") : "" %>
                    </div>

                    <div class="form-footer">
                        <a href="signup.jsp">회원가입</a>
                        <button type="submit">로그인</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</body>
</html>

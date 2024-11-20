<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입 페이지</title>
    <link rel="stylesheet" type="text/css" href="css/signup.css">
</head>
<body>
	
    <div class="signup-page">
        <div class="signup-box">
            <div class="logo-section">
            	<!-- <a href="index.jsp"> -->
               	 	<p>메이플스토리</p> <!-- 메이플스토리 -->
               	 	<p>MapleStory</p>  <!-- MapleStory 추가 -->
                <!-- </a> -->
            </div>
            <div class="form-section">
                <h2>회원가입</h2>
                <form action="signupProcess.jsp" method="post">
                    <div class="input-group">
                        <input type="text" id="userName" name="userName" placeholder="이름" required>
                    </div>
                    <div class="input-group">
                        <input type="text" id="userId" name="userId" placeholder="아이디" required>
                    </div>
                    <div class="input-group">
                        <input type="password" id="password" name="password" placeholder="패스워드" required>
                    </div>
                    <div class="input-group">
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="패스워드 확인" required>
                    </div>
                    <div class="input-group">
                        <input type="email" id="email" name="email" placeholder="이메일" required>
                    </div>
                    <div class="form-footer">
                        <button type="submit" class="submit-btn">회원가입</button>
                        <button type="button" class="cancel-btn" onclick="location.href='login.jsp'">취소</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- 추가된 스크립트: 취소 버튼 클릭 시 로그인 페이지로 이동 -->
    <script>
        // 취소 버튼 클릭 시 로그인 페이지로 이동
        document.querySelector('.cancel-btn').addEventListener('click', function() {
            window.location.href = 'login.jsp';  // 로그인 페이지로 이동
        });
    </script>
</body>
</html>

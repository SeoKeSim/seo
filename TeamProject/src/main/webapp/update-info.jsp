<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    // 세션에서 데이터 가져오기
    // 이름과 이메일 초기 값 설정
    String name = "김*환"; // 기본 이름 값
    String email = "seokes******@gmail.com"; // 기본 이메일 값
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원 정보 수정</title>
    <link rel="stylesheet" href="css/update-info.css">
</head>
<body>
    <div class="update-info-container">
        <header>
            <h1>회원 정보 수정</h1>
        </header>
        <section class="update-form">
            <form action="#" method="post">
                <!-- 이름 필드 -->
                <div class="form-group">
                    <label for="name">이름</label>
                    <!-- 기존 이름 표시 -->
                    <input type="text" id="name" name="name" value="<%= name %>" required>
                </div>
                
                <!-- 이메일 필드 -->
                <div class="form-group">
                    <label for="email">이메일</label>
                    <!-- 기존 이메일 표시 -->
                    <input type="email" id="email" name="email" value="<%= email %>" required>
                </div>
                
                <!-- 비밀번호 필드 -->
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <!-- 기본 비밀번호 필드 (값 없음) -->
                    <input type="password" id="password" name="password" placeholder="새 비밀번호를 입력하세요" required>
                </div>
                
                <!-- 비밀번호 재입력 필드 -->
                <div class="form-group">
                    <label for="confirm-password">비밀번호 재입력</label>
                    <input type="password" id="confirm-password" name="confirm-password" placeholder="새 비밀번호를 다시 입력하세요" required>
                </div>
                
                <!-- 버튼 -->
                <div class="form-actions">
                    <button type="submit" class="btn-submit">변경</button>
                </div>
            </form>
        </section>
    </div>
</body>
</html>

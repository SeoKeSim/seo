<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
            <form action="processUpdateInfo.jsp" method="post">
                <div class="form-group">
                    <label for="email">이메일</label>
                    <% 
                        // 세션에서 이메일 가져오기, 값이 없으면 빈 문자열
                        String email = (String) session.getAttribute("email");
                    %>
                    <input type="email" id="email" name="email" value="<%= email != null ? email : "" %>" required>
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="새 비밀번호를 입력하세요" required>
                </div>
                <div class="form-group">
                    <label for="confirm-password">비밀번호 재입력</label>
                    <input type="password" id="confirm-password" name="confirm-password" placeholder="새 비밀번호를 다시 입력하세요" required>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn-submit">정보 수정</button>
                </div>
            </form>
        </section>
    </div>
</body>
</html>

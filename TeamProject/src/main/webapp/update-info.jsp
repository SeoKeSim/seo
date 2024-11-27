<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
	String mem_id = (String)session.getAttribute("idKey");
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
                    <input type="text" id="name" name="name" value="" required>
                    <button type="submit" class="btn-edit">변경</button>
                </div>
                
                <!-- 이메일 필드 -->
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" id="email" name="email" value="" required>
                    <button type="submit" class="btn-edit">변경</button>
                </div>
                
                <!-- 비밀번호 필드 -->
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input class="update-form" type="password" id="password" name="password" placeholder="새 비밀번호를 입력하세요" required> 
                </div>
                
                <!-- 비밀번호 재입력 필드 -->
                <div class="form-group">
                    <label for="confirm-password">비밀번호 재입력</label>
                    <input type="password" id="confirm-password" name="confirm-password" placeholder="새 비밀번호를 다시 입력하세요" required>
                    <button type="submit" class="btn-edit">변경</button>
                </div>
            </form>
        </section>
    </div>
</body>
</html>

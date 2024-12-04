<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="team.beans.UserDAO" %>
<%@ page import="team.beans.UserDTO" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>문의하기</title>
    <link rel="stylesheet" href="css/QnA.css">
    <link rel="stylesheet" href="css/headermain.css">
</head>
<body>

    <div class="contact-container"> <!-- 문의하기 컨테이너 -->
        <h2>문의하기</h2>

        <form action="<%= request.getContextPath() %>/QnA.do" method="post">
            <div class="form-group">
                <label for="title">문의 제목</label>
                <input type="text" id="title" name="title" placeholder="문의 제목을 입력해주세요." required>
            </div>
            <div class="form-group">
                <label for="content">문의 내용</label>
                <textarea id="question" name="question" rows="5" placeholder="문의 내용을 입력해주세요." required></textarea>
            </div>

            <div class="buttons">
                <button type="submit" class="submit-btn">전송</button>
                <button type="reset" class="cancel-btn">취소</button>
            </div>
        </form>
    </div>

</body>
</html>

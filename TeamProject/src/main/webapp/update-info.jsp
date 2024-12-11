<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="team.beans.UserDAO" %>
<%@ page import="team.beans.UserDTO" %>
<%
    String mem_id = (String)session.getAttribute("idKey");
    UserDAO userDAO = new UserDAO();
    UserDTO user = userDAO.UserCheck(mem_id);
%>
<!DOCTYPE html>
<html lang="UTF-8">
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
            <form action="updateUser" method="post">
                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" value="<%=user.getName()%>" required>
                </div>
                
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" id="email" name="email" value="<%=user.getEmail()%>" required>
                </div>
                
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="새 비밀번호를 입력하세요" required>
                </div>
                
                <div class="form-group">
                    <label for="confirm-password">비밀번호 재입력</label>
                    <input type="password" id="confirm-password" name="confirm-password" 
                           placeholder="새 비밀번호를 다시 입력하세요" required>
                </div>
                
                <button type="submit" class="btn-update">정보 수정</button>
            </form>
        </section>
    </div>
    
    <script>
        document.querySelector('form').addEventListener('submit', function(e) {
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirm-password').value;
            
            if(password !== confirmPassword) {
                e.preventDefault();
                alert('비밀번호가 일치하지 않습니다.');
            }
        });
    </script>
</body>
</html>
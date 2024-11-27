<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="team.beans.UserDAO" %>
<%@ page import="team.beans.UserDTO" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link rel="stylesheet" href="css/mypage.css">
</head>
<body>
    <jsp:include page="/module/headermain.jsp" />
    <div class="mypage-container">
        <section class="mypage-content">
            <h2>내 정보</h2>
            
            <div class="info-box">
            <%
                String mem_id = (String)session.getAttribute("idKey");
                UserDAO userDAO = new UserDAO();
                UserDTO user = userDAO.UserCheck(mem_id);
            %>
                <p><strong>이름:</strong> <%= user.getName() %></p>
                <p><strong>이메일:</strong> <%= user.getEmail() %></p>
            </div>

            <div class="mypage-links">
                <h3>서비스</h3>
                <ul>
                    <li><a href="contact.jsp">문의하기</a></li>
                    <li><a href="favorites.jsp">즐겨찾기</a></li>
                    <li><a href="update-info.jsp">회원 정보 수정</a></li>
                </ul>
            </div>
        </section>
    </div>
</body>
</html>
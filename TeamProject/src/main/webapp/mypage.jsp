<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link rel="stylesheet" href="css/mypage.css"> <!-- CSS 파일 경로 -->
</head>
<body>
    <div class="mypage-container">
        <header>
            <h1>마이페이지</h1>
            <nav>
                <ul>
                    <li><a href="index.jsp" class="nav-link">홈</a></li>
                    <li><a href="guide.jsp" class="nav-link">가이드</a></li>
                    <li><a href="logout.jsp" class="nav-link">로그아웃</a></li>
                </ul>
            </nav>
        </header>

        <section class="mypage-content">
            <h2>내 정보</h2>

            <!-- 사용자 정보 표시 -->
            <div class="info-box">
                <% 
                    // 세션에서 사용자 정보 가져오기
                    String username = (String) session.getAttribute("username");
                    String email = (String) session.getAttribute("email");
                    String joinDate = (String) session.getAttribute("joinDate");
                %>

                <p><strong>이름:</strong> <%= username != null ? username : "" %></p>
                <p><strong>이메일:</strong> <%= email != null ? email : "" %></p>
                <p><strong>가입일:</strong> <%= joinDate != null ? joinDate : "" %></p>

                <!-- 로그인되지 않은 경우 로그인 버튼 표시 -->
                <%-- <%= username == null ? "<a href='login.jsp'><button class='login-btn'>로그인</button></a>" : "" %> --%>
            </div>

            <!-- 마이페이지 서비스 링크 -->
            <div class="mypage-links">
                <h3>서비스</h3>
                <ul>
                    <li><a href="contact.jsp">문의하기</a></li>
                    <li><a href="favorites.jsp">즐겨찾기</a></li>
                    <li><a href="change-password.jsp">비밀번호 변경</a></li>
                    <li><a href="change-email.jsp">이메일 변경</a></li>
                </ul>
            </div>
        </section>
    </div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link rel="stylesheet" href="css/mypage.css"> <!-- CSS 파일 경로 확인 -->
</head>
<body>
    <div class="mypage-container">
        <header>
            <h1>마이페이지</h1>
            <nav>
                <ul>
                    <li><a href="index.jsp">홈</a></li>
                    <li><a href="guide.jsp">사용 안내</a></li>
                    <li><a href="logout.jsp">로그아웃</a></li>
                </ul>
            </nav>
        </header>
        
        <section class="mypage-content">
            <h2>내 정보</h2>
            <div class="info-box">
                <p><strong>이름:</strong> 홍길동</p>
                <p><strong>이메일:</strong> example@example.com</p>
                <p><strong>가입일:</strong> 2024-01-01</p>
            </div>

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

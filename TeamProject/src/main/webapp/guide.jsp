<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>캐릭터 정보 페이지</title>
    <link rel="stylesheet" href="css/guide.css">
    <link rel="stylesheet" href="css/headermain.css">
</head>
<body>

	<%@include file="module/headermain.jsp" %>
	
    <div class="container">
        <!-- 캐릭터 목록 -->
        <div class="character-grid">
            <div class="character"><img src="img/char1.png" alt="캐릭터1"></div>
            <div class="character"><img src="img/char2.png" alt="캐릭터2"></div>
            <div class="character"><img src="img/char3.png" alt="캐릭터3"></div>
            <div class="character"><img src="img/char4.png" alt="캐릭터4"></div>
            <div class="character"><img src="img/char5.png" alt="캐릭터5"></div>
            <div class="character"><img src="img/char6.png" alt="캐릭터6"></div>
            <div class="character"><img src="img/char7.png" alt="캐릭터7"></div>
            <div class="character"><img src="img/char8.png" alt="캐릭터8"></div>
            <div class="character"><img src="img/char9.png" alt="캐릭터9"></div>
            <div class="character"><img src="img/char10.png" alt="캐릭터10"></div>
            <div class="character"><img src="img/char11.png" alt="캐릭터11"></div>
            <div class="character"><img src="img/char12.png" alt="캐릭터12"></div>
        </div>

        <!-- 정보 박스 -->
        <div class="info-box">
            <!-- 텍스트 박스 -->
            <div class="text-box">
                <p>현재 스펙 : 17성 돌돌</p>
                <p>다음 스펙 : 18성 돌돌</p>
                <p>가이드 ________________________________________</p>
            </div>

            <!-- 가이드 링크 박스 -->
            <div class="guide-box">
                <p>가이드 관련 영상 링크</p>
                <p>가이드 관련 영상 링크</p>
                <p>가이드 관련 영상 링크</p>
            </div>
        </div>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>스펙 확인하기!</title>
    <link rel="stylesheet" href="css/char_info.css">
    <link rel="stylesheet" href="css/headermain.css">
</head>
<body>
	<%@include file="module/headermain.jsp" %>
    <div class="profile-container">
        <!-- 캐릭터 이미지 및 정보 -->
        <img src="img/character.png" alt="Character Image">
    </div>

    <!-- 아이템 목록 -->
    <div class="items-container">
        <div class="character-info">전투력</div>
        <div class="item">
            <span class="item-name">아이템 이름 1</span>
            <span class="item-info">세부 정보</span>
        </div>
        <div class="item">
            <span class="item-name">아이템 이름 2</span>
            <span class="item-info">세부 정보</span>
        </div>
        <div class="item">
            <span class="item-name">아이템 이름 3</span>
            <span class="item-info">세부 정보</span>
        </div>
        <div class="item">
            <span class="item-name">아이템 이름 4</span>
            <span class="item-info">세부 정보</span>
        </div>
        <div class="item">
            <span class="item-name">아이템 이름 5</span>
            <span class="item-info">세부 정보</span>
        </div>
        <!-- 추가 아이템 리스트 -->
        <div class="guide-button">
            <button onclick="location.href='guide.jsp'">가이드 바로가기</button>
        </div>
    </div>

    <!-- 가이드 버튼 -->
    
</body>
</html>

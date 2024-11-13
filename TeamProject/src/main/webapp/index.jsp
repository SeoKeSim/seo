<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>스펙 확인하기!</title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/headermain.css">
</head>
<body>

    <%@include file="module/headermain.jsp" %>

    <div class="container">
        <div class="character-icon">
            <img src="img/mushroom.jpg" alt="캐릭터 아이콘">
        </div>
        <div class="search-box">
            <input type="text" placeholder="캐릭터 이름 검색">
            <form action="char_info.jsp" method="post">
                <button>검색하기</button>
            </form>
        </div>
    </div> <!-- end container -->
    
</body>
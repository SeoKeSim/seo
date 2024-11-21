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

    <%@include file="module/headermain.jsp" %> <!-- 헤더 -->

    <div class="container"> <!-- 전체 컨테이너 -->
    
        <div class="character-icon"> <!-- 메이플 이미지 컨테이너 -->
            <img src="img/mushroom.jpg" alt="캐릭터 아이콘">
        </div> <!-- end 메이플 이미지 컨테이너 -->
        
        <div class="search-box"> <!-- 검색바 컨테이너 -->
    		<form action="CharacterSearchServlet" method="post"> <!-- CharacterSearchServlet.java로 보냄 -->
      		  <input type="text" name="characterName" placeholder="캐릭터 이름 검색">
        		<button type="submit">검색하기</button>
   			 </form>
		</div> <!-- end 검색바 컨테이너 -->

    </div> <!-- end container -->
    
</body>
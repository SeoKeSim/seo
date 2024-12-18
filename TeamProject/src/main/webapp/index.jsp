<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="nexon_data.FavoritesDAO" %>
<%@ page import="nexon_data.FavoritesDTO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>스펙 확인하기!</title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/headermain.css">
    <link rel="stylesheet" href="css/quicksearch.css">
</head>
<body>

    <%@include file="module/headermain.jsp" %> <!-- 헤더 -->

    <div class="container"> <!-- 전체 컨테이너 -->
    
        <div class="character-icon"> <!-- 메이플 이미지 컨테이너 -->
            <img src="img/mushroom.jpg" alt="캐릭터 아이콘">
        </div> <!-- end 메이플 이미지 컨테이너 -->
        
        <div class="search-box"> <!-- 검색바 컨테이너 -->
    		<form action="character" method="post"> <!-- CharacterController.java로 보냄 -->
      		  <input type="text" name="characterName" placeholder="캐릭터 이름 검색">
        		<button type="submit">검색하기</button>
   			</form>
		</div> <!-- end 검색바 컨테이너 -->
		
		<div class="quick-search">
		    <%
		    String userId = (String)session.getAttribute("idKey");
		    if (userId != null) {  // 로그인된 경우에만 즐겨찾기 표시
		        FavoritesDAO favoritesDAO = new FavoritesDAO();
		        List<FavoritesDTO> favoritesList = favoritesDAO.getUserFavorites(userId);
		        
		        if (favoritesList != null && !favoritesList.isEmpty()) {
		    %>
		        <div class="favorites-shortcuts">
		            <% for (FavoritesDTO favorite : favoritesList) { %>
		                <a href="character?characterName=<%= favorite.getCharacterName() %>" 
		                   class="favorite-char">
		                    <%= favorite.getCharacterName() %>
		                </a>
		            <% } %>
		        </div>
		    <% 
		        } else { // 즐겨찾기가 없는 경우
		    %>
		        <div class="no-favorites">
		            <p>즐겨찾기한 캐릭터가 없습니다.</p>
		        </div>
		    <%
		        }
		    } else { // 로그인하지 않은 경우
		    %>
		        <div class="login-message">
		            <p>로그인 후 즐겨찾기한 캐릭터를 빠르게 검색할 수 있습니다.</p>
		        </div>
		    <%
		    }
		    %>
		</div>
		
    </div> <!-- end container -->
    
</body>
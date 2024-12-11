<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="nexon_data.FavoritesDAO" %>
<%@ page import="nexon_data.FavoritesDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>즐겨찾기 목록</title>
    <link rel="stylesheet" href="css/headermain.css">
	<link rel="stylesheet" href="css/favorites.css">
</head>
<body>
    <jsp:include page="/module/header_mypage.jsp" />
    
    <div class="favorites-container">
        <h2>즐겨찾기한 캐릭터</h2>
        
        <%
        String userId = (String)session.getAttribute("idKey");
        if (userId != null) {
            FavoritesDAO favoritesDAO = new FavoritesDAO();
            List<FavoritesDTO> favoritesList = favoritesDAO.getUserFavorites(userId);
            
            if (favoritesList != null && !favoritesList.isEmpty()) {
        %>
            <div class="favorites-grid">
            <%
                for (FavoritesDTO favorite : favoritesList) {
            %>
				<div class="character-card">
				    <a href="character?characterName=<%= favorite.getCharacterName() %>" class="character-link">
				        <!-- 이미지 부분 수정 -->
				        <% String characterImage = favorite.getCharacterImage(); %>
				        <% if (characterImage != null && !characterImage.isEmpty()) { %>
				            <img src="<%= characterImage %>" alt="<%= favorite.getCharacterName() %>">
				        <% } else { %>
				            <img src="images/default-character.png" alt="<%= favorite.getCharacterName() %>">
				        <% } %>
				        <h3><%= favorite.getCharacterName() %></h3>
				    </a>
				    <form action="favorite" method="post">
				        <input type="hidden" name="action" value="toggle">
				        <input type="hidden" name="ocid" value="<%= favorite.getOcid() %>">
				        <input type="hidden" name="characterName" value="<%= favorite.getCharacterName() %>">
				        <button type="submit" class="remove-btn">즐겨찾기 해제</button>
				    </form>
				</div>
            <%
                }
            %>
            </div>
        <%
            } else {
        %>
            <div class="no-favorites">
                <p>즐겨찾기한 캐릭터가 없습니다.</p>
            </div>
        <%
            }
        } else {
        %>
            <div class="no-favorites">
                <p>로그인이 필요한 서비스입니다.</p>
            </div>
        <%
        }
        %>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.json.JSONObject, org.json.JSONArray" %>
<%
    // 디버깅을 위한 로그 출력
    System.out.println("characterInfo from request: " + request.getAttribute("characterInfo"));
    System.out.println("characterInfo from session: " + session.getAttribute("characterInfo"));
    
    JSONObject characterInfo = null;
    // request에서 먼저 확인
    if(request.getAttribute("characterInfo") != null) {
        characterInfo = (JSONObject) request.getAttribute("characterInfo");
    }
    // session에서도 확인
    else if(session.getAttribute("characterInfo") != null) {
        characterInfo = (JSONObject) session.getAttribute("characterInfo");
    }
    
    JSONObject characterEquipment = null;
    if(request.getAttribute("characterEquipment") != null) {
        characterEquipment = (JSONObject) request.getAttribute("characterEquipment");
    } else if(session.getAttribute("characterEquipment") != null) {
        characterEquipment = (JSONObject) session.getAttribute("characterEquipment");
    }
    
    String error = (String) request.getAttribute("error");
    String characterImage = null;
    if(request.getAttribute("characterImage") != null) {
        characterImage = (String) request.getAttribute("characterImage");
    } else if(session.getAttribute("characterImage") != null) {
        characterImage = (String) session.getAttribute("characterImage");
    }
%>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>캐릭터 정보</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/char_info.css">
        <link rel="stylesheet" href="css/headermain.css">
    </head>
    <body>
    	<%@include file="module/header_no_contentType.jsp" %> <!-- 헤더 -->
    	
		<nav class="navigation">
		    <a href="${pageContext.request.contextPath}/guide" class="guide-button">가이드</a>
		</nav>
    	
        <h1>캐릭터 정보</h1>

        <div class="search-form">
            <form action="${pageContext.request.contextPath}/character" method="post">
        		<input type="text" name="characterName" placeholder="캐릭터 이름을 입력하세요">
        		<button type="submit">검색</button>
    		</form>
        </div>

        <% if (error != null) { %>
            <div class="error-message">
                <%= error %>
            </div>
        <% } %>

        <% if (characterInfo != null && characterInfo.has("basic")) {
            JSONObject basicInfo = characterInfo.getJSONObject("basic");
        %>
            <div class="character-info">
                <h2>캐릭터 정보</h2>
                <div class="basic-info">
                
					<% if (session.getAttribute("idKey") != null) { %>
						<div class="favorite-button">
							<form action="${pageContext.request.contextPath}/favorite" method="post" id="favoriteForm">
							    <input type="hidden" name="action" value="toggle">
							    <input type="hidden" name="ocid" value="<%= request.getAttribute("ocid") %>">
							    <input type="hidden" name="characterName" value="<%= basicInfo.getString("character_name") %>">
							    <input type="hidden" name="characterImage" value="<%= characterImage %>">
							    
							<!-- 디버깅을 위한 JavaScript 추가 -->
							    <script>
							        console.log("ocid:", "<%= request.getAttribute("ocid") %>");
							        console.log("characterName:", "<%= basicInfo.getString("character_name") %>");
							        console.log("characterImage:", "<%= characterInfo.getString("character_image") %>");
							    </script>							    
							    
							    <button type="submit" id="favoriteBtn" class="favorite">
							        <% if (request.getAttribute("isFavorite") != null && (Boolean)request.getAttribute("isFavorite")) { %>
							            ★ 즐겨찾기 해제
							        <% } else { %>
							            ☆ 즐겨찾기 추가
							        <% } %>
							    </button>
							</form>	
						</div>
					<% } %>               	
                	
                	<div class="character-profile">
   						<% if (characterImage != null && !characterImage.isEmpty()) { %>
        				<img src="<%= characterImage %>" alt="캐릭터 이미지" 
                             onerror="this.onerror=null; this.src='images/default-character.png';"
                             style="width: 200px; height: auto;">
    					<% } else { %>
        				<p>캐릭터를 검색하시면 이미지가 표시됩니다.</p>
    					<% } %>
					</div>
                    <div class="info-item">
                        <div class="info-label">닉네임</div>
                        <div><%= basicInfo.getString("character_name") %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">레벨</div>
                        <div><%= basicInfo.getInt("character_level") %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">직업</div>
                        <div><%= basicInfo.getString("character_class") %></div>
                    </div>
                    <div class="info-item">
   						<div class="info-label">전투력</div>
    					<% if (basicInfo.has("combat_power")) { %>
        				<div><%= basicInfo.getInt("combat_power") %></div>
    					<% } else { %>
        				<div>정보 없음</div>
    					<% } %>
					</div>
                </div>
            </div>
        <% } else { %>
            <div class="message">
                캐릭터를 검색하시면 정보가 표시됩니다.
            </div>
        <% } %>

        <% if (characterEquipment != null && characterEquipment.has("item_equipment")) { %>
            <div class="equipment-section">
                <h2>착용 장비</h2>
                <div class="equipment-grid">
                    <%
                    JSONArray items = characterEquipment.getJSONArray("item_equipment");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                    %>
                        <div class="equipment-item">
                            <% if (item.has("item_icon")) { %>
                                <img src="<%= item.getString("item_icon") %>"
                                     alt="<%= item.getString("item_name") %>"
                                     onerror="this.src='images/default-item.png'"/>
                            <% } %>
                            <div class="item-name"><%= item.getString("item_name") %></div>
                            <div class="item-details">
                                <% if (item.has("item_shape_name")) { %>
                                    <p><%= item.getString("item_shape_name") %></p>
                                <% } %>
                                <% if (item.has("item_equipment_slot")) { %>
                                    <p><%= item.getString("item_equipment_slot") %></p>
                                <% } %>
                                <% if (item.has("starforce")) { %>
                                    <p>★ <%= item.getInt("starforce") %></p>
                                <% } %>
                            </div>
                        </div>
                    <% } %>
                </div>
            </div>
        <% } %>
    </body>
</html>
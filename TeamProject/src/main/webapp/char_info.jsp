<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.json.JSONObject, org.json.JSONArray" %>
<%
    JSONObject characterInfo = (JSONObject) request.getAttribute("characterInfo");
    JSONObject characterEquipment = (JSONObject) request.getAttribute("characterEquipment");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>캐릭터 정보</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/char_info.css">
    </head>
    <body>
    	
    	<nav class="navigation">
    		<a href="${pageContext.request.contextPath}/guide2.jsp" class="guide-button">가이드</a>
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
        <% } else { %>
            <p>장비 데이터를 불러올 수 없습니다.</p>
        <% } %>
    </body>
</html>
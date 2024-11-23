<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.json.JSONObject, org.json.JSONArray" %>
<%
    // Servlet에서 전달받은 데이터 가져오기
    JSONObject characterData = (JSONObject) request.getAttribute("characterData");
    JSONObject characterEquipment = null;

    if (characterData != null && characterData.has("equipment")) {
        characterEquipment = characterData.getJSONObject("equipment");
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>캐릭터 정보</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .character-info, .equipment-image {
            margin: 20px;
            padding: 20px;
            border: 1px solid #ddd;
        }
        .equipment-image img {
            margin: 10px;
            width: 50px;
            height: 50px;
        }
    </style>
</head>
<body>
    <h1>캐릭터 정보</h1>

    <% if (characterData != null) { %>
        <div class="character-info">
            <h2>기본 정보</h2>
            <pre><%= characterData.toString(4) %></pre>
        </div>

        <% if (characterEquipment != null) { %>
            <div class="equipment-image">
                <h2>장착 장비</h2>
                <% 
                    for (Object key : characterEquipment.keySet()) {
                        Object itemObject = characterEquipment.get((String) key);
                        if (itemObject != null && itemObject instanceof JSONObject) {
                            JSONObject item = (JSONObject) itemObject;
                            String imageUrl = item.optString("imageUrl", ""); // 기본값 처리
                            String itemName = item.optString("name", "알 수 없는 장비");
                %>
                            <div>
                                <img src="<%= imageUrl %>" alt="<%= itemName %>" />
                                <p><%= itemName %></p>
                            </div>
                <%      }
                    }
                %>
            </div>
        <% } else { %>
            <p>장착 장비 데이터가 없습니다.</p>
        <% } %>
    <% } else { %>
        <p>캐릭터 데이터를 불러올 수 없습니다.</p>
    <% } %>
</body>
</html>

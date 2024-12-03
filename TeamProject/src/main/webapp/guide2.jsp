<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="nexon_data.MapleCharacter_DTO" %>
<%@ page import="nexon_data.CharacterEquipment_DTO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>캐릭터 스펙 정보</title>
</head>
<body>
    <h1>캐릭터 정보</h1>

    <div>
        <h2>기본 정보</h2>
        <%
        MapleCharacter_DTO character = (MapleCharacter_DTO)session.getAttribute("character");
        if (character != null) {
        %>
            <p>이름: <%= character.getCharacterName() %></p>
            <p>레벨: <%= character.getCharacterLevel() %></p>
            <p>직업: <%= character.getCharacterClass() %></p>
            <p>전투력: <%= character.getTotalPower() %></p>
        <% } %>
    </div>

    <div>
        <h2>장비 정보</h2>
        <table>
            <thead>
                <tr>
                    <th>장비 타입</th>
                    <th>장비 이름</th>
                    <th>장비 레벨</th>
                    <th>스타포스</th>
                    <th>잠재능력 등급</th>
                </tr>
            </thead>
            <tbody>
            <%
            List<CharacterEquipment_DTO> equipments = 
                (List<CharacterEquipment_DTO>)session.getAttribute("equipments");
            if (equipments != null) {
                for (CharacterEquipment_DTO equipment : equipments) {
            %>
                <tr>
                    <td><%= equipment.getEquipmentType() %></td>
                    <td><%= equipment.getEquipmentName() %></td>
                    <td><%= equipment.getEquipmentLevel() %></td>
                    <td><%= equipment.getEquipmentStarForce() %></td>
                    <td><%= equipment.getPotentialGrade() %></td>
                </tr>
            <%
                }
            }
            %>
            </tbody>
        </table>
    </div>
</body>
</html>
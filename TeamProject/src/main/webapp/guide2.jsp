<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="nexon_data.MapleCharacter_DTO" %>
<%@ page import="nexon_data.BossGuideService" %>
<%@ page import="nexon_data.BossStageData" %>
<%@ page import="nexon_data.MapleCharacterDAO" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %>

<!DOCTYPE html>
<html>
<head>
    <title>보스 컨텐츠 가이드</title>
    <link rel="stylesheet" href="css/guide2.css">
    <link rel="stylesheet" href="css/headermain.css">
</head>
<body>
    <%@include file="module/headermain.jsp" %> <!-- 헤더 -->

    <div class="container">
        <div class="boss-timeline">
            <!-- 타임라인 요소들 먼저 선언 -->
            <div class="timeline-line"></div>
            
            <!-- 타임라인 포인트들 -->
            <% 
            MapleCharacter_DTO character = (MapleCharacter_DTO)session.getAttribute("character");
            BossStageData bossData = (BossStageData)request.getAttribute("bossData");
            Integer currentStage = (Integer)request.getAttribute("currentStage");
            String characterImage = (String)request.getAttribute("characterImage");
            
            if(bossData != null) {
                for(int i = 0; i < bossData.getBossImages().length; i++) {
            %>
                <div class="timeline-point" style="left: <%= (i * 230) + 115 %>px"></div>
                <div class="timeline-vertical-line" style="left: <%= (i * 230) + 115 %>px"></div>
            <%
                }
                
                for(int i = 0; i < bossData.getBossImages().length; i++) {
                    String stageClass = (i == currentStage) ? "current-stage" : "";
            %>
                    <div class="boss-stage">
                        <div class="boss-image <%= stageClass %>">
                            <img src="/TeamProject/img/boss/<%= bossData.getBossImages()[i] %>" 
                                 alt="<%= bossData.getBossNames()[i] %>" 
                                 title="<%= bossData.getBossNames()[i] %>">
                        </div>
                        <div class="boss-info">
                            <div class="boss-description">
                                <%= bossData.getBossDescriptions()[i] %>
                            </div>
                            <div class="boss-power">
                                <%= String.format("%,d", bossData.getPowerThresholds()[i]) %> 전투력
                            </div>
                        </div>
                    </div>
            <%
                }
                
                if(characterImage != null && !characterImage.isEmpty()) {
            %>
                    <div class="character-position-container">
						<div class="character-position" style="left: <%= (currentStage * 250) + 295 %>px">
						    <img src="<%= characterImage %>" alt="캐릭터" class="character-image">
						</div>
                    </div>
            <%
                }
            }
            %>
        </div>
    </div>
    
    <!-- 장비 가이드 섹션 -->
    <div class="equipment-guide">
        <h2>장비 가이드</h2>
        <%
        if(character != null) {
            Map<String, MapleCharacterDAO.EquipmentStats> equipments = 
                (Map<String, MapleCharacterDAO.EquipmentStats>)request.getAttribute("equipments");
            long totalPower = character.getTotalPower();
            
            String recommendedStarforce = "";
            String recommendedPotential = "";
            
            if(totalPower < 40000000) {
                recommendedStarforce = "모든 장비 17성 이상";
                recommendedPotential = "유니크 잠재능력";
            } else if(totalPower < 100000000) {
                recommendedStarforce = "모든 장비 22성 이상";
                recommendedPotential = "레전드리 잠재능력";
            } else if(totalPower < 150000000) {
                recommendedStarforce = "모든 장비 22성 이상, 아케인 무기";
                recommendedPotential = "레전드리 잠재능력(3줄)";
            }
            
            if(equipments != null && !equipments.isEmpty()) {
        %>
                <div class="guide-section">
                    <h3>현재 장비 현황</h3>
                    <div class="equipment-list">
                    <% 
                    int totalStarforce = 0;
                    int equipmentCount = 0;
                    for(Map.Entry<String, MapleCharacterDAO.EquipmentStats> entry : equipments.entrySet()) {
                        MapleCharacterDAO.EquipmentStats stats = entry.getValue();
                        if(stats.getStarForce() > 0) {
                            totalStarforce += stats.getStarForce();
                            equipmentCount++;
                        }
                    %>
                        <div class="equipment-item">
                            <span class="item-type"><%= entry.getKey() %></span>
                            <span class="star-force"><%= stats.getStarForce() %></span>
                            <span class="potential <%= stats.getPotentialGrade().toLowerCase() %>">
                                <%= stats.getPotentialGrade() %>
                            </span>
                        </div>
                    <% } %>
                    </div>
                    
                    <div class="recommendations">
                        <h3>권장 사항</h3>
                        <p class="recommendation-item">
                            <span class="label">권장 스타포스:</span>
                            <span class="value"><%= recommendedStarforce %></span>
                        </p>
                        <p class="recommendation-item">
                            <span class="label">권장 잠재능력:</span>
                            <span class="value"><%= recommendedPotential %></span>
                        </p>
                        <p class="recommendation-item">
                            <span class="label">평균 스타포스:</span>
                            <span class="value star-total">
                                <%= equipmentCount > 0 ? String.format("%.1f", (double)totalStarforce / equipmentCount) : "0" %>성
                            </span>
                        </p>
                    </div>
                </div>
        <%  } else { %>
                <p class="no-equipment">장비 정보가 없습니다.</p>
        <%  }
        } %>
    </div>
</body>
</html>
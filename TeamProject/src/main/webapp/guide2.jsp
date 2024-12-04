<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="nexon_data.MapleCharacter_DTO" %>
<%@ page import="nexon_data.BossGuideService" %>

<!DOCTYPE html>
<html>
<head>
    <title>보스 컨텐츠 가이드</title>
    <link rel="stylesheet" href="css/guide2.css">
</head>
<body>
    <div class="container">
        <div class="boss-timeline">
            <%
            MapleCharacter_DTO character = (MapleCharacter_DTO)session.getAttribute("character");
            BossGuideService.BossStageData bossData = 
                (BossGuideService.BossStageData)request.getAttribute("bossData");
            Integer currentStage = (Integer)request.getAttribute("currentStage");
            String characterImage = (String)request.getAttribute("characterImage");
            
            if(bossData != null) {
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
            }
            
            if(characterImage != null && !characterImage.isEmpty()) {
            %>
				<div class="character-position-container">
				    <%
				    if(characterImage != null && !characterImage.isEmpty()) {
				        int leftPosition = currentStage * 170; // 각 단계별 간격
				    %>
				        <div class="character-position" style="left: <%= leftPosition %>px">
				            <img src="<%= characterImage %>" alt="캐릭터" class="character-image">
				        </div>
				    <%
				    }
				    %>
				</div>
            <%
            }
            %>
        </div>
    </div>
</body>
</html>
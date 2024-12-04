<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="nexon_data.MapleCharacter_DTO" %>
<%@ page import="nexon_data.BossGuideService" %>
<%@ page import="nexon_data.BossStageData" %>

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
                       <div class="character-position" style="left: <%= (currentStage * 230) + 115 %>px">
                           <img src="<%= characterImage %>" alt="캐릭터" class="character-image">
                       </div>
                   </div>
           <%
               }
           }
           %>
       </div>
   </div>
</body>
</html>
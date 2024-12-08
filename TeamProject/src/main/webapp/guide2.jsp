<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="nexon_data.MapleCharacter_DTO" %>
<%@ page import="nexon_data.BossGuideService" %>
<%@ page import="nexon_data.BossStageData" %>
<%@ page import="nexon_data.MapleCharacterDAO" %>
<%@ page import="nexon_data.EquipmentService" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>보스 컨텐츠 가이드</title>
    <link rel="stylesheet" href="css/headermain.css">
    <link rel="stylesheet" href="css/guide2.css">
</head>
<body>
    <%@include file="module/headermain.jsp" %>
    
    <%
    // 상단에서 모든 변수 선언
    MapleCharacter_DTO character = (MapleCharacter_DTO)session.getAttribute("character");
    BossStageData bossData = (BossStageData)request.getAttribute("bossData");
    Integer currentStage = (Integer)request.getAttribute("currentStage");
    String characterImage = (String)request.getAttribute("characterImage");
    %>

    <div class="container">
        <div class="boss-timeline">
            <!-- 보스 가이드 안내 문구 추가 -->
            <% if(character != null) { %>
                <div class="boss-guide-info">
                    <div class="guide-message">
                        <% 
                        long characterPower = character.getTotalPower();
                        String recommendMessage = "";
                        if(characterPower < 3000000) {
                            recommendMessage = "아직 본격적인 보스에 도전하기는 이른 것 같네요. 더 성장해보세요!";
                        } else if(characterPower < 9999999) {
                            recommendMessage = "카오스 루타비스에 도전해보세요.";
                        } else if(characterPower < 19999999) {
                            recommendMessage = "노말 가디언 엔젤 슬라임에게 도전해보세요!";
                        } else if(characterPower < 29999999) {
                            recommendMessage = "아케인 지역의 이지 보스중 절반을 잡을 수 있습니다!";
                        } else if(characterPower < 39999999) {
                            recommendMessage = "첫 하드보스 하드 스우에게 도전 해보세요";
                        } else if(characterPower < 49999999) {
                            recommendMessage = "하드 루시드와 카오스 더스크에게 도전 해보세요";
                        } else if(characterPower < 74999999) {
                            recommendMessage = "아케인 지역 최종 보스 검은 마법사 파티 레이드가 가능합니다!";
                        } else if(characterPower < 89999999) {
                            recommendMessage = "아케인 지역 모든 하드보스를 잡을수 있습니다";
                        } else if(characterPower < 149999999) {
                            recommendMessage = "그란디스 지역 첫 보스 노말 세렌에게 도전해보세요";
                        } else if(characterPower < 199999999) {
                            recommendMessage = "이지 칼로스에게 도전해 보시고 검은 마법사도 힘겹게 잡을수있습니다!";
                        } else if(characterPower < 249999999) {
                            recommendMessage = "하드 세렌 에게 도전해 보세요";
                        } else if(characterPower < 299999999) {
                            recommendMessage = "이지 카링에게 도전해 보세요";
                        } else if(characterPower < 349999999) {
                            recommendMessage = "노말 칼로스에게 도전해 보세요";
                        } else if(characterPower < 499999999) {
                            recommendMessage = "익스트림 단계에 도전 하실 수 있습니다! 익스트림 스우에게 도전해보세요";
                        } else if(characterPower < 699999999) {
                            recommendMessage = "현재 메이플 최상위 보스 노말 림보에게 도전 할 수있습니다!";
                        } else {
                            recommendMessage = "현재 메이플 최상위 보스 하드 림보에게 도전 할 수있습니다!";
                        }

                        %>
                        <h3>보스 도전 가이드</h3>
                        <p><%= recommendMessage %></p>
                        <p class="power-info">현재 전투력: <%= String.format("%,d", characterPower) %></p>
                    </div>
                </div>
            <% } %>

            <div class="boss-stages-container">
                <% if(bossData != null) { %>
                    <!-- 보스 스테이지 내용 -->
                    <div class="boss-stages">
                        <%
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
                        <% } %>
                    </div>

                    <!-- 타임라인 요소들 -->
                    <div class="timeline-wrapper">
                        <div class="timeline-line"></div>
                        <% for(int i = 0; i < bossData.getBossImages().length; i++) { %>
                            <div class="timeline-point" data-position="<%= i %>"></div>
                            <div class="timeline-vertical-line" data-position="<%= i %>"></div>
                        <% } %>
                    </div>

                    <!-- 캐릭터 위치 -->
                    <% if(characterImage != null && !characterImage.isEmpty()) { %>
                        <div class="character-position-container">
                            <div class="character-position" data-stage="<%= currentStage %>">
                                <img src="<%= characterImage %>" alt="캐릭터" class="character-image">
                            </div>
                        </div>
                    <% } %>
                <% } %>
            </div>
        </div>

        <!-- 장비 가이드 섹션 -->
        <div class="equipment-guide">
            <h2>장비 가이드</h2>
            <% if(character != null) {
                EquipmentService.EquipmentAnalysis analysis = 
                    (EquipmentService.EquipmentAnalysis)request.getAttribute("equipmentAnalysis");
                    
                if(analysis != null) { %>
                    <div class="guide-section">
                        <!-- 무기/보조무기/엠블렘 섹션 -->
                        <div class="weapon-section">
                            <h3>무기세트 잠재능력</h3>
                            <div class="weapon-list">
                                <% for(Map.Entry<String, MapleCharacterDAO.EquipmentStats> entry : 
                                       analysis.getWeaponSet().entrySet()) { %>
                                    <div class="weapon-item">
                                        <span class="item-type"><%= entry.getKey() %></span>
                                        <span class="potential <%= entry.getValue().getPotentialGrade().toLowerCase() %>">
                                            <%= entry.getValue().getPotentialGrade() %>
                                        </span>
                                    </div>
                                <% } %>
                            </div>
                            <div class="recommendation">
                                <% if (!analysis.isAllLegendary()) { %>
                                    <p>모든 무기세트를 레전더리로 만드는 것을 목표로 하세요.</p>
                                <% } else { %>
                                    <p>잠재능력이 충분합니다. 이제 좋은 옵션을 노려보세요.</p>
                                <% } %>
                            </div>
                        </div>

                        <!-- 방어구 섹션 -->
                        <div class="armor-section">
                            <h3>방어구 스타포스</h3>
                            <p class="avg-starforce">평균: <%= String.format("%.1f성", analysis.getArmorAvgStarforce()) %></p>
                            <div class="recommendation">
                                <% if (analysis.getArmorAvgStarforce() < 15) { %>
                                    <p>모든 방어구를 15성 이상으로 강화하세요.</p>
                                    <p>썬데이 이벤트 중, 10성이하 1+1 이벤트를 활용 해 보세요.</p>
                                <% } else if (analysis.getArmorAvgStarforce() < 17) { %>
                                    <p>17성 달성을 목표로 하세요.</p>
                                    <p>썬데이 이벤트 중, 5, 10, 15성 강화시 100%성공 이벤트를 활용 해 보세요.</p>
                                    <p>여러 이벤트 보상중, 카르마 17성 스타포스 강화권을 노려보세요</p>
                                <% } else if (analysis.getArmorAvgStarforce() < 22) { %>
                                    <p>22성 도전을 시작해보세요.</p>
                                    <p>썬데이 이벤트 중, 샤이닝 스타포스 이벤트를 노려보세요!</p>
                                    <p>여기서 부터는 구매 하는것도 좋은 방법 입니다.</p>
                                <% } else { %>
                                    <p>충분한 스타포스입니다.</p>
                                <% } %>
                            </div>
                        </div>

                        <!-- 장신구 섹션 -->
                        <div class="accessory-section">
                            <h3>장신구 스타포스</h3>
                            <p class="avg-starforce">평균: <%= String.format("%.1f성", analysis.getAccessoryAvgStarforce()) %></p>
                            <div class="recommendation">
                                <% if (analysis.getAccessoryAvgStarforce() < 15) { %>
                                    <p>모든 장신구를 15성 이상으로 강화하세요.</p>
                                    <p>썬데이 이벤트 중, 10성이하 1+1 이벤트를 활용 해 보세요.</p>
                                <% } else if (analysis.getAccessoryAvgStarforce() < 17) { %>
                                    <p>17성 달성을 목표로 하세요.</p>
                                    <p>썬데이 이벤트 중, 5, 10, 15성 강화시 100%성공 이벤트를 활용 해 보세요.</p>
                                    <p>여러 이벤트 보상중, 카르마 17성 스타포스 강화권을 노려보세요</p>
                                <% } else if (analysis.getAccessoryAvgStarforce() < 22) { %>
                                    <p>22성 도전을 시작해보세요.</p>
                                    <p>썬데이 이벤트 중, 샤이닝 스타포스 이벤트를 노려보세요!</p>
                                    <p>여기서 부터는 구매 하는것도 좋은 방법 입니다.</p>
                                <% } else { %>
                                    <p>충분한 스타포스입니다.</p>
                                <% } %>
                            </div>
                        </div>
                    </div>
                <% } else { %>
                    <p class="no-equipment">장비 정보가 없습니다.</p>
                <% }
            } %>
        </div>
    </div>
</body>
</html>
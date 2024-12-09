package nexon_data;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/guide")
public class BossGuideController extends HttpServlet {
    private BossGuideService bossGuideService;
    private EquipmentService equipmentService;
    private MapleCharacterDAO characterDAO;
    
    @Override
    public void init() throws ServletException {
        bossGuideService = new BossGuideService();
        equipmentService = new EquipmentService();
        characterDAO = new MapleCharacterDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        MapleCharacter_DTO character = (MapleCharacter_DTO)session.getAttribute("character");
        
        System.out.println("BossGuideController - doGet called");
        System.out.println("Character from session: " + 
                (character != null ? character.getCharacterName() : "null"));
        System.out.println("BossGuideController - Character: " + 
                (character != null ? character.getCharacterName() : "null"));
        
        if(character != null) {
            // 보스 가이드 데이터 처리
            BossStageData bossData = bossGuideService.getBossStageData();
            int currentStage = bossGuideService.getCurrentStage(character.getTotalPower());
            
            // 캐릭터 이미지 처리
            String characterImage = (String)request.getAttribute("characterImage");
            if(characterImage == null) {
                characterImage = (String)session.getAttribute("characterImage");
            }
            
            // 장비 분석 데이터 처리
            Map<String, MapleCharacterDAO.EquipmentStats> equipments = 
                    characterDAO.getCharacterEquipments(character.getCharacterName());
                    
            // 장비 분석 결과 생성
            EquipmentService.EquipmentAnalysis equipmentAnalysis = 
                    equipmentService.analyzeEquipment(character.getCharacterName());
            
            // request에 데이터 설정
            request.setAttribute("bossData", bossData);
            request.setAttribute("currentStage", currentStage);
            request.setAttribute("characterImage", characterImage);
            request.setAttribute("equipments", equipments);
            request.setAttribute("equipmentAnalysis", equipmentAnalysis);
            
            // 디버깅 로그
            System.out.println("Current Stage: " + currentStage);
            System.out.println("Total Power: " + character.getTotalPower());
            System.out.println("Character Image: " + characterImage);
        }
        
        // JSP로 포워딩
        request.getRequestDispatcher("/guide2.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
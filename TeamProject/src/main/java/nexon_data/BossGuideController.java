package nexon_data;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/guide")
public class BossGuideController extends HttpServlet {
    private BossGuideService bossGuideService;
    
    @Override
    public void init() throws ServletException {
        bossGuideService = new BossGuideService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        MapleCharacter_DTO character = (MapleCharacter_DTO)session.getAttribute("character");
        
        System.out.println("BossGuideController - doGet called");
        System.out.println("Character from session: " + (character != null ? character.getCharacterName() : "null"));
        System.out.println("BossGuideController - Character: " + 
                (character != null ? character.getCharacterName() : "null"));
        
        if(character != null) {
            // BossStageData로 변경
            BossStageData bossData = bossGuideService.getBossStageData();
            int currentStage = bossGuideService.getCurrentStage(character.getTotalPower());
            
            String characterImage = (String)request.getAttribute("characterImage");
            if(characterImage == null) {
                characterImage = (String)session.getAttribute("characterImage");
            }
            
            request.setAttribute("bossData", bossData);
            request.setAttribute("currentStage", currentStage);
            request.setAttribute("characterImage", characterImage);
            
            System.out.println("Current Stage: " + currentStage);
            System.out.println("Total Power: " + character.getTotalPower());
            System.out.println("Character Image: " + characterImage);
        }
        
        request.getRequestDispatcher("/guide2.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
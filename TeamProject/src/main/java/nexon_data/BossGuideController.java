package nexon_data;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

import nexon_data.BossGuideService.BossStageData;

@WebServlet("/guide")
public class BossGuideController extends HttpServlet {
    private BossGuideService bossGuideService = new BossGuideService();
    
    @Override
    public void init() throws ServletException {
        bossGuideService = new BossGuideService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    	HttpSession session = request.getSession();
        MapleCharacter_DTO character = (MapleCharacter_DTO)request.getSession().getAttribute("character");
        String characterImage = (String)request.getAttribute("characterImage");
        
        System.out.println("Character Image URL: " + characterImage);
        
        if(character != null) {
            BossGuideService.BossStageData bossData = bossGuideService.getBossStageData();
            int currentStage = bossGuideService.getCurrentStage(character.getTotalPower());
            
            request.setAttribute("bossData", bossData);
            request.setAttribute("currentStage", currentStage);
            request.setAttribute("characterImage", characterImage);
            
            System.out.println("현재 단계: " + currentStage);
            System.out.println("전투력: " + character.getTotalPower());
        }
        
        request.getRequestDispatcher("/guide2.jsp").forward(request, response);
    }
}
// CharacterController.java
package nexon_data;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

@WebServlet("/character")
public class CharacterController extends HttpServlet {
   private static final String API_KEY = "test_43e98710a8effa6ca0f7323e240a0f3b61b6ea5a35ef83a972a59e22136864feefe8d04e6d233bd35cf2fabdeb93fb0d";
   private MapleCharacterDAO characterDAO;
   private String fullSavePath;

   public void init() throws ServletException {
       characterDAO = new MapleCharacterDAO();
       fullSavePath = "D:\\rrg0916\\dong\\backend\\seo\\TeamProject\\src\\main\\webapp\\character_data";
       System.out.println("서블릿 초기화 완료");
   }

   private String saveCharacterInfoToJson(String characterName, JSONObject characterInfo) {
       try {
           String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
           String fileName = String.format("%s_%s.json", characterName, timestamp);
           String filePath = Paths.get(fullSavePath, fileName).toString();
           
           try (FileWriter file = new FileWriter(filePath)) {
               file.write(characterInfo.toString(4));
               file.flush();
           }
           
           System.out.println("파일 저장 성공: " + filePath);
           return "character_data/" + fileName;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }

   private JSONObject fetchDataFromApi(String apiUrl) throws Exception {
       URL url = new URL(apiUrl);
       HttpURLConnection connection = (HttpURLConnection) url.openConnection();
       connection.setRequestMethod("GET");
       connection.setRequestProperty("x-nxopen-api-key", API_KEY);
       connection.setRequestProperty("accept", "application/json");

       if (connection.getResponseCode() == 200) {
           try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
               StringBuilder response = new StringBuilder();
               String line;
               while ((line = in.readLine()) != null) {
                   response.append(line);
               }
               return new JSONObject(response.toString());
           }
       }
       return null;
   }

   private JSONObject getCharacterInfo(String ocid) throws Exception {
       JSONObject basicInfo = fetchDataFromApi("https://open.api.nexon.com/maplestory/v1/character/basic?ocid=" + ocid);
       JSONObject statInfo = fetchDataFromApi("https://open.api.nexon.com/maplestory/v1/character/stat?ocid=" + ocid);
       
       JSONObject mergedData = new JSONObject();
       if (basicInfo != null) {
           mergedData.put("basic", basicInfo);
           if (statInfo != null && statInfo.has("final_stat")) {
               JSONArray finalStats = statInfo.getJSONArray("final_stat");
               for (int i = 0; i < finalStats.length(); i++) {
                   JSONObject stat = finalStats.getJSONObject(i);
                   if ("전투력".equals(stat.getString("stat_name"))) {
                       basicInfo.put("combat_power", stat.getInt("stat_value"));
                       break;
                   }
               }
           }
       }
       if (statInfo != null) {
           mergedData.put("stat", statInfo);
       }
       return mergedData;
   }

   private JSONObject getCharacterEquipment(String ocid) throws Exception {
       return fetchDataFromApi("https://open.api.nexon.com/maplestory/v1/character/item-equipment?ocid=" + ocid);
   }
   
   private String getCharacterOcid(String characterName) throws Exception {
       String encodedName = URLEncoder.encode(characterName, "UTF-8");
       JSONObject response = fetchDataFromApi("https://open.api.nexon.com/maplestory/v1/id?character_name=" + encodedName);
       return response != null && response.has("ocid") ? response.getString("ocid") : null;
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       doGet(request, response);
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       request.setCharacterEncoding("UTF-8");
       String characterName = request.getParameter("characterName");
       System.out.println("캐릭터 이름: " + characterName);

       try {
           String ocid = getCharacterOcid(characterName);
           System.out.println("OCID 조회 결과: " + ocid);
           
           if (ocid != null) {
               MapleCharacter_DTO character = new MapleCharacter_DTO();
               character.setOcid(ocid);
               character.setCharacterName(characterName);
               
               JSONObject characterInfo = getCharacterInfo(ocid);
               System.out.println("캐릭터 정보 API 응답: " + characterInfo);
               
               if (characterInfo != null && characterInfo.has("basic")) {
                   JSONObject basicInfo = characterInfo.getJSONObject("basic");
                   character.setCharacterLevel(basicInfo.getInt("character_level"));
                   character.setCharacterClass(basicInfo.getString("character_class"));
                   character.setTotalPower(basicInfo.optInt("combat_power", 0));
                   
                   String savedFilePath = saveCharacterInfoToJson(characterName, characterInfo);
                   request.setAttribute("downloadPath", savedFilePath);
               }

               System.out.println("DB 저장 시도: " + character.getCharacterName());
               characterDAO.saveCharacterInfo(character);

               JSONObject equipmentInfo = getCharacterEquipment(ocid);
               System.out.println("장비 정보 API 응답: " + equipmentInfo);
               
               List<CharacterEquipment_DTO> equipments = new ArrayList<>();
               if (equipmentInfo != null && equipmentInfo.has("item_equipment")) {
                   JSONArray items = equipmentInfo.getJSONArray("item_equipment");
                   for (int i = 0; i < items.length(); i++) {
                       JSONObject item = items.getJSONObject(i);
                       CharacterEquipment_DTO equipment = new CharacterEquipment_DTO();
                       equipment.setOcid(ocid);
                       equipment.setEquipmentType(item.getString("item_equipment_slot"));
                       equipment.setEquipmentName(item.getString("item_name"));
                       equipment.setEquipmentLevel(item.optInt("scroll_upgrade", 0));
                       equipment.setEquipmentStarForce(item.optInt("starforce", 0));
                       equipment.setPotentialGrade(item.optString("potential_option_grade", "없음"));
                       equipments.add(equipment);
                       
                       System.out.println("장비 저장 시도: " + equipment.getEquipmentName());
                       characterDAO.saveCharacterEquipment(equipment);
                   }
               }

               request.setAttribute("character", character);
               request.setAttribute("equipments", equipments);
               request.getRequestDispatcher("/char_info.jsp").forward(request, response);
           }
       } catch (Exception e) {
           System.err.println("에러 발생: " + e.getMessage());
           e.printStackTrace();
           request.setAttribute("error", e.getMessage());
           request.getRequestDispatcher("/char_info.jsp").forward(request, response);
       }
   }
}
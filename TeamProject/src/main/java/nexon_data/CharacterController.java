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


@WebServlet("/character")
public class CharacterController extends HttpServlet {
	
   // API 키 및 기본 설정
   private static final String API_KEY = "test_43e98710a8effa6ca0f7323e240a0f3b61b6ea5a35ef83a972a59e22136864feefe8d04e6d233bd35cf2fabdeb93fb0d";
   private MapleCharacterDAO characterDAO;
   private String fullSavePath;

   // 서블릿 초기화
   public void init() throws ServletException {
       characterDAO = new MapleCharacterDAO();
       fullSavePath = "D:\\rrg0916\\dong\\backend\\seo\\TeamProject\\src\\main\\webapp\\character_data";
       System.out.println("서블릿 초기화 완료");
   }

	/*
	 * // JSON 파일로 캐릭터 정보 저장 private String saveCharacterInfoToJson(String
	 * characterName, JSONObject characterInfo) { try { String timestamp =
	 * LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
	 * String fileName = String.format("%s_%s.json", characterName, timestamp);
	 * String filePath = Paths.get(fullSavePath, fileName).toString();
	 * 
	 * try (FileWriter file = new FileWriter(filePath)) {
	 * file.write(characterInfo.toString(4)); file.flush(); }
	 * 
	 * System.out.println("파일 저장 성공: " + filePath); return "character_data/" +
	 * fileName; } catch (Exception e) { e.printStackTrace(); return null; } }
	 */

   // Nexon API 호출 메서드
   private JSONObject fetchDataFromApi(String apiUrl) throws Exception {
       URL url = new URL(apiUrl);
       HttpURLConnection connection = (HttpURLConnection) url.openConnection();
       connection.setRequestMethod("GET");
       connection.setRequestProperty("x-nxopen-api-key", API_KEY);
       connection.setRequestProperty("accept", "application/json");

       System.out.println("API 요청: " + apiUrl);
       int responseCode = connection.getResponseCode();
       System.out.println("응답 코드: " + responseCode);

       if (responseCode == 200) {
           try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
               StringBuilder response = new StringBuilder();
               String line;
               while ((line = in.readLine()) != null) {
                   response.append(line);
               }
               String responseData = response.toString();
               System.out.println("API 응답: " + responseData);
               return new JSONObject(responseData);
           }
       } else {
           try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
               StringBuilder error = new StringBuilder();
               String line;
               while ((line = in.readLine()) != null) {
                   error.append(line);
               }
               System.out.println("API 에러: " + error.toString());
           }
       }
       return null;
   }

   // 캐릭터 기본 정보 조회
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
           
           // 캐릭터 이미지 추가
           if (basicInfo.has("character_image")) {
               String characterImage = basicInfo.getString("character_image");
               System.out.println("API에서 반환된 캐릭터 이미지: " + characterImage);
               mergedData.put("character_image", characterImage);
           } else {
               System.out.println("캐릭터 이미지가 없습니다.");
               mergedData.put("character_image", "default_image.png");
           }
       }
       if (statInfo != null) {
           mergedData.put("stat", statInfo);
       }
       return mergedData;
   }

   // 캐릭터 장비 정보 조회
   private JSONObject getCharacterEquipment(String ocid) throws Exception {
       return fetchDataFromApi("https://open.api.nexon.com/maplestory/v1/character/item-equipment?ocid=" + ocid);
   }
   
   // 캐릭터 OCID 조회
   private String getCharacterOcid(String characterName) throws Exception {
       String encodedName = URLEncoder.encode(characterName, "UTF-8");
       JSONObject response = fetchDataFromApi("https://open.api.nexon.com/maplestory/v1/id?character_name=" + encodedName);
       return response != null && response.has("ocid") ? response.getString("ocid") : null;
   }

   // POST 요청 처리
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       doGet(request, response);
   }

// GET 요청 처리 (메인 로직)
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String characterName = request.getParameter("characterName");
    System.out.println("캐릭터 이름: " + characterName);

    if (characterName == null || characterName.trim().isEmpty()) {
        request.setAttribute("error", "캐릭터 이름을 입력해주세요.");
        request.getRequestDispatcher("/char_info.jsp").forward(request, response);
        return;
    }

    try {
        // OCID 조회
        String ocid = getCharacterOcid(characterName);
        System.out.println("OCID 조회 결과: " + ocid);
        
        if (ocid != null) {
            // DTO 객체 생성 및 데이터 설정
            MapleCharacter_DTO character = new MapleCharacter_DTO();
            character.setOcid(ocid);
            character.setCharacterName(characterName);
            
            // 캐릭터 정보 조회 및 설정
            JSONObject characterInfo = getCharacterInfo(ocid);
            System.out.println("캐릭터 정보 API 응답: " + characterInfo);
            
            if (characterInfo != null && characterInfo.has("basic")) {
                JSONObject basicInfo = characterInfo.getJSONObject("basic");
                character.setCharacterLevel(basicInfo.getInt("character_level"));
                character.setCharacterClass(basicInfo.getString("character_class"));
                character.setTotalPower(basicInfo.optInt("combat_power", 0));
                
                if (characterInfo.has("character_image")) {
                    request.setAttribute("characterImage", characterInfo.getString("character_image"));
                } else {
                    request.setAttribute("characterImage", "default_image.png");
                }
            }

            // DB에 캐릭터 정보 저장
            System.out.println("DB 저장 시도: " + character.getCharacterName());
            characterDAO.saveCharacterInfo(character);

            // 장비 정보 조회 및 처리
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

            // JSP로 데이터 전달 (request와 session 모두 사용)
            request.setAttribute("character", character);
            request.setAttribute("equipments", equipments);
            request.setAttribute("characterInfo", characterInfo);
            request.setAttribute("characterEquipment", equipmentInfo);
            
            HttpSession session = request.getSession();
            session.setAttribute("character", character);
            session.setAttribute("equipments", equipments);

            // 결과 페이지로 포워딩
            request.getRequestDispatcher("/char_info.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "캐릭터를 찾을 수 없습니다.");
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
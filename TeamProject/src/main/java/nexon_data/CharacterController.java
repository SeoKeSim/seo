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
   
   //장비 분류 db저장 기능
   private void processEquipmentByType(String ocid, JSONObject item) {
	    String equipmentSlot = item.getString("item_equipment_slot");
	    
	    // 기본 데이터 설정
	    BaseEquipmentDTO equipment = null;
	    
	    // 장비 종류별 분류
	    if (isAccessory(equipmentSlot)) {
	        equipment = new Accessory_DTO();
	    } else if (isArmor(equipmentSlot)) {
	        equipment = new Armor_DTO();
	    } else if (isWeaponOrEmblem(equipmentSlot)) {
	        equipment = new WeaponEmblem_DTO();
	    }

	    if (equipment != null) {
	        equipment.setOcid(ocid);
	        equipment.setEquipmentType(equipmentSlot);
	        equipment.setEquipmentName(item.getString("item_name"));
	        equipment.setEquipmentLevel(item.optInt("scroll_upgrade", 0));
	        equipment.setEquipmentStarForce(item.optInt("starforce", 0));
	        equipment.setPotentialGrade(item.optString("potential_option_grade", "없음"));
	        
	        // 종류별 저장
	        saveEquipmentToDatabase(equipment);
	    }
	}

	private boolean isAccessory(String slot) {
	    return slot.contains("반지") || slot.contains("펜던트") || slot.contains("뱃지") || 
	           slot.contains("귀고리") || slot.contains("훈장");
	}

	private boolean isArmor(String slot) {
	    return slot.contains("모자") || slot.contains("상의") || slot.contains("하의") || 
	           slot.contains("신발") || slot.contains("장갑") || slot.contains("망토");
	}

	private boolean isWeaponOrEmblem(String slot) {
	    return slot.contains("무기") || slot.contains("보조무기") || slot.contains("엠블렘");
	}

	private void saveEquipmentToDatabase(BaseEquipmentDTO equipment) {
	    if (equipment instanceof Accessory_DTO) {
	        characterDAO.saveAccessory((Accessory_DTO) equipment);
	    } else if (equipment instanceof Armor_DTO) {
	        characterDAO.saveArmor((Armor_DTO) equipment);
	    } else if (equipment instanceof WeaponEmblem_DTO) {
	        characterDAO.saveWeaponEmblem((WeaponEmblem_DTO) equipment);
	    }
	}
   
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

   //메인 기능
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      String characterName = request.getParameter("characterName");
      
      if (characterName == null || characterName.trim().isEmpty()) {
          request.setAttribute("error", "캐릭터 이름을 입력해주세요.");
          request.getRequestDispatcher("/char_info.jsp").forward(request, response);
          return;
      }

      try {
          String ocid = getCharacterOcid(characterName);
          
          if (ocid != null) {
              MapleCharacter_DTO character = new MapleCharacter_DTO();
              character.setOcid(ocid);
              character.setCharacterName(characterName);
              
              JSONObject characterInfo = getCharacterInfo(ocid);
              
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

              characterDAO.saveCharacterInfo(character);

              JSONObject equipmentInfo = getCharacterEquipment(ocid);
              if (equipmentInfo != null && equipmentInfo.has("item_equipment")) {
                  JSONArray items = equipmentInfo.getJSONArray("item_equipment");
                  for (int i = 0; i < items.length(); i++) {
                      JSONObject item = items.getJSONObject(i);
                      String slot = item.getString("item_equipment_slot");
                      
                      BaseEquipmentDTO equipment = null;
                      String tableName = "";
                      
                      if (checkAccessoryType(slot)) {
                    	    equipment = new Accessory_DTO();
                    	    tableName = "accessory";
                    	} else if (checkArmorType(slot)) {
                    	    equipment = new Armor_DTO();
                    	    tableName = "armor";
                    	} else if (checkWeaponType(slot)) {
                    	    equipment = new WeaponEmblem_DTO();
                    	    tableName = "weapon_emblem";
                    	}

                      if (equipment != null) {
                          equipment.setOcid(ocid);
                          equipment.setEquipmentType(slot);
                          equipment.setEquipmentName(item.getString("item_name"));
                          equipment.setEquipmentLevel(item.optInt("scroll_upgrade", 0));
                          equipment.setEquipmentStarForce(item.optInt("starforce", 0));
                          equipment.setPotentialGrade(item.optString("potential_option_grade", "없음"));
                          
                          characterDAO.saveEquipment(tableName, equipment);
                      }
                  }
              }

              request.setAttribute("characterInfo", characterInfo);
              request.setAttribute("characterEquipment", equipmentInfo);
              
              HttpSession session = request.getSession();
              session.setAttribute("character", character);

              request.getRequestDispatcher("/char_info.jsp").forward(request, response);
          } else {
              request.setAttribute("error", "캐릭터를 찾을 수 없습니다.");
              request.getRequestDispatcher("/char_info.jsp").forward(request, response);
          }
      } catch (Exception e) {
          request.setAttribute("error", e.getMessage());
          request.getRequestDispatcher("/char_info.jsp").forward(request, response);
      }
   }

   private boolean checkAccessoryType(String slot) {
	    return slot.contains("반지") || slot.contains("펜던트") || slot.contains("뱃지") || 
	           slot.contains("귀고리") || slot.contains("훈장");
	}

	private boolean checkArmorType(String slot) {
	    return slot.contains("모자") || slot.contains("상의") || slot.contains("하의") || 
	           slot.contains("신발") || slot.contains("장갑") || slot.contains("망토") ||
	           slot.contains("어깨장식");
	}

	private boolean checkWeaponType(String slot) {
	    return slot.contains("무기") || slot.contains("보조무기") || slot.contains("엠블렘");
	}
}
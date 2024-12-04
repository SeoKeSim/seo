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
	    System.out.println("API 호출 시작: " + apiUrl);
	    
	    URL url = new URL(apiUrl);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("x-nxopen-api-key", API_KEY);
	    connection.setRequestProperty("accept", "application/json");

	    int responseCode = connection.getResponseCode();
	    System.out.println("API 응답 코드: " + responseCode);

	    StringBuilder response = new StringBuilder();
	    
	    // 성공적인 응답 처리 (200)
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        try (BufferedReader in = new BufferedReader(
	                new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
	            String line;
	            while ((line = in.readLine()) != null) {
	                response.append(line);
	            }
	            String responseData = response.toString();
	            System.out.println("API 응답 데이터: " + responseData);
	            
	            if (responseData.trim().isEmpty()) {
	                System.err.println("빈 응답 데이터 수신");
	                return null;
	            }
	            
	            try {
	                return new JSONObject(responseData);
	            } catch (JSONException e) {
	                System.err.println("JSON 파싱 오류: " + e.getMessage());
	                throw new RuntimeException("API 응답을 JSON으로 파싱할 수 없습니다", e);
	            }
	        }
	    } 
	    // 에러 응답 처리
	    else {
	        try (BufferedReader in = new BufferedReader(
	                new InputStreamReader(connection.getErrorStream(), "UTF-8"))) {
	            String line;
	            while ((line = in.readLine()) != null) {
	                response.append(line);
	            }
	            String errorMsg = response.toString();
	            System.err.println("API 에러 응답: " + errorMsg);
	            throw new RuntimeException("API 호출 실패 - HTTP 에러 코드: " + responseCode + ", 메시지: " + errorMsg);
	        }
	    }
	}


	// 캐릭터 기본 정보 조회
	private JSONObject getCharacterInfo(String ocid) throws Exception {
	    try {
	        System.out.println("캐릭터 기본 정보 API 호출 시작 - OCID: " + ocid);
	        
	        JSONObject mergedData = new JSONObject();
	        
	        // 기본 정보 API 호출
	        JSONObject basicInfo = fetchDataFromApi("https://open.api.nexon.com/maplestory/v1/character/basic?ocid=" + ocid);
	        if (basicInfo != null) {
	            System.out.println("기본 정보 처리 시작");
	            JSONObject basicData = new JSONObject();
	            basicData.put("character_level", basicInfo.optInt("character_level", 0));
	            basicData.put("character_class", basicInfo.optString("character_class", ""));
	            basicData.put("character_name", basicInfo.optString("character_name", ""));
	            basicData.put("world_name", basicInfo.optString("world_name", ""));
	            mergedData.put("basic", basicData);
	            
	            if (basicInfo.has("character_image")) {
	                String characterImage = basicInfo.getString("character_image");
	                System.out.println("캐릭터 이미지 URL: " + characterImage);
	                mergedData.put("character_image", characterImage);
	            }
	        }
	        
	        // 스탯 정보 API 호출
	        JSONObject statInfo = fetchDataFromApi("https://open.api.nexon.com/maplestory/v1/character/stat?ocid=" + ocid);
	        if (statInfo != null && statInfo.has("final_stat")) {
	            System.out.println("스탯 정보 처리 시작");
	            JSONArray finalStats = statInfo.getJSONArray("final_stat");
	            for (int i = 0; i < finalStats.length(); i++) {
	                JSONObject stat = finalStats.getJSONObject(i);
	                if ("전투력".equals(stat.optString("stat_name"))) {
	                    mergedData.getJSONObject("basic").put("combat_power", stat.optInt("stat_value", 0));
	                    System.out.println("전투력 추출 완료: " + stat.optInt("stat_value", 0));
	                    break;
	                }
	            }
	            mergedData.put("stat", statInfo);
	        }
	        
	        System.out.println("최종 병합된 데이터 크기: " + mergedData.toString().length());
	        return mergedData;
	        
	    } catch (Exception e) {
	        System.err.println("캐릭터 정보 조회 중 오류 발생: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("캐릭터 정보 조회 실패", e);
	    }
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

   
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
       // 요청 인코딩 설정
       request.setCharacterEncoding("UTF-8");
       String characterName = request.getParameter("characterName");
       
       // 캐릭터 이름 유효성 검사
       if (characterName == null || characterName.trim().isEmpty()) {
           request.setAttribute("error", "캐릭터 이름을 입력해주세요.");
           request.getRequestDispatcher("/char_info.jsp").forward(request, response);
           return;
       }

       try {
           // 1. OCID 조회
           System.out.println("캐릭터 OCID 조회 시도: " + characterName);
           String ocid = getCharacterOcid(characterName);
           
           if (ocid != null) {
               // 2. 캐릭터 기본 정보 설정
               System.out.println("OCID 조회 성공: " + ocid);
               MapleCharacter_DTO character = new MapleCharacter_DTO();
               character.setOcid(ocid);
               character.setCharacterName(characterName);
               
               // 3. 캐릭터 상세 정보 조회
               System.out.println("캐릭터 정보 조회 시도: " + ocid);
               JSONObject characterInfo = getCharacterInfo(ocid);
               
               // 4. 기본 정보 처리
               if (characterInfo != null && characterInfo.has("basic")) {
                   try {
                       JSONObject basicInfo = characterInfo.getJSONObject("basic");
                       character.setCharacterLevel(basicInfo.getInt("character_level"));
                       character.setCharacterClass(basicInfo.getString("character_class"));
                       character.setTotalPower(basicInfo.optInt("combat_power", 0));
                       
                       // 디버깅 로그
                       System.out.println("저장할 캐릭터 정보:");
                       System.out.println("OCID: " + character.getOcid());
                       System.out.println("이름: " + character.getCharacterName());
                       System.out.println("레벨: " + character.getCharacterLevel());
                       System.out.println("직업: " + character.getCharacterClass());
                       System.out.println("전투력: " + character.getTotalPower());
                   } catch (Exception e) {
                       System.err.println("캐릭터 기본 정보 처리 중 오류: " + e.getMessage());
                       e.printStackTrace();
                       throw e;
                   }
               }

               // 5. DB에 캐릭터 정보 저장
               try {
                   System.out.println("캐릭터 정보 DB 저장 시도");
                   characterDAO.saveCharacterInfo(character);
                   System.out.println("캐릭터 정보 DB 저장 성공");
               } catch (Exception e) {
                   System.err.println("캐릭터 정보 DB 저장 실패: " + e.getMessage());
                   e.printStackTrace();
                   throw e;
               }

               // 6. 장비 정보 처리
               System.out.println("장비 정보 조회 시도");
               JSONObject equipmentInfo = getCharacterEquipment(ocid);
               if (equipmentInfo != null && equipmentInfo.has("item_equipment")) {
                   processEquipmentInfo(equipmentInfo, ocid, characterName);
               }

               // 7. 세션 및 request에 데이터 설정
               HttpSession session = request.getSession();
               session.setAttribute("character", character);
               
               // 8. 캐릭터 이미지 처리
               if (characterInfo != null && characterInfo.has("character_image")) {
                   String characterImage = characterInfo.getString("character_image");
                   session.setAttribute("characterImage", characterImage);
                   request.setAttribute("characterImage", characterImage);
               } else {
                   session.setAttribute("characterImage", "default_image.png");
                   request.setAttribute("characterImage", "default_image.png");
               }

               // 9. request에 캐릭터 정보 설정
               request.setAttribute("characterInfo", characterInfo);
               request.setAttribute("characterEquipment", equipmentInfo);

               // 10. JSP로 포워딩
               request.getRequestDispatcher("/char_info.jsp").forward(request, response);
           } else {
               // 캐릭터를 찾지 못한 경우
               System.out.println("캐릭터를 찾을 수 없음: " + characterName);
               request.setAttribute("error", "캐릭터를 찾을 수 없습니다.");
               request.getRequestDispatcher("/char_info.jsp").forward(request, response);
           }
       } catch (Exception e) {
           // 오류 처리
           System.err.println("전체 처리 중 오류 발생: " + e.getMessage());
           e.printStackTrace();
           request.setAttribute("error", e.getMessage());
           request.getRequestDispatcher("/char_info.jsp").forward(request, response);
       }
   }

   // 장비 정보 처리를 위한 별도 메서드
   private void processEquipmentInfo(JSONObject equipmentInfo, String ocid, String characterName) {
       JSONArray items = equipmentInfo.getJSONArray("item_equipment");
       System.out.println("조회된 장비 개수: " + items.length());
       
       for (int i = 0; i < items.length(); i++) {
           try {
               JSONObject item = items.getJSONObject(i);
               String slot = item.getString("item_equipment_slot");
               System.out.println("장비 처리 중: " + slot);
               
               BaseEquipmentDTO equipment = null;
               String tableName = "";
               
               // 장비 타입 결정
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

               // 장비 정보 저장
               if (equipment != null) {
                   setEquipmentInfo(equipment, item, ocid, characterName);
                   characterDAO.saveEquipment(tableName, equipment);
               }
           } catch (Exception e) {
               System.err.println("장비 처리 중 오류: " + e.getMessage());
               e.printStackTrace();
           }
       }
   }
   
   private void setEquipmentInfo(BaseEquipmentDTO equipment, JSONObject item, String ocid, String characterName) {
	    equipment.setOcid(ocid);
	    equipment.setEquipmentType(item.getString("item_equipment_slot"));
	    equipment.setEquipmentName(item.getString("item_name"));
	    equipment.setEquipmentLevel(item.optInt("scroll_upgrade", 0));
	    equipment.setEquipmentStarForce(item.optInt("starforce", 0));
	    equipment.setPotentialGrade(item.optString("potential_option_grade", "없음"));
	    equipment.setNickname(characterName);
	    
	    // 디버깅을 위한 로그 추가
	    System.out.println("장비 저장 시도: " + equipment.getEquipmentName() + ", 캐릭터 닉네임: " + equipment.getNickname());
	}
   
   //장비 분류
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
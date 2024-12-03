package nexonapitest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/CharacterSearchServlet")
public class CharacterSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "test_43e98710a8effa6ca0f7323e240a0f3b61b6ea5a35ef83a972a59e22136864feefe8d04e6d233bd35cf2fabdeb93fb0d";
	/*
	 * private static final String PROJECT_PATH =
	 * "D:\\rrg0916\\dong\\backend\\seo\\TeamProject"; private static final String
	 * SAVE_DIRECTORY = "src\\main\\webapp\\character_data";
	 */
    String fullSavePath = "D:\\rrg0916\\dong\\backend\\seo\\TeamProject\\src\\main\\webapp\\character_data";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.sendRedirect("char_info.jsp");
    }

    private String saveCharacterInfoToJson(String characterName, JSONObject characterInfo) {
        try {
            // 웹 애플리케이션의 실제 경로 사용
            String fullSavePath = getServletContext().getRealPath("/character_data");
            
            // 디렉토리가 없으면 생성
            java.io.File directory = new java.io.File(fullSavePath);
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs();
                if (!dirCreated) {
                    System.out.println("디렉토리 생성 실패: " + fullSavePath);
                    return null;
                }
            }
            
            // 파일명 생성
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("%s_%s.json", characterName, timestamp);
            String filePath = Paths.get(fullSavePath, fileName).toString();
            
            // JSON 파일 저장
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(characterInfo.toString(4)); // JSON 내용을 들여쓰기하여 저장
                file.flush();
            }
            
            System.out.println("파일 저장 성공: " + filePath);
            return "character_data/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("예외 발생: " + e.getMessage());
            return null;
        }
    }

    private String saveCharacterInfoToJson(String characterName, JSONObject characterInfo, ServletContext context) {
        try {
            // 프로젝트의 실제 경로 설정
            String projectPath = "D:\\rrg0916\\dong\\backend\\seo\\TeamProject\\src\\main\\webapp\\character_data";
            
            // 파일명 생성
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("%s_%s.json", characterName, timestamp);
            String filePath = Paths.get(projectPath, fileName).toString();

            // JSON 파일 저장
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(characterInfo.toString(4));
                file.flush();
            }

            System.out.println("파일 저장 성공: " + filePath);
            return "character_data/" + fileName;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("예외 발생: " + e.getMessage());
            return null;
        }
    }

    private JSONObject fetchDataFromApi(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("x-nxopen-api-key", API_KEY);
        connection.setRequestProperty("accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            return new JSONObject(response.toString());
        } else {
            System.out.println("API 요청 실패: " + apiUrl + " (응답 코드: " + responseCode + ")");
            return null;
        }
    }

    public JSONObject getCharacterInfo(String ocid) throws Exception {
        String apiUrlBasic = "https://open.api.nexon.com/maplestory/v1/character/basic?ocid=" + ocid;
        String apiUrlStat = "https://open.api.nexon.com/maplestory/v1/character/stat?ocid=" + ocid;

        JSONObject basicInfo = fetchDataFromApi(apiUrlBasic);
        JSONObject statInfo = fetchDataFromApi(apiUrlStat);

        JSONObject mergedData = new JSONObject();
        if (basicInfo != null) {
            mergedData.put("basic", basicInfo);
            
            // 전투력 추가
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

    public JSONObject getCharacterEquipment(String ocid) throws Exception {
        String apiUrlEquipment = "https://open.api.nexon.com/maplestory/v1/character/item-equipment?ocid=" + ocid;
        return fetchDataFromApi(apiUrlEquipment);
    }
    
    public String getCharacterOcid(String characterName) throws Exception {
        String encodedName = URLEncoder.encode(characterName, "UTF-8");
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/id?character_name=" + encodedName;
        
        JSONObject response = fetchDataFromApi(apiUrl);
        if (response != null && response.has("ocid")) {
            return response.getString("ocid");
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String characterName = request.getParameter("characterName");

        if (characterName != null && !characterName.isEmpty()) {
            try {
                String ocid = getCharacterOcid(characterName);
                if (ocid != null) {
                    JSONObject characterInfo = getCharacterInfo(ocid);
                    JSONObject characterEquipment = getCharacterEquipment(ocid);

                    if (characterInfo != null) {
                        String savedFilePath = saveCharacterInfoToJson(characterName, characterInfo, getServletContext());

                        request.setAttribute("characterInfo", characterInfo);
                        request.setAttribute("characterEquipment", characterEquipment);
                        request.setAttribute("downloadPath", savedFilePath);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
            }
        } else {
            request.setAttribute("error", "캐릭터 이름을 입력하세요.");
        }

        request.getRequestDispatcher("/char_info.jsp").forward(request, response);
    }
}

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

import org.json.JSONObject;

@WebServlet("/CharacterSearchServlet")
public class CharacterSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "test_43e98710a8effa6ca0f7323e240a0f3b61b6ea5a35ef83a972a59e22136864feefe8d04e6d233bd35cf2fabdeb93fb0d";
    private static final String PROJECT_PATH = "D:\\rrg0916\\dong\\backend\\seo\\TeamProject";
    private static final String SAVE_DIRECTORY = "src\\main\\webapp\\character_data";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.sendRedirect("char_info.jsp");
    }

    private String saveCharacterInfoToJson(String characterName, JSONObject characterInfo, ServletContext context) {
        try {
            String fullPath = Paths.get(PROJECT_PATH, SAVE_DIRECTORY).toString();

            File directory = new File(fullPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("%s_%s.json", characterName, timestamp);
            String filePath = Paths.get(fullPath, fileName).toString();

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

    private String getCharacterOcid(String characterName) throws Exception {
        String encodedCharacterName = URLEncoder.encode(characterName, "UTF-8").replace("+", "%20");
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/id?character_name=" + encodedCharacterName;

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

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getString("ocid");
        }
        return null;
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

    private JSONObject getCharacterInfo(String ocid) throws Exception {
        String apiUrlBasic = "https://open.api.nexon.com/maplestory/v1/character/basic?ocid=" + ocid;
        String apiUrlStat = "https://open.api.nexon.com/maplestory/v1/character/stat?ocid=" + ocid;

        JSONObject basicInfo = fetchDataFromApi(apiUrlBasic);
        JSONObject statInfo = fetchDataFromApi(apiUrlStat);

        JSONObject mergedData = new JSONObject();
        if (basicInfo != null) {
            mergedData.put("basic", basicInfo);
        }
        if (statInfo != null) {
            mergedData.put("stat", statInfo);
        }

        return mergedData;
    }

    private JSONObject getCharacterEquipment(String ocid) throws Exception {
        String apiUrlEquipment = "https://open.api.nexon.com/maplestory/v1/character/cashitem-equipment?ocid=" + ocid;
        return fetchDataFromApi(apiUrlEquipment);
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

package nexonapitest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>메이플스토리 캐릭터 검색</title>");
        response.getWriter().println("<style>");
        response.getWriter().println(".character-info { margin: 20px; padding: 20px; border: 1px solid #ddd; }");
        response.getWriter().println(".character-image { margin: 20px 0; }");
        response.getWriter().println("</style>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h2>메이플스토리 캐릭터 검색</h2>");
        response.getWriter().println("<form action='CharacterSearchServlet' method='post'>");
        response.getWriter().println("캐릭터 이름: <input type='text' name='characterName'>");
        response.getWriter().println("<input type='submit' value='검색'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
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

    private JSONObject getCharacterInfo(String ocid) throws Exception {
        String apiUrl = "https://open.api.nexon.com/maplestory/v1/character/basic?ocid=" + ocid;
        
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
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String characterName = request.getParameter("characterName");

        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>검색 결과</title>");
        response.getWriter().println("<style>");
        response.getWriter().println(".character-info { margin: 20px; padding: 20px; border: 1px solid #ddd; }");
        response.getWriter().println(".character-image { margin: 20px 0; }");
        response.getWriter().println("</style>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        if (characterName != null && !characterName.isEmpty()) {
            try {
                String ocid = getCharacterOcid(characterName);
                if (ocid != null) {
                    JSONObject characterInfo = getCharacterInfo(ocid);
                    if (characterInfo != null) {
                        // 응답 데이터 출력
                        response.getWriter().println("<div class='character-info'>");
                        response.getWriter().println("<h2>캐릭터 정보</h2>");
                        response.getWriter().println("<pre>" + characterInfo.toString(4) + "</pre>"); // JSON 포맷팅
                        
                        // 이미지 URL 추출 및 표시
                        if (characterInfo.has("character_image")) {
                            String imageUrl = characterInfo.getString("character_image");
                            response.getWriter().println("<div class='character-image'>");
                            response.getWriter().println("<h3>캐릭터 이미지</h3>");
                            response.getWriter().println("<img src='" + imageUrl + "' alt='캐릭터 이미지'>");
                            response.getWriter().println("</div>");
                        }
                        
                        response.getWriter().println("</div>");
                    }
                }
            } catch (Exception e) {
                response.getWriter().println("<h2>예외 발생</h2>");
                response.getWriter().println("<pre>" + e.getMessage() + "</pre>");
                e.printStackTrace();
            }
        } else {
            response.getWriter().println("<h2>캐릭터 이름을 입력하세요.</h2>");
        }

        // 검색 폼 다시 표시
        response.getWriter().println("<hr>");
        response.getWriter().println("<form action='CharacterSearchServlet' method='post'>");
        response.getWriter().println("캐릭터 이름: <input type='text' name='characterName' value='" + 
                (characterName != null ? characterName.replace("'", "&#39;") : "") + "'>");
        response.getWriter().println("<input type='submit' value='검색'>");
        response.getWriter().println("</form>");
        
        response.getWriter().println("</body></html>");
    }
}
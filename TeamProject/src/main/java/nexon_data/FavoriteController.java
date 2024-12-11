package nexon_data;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/favorite")
public class FavoriteController extends HttpServlet {
    private FavoritesDAO favoritesDAO;
    
    @Override
    public void init() throws ServletException {
        favoritesDAO = new FavoritesDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("idKey");
        
        // 로그인 체크
        if (userId == null) {
            System.out.println("로그인되지 않은 사용자의 접근");
            response.sendRedirect("login.jsp");
            return;
        }
        
        // 파라미터 받기
        String action = request.getParameter("action");
        String ocid = request.getParameter("ocid");
        String characterName = request.getParameter("characterName");
        String characterImage = request.getParameter("characterImage"); // 이미지 URL 파라미터 추가
        
        // 파라미터 유효성 검사
        if (action == null || ocid == null || characterName == null) {
            System.out.println("필수 파라미터 누락");
            System.out.println("action: " + action);
            System.out.println("ocid: " + ocid);
            System.out.println("characterName: " + characterName);
            System.out.println("characterImage: " + characterImage);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수 파라미터가 누락되었습니다.");
            return;
        }
        
        try {
            if ("toggle".equals(action)) {
                boolean isFavorite = favoritesDAO.isFavorite(userId, ocid);
                System.out.println("현재 즐겨찾기 상태: " + isFavorite);
                
                if (isFavorite) {
                    System.out.println("즐겨찾기 해제 시도 - userId: " + userId + ", ocid: " + ocid);
                    boolean removed = favoritesDAO.removeFavorite(userId, ocid);
                    System.out.println("즐겨찾기 해제 결과: " + removed);
                } else {
                    System.out.println("즐겨찾기 추가 시도 - userId: " + userId + ", ocid: " + ocid);
                    FavoritesDTO favorite = new FavoritesDTO();
                    favorite.setUserId(userId);
                    favorite.setOcid(ocid);
                    favorite.setCharacterName(characterName);
                    favorite.setCharacterImage(characterImage); // 이미지 URL 설정
                    
                 // 디버깅을 위한 로그 추가
                    System.out.println("=== 즐겨찾기 추가 시도 정보 ===");
                    System.out.println("userId: " + userId);
                    System.out.println("ocid: " + ocid);
                    System.out.println("characterName: " + characterName);
                    System.out.println("characterImage: " + characterImage);

                    
                    boolean added = favoritesDAO.addFavorite(favorite);
                    System.out.println("즐겨찾기 추가 결과: " + added);
                }
                
                // favorites.jsp에서 온 요청인 경우 favorites.jsp로 리다이렉트
                String referer = request.getHeader("Referer");
                if (referer != null && referer.contains("favorites.jsp")) {
                    response.sendRedirect(request.getContextPath() + "/favorites.jsp");
                } else {
                    // 캐릭터 정보 페이지로 리다이렉트
                    String encodedName = URLEncoder.encode(characterName, "UTF-8");
                    System.out.println("리다이렉트 URL: " + request.getContextPath() + "/character?characterName=" + encodedName);
                    response.sendRedirect(request.getContextPath() + "/character?characterName=" + encodedName);
                }
                
            } else {
                System.out.println("지원하지 않는 action 값: " + action);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원하지 않는 action입니다.");
            }
            
        } catch (SQLIntegrityConstraintViolationException e) {
            // 중복 키 예외 처리
            System.out.println("이미 즐겨찾기에 추가된 캐릭터입니다.");
            response.sendRedirect(request.getContextPath() + "/character?characterName=" + URLEncoder.encode(characterName, "UTF-8"));
        } catch (SQLException e) {
        	System.err.println("데이터베이스 작업 중 오류 발생: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다. 오류 내용: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("예상치 못한 오류 발생: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // GET 요청은 POST로 처리
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported");
    }
}
package team.beans;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


@WebServlet("/user")
public class UserCheck extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 데이터베이스 연결 변수
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 1. 데이터베이스 연결
            conn = JDBCUtil.getConnection();

            // 2. SQL 쿼리 작성 (사용자 정보 조회)
            String sql = "SELECT id, name, email FROM users WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            
            // 3. 쿼리 매개변수 설정
            String userId = request.getParameter("id");  // URL 파라미터에서 'id' 값 가져오기
            stmt.setString(1, userId);  // 쿼리의 첫 번째 ?에 값 설정

            // 4. 쿼리 실행
            rs = stmt.executeQuery();

            // 5. 결과 처리
            if (rs.next()) {
                // 사용자 정보가 있으면 DTO 객체에 세팅
                UserDTO user = new UserDTO();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));

                // DTO 객체를 request 속성에 담아서 JSP로 전달
                request.setAttribute("user", user);

                // 6. JSP 페이지로 포워딩
                RequestDispatcher dispatcher = request.getRequestDispatcher("/userInfo.jsp");
                dispatcher.forward(request, response);
            } else {
                // 사용자 정보가 없으면 메시지 처리
                response.getWriter().println("사용자를 찾을 수 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("데이터베이스 오류 발생.");
        } finally {
            // 7. 자원 반납 (JDBCUtil을 통해 연결 종료)
            JDBCUtil.close(rs, stmt, conn);
        }
    }
}
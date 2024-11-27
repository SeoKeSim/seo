package team.beans;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminUserController")
public class AdminUserController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateUser(request, response);
        } else if ("delete".equals(action)) {
            deleteUser(request, response); // SQLException 처리
        } else {
            response.sendRedirect("user-management.jsp");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDTO user = new UserDTO();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        UserDAO uDAO = new UserDAO();
        boolean isUpdated = false; // 초기값 설정

        try {
            isUpdated = uDAO.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "데이터베이스 오류가 발생했습니다. 다시 시도해주세요.");
            request.getRequestDispatcher("user-management.jsp").forward(request, response);
            return; // 오류 발생 시 이후 코드 실행 방지
        }

        if (isUpdated) {
            request.setAttribute("successMessage", "사용자 정보가 성공적으로 수정되었습니다.");
        } else {
            request.setAttribute("errorMessage", "사용자 정보 수정에 실패했습니다.");
        }

        request.getRequestDispatcher("user-management.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        UserDAO uDAO = new UserDAO();
        boolean isDeleted = false; // 초기값 설정

        try {
            isDeleted = uDAO.deleteUser(id); // SQLException 처리
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "데이터베이스 오류가 발생했습니다. 다시 시도해주세요.");
            request.getRequestDispatcher("user-management.jsp").forward(request, response);
            return; // 오류 발생 시 이후 코드 실행 방지
        }

        if (isDeleted) {
            request.setAttribute("successMessage", "사용자가 성공적으로 삭제되었습니다.");
        } else {
            request.setAttribute("errorMessage", "사용자 삭제에 실패했습니다.");
        }

        request.getRequestDispatcher("user-management.jsp").forward(request, response);
    }
}
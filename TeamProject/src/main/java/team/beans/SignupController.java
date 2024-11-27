package team.beans;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/signup")
public class SignupController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//인코딩 에러 방지
    	request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
    	
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");

        // 입력 데이터 유지용
        request.setAttribute("name", name);
        request.setAttribute("id", id);
        request.setAttribute("email", email);

        // 비밀번호 확인
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        UserDTO user = new UserDTO();
        user.setName(name);
        user.setId(id);
        user.setPassword(password);
        user.setEmail(email);

        UserDAO uDAO = new UserDAO();
        try {
            if (uDAO.idCheck(id)) {
                request.setAttribute("error", "이미 사용 중인 아이디입니다.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
                dispatcher.forward(request, response);
                return;
            }

            boolean isInserted = uDAO.insertUser(user);
            if (isInserted) {
            	request.setAttribute("successMessage", "가입 성공!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }
    }
}
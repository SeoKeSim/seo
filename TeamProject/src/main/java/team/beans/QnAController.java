package team.beans;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QnAController
 */
@WebServlet("/QnA.do")
public class QnAController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String userId = (String) request.getSession().getAttribute("idKey"); // 로그인된 사용자 ID
        String title = request.getParameter("title");
        String question = request.getParameter("question");

        QnADTO qna = new QnADTO();
        qna.setUserId(userId);
        qna.setTitle(title);
        qna.setQuestion(question);

        QnADAO qnaDAO = new QnADAO();
        boolean isInserted = qnaDAO.insertQnA(qna);

        if (isInserted) {
            // 성공: 결과를 JSP에 전달하고 뷰에서 처리
            request.setAttribute("message", "문의가 성공적으로 보내졌습니다!");
            request.setAttribute("redirectUrl", "mypage.jsp"); // 리다이렉트할 URL
            RequestDispatcher dispatcher = request.getRequestDispatcher("QnAresult.jsp");
            dispatcher.forward(request, response);
        } else {
            // 실패: 결과를 JSP에 전달하고 뷰에서 처리
            request.setAttribute("message", "문의 등록에 실패했습니다. 다시 시도해주세요.");
            request.setAttribute("redirectUrl", "QnA.jsp"); // 리다이렉트할 URL
            RequestDispatcher dispatcher = request.getRequestDispatcher("QnAresult.jsp");
            dispatcher.forward(request, response);
        }
    }
}

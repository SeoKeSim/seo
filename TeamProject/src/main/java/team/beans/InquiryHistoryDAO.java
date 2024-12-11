package team.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryHistoryDAO {

    // 특정 사용자의 문의 내역을 조회하는 메서드
    public List<QnADTO> getInquiryHistory(String userId) { 
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<QnADTO> inquiryList = new ArrayList<>();

        try {
        	conn = JDBCUtil.getConnection();
        	String sql = "SELECT unique_id, user_id, title, question, answer FROM QnA WHERE user_id = ? ORDER BY unique_id DESC";
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, userId);
        	rs = pstmt.executeQuery();
        	
            while (rs.next()) {
                // 결과를 DTO 객체로 변환
                QnADTO inquiry = new QnADTO();
                inquiry.setUniqueId(rs.getInt("unique_id")); // 문의 ID
                inquiry.setUserId(rs.getString("user_id"));  // 사용자 ID
                inquiry.setTitle(rs.getString("title"));     // 문의 제목
                inquiry.setQuestion(rs.getString("question")); // 문의 내용

                // 답변이 없으면 "답변 대기 중"으로 설정
                String answer = rs.getString("answer");
                if (answer == null || answer.isEmpty()) {
                    answer = "답변 대기 중";  // 답변이 없으면 "답변 대기 중"
                }
                inquiry.setAnswer(answer);   // 관리자 답변

                // 리스트에 추가
                inquiryList.add(inquiry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	JDBCUtil.close(rs, pstmt, conn);
        }

        return inquiryList; // 조회된 문의 내역 리스트 반환
    }
}

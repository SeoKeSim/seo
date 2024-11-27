package team.beans;

import java.sql.*;


public class UserDAO {

    public UserDTO UserCheck(String userId) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserDTO user = null;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT id, name, email FROM users WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
            }
        } finally {
            JDBCUtil.close(rs, stmt, conn); // 자원 반납
        }

        return user;
    }
}
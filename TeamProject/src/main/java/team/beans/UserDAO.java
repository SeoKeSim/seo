package team.beans;

import java.sql.*;


public class UserDAO {

    public UserDTO UserCheck(String id) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserDTO uDTO = null;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT id, name, email FROM users WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
            	uDTO = new UserDTO();
            	uDTO.setId(rs.getString("id"));
            	uDTO.setName(rs.getString("name"));
            	uDTO.setEmail(rs.getString("email"));
            }
        } finally {
            JDBCUtil.close(rs, stmt, conn); // 자원 반납
        }

        return uDTO;
    }
}
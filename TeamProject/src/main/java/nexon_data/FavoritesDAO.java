package nexon_data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritesDAO {
    

	// 즐겨찾기 추가
	public boolean addFavorite(FavoritesDTO favorite) throws SQLException {
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	        conn = JDBCUtil.getConnection();
	        String sql = "INSERT INTO favorites (user_id, ocid, character_name, character_image) VALUES (?, ?, ?, ?)";
	        stmt = conn.prepareStatement(sql);

	        stmt.setString(1, favorite.getUserId());
	        stmt.setString(2, favorite.getOcid());
	        stmt.setString(3, favorite.getCharacterName());
	        stmt.setString(4, favorite.getCharacterImage());

	        int result = stmt.executeUpdate();
	        return result > 0;

	    } finally {
	        JDBCUtil.close(stmt, conn);
	    }
	}
    
    // 즐겨찾기 해제
    public boolean removeFavorite(String userId, String ocid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "DELETE FROM favorites WHERE user_id = ? AND ocid = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, userId);
            stmt.setString(2, ocid);
            
            int result = stmt.executeUpdate();
            return result > 0;
            
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
    
    // 즐겨찾기 여부 확인
    public boolean isFavorite(String userId, String ocid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND ocid = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, userId);
            stmt.setString(2, ocid);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
            
        } finally {
            JDBCUtil.close(rs, stmt, conn);
        }
    }
    
    // 사용자의 즐겨찾기 목록 조회
    public List<FavoritesDTO> getUserFavorites(String userId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FavoritesDTO> favoritesList = new ArrayList<>();
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM favorites WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, userId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                FavoritesDTO favorite = new FavoritesDTO();
                favorite.setUserId(rs.getString("user_id"));
                favorite.setOcid(rs.getString("ocid"));
                favorite.setCharacterName(rs.getString("character_name"));
                favorite.setCharacterImage(rs.getString("character_image"));
                favoritesList.add(favorite);
            }
            
            return favoritesList;
            
        } finally {
            JDBCUtil.close(rs, stmt, conn);
        }
    }
    
    // 전체 즐겨찾기 목록 조회
    public List<FavoritesDTO> getAllFavorites() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FavoritesDTO> favoritesList = new ArrayList<>();
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM favorites";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                FavoritesDTO favorite = new FavoritesDTO();
                favorite.setUserId(rs.getString("user_id"));
                favorite.setOcid(rs.getString("ocid"));
                favorite.setCharacterName(rs.getString("character_name"));
                favorite.setCharacterImage(rs.getString("character_image"));
                favoritesList.add(favorite);
            }
            
            return favoritesList;
            
        } finally {
            JDBCUtil.close(rs, stmt, conn);
        }
    }
    
    // 사용자의 즐겨찾기 개수 조회
    public int getFavoriteCount(String userId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, userId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
            
        } finally {
            JDBCUtil.close(rs, stmt, conn);
        }
    }
    
    
    
}
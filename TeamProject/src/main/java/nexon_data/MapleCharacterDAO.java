package nexon_data;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import nexon_data.MapleCharacter_DTO;
import nexon_data.CharacterEquipment_DTO;

public class MapleCharacterDAO {
	
	public void saveCharacterInfo(MapleCharacter_DTO character) {
	    String sql = "INSERT INTO maple_character (ocid, character_name, character_level, character_class, total_power) " +
	                "VALUES (?, ?, ?, ?, ?) " +
	                "ON DUPLICATE KEY UPDATE " +
	                "character_level = ?, character_class = ?, total_power = ?";

	    try (Connection conn = JDBCUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // SQL 실행 전 파라미터 로깅
	        System.out.println("SQL 실행: " + sql);
	        System.out.println("파라미터 값들:");
	        System.out.println("1. OCID: " + character.getOcid());
	        System.out.println("2. 캐릭터명: " + character.getCharacterName());
	        System.out.println("3. 레벨: " + character.getCharacterLevel());
	        System.out.println("4. 직업: " + character.getCharacterClass());
	        System.out.println("5. 전투력: " + character.getTotalPower());

	        pstmt.setString(1, character.getOcid());
	        pstmt.setString(2, character.getCharacterName());
	        pstmt.setInt(3, character.getCharacterLevel());
	        pstmt.setString(4, character.getCharacterClass());
	        pstmt.setInt(5, character.getTotalPower());
	        // UPDATE 구문을 위한 파라미터
	        pstmt.setInt(6, character.getCharacterLevel());
	        pstmt.setString(7, character.getCharacterClass());
	        pstmt.setInt(8, character.getTotalPower());

	        int result = pstmt.executeUpdate();
	        System.out.println("DB 저장 결과: " + result + "행이 영향을 받음");

	    } catch (SQLException e) {
	        System.err.println("DB 저장 중 오류 발생:");
	        System.err.println("에러 코드: " + e.getErrorCode());
	        System.err.println("SQL 상태: " + e.getSQLState());
	        System.err.println("에러 메시지: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("캐릭터 정보 저장 실패", e);
	    }
	}
	
	// 캐릭터의 전체 장비 정보 조회
	public Map<String, EquipmentStats> getCharacterEquipments(String nickname) {
	    Map<String, EquipmentStats> equipments = new HashMap<>();
	    
	    // 각 테이블에서 장비 정보 조회
	    String[] tables = {"accessory", "armor", "weapon_emblem"};
	    
	    for(String table : tables) {
	        String sql = "SELECT equipment_type, equipment_level, equipment_star_force, potential_grade " +
	                    "FROM " + table + " WHERE nickname = ?";
	                    
	        try (Connection conn = JDBCUtil.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setString(1, nickname);
	            ResultSet rs = pstmt.executeQuery();
	            
	            while(rs.next()) {
	                EquipmentStats stats = new EquipmentStats(
	                    rs.getInt("equipment_level"),
	                    rs.getInt("equipment_star_force"),
	                    rs.getString("potential_grade")
	                );
	                equipments.put(rs.getString("equipment_type"), stats);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return equipments;
	}

	// 장비 스탯을 저장하기 위한 내부 클래스
	public class EquipmentStats {
	    private int level;
	    private int starForce;
	    private String potentialGrade;
	    
	    public EquipmentStats(int level, int starForce, String potentialGrade) {
	        this.level = level;
	        this.starForce = starForce;
	        this.potentialGrade = potentialGrade;
	    }
	    
	    // getter 메서드들
	    public int getLevel() { return level; }
	    public int getStarForce() { return starForce; }
	    public String getPotentialGrade() { return potentialGrade; }
	}
    
	/*
	 * public void saveCharacterEquipment(CharacterEquipment_DTO equipment) { String
	 * sql =
	 * "INSERT INTO character_equipment (ocid, equipment_type, equipment_name, " +
	 * "equipment_level, equipment_star_force, potential_grade) " +
	 * "VALUES (?, ?, ?, ?, ?, ?) " +
	 * "ON DUPLICATE KEY UPDATE equipment_level = ?, " +
	 * "equipment_star_force = ?, potential_grade = ?";
	 * 
	 * try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt =
	 * conn.prepareStatement(sql)) {
	 * 
	 * System.out.println("장비 DB 저장 시도: " + equipment.getEquipmentName()); // 로그 추가
	 * 
	 * pstmt.setString(1, equipment.getOcid()); pstmt.setString(2,
	 * equipment.getEquipmentType()); pstmt.setString(3,
	 * equipment.getEquipmentName()); pstmt.setInt(4,
	 * equipment.getEquipmentLevel()); pstmt.setInt(5,
	 * equipment.getEquipmentStarForce()); pstmt.setString(6,
	 * equipment.getPotentialGrade()); pstmt.setInt(7,
	 * equipment.getEquipmentLevel()); pstmt.setInt(8,
	 * equipment.getEquipmentStarForce()); pstmt.setString(9,
	 * equipment.getPotentialGrade());
	 * 
	 * int result = pstmt.executeUpdate(); System.out.println("장비 저장 결과: " +
	 * result); // 로그 추가
	 * 
	 * } catch (SQLException e) { System.out.println("장비 저장 에러: " + e.getMessage());
	 * // 로그 추가 e.printStackTrace(); } }
	 */
    
    public void saveAccessory(Accessory_DTO accessory) {
        String sql = "INSERT INTO accessory (ocid, equipment_type, equipment_name, " +
                     "equipment_level, equipment_star_force, potential_grade) " +
                     "VALUES (?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "equipment_level = ?, equipment_star_force = ?, potential_grade = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accessory.getOcid());
            pstmt.setString(2, accessory.getEquipmentType());
            pstmt.setString(3, accessory.getEquipmentName());
            pstmt.setInt(4, accessory.getEquipmentLevel());
            pstmt.setInt(5, accessory.getEquipmentStarForce());
            pstmt.setString(6, accessory.getPotentialGrade());
            pstmt.setInt(7, accessory.getEquipmentLevel());
            pstmt.setInt(8, accessory.getEquipmentStarForce());
            pstmt.setString(9, accessory.getPotentialGrade());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void saveArmor(Armor_DTO accessory) {
        String sql = "INSERT INTO accessory (ocid, equipment_type, equipment_name, " +
                     "equipment_level, equipment_star_force, potential_grade) " +
                     "VALUES (?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "equipment_level = ?, equipment_star_force = ?, potential_grade = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accessory.getOcid());
            pstmt.setString(2, accessory.getEquipmentType());
            pstmt.setString(3, accessory.getEquipmentName());
            pstmt.setInt(4, accessory.getEquipmentLevel());
            pstmt.setInt(5, accessory.getEquipmentStarForce());
            pstmt.setString(6, accessory.getPotentialGrade());
            pstmt.setInt(7, accessory.getEquipmentLevel());
            pstmt.setInt(8, accessory.getEquipmentStarForce());
            pstmt.setString(9, accessory.getPotentialGrade());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void saveWeaponEmblem(WeaponEmblem_DTO accessory) {
        String sql = "INSERT INTO accessory (ocid, equipment_type, equipment_name, " +
                     "equipment_level, equipment_star_force, potential_grade) " +
                     "VALUES (?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "equipment_level = ?, equipment_star_force = ?, potential_grade = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accessory.getOcid());
            pstmt.setString(2, accessory.getEquipmentType());
            pstmt.setString(3, accessory.getEquipmentName());
            pstmt.setInt(4, accessory.getEquipmentLevel());
            pstmt.setInt(5, accessory.getEquipmentStarForce());
            pstmt.setString(6, accessory.getPotentialGrade());
            pstmt.setInt(7, accessory.getEquipmentLevel());
            pstmt.setInt(8, accessory.getEquipmentStarForce());
            pstmt.setString(9, accessory.getPotentialGrade());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void saveEquipment(String tableName, BaseEquipmentDTO equipment) {
        String sql = "INSERT INTO " + tableName + " (ocid, equipment_type, equipment_name, " +
                    "equipment_level, equipment_star_force, potential_grade, nickname) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "equipment_level = ?, equipment_star_force = ?, potential_grade = ?, nickname = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipment.getOcid());
            pstmt.setString(2, equipment.getEquipmentType());
            pstmt.setString(3, equipment.getEquipmentName());
            pstmt.setInt(4, equipment.getEquipmentLevel());
            pstmt.setInt(5, equipment.getEquipmentStarForce());
            pstmt.setString(6, equipment.getPotentialGrade());
            pstmt.setString(7, equipment.getNickname());
            pstmt.setInt(8, equipment.getEquipmentLevel());
            pstmt.setInt(9, equipment.getEquipmentStarForce());
            pstmt.setString(10, equipment.getPotentialGrade());
            pstmt.setString(11, equipment.getNickname());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
package nexon_data;

import java.sql.*;
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

            pstmt.setString(1, character.getOcid());
            pstmt.setString(2, character.getCharacterName());
            pstmt.setInt(3, character.getCharacterLevel());
            pstmt.setString(4, character.getCharacterClass());
            pstmt.setInt(5, character.getTotalPower());
            // UPDATE 구문을 위한 파라미터
            pstmt.setInt(6, character.getCharacterLevel());
            pstmt.setString(7, character.getCharacterClass());
            pstmt.setInt(8, character.getTotalPower());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                    "equipment_level, equipment_star_force, potential_grade) " +
                    "VALUES (?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "equipment_level = ?, equipment_star_force = ?, potential_grade = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipment.getOcid());
            pstmt.setString(2, equipment.getEquipmentType());
            pstmt.setString(3, equipment.getEquipmentName());
            pstmt.setInt(4, equipment.getEquipmentLevel());
            pstmt.setInt(5, equipment.getEquipmentStarForce());
            pstmt.setString(6, equipment.getPotentialGrade());
            pstmt.setInt(7, equipment.getEquipmentLevel());
            pstmt.setInt(8, equipment.getEquipmentStarForce());
            pstmt.setString(9, equipment.getPotentialGrade());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
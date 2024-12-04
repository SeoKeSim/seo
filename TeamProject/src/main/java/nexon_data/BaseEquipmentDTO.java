package nexon_data;

public interface BaseEquipmentDTO {
    int getId();
    void setId(int id);
    String getOcid();
    void setOcid(String ocid);
    String getEquipmentType();
    void setEquipmentType(String equipmentType);
    String getEquipmentName();
    void setEquipmentName(String equipmentName);
    int getEquipmentLevel();
    void setEquipmentLevel(int equipmentLevel);
    int getEquipmentStarForce();
    void setEquipmentStarForce(int equipmentStarForce);
    String getPotentialGrade();
    void setPotentialGrade(String potentialGrade);
    String getNickname();
    void setNickname(String nickname);
}
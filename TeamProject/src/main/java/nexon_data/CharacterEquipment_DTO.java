package nexon_data;

public class CharacterEquipment_DTO {
	//미사용
	private int id;
    private String ocid;
    private String equipmentType;
    private String equipmentName;
    private int equipmentLevel;
    private int equipmentStarForce;
    private String potentialGrade;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getOcid() {
        return ocid;
    }
    public void setOcid(String ocid) {
        this.ocid = ocid;
    }
    public String getEquipmentType() {
        return equipmentType;
    }
    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }
    public String getEquipmentName() {
        return equipmentName;
    }
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    public int getEquipmentLevel() {
        return equipmentLevel;
    }
    public void setEquipmentLevel(int equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }
    public int getEquipmentStarForce() {
        return equipmentStarForce;
    }
    public void setEquipmentStarForce(int equipmentStarForce) {
        this.equipmentStarForce = equipmentStarForce;
    }
    public String getPotentialGrade() {
        return potentialGrade;
    }
    public void setPotentialGrade(String potentialGrade) {
        this.potentialGrade = potentialGrade;
    }
	
}

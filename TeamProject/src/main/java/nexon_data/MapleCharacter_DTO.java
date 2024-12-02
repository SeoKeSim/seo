package nexon_data;

public class MapleCharacter_DTO {
	
	private String ocid;
    private String characterName;
    private int characterLevel;
    private String characterClass;
    private int totalPower;

    public String getOcid() {
        return ocid;
    }
    public void setOcid(String ocid) {
        this.ocid = ocid;
    }
    public String getCharacterName() {
        return characterName;
    }
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
    public int getCharacterLevel() {
        return characterLevel;
    }
    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }
    public String getCharacterClass() {
        return characterClass;
    }
    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }
    public int getTotalPower() {
        return totalPower;
    }
    public void setTotalPower(int totalPower) {
        this.totalPower = totalPower;
    }
    
}

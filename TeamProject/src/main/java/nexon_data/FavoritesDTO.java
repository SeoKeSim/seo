package nexon_data;

public class FavoritesDTO {
    private String userId;
    private String ocid;
    private String characterName;
    private String characterImage;
    
    // Getters and Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
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
    public String getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(String characterImage) {
        this.characterImage = characterImage;
    }
}
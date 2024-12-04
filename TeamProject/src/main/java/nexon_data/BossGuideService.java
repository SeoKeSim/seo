package nexon_data;

public class BossGuideService {    
    public int getCurrentStage(long powerLevel) {
        BossStageData data = new BossStageData();
        int currentStage = 0;
        for(int i = 0; i < data.getPowerThresholds().length; i++) {
            if(powerLevel >= data.getPowerThresholds()[i]) {
                currentStage = i;
            }
        }
        return currentStage;
    }
    
    public BossStageData getBossStageData() {
        return new BossStageData();
    }
}
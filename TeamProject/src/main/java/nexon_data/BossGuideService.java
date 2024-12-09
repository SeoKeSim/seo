package nexon_data;

public class BossGuideService {    
    public int getCurrentStage(long powerLevel) {
        BossStageData data = new BossStageData();
        int currentStage = 0;
        
        // 디버깅 로그 추가
        System.out.println("현재 전투력: " + powerLevel);
        
        long[] thresholds = data.getPowerThresholds();
        
        // 전투력 기준값 출력
        System.out.println("전투력 기준값들:");
        for(int i = 0; i < thresholds.length; i++) {
            System.out.println("Stage " + i + ": " + thresholds[i]);
        }
        
        for(int i = 0; i < thresholds.length - 1; i++) {
            System.out.println("비교 중: " + powerLevel + " >= " + thresholds[i] + 
                             " && " + powerLevel + " < " + thresholds[i + 1]);
                             
            if(powerLevel >= thresholds[i] && powerLevel < thresholds[i + 1]) {
                currentStage = i;
                System.out.println("Found stage: " + i);
                break;
            }
        }
        
        if(powerLevel >= thresholds[thresholds.length - 1]) {
            currentStage = thresholds.length - 1;
            System.out.println("최종 스테이지로 설정: " + (thresholds.length - 1));
        }
        
        System.out.println("최종 선택된 스테이지: " + currentStage);
        return currentStage;
    }
    
    public BossStageData getBossStageData() {
        return new BossStageData();
    }
}
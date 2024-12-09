package nexon_data;

public class BossStageData {
    private final String[] bossImages;
    private final String[] bossNames;
    private final long[] powerThresholds;
    private final String[] bossDescriptions;
    
    public BossStageData() {
        this.bossImages = new String[] {
            "Belrum.webp", "Guardian angel slime.webp", "Will.webp", "Swoo.webp",
            "Dusk.webp", "Dunkel.webp", "Turehila.webp", "Seren.webp",
            "Kalos.webp", "Seren.webp", "Karing.webp", "Kalos.webp",
            "Swoo.webp", "Limbo.webp", "Limbo.webp"
        };
        
        this.bossNames = new String[] {
            "카오스 루타비스", "수호천사 슬라임", "윌", "스우", "더스크", 
            "듄켈", "트루힐라", "세렌", "칼로스", "하드 세렌", 
            "카링", "노말 칼로스", "익스트림 스우", "노말 림보", "하드 림보"
        };
        
        this.powerThresholds = new long[] {
            3000000L, 10000000L, 20000000L, 30000000L,
            40000000L, 55000000L, 75000000L, 100000000L,
            150000000L, 200000000L, 250000000L, 300000000L,
            450000000L, 500000000L, 700000000L
        };
        
        this.bossDescriptions = new String[] {
            "카오스 루타비스",
            "노말 가엔, 이지 루시드",
            "이지 윌, 노말 루시드, 노말 더스크",
            "하드 스우, 노말 윌",
            "하드 루시드, 카오스 더스크",
            "검은 마법사 파티격, 하드 듄켈",
            "검밑솔",
            "노말세렌 솔플",
            "이지 칼로스 솔플, 검마 솔플",
            "하드 세렌 솔플",
            "이지 카링 솔플",
            "노말 칼로스 솔플, 익스스우 2인",
            "익스스우 솔플",
            "노말림보 파티",
            "하드 림포 파티"
        };
    }

    public String[] getBossImages() { return bossImages; }
    public String[] getBossNames() { return bossNames; }
    public long[] getPowerThresholds() { return powerThresholds; }
    public String[] getBossDescriptions() { return bossDescriptions; }
}
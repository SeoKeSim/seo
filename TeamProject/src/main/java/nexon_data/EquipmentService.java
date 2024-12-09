package nexon_data;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EquipmentService {
    private MapleCharacterDAO characterDAO;
    
    public EquipmentService() {
        this.characterDAO = new MapleCharacterDAO();
    }
    
    // 분석 결과를 담는 클래스
    public class EquipmentAnalysis {
        private Map<String, MapleCharacterDAO.EquipmentStats> weaponSet;
        private List<MapleCharacterDAO.EquipmentStats> armorSet;
        private List<MapleCharacterDAO.EquipmentStats> accessorySet;
        private double armorAvgStarforce;
        private double accessoryAvgStarforce;
        
     // EquipmentService.java의 EquipmentAnalysis 클래스 내에 추가
        public boolean isAllLegendary() {
            return this.weaponSet.values().stream()
                .allMatch(equip -> "레전더리".equals(equip.getPotentialGrade()));
        }
        
        public EquipmentAnalysis(Map<String, MapleCharacterDAO.EquipmentStats> equipments) {
            Map<String, MapleCharacterDAO.EquipmentStats> weapons = new HashMap<>();
            List<MapleCharacterDAO.EquipmentStats> armors = new ArrayList<>();
            List<MapleCharacterDAO.EquipmentStats> accessories = new ArrayList<>();
            
            // 장비 분류
            for (Map.Entry<String, MapleCharacterDAO.EquipmentStats> entry : equipments.entrySet()) {
                if (isWeaponSet(entry.getKey())) {
                    weapons.put(entry.getKey(), entry.getValue());
                } else if (isArmor(entry.getKey())) {
                    armors.add(entry.getValue());
                } else {
                    accessories.add(entry.getValue());
                }
            }
            
            this.weaponSet = weapons;
            this.armorSet = armors;
            this.accessorySet = accessories;
            this.armorAvgStarforce = calculateAverageStarforce(armorSet);
            this.accessoryAvgStarforce = calculateAverageStarforce(accessorySet);
        }
        
        private double calculateAverageStarforce(List<MapleCharacterDAO.EquipmentStats> items) {
            if (items.isEmpty()) return 0.0;
            
            List<MapleCharacterDAO.EquipmentStats> validItems = items.stream()
                .filter(item -> item.getStarForce() > 0)
                .collect(Collectors.toList());
                
            if (validItems.isEmpty()) return 0.0;
            
            double total = validItems.stream()
                .mapToInt(MapleCharacterDAO.EquipmentStats::getStarForce)
                .sum();
                
            return total / validItems.size();
        }
        
        // Getters
        public Map<String, MapleCharacterDAO.EquipmentStats> getWeaponSet() { 
            return this.weaponSet; 
        }
        
        public List<MapleCharacterDAO.EquipmentStats> getArmorSet() { 
            return this.armorSet; 
        }
        
        public List<MapleCharacterDAO.EquipmentStats> getAccessorySet() { 
            return this.accessorySet; 
        }
        
        public double getArmorAvgStarforce() { 
            return this.armorAvgStarforce; 
        }
        
        public double getAccessoryAvgStarforce() { 
            return this.accessoryAvgStarforce; 
        }
        
        public boolean hasLegendaryWeapons() {
            return this.weaponSet.values().stream()
                .allMatch(equip -> "레전더리".equals(equip.getPotentialGrade()));
        }
    }
    
    private boolean isWeaponSet(String type) {
        return type.equals("무기") || type.equals("보조무기") || type.equals("엠블렘");
    }
    
    private boolean isArmor(String type) {
        return type.equals("모자") || type.equals("상의") || type.equals("하의") ||
               type.equals("장갑") || type.equals("신발") || type.equals("어깨장식");
    }
    
    public EquipmentAnalysis analyzeEquipment(String nickname) {
        Map<String, MapleCharacterDAO.EquipmentStats> equipments = 
            characterDAO.getCharacterEquipments(nickname);
        return new EquipmentAnalysis(equipments);
    }
}
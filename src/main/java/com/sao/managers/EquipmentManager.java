package com.sao.managers;

import com.sao.SAOPlugin;
import com.sao.models.Equipment;
import org.bukkit.entity.Player;

import java.util.*;

public class EquipmentManager {
    private SAOPlugin plugin;
    private Map<String, Equipment> equipmentCatalog = new HashMap<>();

    public EquipmentManager(SAOPlugin plugin) {
        this.plugin = plugin;
        initializeEquipment();
    }

    private void initializeEquipment() {
        Equipment ironSword = new Equipment("item_001", "Iron Sword", Equipment.EquipmentType.SWORD, 1);
        ironSword.setDescription("A basic iron sword");
        equipmentCatalog.put(ironSword.getId(), ironSword);

        Equipment silverSword = new Equipment("item_002", "Silver Sword", Equipment.EquipmentType.SWORD, 2);
        silverSword.setDescription("A shiny silver blade");
        silverSword.setAttack(25);
        equipmentCatalog.put(silverSword.getId(), silverSword);

        Equipment darkSword = new Equipment("item_003", "Dark Sword", Equipment.EquipmentType.SWORD, 4);
        darkSword.setDescription("A cursed dark blade");
        darkSword.setAttack(60);
        equipmentCatalog.put(darkSword.getId(), darkSword);

        Equipment ironArmor = new Equipment("item_004", "Iron Armor", Equipment.EquipmentType.ARMOR, 1);
        ironArmor.setDescription("Basic iron protection");
        equipmentCatalog.put(ironArmor.getId(), ironArmor);

        Equipment legendaryArmor = new Equipment("item_005", "Legendary Armor", Equipment.EquipmentType.ARMOR, 5);
        legendaryArmor.setDescription("The ultimate protection");
        legendaryArmor.setDefense(100);
        legendaryArmor.setHealth(50);
        equipmentCatalog.put(legendaryArmor.getId(), legendaryArmor);
    }

    public Equipment getEquipment(String equipmentId) {
        return equipmentCatalog.get(equipmentId);
    }

    public void equipItem(Player player, Equipment equipment) {
        equipment.setEquipped(true);
        player.sendMessage("\u00a7a[EQUIP] \u00a7fYou equipped: " + equipment.getRarityColor() + equipment.getName());
    }

    public void unequipItem(Player player, Equipment equipment) {
        equipment.setEquipped(false);
        player.sendMessage("\u00a7c[EQUIP] \u00a7fYou unequipped: " + equipment.getName());
    }

    public List<Equipment> getAllEquipment() {
        return new ArrayList<>(equipmentCatalog.values());
    }

    public double calculateTotalAttack(List<Equipment> equippedItems) {
        return equippedItems.stream()
            .filter(Equipment::isEquipped)
            .mapToDouble(Equipment::getAttack)
            .sum();
    }

    public double calculateTotalDefense(List<Equipment> equippedItems) {
        return equippedItems.stream()
            .filter(Equipment::isEquipped)
            .mapToDouble(Equipment::getDefense)
            .sum();
    }
}
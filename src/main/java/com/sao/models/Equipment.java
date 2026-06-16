package com.sao.models;

public class Equipment {
    private String id;
    private String name;
    private EquipmentType type;
    private int rarity;
    private double attack;
    private double defense;
    private double critChance;
    private double health;
    private double mana;
    private String description;
    private boolean equipped;

    public enum EquipmentType {
        SWORD, AXE, SPEAR, BOW, ARMOR, HELMET, BOOTS, SHIELD, RING, AMULET
    }

    public Equipment(String id, String name, EquipmentType type, int rarity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.equipped = false;
        this.attack = rarity * 10.0;
        this.defense = rarity * 5.0;
        this.critChance = rarity * 2.0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public EquipmentType getType() { return type; }
    public int getRarity() { return rarity; }
    public double getAttack() { return attack; }
    public void setAttack(double attack) { this.attack = attack; }
    public double getDefense() { return defense; }
    public void setDefense(double defense) { this.defense = defense; }
    public double getCritChance() { return critChance; }
    public void setCritChance(double critChance) { this.critChance = critChance; }
    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = health; }
    public double getMana() { return mana; }
    public void setMana(double mana) { this.mana = mana; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isEquipped() { return equipped; }
    public void setEquipped(boolean equipped) { this.equipped = equipped; }

    public String getRarityColor() {
        switch(rarity) {
            case 1: return "§7";
            case 2: return "§a";
            case 3: return "§3";
            case 4: return "§5";
            case 5: return "§6";
            default: return "§f";
        }
    }

    public String getRarityName() {
        switch(rarity) {
            case 1: return "Common";
            case 2: return "Uncommon";
            case 3: return "Rare";
            case 4: return "Epic";
            case 5: return "Legendary";
            default: return "Unknown";
        }
    }
}
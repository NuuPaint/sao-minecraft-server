package com.sao.models;

public class Skill {
    private String id;
    private String name;
    private String description;
    private SkillType type;
    private int level;
    private double damage;
    private double manaCost;
    private int cooldown;
    private long lastUsed;
    private boolean unlocked;

    public enum SkillType {
        SLASH, PIERCE, BASH, HEAL, MANA_RECOVER, BUFF, DEBUFF, AOE, INSTANT
    }

    public Skill(String id, String name, SkillType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = 1;
        this.damage = 10.0;
        this.manaCost = 20.0;
        this.cooldown = 5;
        this.lastUsed = 0;
        this.unlocked = false;
    }

    public boolean canUse() {
        return System.currentTimeMillis() - lastUsed >= (cooldown * 1000L);
    }

    public void use() {
        this.lastUsed = System.currentTimeMillis();
    }

    public long getCooldownRemaining() {
        long remaining = (cooldown * 1000L) - (System.currentTimeMillis() - lastUsed);
        return Math.max(0, remaining / 1000);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public SkillType getType() { return type; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public double getDamage() { return damage; }
    public void setDamage(double damage) { this.damage = damage; }
    public double getManaCost() { return manaCost; }
    public void setManaCost(double manaCost) { this.manaCost = manaCost; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown; }
    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
}
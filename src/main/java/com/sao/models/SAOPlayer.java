package com.sao.models;

import java.util.*;
import org.bukkit.entity.Player;

public class SAOPlayer {
    private UUID uuid;
    private String name;
    private int level;
    private long experience;
    private double health;
    private double maxHealth;
    private double mana;
    private double maxMana;
    private double gold;
    private String guildName;
    private String guildRole;
    private List<String> questsCompleted;
    private List<String> activeQuests;
    private List<Equipment> equipment;
    private List<Skill> skills;
    private Map<String, Integer> achievements;
    private long playTime;
    private boolean inArena;
    private boolean inDungeon;
    private int winStreak;
    private int killCount;
    private int deathCount;

    public SAOPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.level = 1;
        this.experience = 0;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.maxMana = 100;
        this.mana = maxMana;
        this.gold = 1000.0;
        this.questsCompleted = new ArrayList<>();
        this.activeQuests = new ArrayList<>();
        this.equipment = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.achievements = new HashMap<>();
        this.playTime = 0;
        this.inArena = false;
        this.inDungeon = false;
        this.winStreak = 0;
        this.killCount = 0;
        this.deathCount = 0;
    }

    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public long getExperience() { return experience; }
    public void addExperience(long exp) { this.experience += exp; }
    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = Math.min(health, maxHealth); }
    public void damage(double damage) { this.health = Math.max(0, health - damage); }
    public void heal(double amount) { this.health = Math.min(health + amount, maxHealth); }
    public double getMaxHealth() { return maxHealth; }
    public void setMaxHealth(double maxHealth) { this.maxHealth = maxHealth; }
    public double getMana() { return mana; }
    public void setMana(double mana) { this.mana = Math.min(mana, maxMana); }
    public void useMana(double amount) { this.mana = Math.max(0, mana - amount); }
    public void restoreMana(double amount) { this.mana = Math.min(mana + amount, maxMana); }
    public double getMaxMana() { return maxMana; }
    public void setMaxMana(double maxMana) { this.maxMana = maxMana; }
    public double getGold() { return gold; }
    public void addGold(double amount) { this.gold += amount; }
    public void removeGold(double amount) { this.gold = Math.max(0, gold - amount); }
    public String getGuildName() { return guildName; }
    public void setGuildName(String guildName) { this.guildName = guildName; }
    public String getGuildRole() { return guildRole; }
    public void setGuildRole(String guildRole) { this.guildRole = guildRole; }
    public List<String> getQuestsCompleted() { return questsCompleted; }
    public List<String> getActiveQuests() { return activeQuests; }
    public List<Equipment> getEquipment() { return equipment; }
    public List<Skill> getSkills() { return skills; }
    public Map<String, Integer> getAchievements() { return achievements; }
    public long getPlayTime() { return playTime; }
    public void addPlayTime(long time) { this.playTime += time; }
    public boolean isInArena() { return inArena; }
    public void setInArena(boolean inArena) { this.inArena = inArena; }
    public boolean isInDungeon() { return inDungeon; }
    public void setInDungeon(boolean inDungeon) { this.inDungeon = inDungeon; }
    public int getWinStreak() { return winStreak; }
    public void addWinStreak() { this.winStreak++; }
    public void resetWinStreak() { this.winStreak = 0; }
    public int getKillCount() { return killCount; }
    public void addKill() { this.killCount++; }
    public int getDeathCount() { return deathCount; }
    public void addDeath() { this.deathCount++; }
}
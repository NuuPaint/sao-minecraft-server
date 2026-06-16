package com.sao.models;

import java.util.*;

public class Quest {
    private String id;
    private String name;
    private String description;
    private int requiredLevel;
    private String npcName;
    private QuestType type;
    private QuestStatus status;
    private int rewardExp;
    private double rewardGold;
    private List<Equipment> rewardItems;
    private String objective;
    private int objectiveProgress;
    private int objectiveTarget;
    private long timeLimit;
    private long startTime;

    public enum QuestType {
        KILL_MONSTERS, COLLECT_ITEMS, TALK_TO_NPC, EXPLORE, DEFEAT_BOSS, DUNGEON
    }

    public enum QuestStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED, FAILED, ABANDONED
    }

    public Quest(String id, String name, QuestType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = QuestStatus.NOT_STARTED;
        this.requiredLevel = 1;
        this.rewardExp = 100;
        this.rewardGold = 50.0;
        this.rewardItems = new ArrayList<>();
        this.objectiveProgress = 0;
        this.objectiveTarget = 1;
        this.timeLimit = 3600000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > timeLimit;
    }

    public void updateProgress(int amount) {
        this.objectiveProgress = Math.min(objectiveProgress + amount, objectiveTarget);
    }

    public boolean isCompleted() {
        return objectiveProgress >= objectiveTarget;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getRequiredLevel() { return requiredLevel; }
    public void setRequiredLevel(int level) { this.requiredLevel = level; }
    public String getNpcName() { return npcName; }
    public void setNpcName(String npcName) { this.npcName = npcName; }
    public QuestType getType() { return type; }
    public QuestStatus getStatus() { return status; }
    public void setStatus(QuestStatus status) { this.status = status; }
    public int getRewardExp() { return rewardExp; }
    public void setRewardExp(int exp) { this.rewardExp = exp; }
    public double getRewardGold() { return rewardGold; }
    public void setRewardGold(double gold) { this.rewardGold = gold; }
    public List<Equipment> getRewardItems() { return rewardItems; }
    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }
    public int getObjectiveProgress() { return objectiveProgress; }
    public int getObjectiveTarget() { return objectiveTarget; }
    public void setObjectiveTarget(int target) { this.objectiveTarget = target; }
    public long getStartTime() { return startTime; }
    public void setStartTime(long time) { this.startTime = time; }
}
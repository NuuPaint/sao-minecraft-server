package com.sao.managers;

import com.sao.SAOPlugin;
import com.sao.models.*;
import org.bukkit.entity.Player;

import java.util.*;

public class QuestManager {
    private SAOPlugin plugin;
    private Map<String, Quest> allQuests = new HashMap<>();
    private Map<UUID, List<Quest>> playerQuests = new HashMap<>();

    public QuestManager(SAOPlugin plugin) {
        this.plugin = plugin;
        initializeQuests();
    }

    private void initializeQuests() {
        Quest tutorialQuest = new Quest("quest_001", "Welcome to Aincrad", Quest.QuestType.KILL_MONSTERS);
        tutorialQuest.setDescription("Defeat 10 slimes to understand the basics");
        tutorialQuest.setRequiredLevel(1);
        tutorialQuest.setObjective("Kill Slimes");
        tutorialQuest.setObjectiveTarget(10);
        tutorialQuest.setRewardExp(500);
        tutorialQuest.setRewardGold(100);
        allQuests.put(tutorialQuest.getId(), tutorialQuest);

        Quest forestQuest = new Quest("quest_002", "Explore Dark Forest", Quest.QuestType.EXPLORE);
        forestQuest.setDescription("Explore the mysterious Dark Forest");
        forestQuest.setRequiredLevel(5);
        forestQuest.setObjective("Visit Forest Locations");
        forestQuest.setObjectiveTarget(5);
        forestQuest.setRewardExp(1000);
        forestQuest.setRewardGold(250);
        allQuests.put(forestQuest.getId(), forestQuest);

        Quest bossQuest = new Quest("quest_003", "Face the Floor Guardian", Quest.QuestType.DEFEAT_BOSS);
        bossQuest.setDescription("Defeat the Floor Guardian Boss");
        bossQuest.setRequiredLevel(10);
        bossQuest.setObjective("Defeat Floor Guardian");
        bossQuest.setObjectiveTarget(1);
        bossQuest.setRewardExp(5000);
        bossQuest.setRewardGold(1000);
        allQuests.put(bossQuest.getId(), bossQuest);
    }

    public void assignQuest(Player player, String questId) {
        Quest quest = allQuests.get(questId);
        if (quest == null) return;

        List<Quest> playerQuestList = playerQuests.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>());
        Quest newQuest = copyQuest(quest);
        newQuest.setStatus(Quest.QuestStatus.IN_PROGRESS);
        newQuest.setStartTime(System.currentTimeMillis());
        playerQuestList.add(newQuest);

        player.sendMessage("\u00a7e[QUEST] \u00a7aYou accepted: \u00a7f" + newQuest.getName());
        player.sendMessage("\u00a7e[QUEST] \u00a77" + newQuest.getDescription());
    }

    public void updateQuestProgress(Player player, String questId, int progress) {
        List<Quest> quests = playerQuests.get(player.getUniqueId());
        if (quests == null) return;

        for (Quest quest : quests) {
            if (quest.getId().equals(questId) && quest.getStatus() == Quest.QuestStatus.IN_PROGRESS) {
                quest.updateProgress(progress);
                player.sendMessage("\u00a7e[QUEST] \u00a7f" + quest.getName() + " \u00a7a" + 
                    quest.getObjectiveProgress() + "/" + quest.getObjectiveTarget());
                
                if (quest.isCompleted()) {
                    completeQuest(player, quest);
                }
            }
        }
    }

    public void completeQuest(Player player, Quest quest) {
        quest.setStatus(Quest.QuestStatus.COMPLETED);
        player.sendTitle(
            "\u00a76QUEST COMPLETED!",
            "\u00a7e" + quest.getName(),
            10, 60, 10
        );
        
        player.sendMessage("\u00a7e[REWARD] \u00a7aExp: +" + quest.getRewardExp() + " \u00a76Gold: +" + quest.getRewardGold());
    }

    public List<Quest> getPlayerQuests(Player player) {
        return playerQuests.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

    public Quest getQuest(String questId) {
        return allQuests.get(questId);
    }

    private Quest copyQuest(Quest original) {
        Quest copy = new Quest(original.getId(), original.getName(), original.getType());
        copy.setDescription(original.getDescription());
        copy.setRequiredLevel(original.getRequiredLevel());
        copy.setRewardExp(original.getRewardExp());
        copy.setRewardGold(original.getRewardGold());
        copy.setObjective(original.getObjective());
        copy.setObjectiveTarget(original.getObjectiveTarget());
        return copy;
    }

    public void saveAllQuests() {
        plugin.getLogger().info("Saving " + playerQuests.size() + " player quest data...");
    }
}
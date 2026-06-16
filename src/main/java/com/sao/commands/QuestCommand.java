package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.QuestManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private QuestManager questManager;

    public QuestCommand(SAOPlugin plugin, QuestManager questManager) {
        this.plugin = plugin;
        this.questManager = questManager;
        plugin.getCommand("quest").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("\u00a7e━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            player.sendMessage("\u00a7e[QUEST] Usage:");
            player.sendMessage("\u00a7f/quest list - Show available quests");
            player.sendMessage("\u00a7f/quest accept <questId> - Accept a quest");
            player.sendMessage("\u00a7f/quest progress - Check quest progress");
            player.sendMessage("\u00a7e━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "list":
                player.sendMessage("\u00a7e[QUESTS] Available:");
                player.sendMessage("\u00a7f- quest_001: Welcome to Aincrad (Lvl 1)");
                player.sendMessage("\u00a7f- quest_002: Explore Dark Forest (Lvl 5)");
                player.sendMessage("\u00a7f- quest_003: Face the Floor Guardian (Lvl 10)");
                break;
            case "accept":
                if (args.length < 2) {
                    player.sendMessage("\u00a7c/quest accept <questId>");
                    return true;
                }
                questManager.assignQuest(player, args[1]);
                break;
            case "progress":
                var quests = questManager.getPlayerQuests(player);
                if (quests.isEmpty()) {
                    player.sendMessage("\u00a7c[QUEST] You have no active quests!");
                } else {
                    player.sendMessage("\u00a7e[YOUR QUESTS]");
                    for (var quest : quests) {
                        player.sendMessage("\u00a7f- " + quest.getName() + ": " + 
                            quest.getObjectiveProgress() + "/" + quest.getObjectiveTarget());
                    }
                }
                break;
            default:
                player.sendMessage("\u00a7c[ERROR] Unknown subcommand!");
        }
        return true;
    }
}
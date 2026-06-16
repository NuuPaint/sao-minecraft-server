package com.sao.managers;

import com.sao.SAOPlugin;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.*;

public class DungeonManager {
    private SAOPlugin plugin;
    private Map<String, Dungeon> dungeons = new HashMap<>();
    private Map<UUID, Dungeon> playerDungeons = new HashMap<>();

    public DungeonManager(SAOPlugin plugin) {
        this.plugin = plugin;
        initializeDungeons();
    }

    private void initializeDungeons() {
        Dungeon dungeon1 = new Dungeon("dungeon_001", "Ant Hill Dungeon", 1, 5);
        Dungeon dungeon2 = new Dungeon("dungeon_002", "Spider's Web", 5, 10);
        Dungeon dungeon3 = new Dungeon("dungeon_003", "Dragon's Lair", 10, 50);
        
        dungeons.put(dungeon1.getId(), dungeon1);
        dungeons.put(dungeon2.getId(), dungeon2);
        dungeons.put(dungeon3.getId(), dungeon3);
    }

    public void enterDungeon(Player player, String dungeonId) {
        Dungeon dungeon = dungeons.get(dungeonId);
        if (dungeon == null) {
            player.sendMessage("\u00a7c[DUNGEON] Dungeon not found!");
            return;
        }

        playerDungeons.put(player.getUniqueId(), dungeon);
        player.sendTitle(
            "\u00a76Entered: " + dungeon.getName(),
            "\u00a7eEnemies: " + dungeon.getEnemyCount(),
            10, 60, 10
        );
        
        Bukkit.broadcastMessage(
            "\u00a7e[DUNGEON] " + player.getName() + " \u00a7aentered \u00a76" + dungeon.getName()
        );
    }

    public void leaveDungeon(Player player) {
        Dungeon dungeon = playerDungeons.remove(player.getUniqueId());
        if (dungeon != null) {
            player.sendMessage("\u00a7e[DUNGEON] You left " + dungeon.getName());
        }
    }

    public void completeDungeon(Player player, String dungeonId) {
        Dungeon dungeon = dungeons.get(dungeonId);
        if (dungeon != null) {
            player.sendTitle(
                "\u00a76DUNGEON CLEAR!",
                "\u00a7e" + dungeon.getName(),
                10, 80, 10
            );
            
            Bukkit.broadcastMessage(
                "\u00a76[DUNGEON CLEAR] " + player.getName() + " \u00a7ahas cleared " + dungeon.getName()
            );
        }
    }

    public Dungeon getPlayerDungeon(Player player) {
        return playerDungeons.get(player.getUniqueId());
    }

    public static class Dungeon {
        private String id;
        private String name;
        private int requiredLevel;
        private int enemyCount;
        private int defeatedEnemies;
        private double difficulty;

        public Dungeon(String id, String name, int requiredLevel, int enemyCount) {
            this.id = id;
            this.name = name;
            this.requiredLevel = requiredLevel;
            this.enemyCount = enemyCount;
            this.defeatedEnemies = 0;
            this.difficulty = requiredLevel * 1.5;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public int getRequiredLevel() { return requiredLevel; }
        public int getEnemyCount() { return enemyCount; }
        public int getDefeatedEnemies() { return defeatedEnemies; }
        public void addDefeatedEnemy() { this.defeatedEnemies++; }
        public double getDifficulty() { return difficulty; }
        public boolean isCompleted() { return defeatedEnemies >= enemyCount; }
    }
}
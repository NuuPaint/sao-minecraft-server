package com.sao.managers;

import com.sao.SAOPlugin;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Bukkit;

import java.util.*;

public class ArenaManager {
    private SAOPlugin plugin;
    private Map<String, Arena> arenas = new HashMap<>();
    private Map<UUID, String> playerArenas = new HashMap<>();
    private Queue<UUID> matchmakingQueue = new LinkedList<>();

    public ArenaManager(SAOPlugin plugin) {
        this.plugin = plugin;
        initializeArenas();
    }

    private void initializeArenas() {
        Arena arena1 = new Arena("Arena_1", "The Colosseum");
        Arena arena2 = new Arena("Arena_2", "Sky Arena");
        Arena arena3 = new Arena("Arena_3", "Underground Pit");
        
        arenas.put(arena1.getName(), arena1);
        arenas.put(arena2.getName(), arena2);
        arenas.put(arena3.getName(), arena3);
    }

    public void queueForMatch(Player player) {
        if (playerArenas.containsKey(player.getUniqueId())) {
            player.sendMessage("\u00a7c[ARENA] You are already in an arena!");
            return;
        }

        matchmakingQueue.offer(player.getUniqueId());
        player.sendMessage("\u00a7e[ARENA] \u00a7aYou have been queued for a match. Waiting...");
        
        if (matchmakingQueue.size() >= 2) {
            startMatch();
        }
    }

    private void startMatch() {
        if (matchmakingQueue.size() < 2) return;

        UUID player1Id = matchmakingQueue.poll();
        UUID player2Id = matchmakingQueue.poll();

        Player player1 = Bukkit.getPlayer(player1Id);
        Player player2 = Bukkit.getPlayer(player2Id);

        if (player1 != null && player2 != null) {
            Arena arena = getAvailableArena();
            if (arena != null) {
                arena.setOccupied(true);
                arena.setPlayer1(player1Id);
                arena.setPlayer2(player2Id);
                
                playerArenas.put(player1Id, arena.getName());
                playerArenas.put(player2Id, arena.getName());

                player1.sendTitle("\u00a7c⚡ MATCH START! ⚡", "\u00a7eFight: " + player2.getName(), 10, 60, 10);
                player2.sendTitle("\u00a7c⚡ MATCH START! ⚡", "\u00a7eFight: " + player1.getName(), 10, 60, 10);

                Bukkit.broadcastMessage("\u00a76[ARENA] \u00a7e" + player1.getName() + " \u00a7fvs \u00a7e" + player2.getName() + " \u00a76in " + arena.getDisplayName());
            }
        }
    }

    public void endMatch(String arenaName, UUID winner) {
        Arena arena = arenas.get(arenaName);
        if (arena != null) {
            Player winnerPlayer = Bukkit.getPlayer(winner);
            UUID loser = winner.equals(arena.getPlayer1()) ? arena.getPlayer2() : arena.getPlayer1();
            Player loserPlayer = Bukkit.getPlayer(loser);

            if (winnerPlayer != null && loserPlayer != null) {
                winnerPlayer.sendTitle("\u00a7a\u2714 VICTORY! \u2714", "\u00a76You won the match!", 10, 60, 10);
                loserPlayer.sendTitle("\u00a7c\u2717 DEFEAT! \u2717", "\u00a7cYou lost the match", 10, 60, 10);
                
                Bukkit.broadcastMessage("\u00a76[ARENA] \u00a7e" + winnerPlayer.getName() + " \u00a7aWON \u00a7fvs \u00a7e" + loserPlayer.getName());
            }

            arena.reset();
            playerArenas.remove(arena.getPlayer1());
            playerArenas.remove(arena.getPlayer2());
        }
    }

    private Arena getAvailableArena() {
        return arenas.values().stream()
            .filter(arena -> !arena.isOccupied())
            .findFirst()
            .orElse(null);
    }

    public boolean isPlayerInArena(Player player) {
        return playerArenas.containsKey(player.getUniqueId());
    }

    public static class Arena {
        private String name;
        private String displayName;
        private boolean occupied;
        private UUID player1;
        private UUID player2;

        public Arena(String name, String displayName) {
            this.name = name;
            this.displayName = displayName;
            this.occupied = false;
        }

        public void reset() {
            this.occupied = false;
            this.player1 = null;
            this.player2 = null;
        }

        public String getName() { return name; }
        public String getDisplayName() { return displayName; }
        public boolean isOccupied() { return occupied; }
        public void setOccupied(boolean occupied) { this.occupied = occupied; }
        public UUID getPlayer1() { return player1; }
        public void setPlayer1(UUID player1) { this.player1 = player1; }
        public UUID getPlayer2() { return player2; }
        public void setPlayer2(UUID player2) { this.player2 = player2; }
    }
}
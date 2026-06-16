package com.sao.managers;

import com.sao.SAOPlugin;
import com.sao.models.SAOPlayer;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.*;

public class PlayerManager {
    private SAOPlugin plugin;
    private Map<UUID, SAOPlayer> players = new HashMap<>();

    public PlayerManager(SAOPlugin plugin) {
        this.plugin = plugin;
    }

    public SAOPlayer getOrCreatePlayer(Player player) {
        return players.computeIfAbsent(player.getUniqueId(), uuid -> {
            SAOPlayer saoPlayer = new SAOPlayer(uuid, player.getName());
            plugin.getLogger().info("Created new SAOPlayer: " + player.getName());
            return saoPlayer;
        });
    }

    public SAOPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public SAOPlayer getPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        return player != null ? players.get(player.getUniqueId()) : null;
    }

    public void addExperience(Player player, long exp) {
        SAOPlayer saoPlayer = getPlayer(player.getUniqueId());
        if (saoPlayer != null) {
            saoPlayer.addExperience(exp);
            long expForNextLevel = (long) (saoPlayer.getLevel() * 1000 + (saoPlayer.getLevel() * 500));
            
            if (saoPlayer.getExperience() >= expForNextLevel) {
                levelUp(player);
            }
        }
    }

    public void levelUp(Player player) {
        SAOPlayer saoPlayer = getPlayer(player.getUniqueId());
        if (saoPlayer != null) {
            saoPlayer.setLevel(saoPlayer.getLevel() + 1);
            saoPlayer.setMaxHealth(saoPlayer.getMaxHealth() + 10);
            saoPlayer.setMaxMana(saoPlayer.getMaxMana() + 10);
            saoPlayer.heal(saoPlayer.getMaxHealth());
            saoPlayer.setMana(saoPlayer.getMaxMana());
            
            player.sendTitle(
                "\u00a76⚡ LEVEL UP! ⚡",
                "\u00a7eNow Level " + saoPlayer.getLevel(),
                10, 60, 10
            );
            
            Bukkit.broadcastMessage(
                "\u00a7e[SAO] \u00a7f" + player.getName() + " \u00a76has reached Level " + saoPlayer.getLevel() + "! \u00a7a✪"
            );
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    public void saveAllData() {
        plugin.getLogger().info("Saving " + players.size() + " player profiles...");
    }

    public List<SAOPlayer> getTopPlayers(int limit) {
        return players.values().stream()
            .sorted((p1, p2) -> Integer.compare(p2.getLevel(), p1.getLevel()))
            .limit(limit)
            .toList();
    }

    public int getTotalPlayers() {
        return players.size();
    }
}
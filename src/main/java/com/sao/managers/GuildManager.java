package com.sao.managers;

import com.sao.SAOPlugin;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.*;

public class GuildManager {
    private SAOPlugin plugin;
    private Map<String, Guild> guilds = new HashMap<>();
    private Map<UUID, String> playerGuilds = new HashMap<>();

    public GuildManager(SAOPlugin plugin) {
        this.plugin = plugin;
    }

    public void createGuild(Player player, String guildName) {
        if (guilds.containsKey(guildName)) {
            player.sendMessage("\u00a7c[GUILD] This guild name already exists!");
            return;
        }

        if (playerGuilds.containsKey(player.getUniqueId())) {
            player.sendMessage("\u00a7c[GUILD] You already are in a guild!");
            return;
        }

        Guild guild = new Guild(guildName, player.getUniqueId(), player.getName());
        guilds.put(guildName, guild);
        playerGuilds.put(player.getUniqueId(), guildName);

        Bukkit.broadcastMessage("\u00a7a[GUILD] " + player.getName() + " created guild: \u00a7e" + guildName);
    }

    public void joinGuild(Player player, String guildName) {
        Guild guild = guilds.get(guildName);
        if (guild == null) {
            player.sendMessage("\u00a7c[GUILD] Guild not found!");
            return;
        }

        if (playerGuilds.containsKey(player.getUniqueId())) {
            player.sendMessage("\u00a7c[GUILD] Leave your current guild first!");
            return;
        }

        guild.addMember(player.getUniqueId(), player.getName(), "Member");
        playerGuilds.put(player.getUniqueId(), guildName);
        player.sendMessage("\u00a7a[GUILD] You joined: \u00a7e" + guildName);
    }

    public void leaveGuild(Player player) {
        String guildName = playerGuilds.get(player.getUniqueId());
        if (guildName == null) {
            player.sendMessage("\u00a7c[GUILD] You are not in a guild!");
            return;
        }

        Guild guild = guilds.get(guildName);
        guild.removeMember(player.getUniqueId());
        playerGuilds.remove(player.getUniqueId());
        player.sendMessage("\u00a7c[GUILD] You left: \u00a7e" + guildName);
    }

    public Guild getPlayerGuild(Player player) {
        String guildName = playerGuilds.get(player.getUniqueId());
        return guildName != null ? guilds.get(guildName) : null;
    }

    public void broadcastToGuild(String guildName, String message) {
        Guild guild = guilds.get(guildName);
        if (guild != null) {
            for (UUID memberId : guild.getMembers().keySet()) {
                Player member = Bukkit.getPlayer(memberId);
                if (member != null && member.isOnline()) {
                    member.sendMessage("\u00a7e[GUILD " + guildName + "] \u00a7f" + message);
                }
            }
        }
    }

    public void saveAllGuilds() {
        plugin.getLogger().info("Saving " + guilds.size() + " guilds...");
    }

    public static class Guild {
        private String name;
        private UUID leader;
        private String leaderName;
        private Map<UUID, String> members;
        private int level;
        private double treasury;
        private long createdDate;

        public Guild(String name, UUID leader, String leaderName) {
            this.name = name;
            this.leader = leader;
            this.leaderName = leaderName;
            this.members = new HashMap<>();
            this.members.put(leader, "Leader");
            this.level = 1;
            this.treasury = 0;
            this.createdDate = System.currentTimeMillis();
        }

        public void addMember(UUID uuid, String name, String role) {
            members.put(uuid, role);
        }

        public void removeMember(UUID uuid) {
            members.remove(uuid);
        }

        public String getName() { return name; }
        public UUID getLeader() { return leader; }
        public String getLeaderName() { return leaderName; }
        public Map<UUID, String> getMembers() { return members; }
        public int getLevel() { return level; }
        public double getTreasury() { return treasury; }
        public void addTreasury(double amount) { this.treasury += amount; }
        public void removeTreasury(double amount) { this.treasury = Math.max(0, treasury - amount); }
    }
}
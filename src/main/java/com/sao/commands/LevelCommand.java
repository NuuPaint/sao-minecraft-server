package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.PlayerManager;
import com.sao.models.SAOPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private PlayerManager playerManager;

    public LevelCommand(SAOPlugin plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
        plugin.getCommand("level").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        SAOPlayer saoPlayer = playerManager.getPlayer(player.getUniqueId());

        if (saoPlayer == null) {
            player.sendMessage("\u00a7c[ERROR] Player data not found!");
            return true;
        }

        player.sendMessage("\u00a7e━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        player.sendMessage("\u00a7e[PLAYER STATS]");
        player.sendMessage("\u00a7fName: \u00a7a" + saoPlayer.getName());
        player.sendMessage("\u00a7fLevel: \u00a76" + saoPlayer.getLevel());
        player.sendMessage("\u00a7fExp: \u00a7a" + saoPlayer.getExperience());
        player.sendMessage("\u00a7fHealth: \u00a7c" + (int)saoPlayer.getHealth() + "/" + (int)saoPlayer.getMaxHealth());
        player.sendMessage("\u00a7fMana: \u00a7b" + (int)saoPlayer.getMana() + "/" + (int)saoPlayer.getMaxMana());
        player.sendMessage("\u00a7fGold: \u00a76" + (int)saoPlayer.getGold());
        player.sendMessage("\u00a7fKills: \u00a7c" + saoPlayer.getKillCount());
        player.sendMessage("\u00a7fDeaths: \u00a77" + saoPlayer.getDeathCount());
        player.sendMessage("\u00a7e━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        return true;
    }
}
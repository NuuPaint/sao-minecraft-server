package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.BossManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BossCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private BossManager bossManager;

    public BossCommand(SAOPlugin plugin, BossManager bossManager) {
        this.plugin = plugin;
        this.bossManager = bossManager;
        plugin.getCommand("boss").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("\u00a7e[BOSS] Available Bosses:");
            player.sendMessage("\u00a7f- boss_001: Illfang the Cobalt Swordsman (Floor 1)");
            player.sendMessage("\u00a7f- boss_002: Xaxa (Floor 5)");
            player.sendMessage("\u00a7f- boss_003: Heathcliff (Floor 10)");
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "fight":
                if (args.length < 2) {
                    player.sendMessage("\u00a7c/boss fight <bossId>");
                    return true;
                }
                bossManager.startBossFight(player, args[1]);
                break;
            default:
                player.sendMessage("\u00a7c[ERROR] Unknown subcommand!");
        }
        return true;
    }
}
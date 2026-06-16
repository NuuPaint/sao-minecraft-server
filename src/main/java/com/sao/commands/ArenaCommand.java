package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private ArenaManager arenaManager;

    public ArenaCommand(SAOPlugin plugin, ArenaManager arenaManager) {
        this.plugin = plugin;
        this.arenaManager = arenaManager;
        plugin.getCommand("arena").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("\u00a7e[ARENA] Usage:");
            player.sendMessage("\u00a7f/arena queue - Join matchmaking queue");
            player.sendMessage("\u00a7f/arena status - Check queue status");
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "queue":
                arenaManager.queueForMatch(player);
                break;
            case "status":
                player.sendMessage("\u00a7e[ARENA] PvP Arena System Ready!");
                player.sendMessage("\u00a7fType /arena queue to join a match");
                break;
            default:
                player.sendMessage("\u00a7c[ERROR] Unknown subcommand!");
        }
        return true;
    }
}
package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.DungeonManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DungeonCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private DungeonManager dungeonManager;

    public DungeonCommand(SAOPlugin plugin, DungeonManager dungeonManager) {
        this.plugin = plugin;
        this.dungeonManager = dungeonManager;
        plugin.getCommand("dungeon").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("\u00a7e[DUNGEON] Available Dungeons:");
            player.sendMessage("\u00a7f- dungeon_001: Ant Hill Dungeon (Lvl 1)");
            player.sendMessage("\u00a7f- dungeon_002: Spider's Web (Lvl 5)");
            player.sendMessage("\u00a7f- dungeon_003: Dragon's Lair (Lvl 10)");
            player.sendMessage("\u00a7f/dungeon enter <id> to enter");
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "enter":
                if (args.length < 2) {
                    player.sendMessage("\u00a7c/dungeon enter <id>");
                    return true;
                }
                dungeonManager.enterDungeon(player, args[1]);
                break;
            case "leave":
                dungeonManager.leaveDungeon(player);
                break;
            default:
                player.sendMessage("\u00a7c[ERROR] Unknown subcommand!");
        }
        return true;
    }
}
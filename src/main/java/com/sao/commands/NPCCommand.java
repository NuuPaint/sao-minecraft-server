package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.NPCManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private NPCManager npcManager;

    public NPCCommand(SAOPlugin plugin, NPCManager npcManager) {
        this.plugin = plugin;
        this.npcManager = npcManager;
        plugin.getCommand("npc").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("\u00a7e[NPCs] Available NPCs:");
            player.sendMessage("\u00a7f- npc_001: Argo the Rat (Information Broker)");
            player.sendMessage("\u00a7f- npc_002: Lisbeth (Blacksmith)");
            player.sendMessage("\u00a7f- npc_003: Silica (Healer)");
            return true;
        }

        if (args[0].equalsIgnoreCase("talk") && args.length >= 2) {
            npcManager.interactWithNPC(player, args[1]);
        }
        return true;
    }
}
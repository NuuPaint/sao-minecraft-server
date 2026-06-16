package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.GuildManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private GuildManager guildManager;

    public GuildCommand(SAOPlugin plugin, GuildManager guildManager) {
        this.plugin = plugin;
        this.guildManager = guildManager;
        plugin.getCommand("guild").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("\u00a7e[GUILD] Usage:");
            player.sendMessage("\u00a7f/guild create <name> - Create a new guild");
            player.sendMessage("\u00a7f/guild join <name> - Join a guild");
            player.sendMessage("\u00a7f/guild leave - Leave your guild");
            player.sendMessage("\u00a7f/guild info - Check guild info");
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "create":
                if (args.length < 2) {
                    player.sendMessage("\u00a7c/guild create <name>");
                    return true;
                }
                guildManager.createGuild(player, args[1]);
                break;
            case "join":
                if (args.length < 2) {
                    player.sendMessage("\u00a7c/guild join <name>");
                    return true;
                }
                guildManager.joinGuild(player, args[1]);
                break;
            case "leave":
                guildManager.leaveGuild(player);
                break;
            case "info":
                var guild = guildManager.getPlayerGuild(player);
                if (guild == null) {
                    player.sendMessage("\u00a7c[GUILD] You are not in a guild!");
                } else {
                    player.sendMessage("\u00a7e[GUILD INFO]");
                    player.sendMessage("\u00a7fName: \u00a7a" + guild.getName());
                    player.sendMessage("\u00a7fLeader: \u00a76" + guild.getLeaderName());
                    player.sendMessage("\u00a7fMembers: \u00a7b" + guild.getMembers().size());
                    player.sendMessage("\u00a7fLevel: \u00a7e" + guild.getLevel());
                    player.sendMessage("\u00a7fTreasury: " + (int)guild.getTreasury() + " Gold");
                }
                break;
            default:
                player.sendMessage("\u00a7c[ERROR] Unknown subcommand!");
        }
        return true;
    }
}
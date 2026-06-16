package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private EconomyManager economyManager;

    public EconomyCommand(SAOPlugin plugin, EconomyManager economyManager) {
        this.plugin = plugin;
        this.economyManager = economyManager;
        plugin.getCommand("economy").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            double balance = economyManager.getBalance(player);
            player.sendMessage("\u00a7e━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            player.sendMessage("\u00a7e[BALANCE] \u00a7aYour Gold: \u00a76" + (int)balance);
            player.sendMessage("\u00a7e━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return true;
        }

        if (args[0].equalsIgnoreCase("give") && args.length >= 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("\u00a7cPlayer not found!");
                return true;
            }
            try {
                double amount = Double.parseDouble(args[2]);
                economyManager.transferGold(player, target, amount);
            } catch (NumberFormatException e) {
                player.sendMessage("\u00a7cInvalid amount!");
            }
        }
        return true;
    }
}
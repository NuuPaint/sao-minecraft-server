package com.sao.commands;

import com.sao.SAOPlugin;
import com.sao.managers.EquipmentManager;
import com.sao.models.Equipment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EquipCommand implements CommandExecutor {
    private SAOPlugin plugin;
    private EquipmentManager equipmentManager;

    public EquipCommand(SAOPlugin plugin, EquipmentManager equipmentManager) {
        this.plugin = plugin;
        this.equipmentManager = equipmentManager;
        plugin.getCommand("equip").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("\u00a7e[EQUIPMENT] Available Items:");
            for (Equipment item : equipmentManager.getAllEquipment()) {
                player.sendMessage("\u00a7f- " + item.getRarityColor() + item.getName() + " \u00a77[" + item.getRarityName() + "]");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("equip") && args.length >= 2) {
            Equipment item = equipmentManager.getEquipment(args[1]);
            if (item != null) {
                equipmentManager.equipItem(player, item);
            }
        }
        return true;
    }
}
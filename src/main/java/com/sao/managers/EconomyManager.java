package com.sao.managers;

import com.sao.SAOPlugin;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.*;

public class EconomyManager {
    private SAOPlugin plugin;
    private Map<UUID, Double> balances = new HashMap<>();

    public EconomyManager(SAOPlugin plugin) {
        this.plugin = plugin;
    }

    public void setBalance(Player player, double amount) {
        balances.put(player.getUniqueId(), Math.max(0, amount));
    }

    public double getBalance(Player player) {
        return balances.getOrDefault(player.getUniqueId(), 1000.0);
    }

    public void addBalance(Player player, double amount) {
        double current = getBalance(player);
        setBalance(player, current + amount);
    }

    public boolean removeBalance(Player player, double amount) {
        double current = getBalance(player);
        if (current >= amount) {
            setBalance(player, current - amount);
            return true;
        }
        return false;
    }

    public void transferGold(Player from, Player to, double amount) {
        if (removeBalance(from, amount)) {
            addBalance(to, amount);
            from.sendMessage("\u00a7c[TRANSFER] \u00a7f-" + amount + " Gold \u2192 " + to.getName());
            to.sendMessage("\u00a7a[TRANSFER] +" + amount + " Gold from " + from.getName());
        } else {
            from.sendMessage("\u00a7c[ERROR] You don't have enough gold!");
        }
    }

    public void buyFromShop(Player player, String itemName, double price) {
        if (removeBalance(player, price)) {
            player.sendMessage("\u00a7a[SHOP] \u00a7fYou bought: \u00a7e" + itemName + " \u00a7afor " + price + " Gold");
        } else {
            player.sendMessage("\u00a7c[SHOP] \u00a7fYou need " + price + " Gold to buy this!");
        }
    }

    public void saveAllBalance() {
        plugin.getLogger().info("Saving " + balances.size() + " player balances...");
    }
}
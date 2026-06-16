package com.sao.managers;

import com.sao.SAOPlugin;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Bukkit;

import java.util.*;

public class BossManager {
    private SAOPlugin plugin;
    private Map<String, Boss> bosses = new HashMap<>();
    private Map<LivingEntity, String> activeBosses = new HashMap<>();

    public BossManager(SAOPlugin plugin) {
        this.plugin = plugin;
        initializeBosses();
    }

    private void initializeBosses() {
        Boss illfang = new Boss("boss_001", "Illfang the Cobalt Swordsman", 100, 50.0);
        illfang.setDescription("\u00a7cThe guardian of Floor 1");
        illfang.setRewardExp(5000);
        illfang.setRewardGold(2000);
        bosses.put(illfang.getId(), illfang);

        Boss xaxa = new Boss("boss_002", "Xaxa", 200, 75.0);
        xaxa.setDescription("\u00a7cThe mad scientist of Floor 5");
        xaxa.setRewardExp(10000);
        xaxa.setRewardGold(5000);
        bosses.put(xaxa.getId(), xaxa);

        Boss heathcliff = new Boss("boss_003", "Heathcliff", 300, 100.0);
        heathcliff.setDescription("\u00a74\u00a7lThe Legendary Knight - King of the Floors");
        heathcliff.setRewardExp(25000);
        heathcliff.setRewardGold(15000);
        bosses.put(heathcliff.getId(), heathcliff);
    }

    public Boss getBoss(String bossId) {
        return bosses.get(bossId);
    }

    public void startBossFight(Player player, String bossId) {
        Boss boss = bosses.get(bossId);
        if (boss == null) {
            player.sendMessage("\u00a7c[BOSS] Boss not found!");
            return;
        }

        Bukkit.broadcastMessage("\u00a74\u00a7l━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Bukkit.broadcastMessage("\u00a74[BOSS APPEARED] " + boss.getDisplayName());
        Bukkit.broadcastMessage("\u00a7c" + boss.getDescription());
        Bukkit.broadcastMessage("\u00a74\u00a7l━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    public void damageBoss(LivingEntity boss, double damage) {
        String bossId = activeBosses.get(boss);
        if (bossId != null) {
            Boss bossData = bosses.get(bossId);
            if (bossData != null) {
                bossData.takeDamage(damage);
            }
        }
    }

    public void completeBossFight(Player player, String bossId) {
        Boss boss = bosses.get(bossId);
        if (boss != null) {
            player.sendTitle(
                "\u00a76\u2605 BOSS DEFEATED! \u2605",
                "\u00a7e" + boss.getDisplayName(),
                10, 80, 10
            );
            
            Bukkit.broadcastMessage(
                "\u00a76[VICTORY] \u00a7e" + player.getName() + " \u00a7ahas defeated \u00a76" + boss.getDisplayName()
            );
        }
    }

    public static class Boss {
        private String id;
        private String name;
        private String displayName;
        private double maxHealth;
        private double health;
        private double attack;
        private String description;
        private int rewardExp;
        private double rewardGold;
        private boolean defeated;

        public Boss(String id, String name, double maxHealth, double attack) {
            this.id = id;
            this.name = name;
            this.displayName = "\u00a74" + name;
            this.maxHealth = maxHealth;
            this.health = maxHealth;
            this.attack = attack;
            this.defeated = false;
        }

        public void takeDamage(double damage) {
            this.health = Math.max(0, health - damage);
            if (health <= 0) {
                this.defeated = true;
            }
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getDisplayName() { return displayName; }
        public double getHealth() { return health; }
        public double getMaxHealth() { return maxHealth; }
        public double getAttack() { return attack; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public int getRewardExp() { return rewardExp; }
        public void setRewardExp(int exp) { this.rewardExp = exp; }
        public double getRewardGold() { return rewardGold; }
        public void setRewardGold(double gold) { this.rewardGold = gold; }
        public boolean isDefeated() { return defeated; }
    }
}
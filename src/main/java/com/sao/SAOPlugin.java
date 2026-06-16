package com.sao;

import org.bukkit.plugin.java.JavaPlugin;
import com.sao.managers.*;
import com.sao.listeners.*;

public class SAOPlugin extends JavaPlugin {

    private PlayerManager playerManager;
    private QuestManager questManager;
    private GuildManager guildManager;
    private ArenaManager arenaManager;
    private DungeonManager dungeonManager;
    private BossManager bossManager;
    private EquipmentManager equipmentManager;
    private EconomyManager economyManager;
    private NPCManager npcManager;

    @Override
    public void onEnable() {
        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        getLogger().info("  SAO Minecraft Server Enabled!");
        getLogger().info("  Welcome to Sword Art Online");
        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        playerManager = new PlayerManager(this);
        questManager = new QuestManager(this);
        guildManager = new GuildManager(this);
        arenaManager = new ArenaManager(this);
        dungeonManager = new DungeonManager(this);
        bossManager = new BossManager(this);
        equipmentManager = new EquipmentManager(this);
        economyManager = new EconomyManager(this);
        npcManager = new NPCManager(this);

        registerListeners();
        registerCommands();

        getLogger().info("All systems initialized successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving all data...");
        playerManager.saveAllData();
        questManager.saveAllQuests();
        guildManager.saveAllGuilds();
        economyManager.saveAllBalance();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this, playerManager, bossManager), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this, playerManager, equipmentManager), this);
        getServer().getPluginManager().registerEvents(new ArenaListener(this, arenaManager), this);
        getServer().getPluginManager().registerEvents(new DungeonListener(this, dungeonManager), this);
    }

    private void registerCommands() {
        new QuestCommand(this, questManager);
        new LevelCommand(this, playerManager);
        new GuildCommand(this, guildManager);
        new ArenaCommand(this, arenaManager);
        new DungeonCommand(this, dungeonManager);
        new BossCommand(this, bossManager);
        new EquipCommand(this, equipmentManager);
        new EconomyCommand(this, economyManager);
        new NPCCommand(this, npcManager);
    }

    public PlayerManager getPlayerManager() { return playerManager; }
    public QuestManager getQuestManager() { return questManager; }
    public GuildManager getGuildManager() { return guildManager; }
    public ArenaManager getArenaManager() { return arenaManager; }
    public DungeonManager getDungeonManager() { return dungeonManager; }
    public BossManager getBossManager() { return bossManager; }
    public EquipmentManager getEquipmentManager() { return equipmentManager; }
    public EconomyManager getEconomyManager() { return economyManager; }
    public NPCManager getNPCManager() { return npcManager; }
}
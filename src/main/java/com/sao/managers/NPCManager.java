package com.sao.managers;

import com.sao.SAOPlugin;
import org.bukkit.entity.Player;
import java.util.*;

public class NPCManager {
    private SAOPlugin plugin;
    private Map<String, NPC> npcs = new HashMap<>();

    public NPCManager(SAOPlugin plugin) {
        this.plugin = plugin;
        initializeNPCs();
    }

    private void initializeNPCs() {
        NPC argo = new NPC("npc_001", "Argo the Rat", "A mysterious information broker");
        argo.addDialogue("Want some information? I have the latest data on all floors!");
        argo.addDialogue("Beware of high-level monsters on upper floors.");
        npcs.put(argo.getId(), argo);

        NPC liz = new NPC("npc_002", "Lisbeth", "A master blacksmith and shop owner");
        liz.addDialogue("Welcome to my shop! Browse my finest equipment!");
        liz.addDialogue("Special deals available today!");
        npcs.put(liz.getId(), liz);

        NPC silica = new NPC("npc_003", "Silica", "A skilled healer");
        silica.addDialogue("Need healing? I can help you recover!");
        silica.addDialogue("Strength comes from friendship and courage.");
        npcs.put(silica.getId(), silica);
    }

    public void interactWithNPC(Player player, String npcId) {
        NPC npc = npcs.get(npcId);
        if (npc != null) {
            String dialogue = npc.getRandomDialogue();
            player.sendMessage("\u00a7e[" + npc.getName() + "] \u00a7f" + dialogue);
        }
    }

    public NPC getNPC(String npcId) {
        return npcs.get(npcId);
    }

    public static class NPC {
        private String id;
        private String name;
        private String description;
        private List<String> dialogues;

        public NPC(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.dialogues = new ArrayList<>();
        }

        public void addDialogue(String dialogue) {
            dialogues.add(dialogue);
        }

        public String getRandomDialogue() {
            return dialogues.get((int)(Math.random() * dialogues.size()));
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
    }
}
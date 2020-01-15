package cz.craftmania.crafttweaks.crafttweaks.listeners;

import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class CreatureSpawnListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.isCancelled()) {
            return;
        }

        if (Main.getInstance().getConfig().getBoolean("entity-spawnrate.enabled")) {
            if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
                Entity creature = e.getEntity();
                int chance = Main.getInstance().getConfig().getInt("entity-spawnrate.default-spawnrate");
                if (Main.getInstance().getConfig().isSet("entity-spawnrate.entities." + creature.getType().name())) {
                    chance = Main.getInstance().getConfig().getInt("entity-spawnrate.entities." + creature.getType().name());
                }
                if (chance >= 100 || chance < 0) {
                    return;
                }
                if (chance == 0) {
                    e.setCancelled(true);
                    return;
                }
                if (!getPercentageChance(chance)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private boolean getPercentageChance(int percentage) {
        return new Random().nextInt(100) + 1 <= percentage;
    }
}

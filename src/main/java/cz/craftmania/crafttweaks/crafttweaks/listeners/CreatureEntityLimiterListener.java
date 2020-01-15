package cz.craftmania.crafttweaks.crafttweaks.listeners;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureEntityLimiterListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreatureSpawn(final CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();

        if (entity.getWorld().getEntities().size() >= 1500) { // LIMIT 1500 entit?
            if (spawnReason == CreatureSpawnEvent.SpawnReason.DEFAULT
                    || spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL
                    || spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER
                    || spawnReason == CreatureSpawnEvent.SpawnReason.EGG) {
                event.setCancelled(true);
                return;
            }
        }

        // Zrušení spawnu pigman
        if (spawnReason == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL) {
            event.setCancelled(true);
        }

    }
}

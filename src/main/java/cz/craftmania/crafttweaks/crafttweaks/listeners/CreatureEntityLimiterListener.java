package cz.craftmania.crafttweaks.crafttweaks.listeners;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class CreatureEntityLimiterListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawn(final PreCreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getReason();

        if (event.getSpawnLocation().getWorld().getEntityCount() >= Main.getInstance().getConfig().getInt("entity-limiter.max-entities", 1500)) {
            if (spawnReason == CreatureSpawnEvent.SpawnReason.DEFAULT
                    || spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL
                    || spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER
                    || spawnReason == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL) {
                event.setCancelled(true);
            }
        }
    }
}

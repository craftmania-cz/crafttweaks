package cz.craftmania.crafttweaks.crafttweaks.listeners;

import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class CreatureEntityLimiterListener implements Listener {

    private Random randObj = new Random();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreatureSpawn(final CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();


        if (spawnReason == CreatureSpawnEvent.SpawnReason.DEFAULT
                || spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL
                || spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER
                || spawnReason == CreatureSpawnEvent.SpawnReason.EGG
                || spawnReason == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL) {

            if (entity.getWorld().getEntities().size() >= 1000) {
                event.setCancelled(true);
            }

            int tps = (int) Main.getInstance().getTPS();
            int randNum = randObj.nextInt(100) + 1;

            if (tps >= 20) {
                event.setCancelled(false);
            } else if (tps >= 19) {
                if (!(randNum >= 10)) {
                    event.setCancelled(true);
                }
            } else if (tps >= 18) {
                if (!(randNum >= 20)) {
                    event.setCancelled(true);
                }
            } else if (tps >= 17) {
                if (!(randNum >= 30)) {
                    event.setCancelled(true);
                }
            } else if (tps >= 16) {
                if (!(randNum >= 40)) {
                    event.setCancelled(true);
                }
            } else if (tps >= 15) {
                if (!(randNum >= 50)) {
                    event.setCancelled(true);
                }
            } else if (tps >= 14) {
                if (!(randNum >= 60)) {
                    event.setCancelled(true);
                }
            } else if (tps >= 13) {
                if (!(randNum >= 70)) {
                    event.setCancelled(true);
                }
            } else if (tps >= 12) {
                if (!(randNum >= 80)) {
                    event.setCancelled(true);
                }
            } else if (tps >= 11) {
                if (!(randNum >= 90)) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        /*if (entity.getWorld().getEntities().size() >= 1000) { // LIMIT 1500 entit?
            if (spawnReason == CreatureSpawnEvent.SpawnReason.DEFAULT
                    || spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL
                    || spawnReason == CreatureSpawnEvent.SpawnReason.SPAWNER
                    || spawnReason == CreatureSpawnEvent.SpawnReason.EGG
                    || spawnReason == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL) {



                event.setCancelled(true);
            }
        }*/
    }
}

package cz.craftmania.crafttweaks.crafttweaks.listeners;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import cz.craftmania.crafttweaks.crafttweaks.Main;
import cz.craftmania.crafttweaks.crafttweaks.utils.Logger;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class CreatureSpawnListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawn(PreCreatureSpawnEvent e) {
        if (e.getReason() == CreatureSpawnEvent.SpawnReason.NATURAL
                || e.getReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            Logger.debug("[PreCreatureSpawnEvent]: Entity: " + e.getType());
            int chance = Main.getInstance().getConfig().getInt("entity-spawnrate.default-spawnrate");
            if (Main.getInstance().getConfig().isSet("entity-spawnrate.entities." + e.getType().name())) {
                chance = Main.getInstance().getConfig().getInt("entity-spawnrate.entities." + e.getType().name());
            }
            if (chance >= 100 || chance < 0) {
                return;
            }
            if (chance == 0) {
                Logger.debug(("[PreCreatureSpawnEvent]: Chance 0 -> kompletně zrušeno."));
                e.setCancelled(true);
                return;
            }
            if (!getPercentageChance(chance)) {
                Logger.debug(("[PreCreatureSpawnEvent]: Chance random -> zrušeno."));
                e.setCancelled(true);
            }
        }
    }

    private boolean getPercentageChance(int percentage) {
        return new Random().nextInt(100) + 1 <= percentage;
    }
}

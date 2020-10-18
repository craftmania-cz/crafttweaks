package cz.craftmania.crafttweaks.crafttweaks.listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ArmorStandGravityDisabler implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlaceArmorStand(EntitySpawnEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof ArmorStand) {
            entity.setGravity(false);
        }
    }
}

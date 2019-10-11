package cz.craftmania.crafttweaks.crafttweaks.listeners.blockers;

import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DisableBlockBreakListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBreakBlock(final BlockBreakEvent event) {
        World bukkitWorld = event.getPlayer().getWorld();
        Player player = event.getPlayer();
        Main.getDisabledBlockBreakWorlds().forEach(world -> {
            if (world.equalsIgnoreCase(bukkitWorld.getName())) {
                player.sendMessage("§c§l[!] §cV tomto světě je zákázáno níčit bloky!");
                event.setCancelled(true);
            }
        });
    }
}

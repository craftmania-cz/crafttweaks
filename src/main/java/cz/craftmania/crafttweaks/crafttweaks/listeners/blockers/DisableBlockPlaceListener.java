package cz.craftmania.crafttweaks.crafttweaks.listeners.blockers;

import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class DisableBlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        World bukkitWorld = event.getPlayer().getWorld();
        Player player = event.getPlayer();
        Main.getInstance().getDisabledBlockPlaceWorlds().forEach(world -> {
            if (world.equalsIgnoreCase(bukkitWorld.getName())) {
                player.sendMessage("§c§l[!] §cV tomto světě je zákázáno pokládat bloky!");
                event.setCancelled(true);
            }
        });
    }
}

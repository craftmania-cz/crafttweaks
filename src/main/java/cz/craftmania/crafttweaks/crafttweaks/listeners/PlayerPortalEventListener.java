package cz.craftmania.crafttweaks.crafttweaks.listeners;

import cz.craftmania.crafttweaks.crafttweaks.utils.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerPortalEventListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleportInPortal(final PlayerPortalEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL
                || event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL
                || event.getCause() == PlayerTeleportEvent.TeleportCause.END_GATEWAY) {
            Logger.info("Zablokovany teleport skrz portal hraci: " + event.getPlayer().getName() + " [" + event.getFrom() + "]");
            event.setCancelled(true);
        }
    }
}

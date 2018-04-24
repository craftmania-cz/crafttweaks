package cz.craftmania.crafttweaks.crafttweaks.listeners;

import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StackingListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inInventory(InventoryClickEvent e) {
        if (e.getCursor().getType().equals(e.getCurrentItem().getType()) && e.getCursor().getDurability() == e.getCurrentItem().getDurability()) {
            if (Main.getStackingList().contains(e.getCurrentItem().getType()) && Main.getStackingList().contains(e.getCursor().getType())) {
                e.getCursor().setAmount(e.getCurrentItem().getAmount() + e.getCursor().getAmount());
                e.getCurrentItem().setAmount(0);
            }
        }
    }
}

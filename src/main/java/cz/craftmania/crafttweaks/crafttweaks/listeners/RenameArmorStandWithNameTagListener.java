package cz.craftmania.crafttweaks.crafttweaks.listeners;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class RenameArmorStandWithNameTagListener implements Listener {

    /*
        Oprava klikani s NameTagy na Armorstandy -> bugovani itemu (duplikace)
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractAtEntityEvent e){
        final Player p = e.getPlayer();
        final Entity entity = e.getRightClicked();
        final ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

        if(entity instanceof ArmorStand){
            if(item.getType() == Material.NAME_TAG){
                p.sendMessage("§c§l(!) §cNemůžeš provádět tuto akci!");
                e.setCancelled(true);
            }
        }


    }
}

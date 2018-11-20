package cz.craftmania.crafttweaks.crafttweaks.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockChunkLimitListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e){

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        final Player p = e.getPlayer();
        final Block b = e.getClickedBlock();
        final Location loc = b.getLocation();
        final Chunk c = loc.getChunk();
        final ItemStack i = p.getInventory().getItemInMainHand();

        if (i.getType() == null) {
            return;
        }

        //System.out.println("Entity spawn: " + i.getType().name());

        EntityType type;

        try {
            type = EntityType.valueOf(i.getType().name());
        } catch (Exception ex) {
            // nic
            return;
        }

        int count = 0;
        for (Entity chunkEntity : c.getEntities()){
            if (chunkEntity.getType() == type) {
                count++;
            }
        }

        //System.out.println("Entity in chunks (" + i.getType().name() + ") - " + count);

        if (count > 40) {
            e.setCancelled(true);
            p.sendMessage("§cDosahl jsi limitu poctu entit v chunku pro §f" + i.getType().name());
        }

        /*if (Main.getInstance().getConfig().getStringList("chunk-limiter.blocks").contains(e.getEntityType().name())) {
            Entity entity = e.getEntity();
            System.out.println("call chunklimiter(), item name: " + e.getEntity().getType().name());
            System.out.println("count: " + count);
            int maxValue = Main.getInstance().getConfig().getInt("chunk-limiter.blocks." + entity.getType().name());
            System.out.println("max value: " + maxValue);
            if (count > maxValue) {
                System.out.println("canceled!");
                e.setCancelled(true);
                //p.sendMessage("§cV tomto chunku je jiz dosazen maximalni pocet: " + item.getType().name());
            }
        }*/


    }
}

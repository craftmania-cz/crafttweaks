package cz.craftmania.crafttweaks.crafttweaks.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class FarmDisasterListener implements Listener {

    /*
        Deaktivace Wheat farem
     */
    @EventHandler(ignoreCancelled = true)
    public void onWaterBreakingWheat(BlockFromToEvent e) {
        if (e.getBlock().getType().equals(Material.WATER) && e.getToBlock().getType().equals(Material.WHEAT)) {
            e.getToBlock().setType(Material.AIR);
        }
    }

    /*
        Deaktivace Sugarcane, Melon, Pumpin farem
     */
    @EventHandler (ignoreCancelled = true)
    public void onPistonFarming(BlockPistonExtendEvent e) {
        for (Block block : e.getBlocks().toArray(new Block[0])) {
            Material type = block.getType();
            if (type.equals(Material.SUGAR_CANE) || type.equals(Material.PUMPKIN) || type.equals(Material.MELON)) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                return;
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlantGrow(BlockGrowEvent e){
        if(e.getBlock().getType() == Material.CACTUS){
            e.setCancelled(true);
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onPhysicsChange(BlockPhysicsEvent e){
        if(e.getBlock().getType() == Material.CACTUS){
            e.setCancelled(true);
        }
    }
}

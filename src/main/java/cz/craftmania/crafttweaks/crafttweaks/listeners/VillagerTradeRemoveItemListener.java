package cz.craftmania.crafttweaks.crafttweaks.listeners;

import com.google.common.collect.Lists;
import cz.craftmania.crafttweaks.crafttweaks.Main;
import cz.craftmania.crafttweaks.crafttweaks.utils.Logger;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VillagerTradeRemoveItemListener implements Listener {

    @EventHandler
    public void onVillagerInteract(final PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager)) return;
        Villager villager = (Villager) event.getRightClicked();
        final List<MerchantRecipe> recipes = Lists.newArrayList(villager.getRecipes());
        final List<MerchantRecipe> finalList = new ArrayList<>();

        for (MerchantRecipe value : recipes) {
            if (!Main.getInstance().getVillagerTradeMaterialList().contains(value.getResult().getType().name())) {
                finalList.add(value);
            } else {
                Logger.info("Odebran item: " + value.getResult().getType().name() + " z Villager menu, pozice: " + villager.getLocation());
            }
        }

        villager.setRecipes(finalList);
    }
}

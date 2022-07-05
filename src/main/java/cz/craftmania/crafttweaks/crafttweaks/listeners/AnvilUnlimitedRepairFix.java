package cz.craftmania.crafttweaks.crafttweaks.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class AnvilUnlimitedRepairFix implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    private void onInventoryOpen(@NotNull InventoryOpenEvent event) {
        if (!(event.getInventory() instanceof AnvilInventory)) {
            return;
        }
        ((AnvilInventory) event.getInventory())
                .setMaximumRepairCost(Main.getInstance().getConfig().getInt("disables-and-fixes.anvil-unlimited-repair.maxCost"));
        if (event.getPlayer() instanceof Player
                && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            setFakeCreativeForPlayer((Player) event.getPlayer(), true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onInventoryClose(@NotNull InventoryCloseEvent event) {
        if (event.getInventory() instanceof AnvilInventory
                && event.getPlayer() instanceof Player
                && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            setFakeCreativeForPlayer((Player) event.getPlayer(), false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPrepareAnvil(@NotNull PrepareAnvilEvent event) {
        if (!(event.getView().getPlayer() instanceof Player)
                || event.getView().getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        Main.getInstance().getServer().getScheduler().runTask(Main.getInstance(), () -> {
            AnvilInventory anvil = event.getInventory();
            ItemStack input2 = anvil.getItem(1);
            setFakeCreativeForPlayer(
                    (Player) event.getView().getPlayer(),
                    // Fix Too expensive pro 2. slot
                    input2 == null || input2.getType() == Material.AIR
                            // Když je hodnota větší jak maxCost v configu tak zobrazit "Too Expensive"
                            || anvil.getRepairCost() < anvil.getMaximumRepairCost());
        });
    }

    private void setFakeCreativeForPlayer(@NotNull Player player, boolean instantBuild) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ABILITIES);
        packet.getBooleans().write(0, player.isInvulnerable());
        packet.getBooleans().write(1, player.isFlying());
        packet.getBooleans().write(2, player.getAllowFlight());
        packet.getBooleans().write(3, instantBuild);
        packet.getFloat().write(0, player.getFlySpeed() / 2);
        packet.getFloat().write(1, player.getWalkSpeed() / 2);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

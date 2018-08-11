package cz.craftmania.crafttweaks.crafttweaks.listeners;

import cz.craftmania.crafttweaks.crafttweaks.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private Main plugin;

    public PlayerChatListener(Main plugin) {
        this.plugin = plugin;
    }

    public void onChat(final AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        final String text = e.getMessage();

        if(plugin.isBlockUnicode()) {
            if(isUnicode(text)) {
                e.setCancelled(true);
                p.sendMessage("§c§l(!) §cProsim nepouzivej nepovolene Unicode znaky!");
                //TODO: Log
            }
        }
    }

    /**
     * Method that compare string and if cointains Unicode character returns true
     * @param string Text
     * @return Boolean
     */
    public static boolean isUnicode(final String string) {
        for (int i = 0; i < string.length(); ++i) {
            final int c = string.charAt(i);
            if (c > 15000) {
                return true;
            }
        }
        return false;
    }
}

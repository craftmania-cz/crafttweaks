package cz.craftmania.crafttweaks.crafttweaks;

import cz.craftmania.crafttweaks.crafttweaks.listeners.FixesListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        Bukkit.getServer().getPluginManager().registerEvents(new FixesListener(), this);

    }

    @Override
    public void onDisable() {

        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }
}

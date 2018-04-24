package cz.craftmania.crafttweaks.crafttweaks;

import cz.craftmania.crafttweaks.crafttweaks.listeners.FarmDisasterListener;
import cz.craftmania.crafttweaks.crafttweaks.listeners.FixesListener;
import cz.craftmania.crafttweaks.crafttweaks.listeners.StackingListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static boolean stackingEnabled = false;
    private static List<Material> stackingList = new ArrayList<>();
    private static boolean nametagsArmorstand = false;
    private static boolean waterBreakingWheat = false;
    private static boolean cactusGrowing = false;
    private static boolean pistonEjectBlocks = false;

    @Override
    public void onEnable() {

        // Instance
        instance = this;

        if(getConfig().getBoolean("disables-and-fixes.nametag-on-armorstand")){
            Bukkit.getServer().getPluginManager().registerEvents(new FixesListener(), this);
        }

        if(getConfig().getBoolean("hack-minecraft.stacking.enabled")){
            Bukkit.getServer().getPluginManager().registerEvents(new StackingListener(), this);
        }

        //Bukkit.getServer().getPluginManager().registerEvents(new FarmDisasterListener(), this);

        // Konfigurace
        loadConfiguration();

    }

    @Override
    public void onDisable() {

        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

    private void loadConfiguration(){
        stackingEnabled = getConfig().getBoolean("hack-minecraft.stacking.enabled", false);
        if(stackingEnabled){
            getConfig().getList("hack-minecraft.stacking.list").forEach(s -> {
                stackingList.add(Material.getMaterial((String) s));
                System.out.println(s);
            });
        }
        nametagsArmorstand = getConfig().getBoolean("disables-and-fixes.nametag-on-armorstand", false);
        waterBreakingWheat = getConfig().getBoolean("disables-and-fixes.water-breaking-wheat", false);
        cactusGrowing = getConfig().getBoolean("disables-and-fixes.cactus-growing", false);
        pistonEjectBlocks = getConfig().getBoolean("disables-and-fixes.piston-eject-blocks", false);
    }

    public static boolean isStackingEnabled() {
        return stackingEnabled;
    }

    public static List<Material> getStackingList() {
        return stackingList;
    }

    public static boolean isNametagsArmorstand() {
        return nametagsArmorstand;
    }

    public static boolean isWaterBreakingWheat() {
        return waterBreakingWheat;
    }

    public static boolean isCactusGrowing() {
        return cactusGrowing;
    }

    public static boolean isPistonEjectBlocks() {
        return pistonEjectBlocks;
    }
}

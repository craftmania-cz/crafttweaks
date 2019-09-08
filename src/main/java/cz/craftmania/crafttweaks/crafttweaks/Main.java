package cz.craftmania.crafttweaks.crafttweaks;

import cz.craftmania.crafttweaks.crafttweaks.listeners.*;
import cz.craftmania.crafttweaks.crafttweaks.utils.Logger;
import cz.craftmania.crafttweaks.crafttweaks.utils.console.ConsoleEngine;
import cz.craftmania.crafttweaks.crafttweaks.utils.console.EngineInterface;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
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
    private static boolean blockUnicode = true;
    private static boolean disabledPortals = false;
    private static java.util.logging.Logger log;
    private static EngineInterface eng;

    @Override
    public void onEnable() {

        // Instance
        instance = this;
        log = this.getLogger();

        // Konfigurace
        loadConfiguration();

        // Listeners
        loadListeners();

        // Console error engine
        eng = new ConsoleEngine(this);
        this.getEngine().hideConsoleMessages();
    }

    @Override
    public void onDisable() {

        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

    private void loadConfiguration() {
        stackingEnabled = getConfig().getBoolean("hack-minecraft.stacking.enabled", false);
        if (stackingEnabled) {
            getConfig().getList("hack-minecraft.stacking.list").forEach(s -> {
                stackingList.add(Material.getMaterial((String) s));
            });
        }
        nametagsArmorstand = getConfig().getBoolean("disables-and-fixes.nametag-on-armorstand", false);
        waterBreakingWheat = getConfig().getBoolean("disables-and-fixes.water-breaking-wheat", false);
        cactusGrowing = getConfig().getBoolean("disables-and-fixes.cactus-growing", false);
        pistonEjectBlocks = getConfig().getBoolean("disables-and-fixes.piston-eject-blocks", false);
        disabledPortals = getConfig().getBoolean("disables-and-fixes.disable-portals", false);
        blockUnicode = getConfig().getBoolean("chat.block-unicode", true);
    }

    private void loadListeners() {
        PluginManager manager = Bukkit.getServer().getPluginManager();

        if (isNametagsArmorstand()) {
            manager.registerEvents(new RenameArmorStandWithNameTagListener(), this);
        }

        if (isStackingEnabled()) {
            manager.registerEvents(new PlayerInventoryClickListener(), this);
        }

        if (isDisabledPortals()) {
            Logger.info("Deaktivace portalu na serveru.");
            manager.registerEvents(new PlayerPortalEventListener(), this);
        }

        //Bukkit.getServer().getPluginManager().registerEvents(new FarmDisasterListener(), this);

        manager.registerEvents(new PlayerChatListener(this), this);
        manager.registerEvents(new BlockChunkLimitListener(), this);
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

    public static boolean isDisabledPortals() {
        return disabledPortals;
    }

    public boolean isBlockUnicode() {
        return blockUnicode;
    }

    public EngineInterface getEngine() {
        return eng;
    }

    public List<String> getStringList(final String key) {
        if (!getConfig().contains(key)) {
            return null;
        }
        return getConfig().getStringList(key);
    }
}

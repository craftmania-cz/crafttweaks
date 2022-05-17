package cz.craftmania.crafttweaks.crafttweaks;

import cz.craftmania.crafttweaks.crafttweaks.listeners.*;
import cz.craftmania.crafttweaks.crafttweaks.listeners.blockers.DisableBlockBreakListener;
import cz.craftmania.crafttweaks.crafttweaks.listeners.blockers.DisableBlockPlaceListener;
import cz.craftmania.crafttweaks.crafttweaks.utils.Logger;
import cz.craftmania.crafttweaks.crafttweaks.utils.console.ConsoleEngine;
import cz.craftmania.crafttweaks.crafttweaks.utils.console.EngineInterface;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static List<String> disabledBlockBreakWorlds = new ArrayList<>();
    private static List<String> disabledBlockPlaceWorlds = new ArrayList<>();
    private static boolean nametagsArmorstand = false;
    private static boolean disabledPortals = false;
    private static boolean disabledBlockBreak = false;
    private static boolean disabledBlockPlace = false;
    private static boolean enabledSpawnLimiter = false;
    private static boolean entityLimiter = false;
    private static boolean disabledArmorStandGravity = false;
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

        // Commands after start
        if (Objects.requireNonNull(getConfig().getList("after-start.commands")).size() > 0) {
            Bukkit.getScheduler().runTaskLater(this, () -> getConfig().getList("after-start.commands").forEach(command -> {
                Logger.info("Run: " + command);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (String) command);
            }), 100L);
        }
    }

    @Override
    public void onDisable() {

        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

    private void loadConfiguration() {
        //TODO: Jako server objekt, jelikož toho bude časem víc
        nametagsArmorstand = getConfig().getBoolean("disables-and-fixes.nametag-on-armorstand", false);
        disabledPortals = getConfig().getBoolean("disables-and-fixes.disable-portals", false);
        disabledBlockBreak = getConfig().getBoolean("disables-and-fixes.block-break.enabled", false);
        if (disabledBlockBreak) {
            getConfig().getList("disables-and-fixes.block-break.worlds").forEach(world -> disabledBlockBreakWorlds.add((String) world));
        }
        disabledBlockPlace = getConfig().getBoolean("disables-and-fixes.block-place.enabled", false);
        if (disabledBlockPlace) {
            getConfig().getList("disables-and-fixes.block-place.worlds").forEach(world -> disabledBlockPlaceWorlds.add((String) world));
        }
        enabledSpawnLimiter = Main.getInstance().getConfig().getBoolean("entity-spawnrate.enabled", false);
        entityLimiter = Main.getInstance().getConfig().getBoolean("entity-limiter.enabled", false);
        disabledArmorStandGravity = Main.getInstance().getConfig().getBoolean("disables-and-fixes.armorstand-gravity", false);
    }

    private void loadListeners() {
        PluginManager manager = Bukkit.getServer().getPluginManager();

        if (isNametagsArmorstand()) {
            Logger.info("Deaktivace prejmenovani armorstandu.");
            manager.registerEvents(new RenameArmorStandWithNameTagListener(), this);
        }

        if (isDisabledPortals()) {
            Logger.info("Deaktivace portalu na serveru.");
            manager.registerEvents(new PlayerPortalEventListener(), this);
        }

        if (isDisabledBlockBreak()) {
            Logger.info("Deaktivace niceni bloku v vybranch svetech.");
            manager.registerEvents(new DisableBlockBreakListener(), this);
        }

        if (isDisabledBlockPlace()) {
            Logger.info("Deaktivace pokladani bloku v vybranch svetech.");
            manager.registerEvents(new DisableBlockPlaceListener(), this);
        }

        //Bukkit.getServer().getPluginManager().registerEvents(new FarmDisasterListener(), this);

        if (isEnabledSpawnLimiter()) {
            Logger.info("Aktivace limitovani spawn-ratu entit.");
            manager.registerEvents(new CreatureSpawnListener(), this);
        }

        if (isEntityLimiterEnabled()) {
            Logger.info("Aktivace limitovani celkoveho spawnu entit.");
            Logger.info("Hodnota je nastavena na: " + getConfig().getInt("entity-limiter.max-entities"));
            manager.registerEvents(new CreatureEntityLimiterListener(), this);
        }

        if (isDisabledArmorStandGravity()) {
            Logger.info("Deaktivace gravitace na polozene armorstandy.");
            manager.registerEvents(new ArmorStandGravityDisabler(), this);
        }
    }

    public static boolean isNametagsArmorstand() {
        return nametagsArmorstand;
    }

    public static boolean isDisabledPortals() {
        return disabledPortals;
    }

    public static List<String> getDisabledBlockBreakWorlds() {
        return disabledBlockBreakWorlds;
    }

    public static List<String> getDisabledBlockPlaceWorlds() {
        return disabledBlockPlaceWorlds;
    }

    public static boolean isDisabledBlockBreak() {
        return disabledBlockBreak;
    }

    public static boolean isDisabledBlockPlace() {
        return disabledBlockPlace;
    }

    public static boolean isEnabledSpawnLimiter() {
        return enabledSpawnLimiter;
    }

    public static boolean isDisabledArmorStandGravity() {
        return disabledArmorStandGravity;
    }

    public static boolean isEntityLimiterEnabled() {
        return entityLimiter;
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

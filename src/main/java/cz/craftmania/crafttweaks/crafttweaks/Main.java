package cz.craftmania.crafttweaks.crafttweaks;

import com.github.retrooper.packetevents.PacketEvents;
import cz.craftmania.crafttweaks.crafttweaks.listeners.*;
import cz.craftmania.crafttweaks.crafttweaks.listeners.blockers.DisableBlockBreakListener;
import cz.craftmania.crafttweaks.crafttweaks.listeners.blockers.DisableBlockPlaceListener;
import cz.craftmania.crafttweaks.crafttweaks.utils.Logger;
import cz.craftmania.crafttweaks.crafttweaks.utils.console.ConsoleEngine;
import cz.craftmania.crafttweaks.crafttweaks.utils.console.EngineInterface;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main instance;
    private boolean debug;
    private static List<String> disabledBlockBreakWorlds = new ArrayList<>();
    private static List<String> disabledBlockPlaceWorlds = new ArrayList<>();
    private static boolean nametagsArmorstand = false;
    private static boolean disabledPortals = false;
    private static boolean disabledBlockBreak = false;
    private static boolean disabledBlockPlace = false;
    private static boolean enabledSpawnLimiter = false;
    private static boolean entityLimiter = false;
    private static boolean disabledArmorStandGravity = false;
    private static boolean enabledUnlimitedAnvilCost = false;
    private static boolean fakeEncryptionChat = false;
    private static java.util.logging.Logger log;
    private static EngineInterface eng;

    @Override
    public void onLoad() {

        // Instance
        instance = this;
        log = this.getLogger();

        // Packet Events Preload
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().debug(false).bStats(false).checkForUpdates(false);
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {

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
        PacketEvents.getAPI().terminate();
        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

    private void loadConfiguration() {
        //TODO: Jako server objekt, jelikož toho bude časem víc
        debug = getConfig().getBoolean("general.debug");
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
        enabledUnlimitedAnvilCost = Main.getInstance().getConfig().getBoolean("disables-and-fixes.anvil-unlimited-repair.enabled", false);
        fakeEncryptionChat = Main.getInstance().getConfig().getBoolean("disables-and-fixes.chat-encryption.enabled", true);
    }

    private void loadListeners() {
        PluginManager manager = Bukkit.getServer().getPluginManager();

        if (nametagsArmorstand) {
            Logger.info("Deaktivace prejmenovani armorstandu.");
            manager.registerEvents(new RenameArmorStandWithNameTagListener(), this);
        }

        if (disabledPortals) {
            Logger.info("Deaktivace portalu na serveru.");
            manager.registerEvents(new PlayerPortalEventListener(), this);
        }

        if (disabledBlockBreak) {
            Logger.info("Deaktivace niceni bloku v vybranch svetech.");
            manager.registerEvents(new DisableBlockBreakListener(), this);
        }

        if (disabledBlockPlace) {
            Logger.info("Deaktivace pokladani bloku v vybranch svetech.");
            manager.registerEvents(new DisableBlockPlaceListener(), this);
        }

        //Bukkit.getServer().getPluginManager().registerEvents(new FarmDisasterListener(), this);

        if (enabledSpawnLimiter) {
            Logger.info("Aktivace limitovani spawn-ratu entit.");
            manager.registerEvents(new CreatureSpawnListener(), this);
        }

        if (entityLimiter) {
            Logger.info("Aktivace limitovani celkoveho spawnu entit.");
            Logger.info("Hodnota je nastavena na: " + getConfig().getInt("entity-limiter.max-entities"));
            manager.registerEvents(new CreatureEntityLimiterListener(), this);
        }

        if (disabledArmorStandGravity) {
            Logger.info("Deaktivace gravitace na polozene armorstandy.");
            manager.registerEvents(new ArmorStandGravityDisabler(), this);
        }

        if (enabledUnlimitedAnvilCost) {
            Logger.info("Aktivace upravy limitu opravy v kovalidně.");
            Logger.info("Limit je nastavený na: " + getConfig().getInt("disables-and-fixes.anvil-unlimited-repair.maxCost"));
            if (!manager.isPluginEnabled("ProtocolLib")) {
                Logger.danger("ProtocolLib není aktivní, úprava limitu nebyla aktivována.");
                return;
            }
            manager.registerEvents(new AnvilUnlimitedRepairFix(), this);
        }

        if (fakeEncryptionChat) {
            Logger.info("Aktivace fake encryptování chatu a blokace Mojang reportů.");
            PacketEvents.getAPI().getEventManager().registerListener(new ChatEncryptionListener());
            PacketEvents.getAPI().init();
        }
    }

    public boolean isDebugEnabled() {
        return debug;
    }

    public static List<String> getDisabledBlockBreakWorlds() {
        return disabledBlockBreakWorlds;
    }

    public static List<String> getDisabledBlockPlaceWorlds() {
        return disabledBlockPlaceWorlds;
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

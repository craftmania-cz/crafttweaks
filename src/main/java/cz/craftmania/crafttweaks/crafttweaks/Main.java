package cz.craftmania.crafttweaks.crafttweaks;

import cz.craftmania.crafttweaks.crafttweaks.listeners.*;
import cz.craftmania.crafttweaks.crafttweaks.listeners.blockers.DisableBlockBreakListener;
import cz.craftmania.crafttweaks.crafttweaks.listeners.blockers.DisableBlockPlaceListener;
import cz.craftmania.crafttweaks.crafttweaks.utils.Logger;
import cz.craftmania.crafttweaks.crafttweaks.utils.console.ConsoleEngine;
import cz.craftmania.crafttweaks.crafttweaks.utils.console.EngineInterface;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main instance;
    private @Getter boolean debug;
    private @Getter List<String> disabledBlockBreakWorlds = new ArrayList<>();
    private @Getter List<String> disabledBlockPlaceWorlds = new ArrayList<>();
    private @Getter List<String> villagerTradeMaterialList = new ArrayList<>();
    private @Getter boolean nametagsArmorstand = false;
    private @Getter boolean disabledPortals = false;
    private @Getter boolean disabledBlockBreak = false;
    private @Getter boolean disabledBlockPlace = false;
    private @Getter boolean enabledSpawnLimiter = false;
    private @Getter boolean entityLimiter = false;
    private @Getter boolean disabledArmorStandGravity = false;
    private @Getter boolean enabledUnlimitedAnvilCost = false;
    private @Getter boolean fakeEncryptionChat = false;
    private @Getter boolean villagerTradeMaterialRemoverEnabled = false;
    private @Getter java.util.logging.Logger log;
    private @Getter EngineInterface eng;

    @Override
    public void onLoad() {

        // Instance
        instance = this;
        log = this.getLogger();
    }

    @Override
    public void onEnable() {

        // Konfigurace
        loadConfiguration();

        // Listeners
        loadListeners();

        // Console error engine
        eng = new ConsoleEngine(this);
        this.getEng().hideConsoleMessages();

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
        villagerTradeMaterialRemoverEnabled = Main.getInstance().getConfig().getBoolean("villager-trade-remover.enabled", false);
        if (villagerTradeMaterialRemoverEnabled) {
            getConfig().getList("villager-trade-remover.items").forEach(item -> villagerTradeMaterialList.add((String) item));
        }
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

        if (villagerTradeMaterialRemoverEnabled) {
            Logger.info("Aktivace mazání itemů z inventáře villagerů.");
            Logger.info("Seznam itemů: " + this.villagerTradeMaterialList);
            manager.registerEvents(new VillagerTradeRemoveItemListener(), this);
        }
    }

    public List<String> getStringList(final String key) {
        if (!getConfig().contains(key)) {
            return null;
        }
        return getConfig().getStringList(key);
    }
}

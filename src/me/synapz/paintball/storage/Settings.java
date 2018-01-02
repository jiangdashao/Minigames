package me.synapz.paintball.storage;

import me.synapz.paintball.Paintball;
import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.arenas.ArenaManager;
import me.synapz.paintball.coin.CoinItems;
import me.synapz.paintball.enums.Databases;
import me.synapz.paintball.enums.ScoreboardLine;
import me.synapz.paintball.enums.ServerType;
import me.synapz.paintball.enums.StatType;
import me.synapz.paintball.storage.database.ConnectionPool;
import me.synapz.paintball.storage.database.DatabaseManager;
import me.synapz.paintball.storage.files.*;
import me.synapz.paintball.utils.Messenger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Settings {

    public static String WEBSITE;
    public static String VERSION;
    public static String THEME;
    public static String AUTHOR;
    public static String SECONDARY;

    public static boolean USE_CHAT;
    public static boolean USE_ECONOMY;
    public static boolean HOLOGRAPHIC_DISPLAYS;
    public static ServerType SERVER_TYPE;

    public static Economy ECONOMY = null;
    public static Chat CHAT = null;

    private static PlayerDataFolder playerDataFolder;
    private static StatsFolder statsFolder;
    private static BungeeFile bungeeFile;
    public static ArenaFile ARENA;
    public static MessagesFile MESSAGES;
    public static FileConfiguration ARENA_FILE;
    public static ItemFile ITEMS;
    public static DatabaseManager DATABASE;
    public static DatabaseFile DATABASE_FILE;

    // Variables
    private static Settings instance;
    private Plugin pb;

    public Settings(Plugin plugin) {
        init(plugin); // init all config.yml stuff
        Settings.ARENA.setup(); // setup arena.yml
    }

    public static Settings getSettings() {
        return instance;
    }

    private void init(Plugin pb) {
        instance = this; // inject the instance

        if (!pb.getDataFolder().exists()) {
            pb.getDataFolder().mkdir();
        }

        this.pb = pb;

        loadFromJar("config.yml");
        loadEverything();

        ARENA.loadLeaderboards();
    }

    private void loadEverything() {
        loadSettings(); // loads everything in config.yml into constants
        MESSAGES = new MessagesFile(pb);
        DATABASE_FILE = new DatabaseFile(pb);
        DATABASE = new DatabaseManager();

        playerDataFolder = new PlayerDataFolder(pb);
        PlayerDataFolder.loadPlayerDataFiles();
        statsFolder = new StatsFolder(pb);
        StatsFolder.loadStatsFiles();

        ITEMS = new ItemFile(pb);
        CoinItems.getCoinItems().loadItems();
        ARENA = new ArenaFile(pb);
        ARENA_FILE = ARENA.getFileConfig();
        ScoreboardLine.loadScoreboardLines();
        StatType.loadStatNames();

        bungeeFile = new BungeeFile(pb);

        if (Databases.ENABLED.getBoolean()) {
            try {
                ConnectionPool.init();
                DATABASE.init();
                if (DATABASE.doesTableExist()) {
                    for (UUIDStatsFile uuidStatsFile : statsFolder.getUUIDStatsList()) {
                        DATABASE.addStats(uuidStatsFile.getFileConfig());
                        uuidStatsFile.delete();
                    }
                }
                else {
                    for (UUIDStatsFile uuidStatsFile : statsFolder.getUUIDStatsList()) {
                        DATABASE.updateTable(uuidStatsFile.getFileConfig());
                        uuidStatsFile.delete();
                    }
                }

                DATABASE.loadStats();
                statsFolder.removeAllFiles();
                Messenger.info(Bukkit.getConsoleSender(), "Initialized database connection.");
            } catch (SQLException e) {
                Messenger.error(Bukkit.getConsoleSender(), "Could not initialize database connection!");
                Databases.ENABLED.setBoolean(false);
                e.printStackTrace();
            }
        }

    }

    // Called on server start, reload, and pb admin reload
    private void loadSettings() {
        PluginDescriptionFile pluginYML = pb.getDescription();

        WEBSITE = pluginYML.getWebsite();
        VERSION = pluginYML.getVersion();
        AUTHOR = pluginYML.getAuthors().toString();
        THEME = ChatColor.translateAlternateColorCodes('&', loadString("theme-color"));
        SECONDARY = ChatColor.translateAlternateColorCodes('&', loadString("secondary-color"));

        try {
            String type = loadString("server-type");
            if (type == null) {
                SERVER_TYPE = ServerType.NORMAL;
            } else {
                SERVER_TYPE = ServerType.valueOf(type.toUpperCase());
            }
        } catch (IllegalArgumentException exc) {
            SERVER_TYPE = ServerType.NORMAL;
        }

        HOLOGRAPHIC_DISPLAYS = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
    }

    public PlayerDataFolder getPlayerDataFolder() {
        return playerDataFolder;
    }

    public StatsFolder getStatsFolder() {
        return statsFolder;
    }

    public BungeeFile getBungeeFile() {
        return bungeeFile;
    }

    public void reloadConfig() {
        pb.reloadConfig();
        loadEverything();
        for (Arena a : ArenaManager.getArenaManager().getArenas().values()) {
            if (a.getState() == Arena.ArenaState.WAITING || a.getState() == Arena.ArenaState.DISABLED || a.getState() == Arena.ArenaState.NOT_SETUP)
                a.loadConfigValues();
            else
                a.setReload();
        }

        if (Settings.HOLOGRAPHIC_DISPLAYS) {
            Settings.ARENA.deleteLeaderboards();
            Settings.ARENA.loadLeaderboards();
        }
    }

    public FileConfiguration getConfig() {
        return pb.getConfig();
    }

    private void loadFromJar(String name) {
        boolean loadConfig = true;

        for (File file : pb.getDataFolder().listFiles()) {
            if (file.getName().equals(name)) {
                loadConfig = false;
                break;
            }
        }

        if (loadConfig)
            pb.saveResource(name, false);
    }

    public void backupConfig(String name) {
        Map<String, File> allFiles = new HashMap<String, File>() {{
            for (File file : pb.getDataFolder().listFiles())
                put(file.getName(), file);
        }};
        File oldConfig = allFiles.get(name + ".yml");
        int suffix = 1;

        while (allFiles.keySet().contains(name + "_backup" + suffix + ".yml"))
            suffix++;

        oldConfig.renameTo(new File(pb.getDataFolder(), name + "_backup" + suffix + ".yml"));
        init(JavaPlugin.getProvidingPlugin(Paintball.class));
    }

    private int loadInt(String path) {
        return (int) loadValue("config.yml", path);
    }

    private String loadString(String path) {
        return (String) loadValue("config.yml", path);
    }

    private boolean loadBoolean(String path) {
        return (boolean) loadValue("config.yml", path);
    }

    private Object loadValue(String name, String path) {

        Map<String, File> allFiles = new HashMap<String, File>() {{
            for (File file : JavaPlugin.getProvidingPlugin(Paintball.class).getDataFolder().listFiles())
                put(file.getName(), file);
        }};

        boolean notFoundInConfig = YamlConfiguration.loadConfiguration(allFiles.get(name)).get(path) == null;

        // If this value is null, it was not found, so turn this file to config_backup.yml and load another updated one
        if (notFoundInConfig) {
            Settings.getSettings().backupConfig("config");
            return null;
        }

        // After backup and new one is done, get the value
        return YamlConfiguration.loadConfiguration(allFiles.get(name)).get(path);
    }
}

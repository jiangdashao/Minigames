package me.synapz.paintball.storage.files;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.synapz.paintball.Paintball;
import me.synapz.paintball.arenas.*;
import me.synapz.paintball.enums.ArenaType;
import me.synapz.paintball.enums.StatType;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.locations.HologramLocation;
import me.synapz.paintball.locations.SignLocation;
import me.synapz.paintball.locations.SkullLocation;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

public class ArenaFile extends PaintballFile {

    private Map<Location, SignLocation> leaderboardAndJoinSigns = new HashMap<>();

    public ArenaFile(Plugin pb) {
        super(pb, "arenas.yml");
    }

    // Sets up arenas from arenas.yml
    public void setup() {
        loadArenasFromFile();
        loadSigns();
    }

    public void addSign(SignLocation signLoc) {
        leaderboardAndJoinSigns.put(signLoc.getLocation(), signLoc);
    }

    public void removeSign(SignLocation signLoc) {
        leaderboardAndJoinSigns.remove(signLoc.getLocation(), signLoc);
    }

    public Map<Location, SignLocation> getSigns() {
        return leaderboardAndJoinSigns;
    }

    // Gets the team list for an arena, the Integer is that team's score
    public List<Team> getTeamsList(Arena a) {
        List<Team> teamList = new ArrayList<>();
        for (String rawItem : fileConfig.getStringList(a.getPath() + ".Teams")) {
            String colorCode = rawItem.split(":")[0]; // rawItem will be stored as, colorCode:teamName like, &c:Red
            String teamName = rawItem.split(":")[1];
            teamList.add(new Team(a, colorCode, teamName));
        }
        return teamList;
    }

    // Adds a new arena to arenas.yml
    public void addNewArenaToFile(Arena arena) {
        fileConfig.set(arena.getPath() + "Name", arena.getName());
        fileConfig.set(arena.getPath() + "Enabled", false);
        fileConfig.set(arena.getPath() + "Type", arena.getArenaType().getStaticName());

        ArenaManager.getArenaManager().getArenas().put(arena.getName(), arena);
        addNewConfigSection(arena);
        saveFile();
    }

    public void loadLeaderboards() {
        if (!Settings.HOLOGRAPHIC_DISPLAYS)
            return;

        for (String loc : getHologramList()) {
            HologramLocation hologramLocation = new HologramLocation(loc);
            addLeaderboard(hologramLocation.getLocation(), hologramLocation.getType(), hologramLocation.getPage(), false);
        }
    }

    public void addLeaderboard(Location loc, StatType statType, int page, boolean addToFile) {
        if (!Settings.HOLOGRAPHIC_DISPLAYS)
            return;

        Hologram hologram = HologramsAPI.createHologram(Paintball.getInstance(), loc);

        for (String statLine : Settings.getSettings().getStatsFolder().getPage(statType, page)) {
            hologram.appendTextLine(statLine);
        }

        new HologramLocation(loc, statType, page, addToFile);
    }

    public void deleteLeaderboards() {
        for (Hologram hologram : HologramsAPI.getHolograms(Paintball.getInstance())) {
            hologram.delete();
        }
    }

    public List<String> getHologramList() {
        return Settings.ARENA_FILE.getStringList("Hologram-Locations");
    }

    // Adds a new arena to arena.yml with values default
    public void addNewConfigSection(Arena a) {
        a.loadConfigValues();
    }

    // Load all arenas from arenas.yml
    public void loadArenasFromFile() {
        Set<String> rawArenas = fileConfig.getConfigurationSection("Arenas") == null ? null : fileConfig.getConfigurationSection("Arenas").getKeys(false);

        if (rawArenas == null) {
            return;
        }

        for (String arenaName : rawArenas) {
            Arena a = null;
            String name = fileConfig.getString("Arenas." + arenaName + ".Name");
            ArenaType type = ArenaType.getArenaType(null, fileConfig.getString("Arenas." + arenaName + ".Type"));
            try {
                // add each arena to the server

                switch (type) {
                    case CTF:
                        a = new CTFArena(arenaName, name, false);
                        break;
                    case DOM:
                        a = new DOMArena(arenaName, name, false);
                        break;
                    case DTC:
                        a = new DTCArena(arenaName, name, false);
                        break;
                    case RTF:
                        a = new RTFArena(arenaName, name, false);
                        break;
                    case TDM:
                        a = new Arena(arenaName, name, false);
                        break;
                    case FFA:
                        a = new FFAArena(arenaName, name, false);
                        break;
                    case LTS:
                        a = new LTSArena(arenaName, name, false);
                        break;
                    case KC:
                        a = new KCArena(arenaName, name, false);
                        break;
                    default:
                        a = new Arena(arenaName, name, false);
                        break;
                }

                // set the value of that arena
                a.loadValues();
            }catch (Exception e) {
                Messenger.error(Bukkit.getConsoleSender(), "Error loading " + arenaName + " in arenas.yml. Stacktrace: ");
                e.printStackTrace();
            }
            ArenaManager.getArenaManager().getArenas().put(a.getName(), a);
        }
    }

    public int loadInt(String item, Arena arena) {
        return (int) loadValue(item, arena, false);
    }

    public String loadString(String item, Arena arena) {
        return ChatColor.translateAlternateColorCodes('&', (String) loadValue(item, arena, false));
    }

    public List<String> loadStringList(String item, Arena arena) {
        List<String> whiteList = (List<String>) loadValue(item, arena, true);
        List<String> coloredList = new ArrayList<>();

        for (String toColor : whiteList) {
            coloredList.add(ChatColor.translateAlternateColorCodes('&', toColor));
        }

        return coloredList;
    }

    public double loadDouble(String item, Arena arena) {
        return (double) loadValue(item, arena, false);
    }

    public boolean loadBoolean(String item, Arena arena) {
        return (boolean) loadValue(item, arena, false);
    }

    private Object loadValue(String item, Arena arena, boolean asStringList) {
        Map<String, File> allFiles = new HashMap<String, File>(){{
            for (File file : Paintball.getInstance().getDataFolder().listFiles())
                put(file.getName(), file);
        }};

        String path = getConfigPath(item, arena);
        boolean notFoundInArena = fileConfig.get(path) == null;
        boolean notFoundInConfig = YamlConfiguration.loadConfiguration(allFiles.get("config.yml")).get(getArenaConfigPath(item)) == null;

        if (notFoundInArena) {
            if (asStringList)
                fileConfig.set(path,  new ArrayList<String>() {{ add("default"); }});
            else
                fileConfig.set(path, "default");
        }

        /*
            Since config.yml cannot be saved or it removes its format,
            rename the config file and make a new one
        */
        if (notFoundInConfig) {
            Settings.getSettings().backupConfig("config");
        }

        if (asStringList && fileConfig.getStringList(path).contains("default") || fileConfig.getString(path).equalsIgnoreCase("default")) {
            Object value = Settings.getSettings().getConfig().get(getArenaConfigPath(item));
            if (value == null) {
                if (asStringList)
                    return new ArrayList<>();
                else
                    return "";
            } else {
                return value;
            }
        } else {
            return fileConfig.get(path);
        }
    }

    private void loadSigns() {
        for (String rawLoc : fileConfig.getStringList("Signs.Autojoin")) {
            SignLocation signLoc = new SignLocation(SignLocation.SignLocations.AUTOJOIN, rawLoc);
            leaderboardAndJoinSigns.put(signLoc.getLocation(), signLoc);
        }

        for (String rawLoc : fileConfig.getStringList("Signs.Skull")) {
            SkullLocation skullLoc = new SkullLocation(rawLoc);
            leaderboardAndJoinSigns.put(skullLoc.getLocation(), skullLoc);
        }

        for (String rawLoc : fileConfig.getStringList("Signs.Leaderboard")) {
            SignLocation signLoc = new SignLocation(SignLocation.SignLocations.LEADERBOARD, rawLoc);
            leaderboardAndJoinSigns.put(signLoc.getLocation(), signLoc);
        }
    }

    private String getConfigPath(String value, Arena arena) {
        return arena.getPath() + ".Config." + value;
    }

    private String getArenaConfigPath(String value) {
        return  "Per-Arena-Settings.Defaults." + value;
    }
}

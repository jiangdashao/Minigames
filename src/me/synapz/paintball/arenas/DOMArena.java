package me.synapz.paintball.arenas;

import me.synapz.paintball.enums.ArenaType;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.synapz.paintball.storage.Settings.*;

public class DOMArena extends FlagArena {

    public int SECURE_TIME;
    public int GENERATE_SIZE;
    public int UPDATE_INTERVAL;

    public Sound SECURE;
    public Sound START_SECURE;

    // A running list of all changed blocks
    private Map<Location, Material> oldBlocks = new HashMap<>();
    private Map<Location, Byte> oldData = new HashMap<>();
    private Map<Location, Team> secureLocations = new HashMap<>();
    private Map<Location, Location> centerLoc = new HashMap<>();
    private Map<Team, Integer> runningScore = new HashMap<>();

    public DOMArena(String name, String currentName, boolean addToConfig) {
        super(name, currentName, addToConfig);
    }

    @Override
    public ArenaType getArenaType() {
        return ArenaType.DOM;
    }

    @Override
    public void resetFlags() {
        for (Location loc : oldBlocks.keySet()) {
            if (oldBlocks.get(loc) != null && loc.getBlock() != null) {
                loc.getBlock().setType(oldBlocks.get(loc));
            }
        }

        for (Location loc : oldData.keySet()) {
            if (oldData.get(loc) != null && loc.getBlock() != null)
                loc.getBlock().setData(oldData.get(loc));
        }

        oldBlocks = new HashMap<>();
        oldData = new HashMap<>();
    }

    @Override
    public void loadFlags() {
        for (Team team : getActiveArenaTeamList()) {
            Location center = getFlagLocation(team).subtract(0, 1, 0);

            List<Location> secLoc = makePlatform(center.clone(), Material.STAINED_GLASS, team.getDyeColor(), GENERATE_SIZE, Material.STAINED_CLAY);
            makePlatform(center.clone().subtract(0, 1, 0), Material.STAINED_CLAY, team.getDyeColor(), GENERATE_SIZE, null);
            removeAbove(center.clone());

            // Makes iron under beacon to turn it on
            makePlatform(center.clone().subtract(0, 2, 0), Material.IRON_BLOCK,  null, 1, null);

            // Turns the lower block to a beacon
            setBlock(center.clone().subtract(0, 1, 0), Material.BEACON, null);

            for (Location loc : secLoc) {
                secureLocations.put(Utils.simplifyLocation(loc), team);
                centerLoc.put(Utils.simplifyLocation(loc), Utils.simplifyLocation(center));
            }

            runningScore.put(team, 1);
        }
    }

    @Override
    public void loadConfigValues() {
        super.loadConfigValues();

        SECURE_TIME         = Settings.ARENA.loadInt("DOM.secure-time", this);
        GENERATE_SIZE       = Settings.ARENA.loadInt("DOM.generate-size", this);
        UPDATE_INTERVAL     = Settings.ARENA.loadInt("DOM.update-interval", this);

        SECURE              = (ARENA.loadString("DOM.secure", this).equals("")) ? null : Utils.strToSound(ARENA.loadString("DOM.secure", this));
        START_SECURE        = (ARENA.loadString("DOM.start-secure", this).equals("")) ? null : Utils.strToSound(ARENA.loadString("DOM.start-secure", this));
    }

    public void teamSecured(Location loc, Team team) {
        Location center = centerLoc.get(loc);
        Team pastTeam = secureLocations.get(loc);

        List<Location> secLoc = makePlatform(center.clone(), Material.STAINED_GLASS, team.getDyeColor(), GENERATE_SIZE, Material.STAINED_CLAY);
        makePlatform(center.clone().subtract(0, 1, 0), Material.STAINED_CLAY, team.getDyeColor(), GENERATE_SIZE, null);
        removeAbove(center.clone());

        // Makes iron under beacon to turn it on
        makePlatform(center.clone().subtract(0, 2, 0), Material.IRON_BLOCK, null, 1, null);

        // Turns the lower block to a beacon
        setBlock(center.clone().subtract(0, 1, 0), Material.BEACON, null);

        for (Location locIt : secLoc) {
            secureLocations.replace(Utils.simplifyLocation(locIt), pastTeam, team);
        }

        int pastScore = runningScore.get(pastTeam);
        int newPastScore = runningScore.get(team);

        runningScore.remove(pastTeam);
        runningScore.remove(team);

        runningScore.put(pastTeam, --pastScore);
        runningScore.put(team, ++newPastScore);

        broadcastMessage(THEME + team.getTitleName() + SECONDARY + " has secured " + THEME + pastTeam.getTitleName());
    }

    public Map<Location, Team> getSecureLocations() {
        return secureLocations;
    }

    public Map<Team, Integer> getRunningScores() {
        return runningScore;
    }

    private void setBlock(Location loc, Material material, DyeColor dyeColor) {
        loc = Utils.simplifyLocation(loc);

        if (!oldBlocks.containsKey(loc))
            oldBlocks.put(loc, loc.getBlock().getType());

        if (!oldData.containsKey(loc))
            oldData.put(loc, loc.getBlock().getData());

        loc.getBlock().setType(material);

        if (dyeColor != null) {
            /*
            maybe when the material api is finally done I can use this >.>

            MaterialData data;

            switch (material) {
                case WOOL:
                    data = new Wool(dyeColor);
                    break;
                case STAINED_CLAY:
                    break;
                case STAINED_GLASS:
                    break;
                case STAINED_GLASS_PANE:
                    break;
                default:
                    return;
            }*/

            // loc.getBlock().getState().setData(data);

            loc.getBlock().setData(dyeColor.getWoolData());
        }
    }

    private List<Location> makePlatform(Location center, Material material, DyeColor dyeColor, int radius, Material border) {
        List<Location> secLoc = new ArrayList<>();

        if (border != null)
            radius++;

        // Makes the square platform under the other one
        for (int x = -radius; x <= radius; ++x) {
            for (int z = -radius; z <= radius; ++z) {
                Location point = center.clone().add(x, 0, z);

                if (border != null && (Math.abs(x) == radius || Math.abs(z) == radius)) {
                    setBlock(point, border, dyeColor);
                } else {
                    setBlock(point, material, dyeColor);
                    secLoc.add(point);
                    secLoc.add(point.add(0, 1, 0));
                }
            }
        }

        return secLoc;
    }

    private void removeAbove(Location center) {
        // Sets everything above the center to AIR so the beacon turns on
        for (int y = 1; y <= 240; ++y) {
            Block toChange = center.clone().add(0, y, 0).getBlock();
            if (toChange != null && toChange.getType() != Material.AIR)
                setBlock(toChange.getLocation(), Material.AIR, null);
        }
    }
}

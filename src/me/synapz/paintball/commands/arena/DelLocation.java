package me.synapz.paintball.commands.arena;

import me.synapz.paintball.commands.TeamCommand;
import me.synapz.paintball.enums.CommandType;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.locations.TeamLocation;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.Messenger;

public class DelLocation extends TeamCommand {

    public void onCommand() {
        TeamLocation.TeamLocations type = stringToLocationType(args[3]);

        if (type == null) {
            Messenger.error(player, "Value " + args[3] + " is not a valid type.", "Choose either <spawn/lobby>");
            return;
        }

        if (args[4].equalsIgnoreCase("all")) {
            for (Team t : arena.getActiveArenaTeamList()) {
                int size = t.getSpawnPointsSize(type);
                if (size != 0) {
                    while (size > 0) {
                        arena.delLocation(type, t, size);
                        size--;
                    }
                }
            }
            Messenger.success(player, "Deleted all " + arena.getName() + "'s " + (type.toString().toLowerCase().equals("spawn") ? "arena" : "lobby") + " spawns.", arena.getSteps());
            return;
        }

        if (team.getSpawnPointsSize(type) == 0) {
            Messenger.error(player, "There are no more " + team.getTitleName() + " spawns to be deleted.");
            return;
        }

        arena.delLocation(type, team, team.getSpawnPointsSize(type));
        Messenger.success(player, "Deleted " + arena.getName() + "'s " + team.getTitleName() + " spawn to your location: " + Settings.SECONDARY + team.getSpawnPointsSize(type), arena.getSteps());
    }

    public String getArgs() {
        return "<arena> <spawn/lobby> <team/all>";
    }

    public String getPermission() {
        return "paintball.arena.dellocation";
    }

    public String getName() {
        return "del";
    }

    public Messages getInfo() {
        return Messages.COMMAND_DELLOCATION_INFO;
    }

    public CommandType getCommandType() {
        return CommandType.ARENA;
    }

    public int getMaxArgs() {
        return 5;
    }

    public int getMinArgs() {
        return 5;
    }

    protected int getTeamArg() {
        return 4;
    }

    protected int getArenaArg() {
        return 2;
    }

    private TeamLocation.TeamLocations stringToLocationType(String value) {
        for (TeamLocation.TeamLocations enumLocs : TeamLocation.TeamLocations.values()) {
            if (enumLocs.toString().equalsIgnoreCase(value))
                return enumLocs;
        }
        return null;
    }
}

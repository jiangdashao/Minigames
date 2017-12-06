package me.synapz.paintball.commands;

import me.synapz.paintball.arenas.RTFArena;
import me.synapz.paintball.commands.arena.DelLocation;
import me.synapz.paintball.commands.arena.SetFlag;
import me.synapz.paintball.commands.arena.SetLocation;
import me.synapz.paintball.commands.player.Join;
import me.synapz.paintball.enums.CommandType;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.enums.Tag;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.utils.MessageBuilder;
import me.synapz.paintball.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class TeamCommand extends ArenaCommand {

    protected Team team;
    protected String rawTeamName;

    public void onCommand(Player player, String[] args) {
        super.onCommand(player, args);

        // This means it is an optional argument since it wasn't specified
        try {
            rawTeamName = args[getTeamArg()];
        } catch (ArrayIndexOutOfBoundsException exc) {
            if (arena != null || (this instanceof Join && args.length == 1))
                onCommand();
            return;
        }

        if (arena == null)
            return;

        if (arena.getActiveArenaTeamList().isEmpty()) {
            Messenger.error(player, new MessageBuilder(Messages.NO_TEAMS_SET).replace(Tag.ARENA, arena.toString(ChatColor.RED)).build());
            return;
        }

        if (arena instanceof RTFArena && rawTeamName.equalsIgnoreCase("neutral") || (this instanceof SetLocation || this instanceof DelLocation) && rawTeamName
            .equalsIgnoreCase("all")) {
            onCommand();
            return;
        }

        this.player = player;
        this.args = args;
        this.team = stringToTeam();

        if (teamCheck()) {
            onCommand();
        } else {
            return;
        }
    }

    public abstract void onCommand();

    public abstract String getName();

    public abstract Messages getInfo();

    public abstract String getArgs();

    public abstract String getPermission();

    public abstract CommandType getCommandType();

    public abstract int getMaxArgs();

    public abstract int getMinArgs();

    protected abstract int getTeamArg();

    // Checks to see if a team is invalid, if it is it sends the player the list of valid teams
    private boolean teamCheck() {
        String teamString = args[getTeamArg()];
        StringBuilder validTeams = new StringBuilder(" ");
        String finalValidTeams;
        if (arena.getActiveArenaTeamList().isEmpty()) {
            return false;
        }

        for (Team team : arena.getActiveArenaTeamList()) {
            validTeams.append(ChatColor.stripColor(team.getTitleName().toLowerCase().replace(" ", ""))).append(" ");
        }

        if (this instanceof SetFlag && arena instanceof RTFArena) {
            validTeams.append("neutral ");
        }

        if (!(validTeams.toString().contains(" " + teamString.toLowerCase() + " "))) {
            // remove last space and replace spaces with /. So it should be <red,blue,green>
            finalValidTeams = validTeams.substring(1, validTeams.lastIndexOf(" "));
            Messenger.error(player, new MessageBuilder(Messages.INVALID_TEAM).replace(Tag.TEAM, teamString).replace(Tag.TEAMS, finalValidTeams.replace(" ", ",")).build());
            return false;
        } else {
            return true;
        }
    }

    // Turns a string like 'red' in to a team
    private Team stringToTeam() {
        for (Team t : arena.getActiveArenaTeamList()) {
            if (t.getTitleName().replace(" ", "").equalsIgnoreCase(args[getTeamArg()])) {
                return t;
            }
        }
        return null;
    }
}

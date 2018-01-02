package me.synapz.paintball.commands.player;

import me.synapz.paintball.Paintball;
import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.arenas.ArenaManager;
import me.synapz.paintball.commands.TeamCommand;
import me.synapz.paintball.enums.CommandType;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.enums.ServerType;
import me.synapz.paintball.enums.Tag;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.MessageBuilder;
import me.synapz.paintball.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.Arrays;

public class Join extends TeamCommand {

    public void onCommand() {
        if (args.length == 1) {
            Arena arena = ArenaManager.getArenaManager().getBestArena();
            if (arena == null) {
                Messenger.error(player, Messages.NO_ARENAS);
                return;
            } else {
                arena.joinLobby(player, null);
            }
        } else if (args.length == 2) {
            // If the player types in /pb join Arena
            arena.joinLobby(player, null);
        } else if (args.length == 3) {
            // If the player types in /pb join Arena Team
            if (player.hasPermission("paintball.join.team"))
                arena.joinLobby(player, team);
            else
                Messenger.error(player, Messages.NO_PERMISSION);
        }
    }

    public String getArgs() {
        return "[arena] [team]";
    }

    public String getPermission() {
        return "paintball.join";
    }

    public String getName() {
        return "join";
    }

    public Messages getInfo() {
        return Messages.COMMAND_JOIN_INFO;
    }

    public CommandType getCommandType() {
        return CommandType.PLAYER;
    }

    public int getMaxArgs() {
        return 3;
    }

    public int getMinArgs() {
        return 1;
    }

    protected int getTeamArg() {
        return 2;
    }

    protected int getArenaArg() {
        return 1;
    }
}

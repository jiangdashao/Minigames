package me.synapz.paintball.players;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.arenas.ArenaManager;
import me.synapz.paintball.countdowns.ArenaStartCountdown;
import me.synapz.paintball.countdowns.RotationCountdown;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.storage.PlayerData;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.storage.files.UUIDPlayerDataFile;
import me.synapz.paintball.utils.Messenger;
import me.synapz.paintball.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.ITALIC;
import static org.bukkit.ChatColor.RESET;

public class RotationPlayer {

    private static final Map<String, RotationPlayer> rotationPlayers = new HashMap<>();

    public static List<RotationPlayer> getRotationPlayers() {
        return new ArrayList<>(rotationPlayers.values());
    }

    public static void removePlayer(String name) {
        RotationPlayer rotationPlayer = rotationPlayers.get(name);
        if (rotationPlayer != null) {
            rotationPlayers.remove(name);
        }
    }

    private final Player player;

    public RotationPlayer(Player player) {
        this.player = player;

        Arena nextArena = ArenaManager.getArenaManager().getNextArena();

        if (nextArena == null) {
            Messenger.error(player, "No arenas to join.");
            return;
        }

        switch (nextArena.getState()) {
            case STOPPING:
            case STARTING:
            case IN_PROGRESS:
                if (nextArena.ALLOW_JOIN_IN_PROGRESS) {
                    Arena arena = ArenaManager.getArenaManager().getNextArena();
                    Team team = arena.getTeamWithLessPlayers();

                    // do everything for joining lobby & arena now to join while live
                    new PlayerData(player);
                    UUIDPlayerDataFile uuidPlayerDataFile = new UUIDPlayerDataFile(player.getUniqueId());
                    uuidPlayerDataFile.savePlayerInformation();

                    ArenaPlayer arenaPlayer = arena.makeArenaPlayer(arena, team, player);

                    team.playerJoinTeam();

                    if (ArenaStartCountdown.tasks.get(this) instanceof ArenaStartCountdown) {
                        ArenaStartCountdown countdown = (ArenaStartCountdown) ArenaStartCountdown.tasks.get(this);
                        countdown.addPlayerToStartLocations(player, player.getLocation());
                    }

                    // must be called after it is created
                    arenaPlayer.giveItems();
                    arena.sendCommands(player, arena.JOIN_COMMANDS);
                    arena.cachedHeads.put(player.getUniqueId(), Utils.getSkull(player, Settings.THEME + BOLD + Messages.CLICK.getString() + Messenger.SUFFIX + RESET + Settings.SECONDARY + Messages.TELEPORT_TO.getString() + ITALIC + team.getChatColor() + player.getPlayer().getName()));
                    arena.remakeSpectatorInventory();
                    return;
                } else {
                    nextArena.joinSpectate(player);
                }
                break;
            case WAITING:
                // if already put into lobby, add them too
                if (nextArena.getLobbyPlayers().size() > 0) {
                    new LobbyPlayer(ArenaManager.getArenaManager().getNextArena(), ArenaManager.getArenaManager().getNextArena().getTeamWithLessPlayers(), player);
                    return;
                }

                rotationPlayers.put(player.getName(), this);
                int size = rotationPlayers.values().size();

                Messenger.success(player, "Next arena is " + ChatColor.GRAY + nextArena.getName());


                if (size >= nextArena.getMin()) {
                    new RotationCountdown();
                }
                break;
            case DISABLED:
            case NOT_SETUP:
            case REMOVED:
                Messenger.error(player, "No arenas to join.");
                break;
        }
    }

    public Player getPlayer() {
        return player;
    }
}

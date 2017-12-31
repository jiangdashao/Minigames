package me.synapz.paintball.players;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.enums.Tag;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.MessageBuilder;
import org.bukkit.entity.Player;

import static me.synapz.paintball.storage.Settings.SECONDARY;

public class FFAArenaPlayer extends ArenaPlayer {

    public FFAArenaPlayer(Arena arena, Team team, Player player) {
        super(arena, team, player);
    }

    // FFA will have 16 different colors, so we just want the normal colors here
    public void sendShotMessage(String action, ArenaPlayer died) {
        if (action == null) {
            action = "shot";
        }

        if (action.isEmpty()) {
            action = "shot";
        }

        String message = new MessageBuilder(Messages.SHOT_PLAYER_FORMAT)
                .replace(Tag.TEAM_COLOR, team.getChatColor() + "")
                .replace(Tag.PLAYER, player.getName())
                .replace(Tag.SECONDARY, SECONDARY)
                .replace(Tag.ACTION, action)
                .replace(Tag.DIED_TEAM_COLOR, died.getTeam().getChatColor() + "")
                .replace(Tag.DIED, died.getPlayer().getName()).build();

        arena.broadcastMessage(message);
    }
}
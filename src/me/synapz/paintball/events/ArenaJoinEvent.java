package me.synapz.paintball.events;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.players.ArenaPlayer;

public class ArenaJoinEvent extends ArenaEvent {

    private ArenaPlayer player;

    public ArenaJoinEvent(ArenaPlayer player, Arena arena) {
        super(arena);
        this.player = player;
    }

    public ArenaPlayer getPlayer() {
        return player;
    }
}

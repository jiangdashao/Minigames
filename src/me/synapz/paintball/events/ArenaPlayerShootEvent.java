package me.synapz.paintball.events;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.players.ArenaPlayer;

public class ArenaPlayerShootEvent extends ArenaEvent {

    private ArenaPlayer player;

    public ArenaPlayerShootEvent(ArenaPlayer player) {
        super(player.getArena());
        this.player = player;
    }

    public ArenaPlayer getPlayer() {
        return player;
    }
}

package me.synapz.paintball.events;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.players.ArenaPlayer;
import me.synapz.paintball.players.PaintballPlayer;

public class ArenaLeaveEvent extends ArenaEvent {

    private PaintballPlayer player;

    public ArenaLeaveEvent(PaintballPlayer player, Arena arena) {
        super(arena);
        this.player = player;
    }

    public PaintballPlayer getPlayer() {
        return player;
    }
}

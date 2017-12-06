package me.synapz.paintball.events;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.players.PaintballPlayer;

public class ArenaEvent extends PaintballEvent {

    private Arena arena;

    public ArenaEvent(Arena arena) {
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }
}

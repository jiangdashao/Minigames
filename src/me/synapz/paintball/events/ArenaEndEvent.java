package me.synapz.paintball.events;

import me.synapz.paintball.arenas.Arena;

public class ArenaEndEvent extends ArenaEvent {
    public ArenaEndEvent(Arena arena) {
        super(arena);
    }
}

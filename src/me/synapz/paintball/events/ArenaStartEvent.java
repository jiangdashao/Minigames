package me.synapz.paintball.events;

import me.synapz.paintball.arenas.Arena;

public class ArenaStartEvent extends ArenaEvent {
    public ArenaStartEvent(Arena arena) {
        super(arena);
    }
}

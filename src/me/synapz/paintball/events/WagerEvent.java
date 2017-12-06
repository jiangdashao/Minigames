package me.synapz.paintball.events;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.players.PaintballPlayer;

public class WagerEvent extends ArenaEvent {

    private PaintballPlayer paintballPlayer;
    private int amount;
    private WagerResult result;

    public WagerEvent(PaintballPlayer paintballPlayer, Arena arena, int amount, WagerResult result) {
        super(arena);
        this.paintballPlayer = paintballPlayer;
        this.amount = amount;
        this.result = result;
    }

    public PaintballPlayer getPaintballPlayer() {
        return paintballPlayer;
    }

    public int getAmount() {
        return amount;
    }

    public WagerResult getResult() {
        return result;
    }

    public enum WagerResult {
        SUCCESS, FAILURE
    }
}

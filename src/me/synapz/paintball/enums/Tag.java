package me.synapz.paintball.enums;

public enum Tag {

    ARENA,
    SENDER,
    PLAYER,
    AMOUNT,
    TEAM,
    TEAMS,
    STAT,
    STATS,
    ERROR,
    MAX,
    STEPS,
    ARENA_TYPE,
    ARENA_TYPES,
    DIED_TEAM_COLOR,
    COMMAND,
    THEME,
    TIME,
    SECONDARY,
    PREFIX,
    DESCRIPTION,
    CURRENCY,
    LASTS,
    COINS,
    COST,
    TEAM_COLOR,
    RANK,
    PAGE,
    WAGER_AMOUNT,
    WAGER_TOTAL,
    DIED,
    ITEM,
    STATE,
    ACTION;

    @Override
    public String toString() {
        return "%" + super.toString().toLowerCase().replace("_", "-") + "%"; // Turns ARENA into %arena%
    }
}

package me.synapz.paintball.scoreboards;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.enums.ScoreboardLine;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.players.ArenaPlayer;
import me.synapz.paintball.players.SpectatorPlayer;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.ArrayUtil;
import me.synapz.paintball.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.synapz.paintball.storage.Settings.SECONDARY;

public class SpectatorScoreboard extends GameScoreboard {

    private final SpectatorPlayer spectatorPlayer;
    private final Arena arena;
    private final Player player;

    public SpectatorScoreboard(SpectatorPlayer spectatorPlayer) {
        super(spectatorPlayer.getPlayer());

        this.spectatorPlayer = spectatorPlayer;
        this.arena = spectatorPlayer.getArena();
        this.player = spectatorPlayer.getPlayer();

        updateScoreboard();
    }

    public void updateScoreboard() {
        setTitle(null, arena);

        List<String> slotTexts = new ArrayList<>();
        ArrayUtil helper = new ArrayUtil(slotTexts);

        helper.addLine(ScoreboardLine.LINE);

        helper.addLine(ScoreboardLine.WAGER, arena.CURRENCY + arena.getWagerManager().getWager(), Settings.USE_ECONOMY);
        helper.addLine(ScoreboardLine.MONEY, shortenMoney(Settings.ECONOMY.getBalance(player), arena), Settings.USE_ECONOMY);

        helper.addLine(ScoreboardLine.LINE);

        for (int slotNumber = 0; slotNumber < arena.getActiveArenaTeamList().size(); slotNumber++) {
            Team team = arena.getActiveArenaTeamList().get(slotNumber);
            String value = team.getChatColor() + team.getTitleName() + ": " + SECONDARY + (arena.MAX_SCORE - arena.getTeamScore(team));
            slotTexts.add(value);
        }

        helper.addLine(ScoreboardLine.LINE, Settings.USE_ECONOMY);

        setSlotsFromList(slotTexts);
    }

    public SpectatorPlayer getSpectatorPlayer() {
        return spectatorPlayer;
    }
}

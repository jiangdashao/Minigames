package me.synapz.paintball.scoreboards;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.enums.ScoreboardLine;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.players.ArenaPlayer;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.ArrayUtil;
import me.synapz.paintball.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.synapz.paintball.storage.Settings.SECONDARY;

public class ArenaScoreboard extends GameScoreboard {

    private final ArenaPlayer arenaPlayer;
    private final Arena arena;
    private final Player player;

    public ArenaScoreboard(ArenaPlayer arenaPlayer) {
        super(arenaPlayer.getPlayer());

        this.arenaPlayer = arenaPlayer;
        this.arena = arenaPlayer.getArena();
        this.player = arenaPlayer.getPlayer();

        updateScoreboard();
    }

    public void updateScoreboard() {
        setTitle(arenaPlayer.getTeam(), arena);

        List<String> slotTexts = new ArrayList<>();
        ArrayUtil helper = new ArrayUtil(slotTexts);

        int kills = arenaPlayer.getKills() < 0 ? 0 : arenaPlayer.getKills();
        int lives = arenaPlayer.getLives() < 0 ? arena.LIVES : arenaPlayer.getLives();
        int health = arenaPlayer.getHealth() < 0 ? arena.HITS_TO_KILL : arenaPlayer.getHealth();
        String kd  = arenaPlayer.getKd();
        int killStreak = arenaPlayer.getKillStreak() < 0 ? 0 : arenaPlayer.getKillStreak();
        int coins = arenaPlayer.getCoins() < 0 ? 0 : arenaPlayer.getCoins();

        helper.addLine(ScoreboardLine.LINE);

        helper.addLine(ScoreboardLine.LIVES, lives, arena.LIVES > 0);
        helper.addLine(ScoreboardLine.HEALTH, Utils.makeHealth(health));
        helper.addLine(ScoreboardLine.KILLS, kills);
        helper.addLine(ScoreboardLine.KD, kd);

        helper.addLine(ScoreboardLine.KILL_STREAK, killStreak);
        helper.addLine(ScoreboardLine.COIN, coins, arena.COINS);

        if (Settings.USE_ECONOMY) {
            helper.addLine(ScoreboardLine.MONEY, shortenMoney(Settings.ECONOMY.getBalance(player), arena), Settings.USE_ECONOMY);
        }

        helper.addLine(ScoreboardLine.WAGER, arena.CURRENCY + arena.getWagerManager().getWager(), Settings.USE_ECONOMY);

        helper.addLine(ScoreboardLine.LINE);

        // add teams
        for (int slotNumber = 0; slotNumber < arena.getActiveArenaTeamList().size(); slotNumber++) {
            Team team = arena.getActiveArenaTeamList().get(slotNumber);
            String value = team.getChatColor() + team.getTitleName() + ": " + SECONDARY + (arena.MAX_SCORE - arena.getTeamScore(team));
            slotTexts.add(value);
        }


        helper.addLine(ScoreboardLine.LINE);

        setSlotsFromList(slotTexts);
    }

    public ArenaPlayer getArenaPlayer() {
        return arenaPlayer;
    }
}

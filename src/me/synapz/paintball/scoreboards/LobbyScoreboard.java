package me.synapz.paintball.scoreboards;


import me.synapz.paintball.Paintball;
import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.enums.ScoreboardLine;
import me.synapz.paintball.enums.Team;
import me.synapz.paintball.players.LobbyPlayer;
import me.synapz.paintball.players.PaintballPlayer;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.ArrayUtil;
import me.synapz.paintball.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.synapz.paintball.storage.Settings.SECONDARY;

public class LobbyScoreboard extends GameScoreboard {

    private final LobbyPlayer lobbyPlayer;
    private final Arena arena;
    private final Player player;

    public LobbyScoreboard(LobbyPlayer lobbyPlayer) {
        super(lobbyPlayer);

        this.lobbyPlayer = lobbyPlayer;
        this.arena = lobbyPlayer.getArena();
        this.player = lobbyPlayer.getPlayer();

        updateScoreboard();
    }

    public void updateScoreboard() {
        setTitle(lobbyPlayer.getTeam(), arena);

        List<String> slotTexts = new ArrayList<>();
        ArrayUtil helper = new ArrayUtil(slotTexts);

        helper.addLine(ScoreboardLine.LINE);

        helper.addLine(ScoreboardLine.WAGER, arena.CURRENCY + arena.getWagerManager().getWager(), Settings.USE_ECONOMY);
        helper.addLine(ScoreboardLine.STATUS, arena.getStateAsString());
        helper.addLine(ScoreboardLine.TEAM, lobbyPlayer.getTeam().getChatColor() + lobbyPlayer.getTeam().getTitleName());
        helper.addLine(ScoreboardLine.MODE, arena.getArenaType().getShortName().toUpperCase());
        helper.addLine(ScoreboardLine.PLAYERS, arena.getLobbyPlayers().size() + Settings.SECONDARY + "/" + Settings.THEME + arena.getMax());

        helper.addLine(ScoreboardLine.LINE);

        for (int slotNumber = 0; slotNumber < arena.getActiveArenaTeamList().size(); slotNumber++) {
            Team team = arena.getActiveArenaTeamList().get(slotNumber);
            String value = team.getChatColor() + team.getTitleName() + ": " + SECONDARY + team.getSize();
            slotTexts.add(value);
        }

        helper.addLine(ScoreboardLine.LINE);

        setSlotsFromList(slotTexts);
    }

    public void updateNametags() {
        for (PaintballPlayer pbPlayer : arena.getAllPlayers().values()) {
            if (player.getScoreboard() == null || player.getScoreboard() == null || player.getScoreboard().getTeam(pbPlayer.getTeam().getTitleName()) == null) {
                continue;
            }

            final org.bukkit.scoreboard.Team playerTeam = player.getScoreboard().getTeam(pbPlayer.getTeam().getTitleName());
            playerTeam.setAllowFriendlyFire(false);

            if (!pbPlayer.getArena().NAMETAGS && Paintball.getInstance().IS_1_9) // be enabled in 1.8
                playerTeam.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, org.bukkit.scoreboard.Team.OptionStatus.NEVER);

            playerTeam.setPrefix(String.valueOf(pbPlayer.getTeam().getChatColor()));
            playerTeam.addPlayer(pbPlayer.getPlayer());
        }
    }

    public LobbyPlayer getLobbyPlayer() {
        return lobbyPlayer;
    }
}


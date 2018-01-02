package me.synapz.paintball.countdowns;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.arenas.ArenaManager;
import me.synapz.paintball.players.LobbyPlayer;
import me.synapz.paintball.players.RotationPlayer;
import me.synapz.paintball.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RotationCountdown extends PaintballCountdown {

    public RotationCountdown() {
        super(60); // 60 second timer

        for (RotationPlayer rotationPlayer : RotationPlayer.getRotationPlayers()) {
            Player player = rotationPlayer.getPlayer();

            MessageUtil.sendTitle(player, ChatColor.GREEN + "Joining arena in 60s", "", 3 * 20, 3 * 20, 3 * 20);
        }
    }

    public void onFinish() {
        for (RotationPlayer rotationPlayer : RotationPlayer.getRotationPlayers()) {
            Player player = rotationPlayer.getPlayer();
            new LobbyPlayer(ArenaManager.getArenaManager().getNextArena(), ArenaManager.getArenaManager().getNextArena().getTeamWithLessPlayers(), player);
        }
    }

    // Called every iteration of run()
    public void onIteration() {  }

    // Checks that must be full-filled in order to run, if this is not met, then it will cancel
    public boolean stop() {
        Arena arena = ArenaManager.getArenaManager().getNextArena();
        return arena == null || arena.getState() != Arena.ArenaState.WAITING;
    }

    // Some countdowns have an interval to do things (ex: every 15 seconds print hi). This checks if there is an interval (set return true for no interval)
    public boolean intervalCheck() { return false; }


}

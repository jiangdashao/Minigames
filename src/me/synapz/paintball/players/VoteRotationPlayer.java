package me.synapz.paintball.players;

import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class VoteRotationPlayer extends RotationPlayer {

    public VoteRotationPlayer(Player player) {
        super(player);

        // TODO: scoreboard
        player.getInventory().setItem(0, Utils.makeItem(Material.PAPER, Messages.ARENA_MENU_VOTE_NAME.getString(), null));

        if (Settings.getSettings().getBungeeFile().isBungeeMode()) {
            player.getInventory().setItem(8, Utils.makeItem(Material.BED, Messages.BACK_TO_HUB.getString(), null));
        }
    }

}
package me.synapz.paintball.listeners;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.enums.Tag;
import me.synapz.paintball.events.WagerEvent;
import me.synapz.paintball.events.WagerPayoutEvent;
import me.synapz.paintball.players.ArenaPlayer;
import me.synapz.paintball.players.PaintballPlayer;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.MessageBuilder;
import me.synapz.paintball.utils.Messenger;
import me.synapz.paintball.wager.WagerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class WagerListener implements Listener {

    @EventHandler
    public void onWager(WagerEvent event) {
        Arena arena = event.getArena();
        PaintballPlayer paintballPlayer = event.getPaintballPlayer();
        WagerManager wagerManager = arena.getWagerManager();

        int amount = event.getAmount();
        String stringAmount = String.valueOf(amount);

        WagerEvent.WagerResult result = event.getResult();

        switch (result) {
            case SUCCESS:
                wagerManager.addWager(amount);
                for (PaintballPlayer paintballPlayer1 : arena.getAllPlayers().values()) {
                    Messenger.info(paintballPlayer1.getPlayer(), Messages.PLAYER_WAGERED.getString()
                            .replace(Tag.PLAYER.toString(), paintballPlayer.getPlayer().getName())
                            .replace(Tag.WAGER_AMOUNT.toString(), stringAmount)
                            .replace(Tag.WAGER_TOTAL.toString(), wagerManager.getWager() + "")
                            .replace(Tag.CURRENCY.toString(), arena.CURRENCY));
                }
                // Updates the current wager amount to scoreboards
                arena.updateAllScoreboard();
                break;
            case FAILURE:
                Messenger.error(paintballPlayer.getPlayer(), Messages.VAULT_NOT_ENOUGH_MONEY);
                break;
        }
    }

    @EventHandler
    public void onWagerPayout(WagerPayoutEvent event) {
        List<ArenaPlayer> arenaPlayers = event.getArenaPlayers();
        double amount = event.getAmount();
        double amountToPay = amount / (double) arenaPlayers.size();

        for (ArenaPlayer arenaPlayer : arenaPlayers) {
            Settings.ECONOMY.depositPlayer(arenaPlayer.getPlayer().getName(), amountToPay);
            Messenger.msg(arenaPlayer.getPlayer(), new MessageBuilder(Messages.WAGER_GAIN_AMOUNT).replace(Tag.CURRENCY, arenaPlayer.getArena().CURRENCY).replace(Tag.AMOUNT, ((int) amountToPay) + "").build());
        }
    }
}

package me.synapz.paintball.arenas;

import me.synapz.paintball.countdowns.RotationCountdown;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.players.RotationPlayer;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.Messenger;
import me.synapz.paintball.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.stream.Collectors;

public class VoteManager {

    private final Map<Arena, List<UUID>> voteCounts = new HashMap<>();

    public VoteManager() {
        reset();
    }

    public void openArenaVoteMenu(Player player) {
        String title = Messages.ARENA_MENU_VOTE_NAME.getString();
        List<Arena> arenas = ArenaManager.getArenaManager().getArenas().values().stream().filter(a -> a.getState() == Arena.ArenaState.WAITING).collect(Collectors.toList());
        int size = arenas.size();

        if (size == 0) {
            Messenger.error(player, Messages.NO_ARENAS);
            return;
        }

        if (size % 9 != 0) {
            size = (int) (Math.ceil(size / 9.0) * 9);
        }

        Inventory inventory = Bukkit.createInventory(null, size, title);

        for (int i = 0; i < arenas.size(); i++) {
            Arena arena = arenas.get(i);
            inventory.setItem(i, Utils.makeItem(Material.PAPER, arena.getName(), new ArrayList<String>() {{
                add(Settings.THEME + "Game: " + Settings.SECONDARY + arena.getArenaType().getFullName());
                add(Settings.THEME + "Min: " + Settings.SECONDARY + arena.getMin());
                add(Settings.THEME + "Max: " + Settings.SECONDARY + arena.getMax());
                add(Settings.THEME + "Teams: " + Settings.SECONDARY + arena.getFullTeamList().size());
            }}));
        }

        player.openInventory(inventory);
    }

    public VoteResult addVote(Arena arena, UUID uuid) {
        if (voteCounts.get(arena) == null) {
            return VoteResult.NO_ARENAS;
        }
        if (voteCounts.get(arena).contains(uuid)) {
            return VoteResult.ALREADY_VOTED;
        }
        // remove player from other arenas
        List<List<UUID>> playersToRemove = new ArrayList<List<UUID>>() {{
            for (List<UUID> uuids : voteCounts.values()) {
                if (uuids.contains(uuid)) {
                    add(uuids);
                }
            }
        }};

        for (List list : playersToRemove) {
            list.remove(uuid);
        }

        voteCounts.get(arena).add(uuid);

        if (ArenaManager.getArenaManager().getVoteManager().isTopArenaReady()) {
            new RotationCountdown();
        }

        return VoteResult.SUCCESS;
    }

    public void reset() {
        List<Arena> arenas = ArenaManager.getArenaManager().getArenas().values().stream().filter(a -> a.getState() == Arena.ArenaState.WAITING).collect(Collectors.toList());

        for (Arena arena : arenas) {
            voteCounts.put(arena, new ArrayList<>());
        }
    }

    public Arena getTopArena() {
        List<Integer> intList = new ArrayList<Integer>() {{
            for (List<UUID> uuids : voteCounts.values())
                add(uuids.size());
        }};

        Collections.sort(intList);
        Collections.reverse(intList);

        int maxSize = intList.get(0);

        if (maxSize == 0) {
            return null;
        }

        for (Arena arena : voteCounts.keySet()) {
            if (voteCounts.get(arena).size() == maxSize)
                return arena;
        }

        return null;
    }

    public boolean isTopArenaReady() {
        Arena top = getTopArena();

        if (top == null) {
            return false;
        }

        return RotationPlayer.getRotationPlayers().size() >= top.getMin();
    }

    public enum VoteResult {

        SUCCESS,
        ALREADY_VOTED,
        NO_ARENAS,


    }

}

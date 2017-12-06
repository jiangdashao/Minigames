package me.synapz.paintball.scoreboards;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.enums.Tag;
import me.synapz.paintball.utils.MessageBuilder;
import me.synapz.paintball.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameScoreboard {

    private static HashMap<UUID, GameScoreboard> players = new HashMap<>();

    public static boolean hasScore(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public static GameScoreboard getByPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static GameScoreboard removeScore(Player player) {
        return players.remove(player.getUniqueId());
    }

    private Scoreboard scoreboard;
    private Objective sidebar;

    public GameScoreboard(Player player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        // Create Teams
        for(int i=1; i<=15; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }

        player.setScoreboard(scoreboard);
        players.put(player.getUniqueId(), this);
    }

    public void setTitle(me.synapz.paintball.enums.Team team, Arena arena) {
        String title = (team != null ? team.getChatColor() + "█ " : "") + new MessageBuilder(Messages.SCOREBOARD_TITLE).replace(Tag.TIME, convertToNumberFormat(Utils.getCurrentCounter(arena) == -1 ? arena.LOBBY_COUNTDOWN : Utils.getCurrentCounter(arena))).build();
        title = ChatColor.translateAlternateColorCodes('&', title);
        sidebar.setDisplayName(title.length()>32 ? title.substring(0, 32) : title);
    }

    public void setSlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if(!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot);
        }

        text = ChatColor.translateAlternateColorCodes('&', text);
        String pre = getFirstSplit(text);
        String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(text));
        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void removeSlot(int slot) {
        String entry = genEntry(slot);
        if(scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }

    public void setSlotsFromList(List<String> list) {
        while(list.size()>15) {
            list.remove(list.size()-1);
        }

        int slot = list.size();

        if(slot<15) {
            for(int i=(slot +1); i<=15; i++) {
                removeSlot(i);
            }
        }

        for(String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private String getFirstSplit(String s) {
        return s.length()>16 ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if(s.length()>32) {
            s = s.substring(0, 32);
        }
        return s.length()>16 ? s.substring(16) : "";
    }

    protected String shortenMoney(double money, Arena arena) {
        double calculatedMoney = money;
        String suffix = "";

        if (money >= 1000) {
            if (money >= 1000000) {
                calculatedMoney = money / 1000000;
                suffix = "M";
            } else {
                calculatedMoney = money / 1000;
                suffix = "K";
            }
        }

        return String.format("%s%.2f%s", arena.CURRENCY, calculatedMoney, suffix);
    }

    protected String convertToNumberFormat(int time) {
        int minutes = time/60;
        int seconds = time%60;
        return String.format("%d:" + (seconds < 10 ? "0" : "") + "%d", minutes, seconds);
    }

}

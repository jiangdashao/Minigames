package me.synapz.paintball.utils;

import org.bukkit.entity.Player;

public class TitleUtil {

    public static void sendTitle(Player player, String header, String footer) {
        try {
            player.sendTitle(header, footer);
        } catch (NoSuchMethodError e) {
            return;
        }
    }

    public static void resetTitle(Player player) {
        try {
            player.resetTitle();
        } catch (NoSuchMethodError e) {
            return;
        }
    }

}

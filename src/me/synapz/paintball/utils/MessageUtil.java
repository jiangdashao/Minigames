package me.synapz.paintball.utils;

import org.bukkit.entity.Player;

public class MessageUtil {

    public static void sendTitle(Player player, String header, String footer, int fadeIn, int stay, int fadeOut) {
        try {
            player.sendTitle(header, footer, fadeIn, stay, fadeOut);
        } catch (NoSuchMethodError e) {
            try {
                player.sendTitle(header, footer, fadeIn, stay, fadeOut);
            } catch (NoSuchMethodError e0) {
                return;
            }
            return;
        }
    }

    public static void resetTitle(Player player) {
        try {
            player.sendTitle("", "");
        } catch (NoSuchMethodError e) {
            return;
        }
    }

}

package me.synapz.paintball.utils;

import me.synapz.paintball.commands.PaintballCommand;
import me.synapz.paintball.enums.CommandType;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.storage.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.synapz.paintball.storage.Settings.SECONDARY;
import static me.synapz.paintball.storage.Settings.THEME;
import static org.bukkit.ChatColor.*;

public class Messenger {

    public static final String SUFFIX = SECONDARY + " » ";
    
    public static void error(CommandSender sender, Messages...msgs) {
        for (Messages msg : msgs)
            info(sender, RED + msg.getString());
    }

    public static void error(CommandSender sender, String... msg) {
        for (String str : msg)
            info(sender, RED + str);
    }

    public static void success(CommandSender sender, Messages msg) {
        info(sender, GREEN + msg.getString());
    }

    public static void success(CommandSender sender, String... msg) {
        for (String str : msg)
            info(sender, GREEN + str);
    }

    public static void info(CommandSender sender, String...msg) {
        for (String str : msg) {
            for (String str1 : str.split("/n")) {
                sender.sendMessage(Messages.PREFIX.getString() + Settings.THEME + str1);
            }
        }
    }

    public static void info(CommandSender sender, Messages...msg) {
        for (Messages str : msg) {
            for (String str1 : str.getString().split("/n")) {
                sender.sendMessage(Messages.PREFIX.getString() + Settings.THEME + str1);
            }
        }
    }

    public static void msg(CommandSender sender, String...msg) {
        for (String str : msg)
            sender.sendMessage(str);
    }

    public static void titleMsg(CommandSender sender, boolean inText, String msg) {
        if (inText)
            info(sender, msg);

        if (sender instanceof Player)
            TitleUtil.sendTitle(((Player) sender), Messages.PREFIX.getString(), msg);
    }

    // Checks to see if a player has a permission, returns true if they do false if they don't
    public static boolean permissionValidator(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true;
        } else {
            error(player, Messages.NO_PERMISSION);
            return false;
        }
    }

    // Checks to see if a player has a permission to break/create a sign
    public static boolean signPermissionValidator(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true;
        } else {
            error(player, Messages.NO_SIGN_PERMISSION);
            return false;
        }
    }

    // Sends a message if there is some type of wrong usage
    public static void wrongUsage(PaintballCommand command, Player player, Usage usage) {
        if (usage.equals(Usage.TO_MANY_ARGS)) {
            error(player, Messages.TOO_MANY_ARGUMENTS.getString(), command.getCorrectUsage());
        } else {
            error(player, Messages.NOT_ENOUGH_ARGUMENTS.getString(), command.getCorrectUsage());
        }
    }

    // Get's the help associated with the command type
    public static String getHelpTitle(CommandType type) {
        String title = Messages.COMMAND_TITLE.getString();
        if (type == CommandType.ADMIN) {
            title += " Admin";
        } else if (type == CommandType.ARENA) {
            title += " Arena";
        }
        return SECONDARY + STRIKETHROUGH + Utils.makeSpaces(23) + RESET + DARK_GRAY + BOLD + STRIKETHROUGH + "[-" + RESET + THEME + " " + BOLD + title + " " + DARK_GRAY + BOLD + STRIKETHROUGH + "-]" + SECONDARY + STRIKETHROUGH + Utils.makeSpaces(23);
    }

    public static String createPrefix(String suffix){
        return THEME + BOLD + suffix + SUFFIX;
    }

    public enum Usage {
        TO_MANY_ARGS,
        NOT_ENOUGH_ARGS
    }
}

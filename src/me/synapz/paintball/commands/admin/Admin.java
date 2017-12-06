package me.synapz.paintball.commands.admin;


import me.synapz.paintball.commands.CommandManager;
import me.synapz.paintball.commands.PaintballCommand;
import me.synapz.paintball.enums.CommandType;
import me.synapz.paintball.enums.Messages;
import org.bukkit.entity.Player;

public class Admin extends PaintballCommand {

    private CommandType type = CommandType.PLAYER;

    public Admin(CommandType t) {
        this.type = t;
    }

    public void onCommand(Player player, String[] args) {
        CommandManager.displayHelp(player, CommandType.ADMIN);
    }

    public String getArgs() {
        return "";
    }

    public String getPermission() {
        return "paintball.admin.help";
    }

    public String getName() {
        return "admin";
    }

    public Messages getInfo() {
        return Messages.COMMAND_ADMIN_INFO;
    }

    public CommandType getCommandType() {
        return type;
    }

    public int getMaxArgs() {
        return 1;
    }

    public int getMinArgs() {
        return 1;
    }

}

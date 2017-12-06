package me.synapz.paintball.commands;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.arenas.ArenaManager;
import me.synapz.paintball.commands.player.Join;
import me.synapz.paintball.enums.CommandType;
import me.synapz.paintball.enums.Messages;
import me.synapz.paintball.enums.Tag;
import me.synapz.paintball.utils.MessageBuilder;
import me.synapz.paintball.utils.Messenger;
import org.bukkit.entity.Player;

public abstract class ArenaCommand extends PaintballCommand {

    protected Player player;
    protected String[] args;
    protected Arena arena;

    public void onCommand(Player player, String[] args) {
        this.player = player;
        this.args = args;

        // This means specifying an arena is optional (/pb join)
        String rawArenaName;
        try {
            rawArenaName = args[getArenaArg()];
        } catch (ArrayIndexOutOfBoundsException exc) {
            if (this instanceof Join)
                return;

            onCommand();
            return;
        }

        this.arena = ArenaManager.getArenaManager().getArena(rawArenaName);

        if (arena == null && handleConditionsInSuperClass) {
            Messenger.error(player, new MessageBuilder(Messages.INVALID_ARENA).replace(Tag.ARENA,
                rawArenaName).build());
            return;
        }

        if (!(this instanceof TeamCommand)) { // make sure this doesn't call onCommand if it is a team command, because it still has to check for team
            onCommand();
        } else {
            return;
        }
    }

    public abstract void onCommand();

    public abstract String getName();

    public abstract Messages getInfo();

    public abstract String getArgs();

    public abstract String getPermission();

    public abstract CommandType getCommandType();

    public abstract int getMaxArgs();

    public abstract int getMinArgs();

    protected abstract int getArenaArg();
}

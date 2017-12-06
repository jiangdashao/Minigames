package me.synapz.paintball;

import me.synapz.paintball.arenas.ArenaManager;
import me.synapz.paintball.bungee.BungeeManager;
import me.synapz.paintball.coin.CoinItemListener;
import me.synapz.paintball.commands.CommandManager;
import me.synapz.paintball.compat.NoCheatPlusCompat;
import me.synapz.paintball.enums.Databases;
import me.synapz.paintball.listeners.*;
import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.storage.database.ConnectionPool;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Paintball extends JavaPlugin implements Listener {

    public boolean IS_1_9;
    private static Paintball instance;
    private BungeeManager bungeeManager;

    @Override
    public void onEnable() {
        String myUserString = "Welcome!"
                + "\nYour user ID is %%__USER__%%"
                + "\nThis resource ID is %%__RESOURCE__%%"
                + "\nAnd the unique download ID is %%__NONCE__%%";

        instance = this;

        this.IS_1_9 = is1_9();

        new Settings(this);
        this.bungeeManager = new BungeeManager(this);
        this.setupVault();

        CommandManager commandManager = new CommandManager();
        commandManager.init();

        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new Listeners(this), this);
        pm.registerEvents(new PaintballSigns(), this);
        pm.registerEvents(new ChatSystem(), this);
        pm.registerEvents(new LeaderboardSigns(), this);
        pm.registerEvents(new CoinItemListener(), this);
        pm.registerEvents(new WagerListener(), this);
        pm.registerEvents(this, this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        if (IS_1_9)
            pm.registerEvents(new Listeners1_9(), this);

        getCommand("paintball").setExecutor(commandManager);

        ArenaManager.getArenaManager().updateAllSignsOnServer();

        Metrics metrics = new Metrics(this);

        if (getServer().getPluginManager().getPlugin("NoCheatPlus") != null)
            NoCheatPlusCompat.addHook();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
            new PlaceholderRequest(this).hook();
    }

    @Override
    public void onDisable() {
        ArenaManager.getArenaManager().stopArenas();

        if (Settings.HOLOGRAPHIC_DISPLAYS)
            Settings.ARENA.deleteLeaderboards();

        if (Databases.ENABLED.getBoolean())
            ConnectionPool.closeDataSource();
    }

    private void setupVault() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            Settings.USE_CHAT = false;
            Settings.USE_ECONOMY = false;
            return;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        RegisteredServiceProvider<Chat> rspchat = getServer().getServicesManager().getRegistration(Chat.class);

        if (rsp == null) {
            Settings.USE_ECONOMY = false;
        } else if (rspchat == null) {
            Settings.USE_CHAT = false;
        } else {
            Settings.CHAT = rspchat.getProvider();
            Settings.ECONOMY = rsp.getProvider();

            Settings.USE_CHAT = Settings.CHAT != null;
            Settings.USE_ECONOMY = Settings.ECONOMY != null;
        }
    }

    public static Paintball getInstance() {
        return instance;
    }

    public BungeeManager getBungeeManager() {
        return bungeeManager;
    }

    public boolean is1_9() {
        try {
            Sound.BLOCK_COMPARATOR_CLICK.toString();
            return true;
        } catch (NoSuchFieldError exc) {
            return false;
        }
    }
}

package me.synapz.paintball.storage.files;

import org.bukkit.plugin.Plugin;

public class BungeeFile extends PaintballFile {

    private boolean stateAsMotd = false;
    private boolean bungeeMode = false;
    private String hubServerId;

    public BungeeFile(Plugin pb) {
        super(pb, "bungee.yml");

        if (!this.fileConfig.isSet("bungee-mode")) {
            this.fileConfig.set("bungee-mode", false);
            saveFile();
        }

        if (!this.fileConfig.isSet("hub-server-id")) {
            this.fileConfig.set("hub-server-id", "not-set");
            saveFile();
        }

        if (!this.fileConfig.isSet("state-as-motd")) {
            this.fileConfig.set("state-as-motd", true);
            saveFile();
        }

        stateAsMotd = this.fileConfig.getBoolean("state-as-motd");
        bungeeMode = this.fileConfig.getBoolean("bungee-mode");
        hubServerId = this.fileConfig.getString("hub-server-id");
    }

    public boolean isBungeeMode() {
        return bungeeMode;
    }

    public String getHubServerId() {
        return hubServerId;
    }

    public boolean isStateAsMotd() {
        return stateAsMotd;
    }
}

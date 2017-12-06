package me.synapz.paintball.locations;

import me.synapz.paintball.arenas.Arena;
import me.synapz.paintball.storage.Settings;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class SignLocation extends PaintballLocation {

    public enum SignLocations {
        LEADERBOARD,
        JOIN,
        SKULL,
        AUTOJOIN,
        SPECTATE;

        @Override
        public String toString() {
            return super.toString().toLowerCase().replace(super.toString().toLowerCase().toCharArray()[0], super.toString().toUpperCase().toCharArray()[0]);
        }
    }

    protected final SignLocations type;

    public SignLocation(Arena a, Location loc, SignLocations type) {
        super(a, loc);
        this.type = type;

        setLocation();
        arena.addSignLocation(this);
    }

    public SignLocation(Arena arena, String rawLocation) {
        super(arena, rawLocation);
        this.type = SignLocations.JOIN;
    }

    public SignLocation(SignLocations type, String rawLocation) {
        super(null, rawLocation);
        this.type = type;
    }

    // Just for autojoinin and leaderboard signs, they have NO arena set to them
    public SignLocation(Location loc, SignLocations type) {
        super(null, loc);
        this.type = type;

        if (!(this instanceof SkullLocation)) {
            setLocation();
            Settings.ARENA.addSign(this);
        }
    }

    public SignLocations getType() {
        return type;
    }

    // Remove a sign location from arenas.yml
    public void removeSign() {
        String path;

        if (type == SignLocations.LEADERBOARD || type == SignLocations.AUTOJOIN || type == SignLocations.SKULL) {
            path = "Signs." + type.toString();
            Settings.ARENA.removeSign(this);
        } else {
            path = arena.getPath() + type.toString();
            arena.removeSignLocation(this);
        }
        List<String> signsList = Settings.ARENA_FILE.getStringList(path);

        if (signsList == null || !(signsList.contains(toString()))) {
            return;
        }
        signsList.remove(toString());
        Settings.ARENA_FILE.set(path, signsList);
        Settings.ARENA.saveFile();
    }

    protected void setLocation() {
        String path = type == SignLocations.LEADERBOARD || type == SignLocations.AUTOJOIN || type == SignLocations.SKULL ? "Signs." + type.toString() : arena.getPath() + type.toString();
        List<String> signsList = Settings.ARENA_FILE.getStringList(path);

        if (signsList == null)
            signsList = new ArrayList<>();
        if (signsList.contains(toString()))
            return;

        signsList.add(toString());
        Settings.ARENA_FILE.set(path, signsList);
        Settings.ARENA.saveFile();
    }
}

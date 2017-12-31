package me.synapz.paintball.storage;

import me.synapz.paintball.Paintball;
import me.synapz.paintball.players.PaintballPlayer;
import me.synapz.paintball.utils.ExperienceManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {

    private static final Map<UUID, PlayerData> data = new HashMap<>();

    private final ExperienceManager experienceManager;
    private final Player player;

    private final Location location;
    private final int food;
    private final GameMode gamemode;
    private final boolean allowFlight;
    private final boolean flying;
    private final double speed;
    private final int exp;
    private final double health;
    private final double healthScale;
    private final ItemStack offHand;
    private final ItemStack[] inventory;
    private final ItemStack helmet;
    private final ItemStack chestplate;
    private final ItemStack leggings;
    private final ItemStack boots;

    /**
     * Safety stores a player's state
     * @param paintballPlayer Player's data to store
     */
    public PlayerData(Player paintballPlayer) {
        this.player = paintballPlayer;
        this.experienceManager = new ExperienceManager(player);
        this.location = player.getLocation();
        this.food = player.getFoodLevel();
        this.gamemode = player.getGameMode();
        this.allowFlight = player.getAllowFlight();
        this.flying = player.isFlying();
        this.speed = player.getWalkSpeed();
        this.exp = experienceManager.getCurrentExp();
        this.health = player.getHealth();
        this.healthScale = player.getHealthScale();
        if (Paintball.getInstance().IS_1_9)
            this.offHand = player.getInventory().getItemInOffHand();
        else
            this.offHand = null;

        if(Paintball.getInstance().is1_9())
            this.inventory = player.getInventory().getStorageContents();
        else
            this.inventory = player.getInventory().getContents();

        this.helmet = player.getInventory().getHelmet();
        this.chestplate = player.getInventory().getChestplate();
        this.leggings = player.getInventory().getLeggings();
        this.boots = player.getInventory().getBoots();

        data.put(player.getUniqueId(), this);
    }

    /**
     * Resets a player's data from all the stored values
     */
    public void restore() {
        player.teleport(location);
        player.setFoodLevel(food);
        player.setGameMode(gamemode);
        player.setAllowFlight(allowFlight);
        player.setFlying(flying);
        player.setWalkSpeed((float) speed);
        experienceManager.setExp(exp);

        if (health > 20d || health < 0) {
            player.setHealth(20);
        } else {
            player.setHealth(health);
        }

        player.setHealthScale(healthScale);
        player.getInventory().setContents(inventory);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        if (Paintball.getInstance().IS_1_9)
            player.getInventory().setItemInOffHand(offHand);
        player.updateInventory();

        data.remove(player.getUniqueId());
    }

    /**
     * Resets a player's state by getting it from the hashmap if it is there, if not don't do anything
     * @param player Player's state to reset
     * @return Whether the state was reset or not
     */
    public static boolean resetFromRam(Player player) {
        PlayerData playerData = data.get(player.getUniqueId());

        if (playerData != null)
            playerData.restore();
        else
            return false;

        return true;
    }

    /**
     * When a player joins back after leaving, remove them this way their information is given from file and not from memeory
     * @param player Player to remove from playerdata
     */
    public static void removePlayer(Player player) {
        data.remove(player.getUniqueId());
    }
}
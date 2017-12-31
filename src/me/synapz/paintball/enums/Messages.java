package me.synapz.paintball.enums;

import me.synapz.paintball.storage.Settings;
import me.synapz.paintball.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static me.synapz.paintball.storage.Settings.THEME;

public enum Messages {

    SIGN_TITLE("&8[" + Tag.THEME + "Paintball&8]"),
    COMMAND_TITLE("&lPaintball"),
    PREFIX("&8[&3Paintball&8] "),

    ARENA_LIST_COMMAND("&9Arenas: " + Tag.ARENA),
    COMMAND_ARENA_INFO("Display all Paintball Arena setup commands"),
    COMMAND_CREATE_INFO("Create a new Arena"),
    COMMAND_SETFLAG_INFO("Set Arena flag point"),
    COMMAND_DELFLAG_INFO("Delete Arena flag point"),
    COMMAND_SETLOCATION_INFO("Set Arena location"),
    COMMAND_DELLOCATION_INFO("Delete Arena location"),
    COMMAND_SETSPECTATE_INFO("Set Arena spectate location"),
    COMMAND_DELSPECTATE_INFO("Delete Arena spectate location"),
    COMMAND_REMOVE_INFO("Remove an arena"),
    COMMAND_RENAME_INFO("Rename an Arena"),
    COMMAND_SETMAX_INFO("Set max number of players"),
    COMMAND_SETMIN_INFO("Set min amount of players"),
    COMMAND_SETTEAMS_INFO("Set teams via ChatColors seperated by commas ex. &1,&b,&c", false),
    COMMAND_STEPS_INFO("List steps of an Arena"),
    COMMAND_WAGER_INFO("Wager money for an Arena."),
    COMMAND_ADDCOIN_INFO("Give coins to a player."),

    REMOVED_ALL_FILES("Removed all files from folder."),
    DOUBLE_COINS("Double coins activated for the rest of the game."),

    COMMAND_CONVERT_INFO("Convert an Arena to a different type"),
    COMMAND_DISABLE_INFO("Disable an Arena"),
    COMMAND_ENABLE_INFO("Enable an Arena"),
    COMMAND_INFO_INFO("Display Arena information"),
    COMMAND_RELOAD_INFO("Reloads all yml files"),
    COMMAND_RESET_INFO("Reset a player's stats"),
    COMMAND_START_INFO("Force start an Arena"),
    COMMAND_STOP_INFO("Force stop an Arena"),
    COMMAND_ADMIN_INFO("Display all Paintball Admin commands"),
    COMMAND_SETHOLO_INFO("Creates a leaderboard hologram"),
    COMMAND_HOLO_INFO("Remove holograms around you"),
    COMMAND_KICK_INFO("Kick a player from Paintball"),

    COMMAND_JOIN_INFO("Join an Arena"),
    COMMAND_LEAVE_INFO("Leave an Arena"),
    COMMAND_LIST_INFO("List of all Arenas"),
    COMMAND_SPECTATE_INFO("Spectate an Arena"),
    COMMAND_STATS_INFO("View player's game statistics"),
    COMMAND_TOP_INFO("View leaderboards."),

    DOWNLOAD_HOLO("Please download plugin HolographicDisplays to use this feature."),
    HOLO_LINK("http://dev.bukkit.org/bukkit-plugins/holographic-displays/"),
    HOLO_SET("Hologram leaderboard set to your location!"),
    IN_ARENA_TO_WAGER("You must be in an arena to wager!"),
    AMOUNT_GREATER_THAN_0("Amount must be greater than zero!"),
    WAGER_IN_ARENA("You must be in an arena to wager!"),
    ENABLE_VAULT("Vault must be enabled for this feature."),
    VAULT_NOT_ENOUGH_MONEY("You do not have enough money!"),
    FLYING_ENABLED("Flying has been enabled"),
    FLYING_DISABLED("Flying has been disabled"),
    INVALID_LAST_LOCATION("You do not have a last location."),
    TELEPORTING_TO_LAST_LOCATION("Teleporting to last location..."),
    CANNOT_USE_WHILE_RIDING("You cannot use an item while riding something."),
    VALID_NUMBER("Please enter a valid number"),
    HOLOGRAMS_REMOVED("Removed &7" + Tag.AMOUNT + " &aholograms."),
    NO_HOLOGRAMS_REMOVED("No holograms were removed."),
    NO_ARENAS("No arenas are currently opened."),
    TARGET_NOT_IN_ARENA("Target is not in an arena!"),
    NOT_IN_ARENA("You are not in an arena."),
    CANNOT_JOIN("Error: arena is not available to join!"),
    CANNOT_SPECTATE("Error: arena is not available to spectate!"),
    LEFT_ARENA("Successfully left arena."),
    ARENA_NOT_SETUP(Tag.ARENA + " has not been fully setup."),
    ARENA_DISABLED(Tag.ARENA + " is disabled."),
    ARENA_NOT_IN_PROGRESS(Tag.ARENA + " is currently not in progress"),
    NOT_FOUND("Target '" + Tag.PLAYER + "' was not found."),
    IN_ARENA("You are already in an arena!"),
    ARENA_NOT_FOUND("No arena named " + Tag.ARENA + " found."),
    PAGE_REAL_NUMBER("Please specify a real number as the page."),
    PAGE_BIGGER("The page cannot be lower than 0"),
    PAGE_FIND_ERROR("Page " + Tag.AMOUNT + "/" + Tag.MAX + " cannot be found."),
    ARENA_CREATE(Tag.ARENA + " successfully created!/n" + Tag.STEPS),
    ARENA_NAME_EXISTS("An arena named " + Tag.ARENA + " already exists!"),
    ARENA_REMOVE("Arena has been removed."),
    ARENA_FORCE_STOPPED(Tag.ARENA + " has been force stopped!"),
    ARENA_CONVERT_SAME_TYPE(Tag.ARENA + " is already a " + Tag.ARENA_TYPE + " arena."),
    ARENA_CONVERT_SUCCESS(Tag.ARENA + " has been converted to " + Tag.ARENA_TYPE),
    INVALID_ARENA(Tag.ARENA + " is an invalid arena."),
    INVALID_TEAM(Tag.TEAM + " is an invalid team. Choose either <" + Tag.TEAMS + ">"),
    INVALID_STAT(Tag.STAT + " is an invalid statistic. Choose either " + Tag.STATS),
    INVALID_COMMAND("Unknown Command! Type /paintball for a list of commands."),
    INTERNAL_ERROR("An internal error has occurred: %error%"),
    NO_PERMISSION("You don't have access to that command!"),
    NO_SIGN_PERMISSION("You don't have access to click that!"),
    NO_CONSOLE_PERMISSION("Console does not have access to that command!"),
    NOT_ENOUGH_ARGUMENTS("Not enough arguments!"),
    TOO_MANY_ARGUMENTS("To many arguments!"),
    NO_TEAMS_SET(Tag.ARENA + " does not have any teams set!"),
    INVALID_ARENA_TYPE("Invalid arena type. Choose either <" + Tag.ARENA_TYPES + ">"),
    CHOOSE_ENABLE_OR_DISABLE(Tag.COMMAND + " is an invalid choice. Use either enable/disable"),
    DISABLE_SUCCESS(Tag.ARENA + " has been disabled!"),
    ENABLE_SUCCESS(Tag.ARENA + " has been enabled!"),
    ARENA_ENABLED(Tag.ARENA + " is already enabled."),
    ARENA_IS_FINISHED("Game is already finished."),
    ARENA_DEFAULT_ACTION("shot"),
    ARENA_START_COUNTDOWN_HEADER("&aStarting"),
    ARENA_START_COUNTDOWN_FOOTER("&7" + Tag.TIME + "&a seconds!"),
    ARENA_LOBBY_COUNTDOWN_HEADER("&aWaiting"),
    ARENA_LOBBY_COUNTDOWN_FOOTER("&7" + Tag.TIME + "&a seconds!"),
    ARENA_MOVE_ERROR("You are not allowed to move items in your inventory!"),
    CORE_DESTROYED("Your Core has been destroyed!"),
    ARENA_SHOP_NAME("&6Coin Shop"),
    ARENA_NO_DUEL_WIELD("You are not allowed to duel wield!"),
    TELEPORTING("&aTeleporting into arena..."),
    ARENA_FLAG_DROP("&lThe " + Tag.SECONDARY + Tag.TEAM + Tag.THEME + " team has dropped the flag!"),
    ARENA_FLAG_SCORE("&lThe " + Tag.SECONDARY + Tag.TEAM + Tag.THEME + " team has scored a flag!"),
    ARENA_FLAG_STEAL("&lPlayer " + Tag.SECONDARY + Tag.SENDER + Tag.THEME + " has stolen " + Tag.SECONDARY + Tag.TEAM + Tag.THEME + "'s flag!"),
    ARENA_JOIN_MESSAGE(Tag.TEAM_COLOR + "" + Tag.SENDER + "&a has joined the arena! &7" + Tag.AMOUNT + "/" + Tag.MAX),
    ARENA_YOU_JOINED("&aYou have joined the arena!"),
    ARENA_JOINED("&aJoined arena"),
    ARENA_SIZE("&7" + Tag.AMOUNT + "/" + Tag.MAX),
    ARENA_TEAM_CHANGE("&aYou are now on the " + Tag.TEAM_COLOR + "" + Tag.TEAM + " Team!"),
    ARENA_CANNOT_BREAK_BLOCKS("You are not allowed to break blocks while in the arena!"),
    ARENA_COMMAND_DISABLED("That command is disabled while in the arena."),
    ARENA_DIE_HEADER("&4&lYou died!"),
    ARENA_DIE_FOOTER("&7Respawning..."),
    CANNOT_ATTACK_OWN_CORE("You cannot attack your own Core!"),
    KICK_PLAYER(Tag.PLAYER + " has been kicked from " + Tag.ARENA),
    PLAYER_NOT_IN_ARENA(Tag.PLAYER + " is not in an arena."),
    BALANCING_TEAMS(Tag.THEME + "Balancing Teams"),
    ERROR_PARSING("Error parsing command"),
    SPACE_FILLED("Sorry, your space has been filled by other player."),
    PLACES_NOT_FOUND("Sorry, no places could not be found in this arena."),
    SKULL_CREATED("Leaderboard skull successfully created!"),
    VAlID_4_NUMBER("Line 4 must be a valid number."),
    CHOOSE_RANK_NUMBER("Choose a rank number, for example: 3"),
    NOW_SPECTATING("Now spectating &7" + Tag.PLAYER + "&a!"),
    YOU_ARE_NOW_SPECTATING("&aYou are now spectating!"),
    CONFIG_RELOADED("Successfully reloaded configuration files."),
    PAINTKILLERS_ON("Paintkillers have taken effect!"),
    NOT_FIND_TARGET("Could not find target."),
    ARENA_IS_FILL(Tag.ARENA + " is full!"),

    SCOREBOARD_TITLE(Tag.THEME + "&l  Paintball &f" + Tag.SECONDARY + "%time%  "),
    SCOREBOARD_COINS("Coins " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_KILLS("Kills " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_KILL_STREAK("Kill Streak " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_KD("K/D " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_MONEY("Money " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_LINE("&7&m                    "),
    SCOREBOARD_STATUS("Status " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_LIVES("Lives " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_MODE("Mode " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_HEALTH("Health " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_PLAYERS("Count " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_TEAM("Team " + Tag.SECONDARY + "» " + Tag.AMOUNT),
    SCOREBOARD_WAGER("Wager " + Tag.SECONDARY + "» " + Tag.AMOUNT),

    ITEM_LEAVE_ARENA("&c&lClick" + Tag.SECONDARY + " » &4Leave Arena"),
    SIGN_WRONG_SYNTAX("Wrong syntax for creating Paintball sign."),
    SIGN_AUTOJOIN_CREATED("Auto Join sign successfully created!"),
    SIGN_JOIN_CREATED("Join sign successfully created!"),
    SIGN_LEAVE_CREATED("Leave sign successfully created!"),
    SIGN_SPECTATE_CREATED("Spectate sign successfully created!"),
    SIGN_LEADERBOARD_REMOVED("Leaderboard sign has been successfully removed!"),
    SIGN_LEAVE_REMOVED("Leave sign has been successfully removed!"),
    SIGN_AUTOJOIN_REMOVED("Autojoin sign has been successfully removed!"),
    SIGN_JOIN_REMOVED(Tag.ARENA + "'s join sign has been successfully removed!"),
    SKULL_LEADERBOARD_REMOVED("Leaderboard skull has been successfully removed!"),
    SIGN_SPECTATE_REMOVED(Tag.ARENA + "'s spectate sign has been successfully removed!"),
    SIGN_LEAVE("&cLeave"),
    SIGN_SPECTATE("&aSpectate"),

    ARENA_START_MESSAGE("Game started"),
    ARENA_TEAMS_NOT_BALANCED("You cannot change to this team until the teams are balanced."),

    KILL_CONFIRMED("&e&lKill Confirmed!"),
    KILL_DENIED("&c&lKill Denied!"),

    CTF_SHORT_NAME("CTF"),
    CTF_LONG_NAME("Capture the Flag"),
    CTF_DESCRIPTION("Capture other team's flags and bring them to your base"),

    TDM_SHORT_NAME("TDM"),
    TDM_LONG_NAME("Team Deathmatch"),
    TDM_DESCRIPTION("Kill players on the other team"),

    FFA_SHORT_NAME("FFA"),
    FFA_LONG_NAME("Free For All"),
    FFA_DESCRIPTION("Everyone is on their own team"),

    DOM_SHORT_NAME("DOM"),
    DOM_LONG_NAME("Domination"),
    DOM_DESCRIPTION("Secure other team's beacon points"),

    LTS_SHORT_NAME("LTS"),
    LTS_LONG_NAME("Last Team Standing"),
    LTS_DESCRIPTION("Limited lives, last team standing wins"),

    RTF_SHORT_NAME("RTF"),
    RTF_LONG_NAME("Rush the Flag"),
    RTF_DESCRIPTION("Capture the neutral flag and bring it to your base"),

    DTC_SHORT_NAME("DTC"),
    DTC_LONG_NAME("Destroy the Core"),
    DTC_DESCRIPTION("Get to the other team's Core and shoot it to destroy it"),

    KC_SHORT_NAME("KC"),
    KC_LONG_NAME("Kill Confirmed"),
    KC_DESCRIPTION("After you kill a player, confirm the kill before the other team does"),

    HIGEST_KILL_STREAK("Highest Killstreak"),

    TEAM_WON("The " + Tag.TEAM + " team won!"),
    THERE_WAS_A_TIE_BETWEEN("There was a tie between "),
    WON("won"),
    LOST("lost"),
    YOU("You"),

    KD("K/D"),
    KILLS("Kills"),
    DEATHS("Deaths"),
    ACCURACY("Accuracy"),
    SHOTS("Shots"),
    HITS("Hits"),
    GAMES_PLAYED("Games Played"),
    WINS("Wins"),
    DEFEATS("Defeats"),
    TIES("Ties"),
    FLAGS_CAPTURED("Flags Captured"),
    FLAGS_DROPPED("Flags Dropped"),
    TIME_PLAYED("Time Played"),

    NO_SPACE("You do not have enough hotbar space for this item."),
    ARENA_JOIN_PERMISSION("You do not have permission to join that arena!"),
    TOP_LEADERBOARD_TITLE(Tag.SECONDARY + "&m          " + Tag.THEME + " Paintball Top " + Tag.SECONDARY + Tag.PAGE + Tag.THEME + " Leaderboard " + Tag.SECONDARY + "&m          "),
    PER_LEADERBOARD_TITLE(Tag.SECONDARY + "&m          " + Tag.THEME + " Paintball " + Tag.STAT + " Leaderboard &7" + Tag.PAGE + Tag.THEME + "/&7" + Tag.MAX + " " + Tag.SECONDARY + "&m          "),
    TOP_LEADERBOARD_LAYOUT(Tag.THEME + "#" + Tag.RANK + " " + Tag.SECONDARY + Tag.STAT + " » " + Tag.THEME + Tag.SENDER + " " + Tag.SECONDARY + "- " + Tag.AMOUNT),
    PER_LEADERBOARD_LAYOUT(Tag.THEME + "#" + Tag.RANK + "" + Tag.SECONDARY + " » " + Tag.THEME + Tag.SENDER + " " + Tag.SECONDARY + "- "  + Tag.AMOUNT),

    YOU_ARE_PROTECTED("You are still protected. Protection: " + Tag.TIME + " seconds"),
    THEY_ARE_PROTECTED("That player is protected. Protection: " + Tag.TIME + " seconds"),

    HIT_PLAYER("Hit player! " + Tag.AMOUNT + "/" + Tag.MAX),
    TEAM_FULL("&rTeam " + Tag.TEAM + " is full!"),

    MUST_BE_ARENA_PLAYER("Target must be an arena player!"),

    NOT_SETUP("Not Setup"),
    WAITING("Waiting"),
    DISABLED("Disabled"),
    STARTING("Starting"),
    STOPPING("Stopping"),
    PLAYING("Playing"),
    REMOVED("Remoaced"),

    EXPIRATION_TIME(Tag.THEME + "&l" + "Expiration" + " » " + Tag.SECONDARY + Tag.TIME + Tag.THEME + " seconds"),
    EXPIRATION_END(Tag.THEME + "&l" + "Expiration" + " » " + Tag.SECONDARY + "Item " + Tag.ITEM + Tag.THEME + " has expired"),
    PROTECTION_END(Tag.THEME + "&l" + "Protection" + " » " + Tag.SECONDARY + "Protection has expired"),
    PROTECTION_TIME(Tag.THEME + "&l" + "Protection" + " » " + Tag.SECONDARY + "%time% " + THEME + "seconds"),
    TEAM_SWITCH_TIME(Tag.THEME + "&l" + "Team Switch" + " » " + Tag.SECONDARY + "%time%" + THEME + " seconds"),
    TEAM_SWITCH_END(Tag.THEME + "&l" + "Team Switch" + " » " + Tag.SECONDARY + "Team switching unlocked"),
    TEAM_SWITCH_ERROR(Tag.THEME + "&l" + "Team Switch" + " » " + Tag.SECONDARY + "Wait for the team switch cooldown to end."),
    TEAM_SWITCH_TITLE("Team Switcher"),

    GAVE_COINS("You have given &7" + Tag.AMOUNT + " &acoins to &7" + Tag.PLAYER),

    WAGER_GAIN_AMOUNT(Tag.THEME + "Total money gained from wager: " + Tag.SECONDARY + Tag.CURRENCY + Tag.AMOUNT),

    MISSING_TEAM_FLAG(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Error" + Messenger.SUFFIX + ChatColor.RED + "You are missing you team's flag!"),

    GAME_STATS(Tag.THEME + " Games Stats "),
    MONEY1("Money: "),
    KILLS1("Kills: "),
    DEATHS1("Deaths: "),
    KILLSTREAK1("Killstreak: "),
    KD1("KD: "),
    YOUR_TEAM1("Your team "),
    TIED1("tied"),
    CLICK("Click"),
    JOIN("Join"),
    Teleporter("Teleporter"),
    TELEPORT_TO("Teleport to "),
    CHANGE_TEAM("Change Team"),

    COIN_ITEM_ERROR_1("You don't have permission to use this item!"),
    COIN_ITEM_ERROR_2("You don't have enough coins!"),
    COIN_ITEM_ERROR_3("You don't have enough money!"),
    LORE_DESCRIPTION(Tag.THEME + "Description: "),
    LORE_LASTS(Tag.THEME + "Lasts: "),
    LORE_COST(Tag.THEME + "Cost: "),
    LORE_COINS(Tag.THEME + "Coins: "),
    LORE_MINUTES("minutes"),
    LORE_SECONDS("seconds"),

    STORAGE_NOT_EMPTY("You must clear your inventory storage contents before joining."),
    ARMOUR_NOT_EMPTY("You must clear your inventory armour contents before joining."),
    EXTRA_NOT_EMPTY("You must clear your inventory extra contents before joining."),

    MAX_USES_PER_GAME(ChatColor.RED + "Item has reached its max amount of uses per game."),
    MAX_USES_PER_PLAYER(ChatColor.RED + "Item has reached its max amount of uses per player."),

    RESET_FLAG(ChatColor.BOLD + "" + Tag.PLAYER + " has reset " + Tag.TEAM + "'s flag!"),

    SHOT_PLAYER_FORMAT(Tag.TEAM_COLOR + "" + Tag.PLAYER + "" + Tag.SECONDARY + " " + Tag.ACTION + " " + Tag.DIED_TEAM_COLOR + "" + Tag.DIED),

    ARENA_STATE_CHECK(Tag.ARENA + " is " + Tag.STATE + "."),

    STAT_TITLE(Tag.SECONDARY.toString() + ChatColor.STRIKETHROUGH + "             " + ChatColor.RESET + " " + Tag.THEME.toString() + Tag.PLAYER + "'s Stats " + ChatColor.RESET + " " + Tag.SECONDARY.toString() + ChatColor.STRIKETHROUGH + "             "),

    // Wager
    PLAYER_WAGERED(Tag.PLAYER + " has wagered " + Tag.CURRENCY + Tag.WAGER_AMOUNT + " (" + Tag.CURRENCY + Tag.WAGER_TOTAL + ")");

    private final String defaultString;
    private String string;
    private boolean parseChatColor = true;

    Messages(String defaultString) {
        this.defaultString = defaultString;
        this.string = defaultString;
    }

    Messages(String defaultString, boolean parseChatColor) {
        this(defaultString);
        this.parseChatColor = parseChatColor;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase().replace("_", "-");
    }

    public String getDefaultString() {
        return defaultString;
    }

    public String getString() {
        return string;
    }

    public void setString(String message) {
        this.string = parseChatColor ? ChatColor.translateAlternateColorCodes('&', message) : message;
    }
}

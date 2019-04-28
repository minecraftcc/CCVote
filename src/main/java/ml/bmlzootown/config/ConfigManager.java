package ml.bmlzootown.config;

/**
 * Created by Brandon on 12/19/2015.
 */
import ml.bmlzootown.CCVote;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ConfigManager {
    static Plugin pl = CCVote.plugin;

    public static void setConfigDefaults() {
        FileConfiguration config = pl.getConfig();
        config.options().header("Use <player> in place of player name in commands. \nCommands under general will be executed in all worlds. \nCommands under a world name will only be executed if the player is in said world. \n \ngeneral:\n- say Hello!\n- say Welcome!\nspheres:\n- Oh, I see you're in Spheres!\n\nbroadcast.once-per is set in seconds");
        List<String> commands = Arrays.asList("broadcast A player has voted for us and received a buff! Type /vote to see the current prizes.", "effect <player> 21 1500 1", "effect <player> 3 3600 0", "effect <player> 16 1200 1", "give <player> 19 5", "give <player> minecraft:potion 1 44 {display:{Name:Voting Prize,Lore:[A gift for voting MinecraftCC]},CustomPotionEffects:[{Id:3,Amplifier:1,Duration:12000}]}");
        config.addDefault("general", commands);
        config.addDefault("sites", "google_com");
        config.addDefault("broadcast.enabled", true);
        config.addDefault("broadcast.once-per", "86400");
        config.addDefault("broadcast.command", "broadcast");
        config.addDefault("broadcast.message", "A player has voted for the server and has received a reward! Type /vote to see the current prizes.");
        config.addDefault("debug", false);
        config.options().copyDefaults(true);
        pl.saveConfig();
    }

    public static List<String> getCommands() {
        return pl.getConfig().getStringList("general");
    }

    public static Set<String> getWorlds() { return pl.getConfig().getKeys(false); }

    public static List<String> getWorldCommands(String world) { return pl.getConfig().getStringList(world); }

    public static Set<String> getSites() { return pl.getConfig().getConfigurationSection("sites").getKeys(false); }

    public static List<String> getSiteCommands(String site) { return pl.getConfig().getStringList("sites." + site); }

    public static boolean broadcastEnabled() { return pl.getConfig().getBoolean("broadcast.enabled"); }

    public static int oncePer() { return pl.getConfig().getInt("broadcast.once-per"); }

    public static boolean debug() { return pl.getConfig().getBoolean("debug"); }

    public static String broadcastCmd() { return pl.getConfig().getString("broadcast.command"); }

    public static String broadcastMessage() { return pl.getConfig().getString("broadcast.message"); }

}

package ml.bmlzootown;

import ml.bmlzootown.commands.Commander;
import ml.bmlzootown.config.ConfigManager;
import ml.bmlzootown.listeners.VoteListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Brandon on 2/23/2016.
 */
public class CCVote extends JavaPlugin{
    public static Plugin plugin;

    public void onEnable() {
        plugin = this;
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            ConfigManager.setConfigDefaults();
        }
        getServer().getPluginManager().registerEvents(new VoteListener(), this);
        getCommand("ccvote").setExecutor(new Commander());
    }

    public void onDisable() {

    }

}

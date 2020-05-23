package ml.bmlzootown.ccvote;

import ml.bmlzootown.ccvote.commands.Commander;
import ml.bmlzootown.ccvote.config.ConfigManager;
import ml.bmlzootown.ccvote.config.VotedManager;
import ml.bmlzootown.ccvote.listeners.VoteListener;
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
        // Config Init
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            ConfigManager.setConfigDefaults();
        }
        // LastVote Init
        File vote = new File(getDataFolder(), "voted.yml");
        if (!vote.exists()) {
            VotedManager.createVoted();
            VotedManager.loadVoted();
            VotedManager.saveVoted();
        } else {
            VotedManager.loadVoted();
        }
        getServer().getPluginManager().registerEvents(new VoteListener(), this);
        getCommand("ccvote").setExecutor(new Commander());
    }

    public void onDisable() {

    }

}

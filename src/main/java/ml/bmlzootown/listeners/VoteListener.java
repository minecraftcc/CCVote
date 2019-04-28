package ml.bmlzootown.listeners;

import com.vexsoftware.votifier.model.VotifierEvent;
import ml.bmlzootown.config.ConfigManager;
import ml.bmlzootown.config.VotedManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import static ml.bmlzootown.config.VotedManager.datePattern;

/**
 * Created by Brandon on 2/23/2016.
 */
public class VoteListener implements Listener{
    public static List<String> commands = new ArrayList<>();
    public static Set<String> sites;
    public static Set<String> worlds;
    //public static List<Player> done = new ArrayList<>();

    @EventHandler
    public void onVotifierEvent(VotifierEvent event) {
        commands = ConfigManager.getCommands();
        worlds = ConfigManager.getWorlds();
        sites = ConfigManager.getSites();
        boolean debug = ConfigManager.debug();

        Player player = Bukkit.getPlayer(event.getVote().getUsername());

        if (debug) Bukkit.getLogger().log(Level.INFO, "[CCVote] Debugging enabled...");

        if (ConfigManager.broadcastEnabled()) {
            String msg = ConfigManager.broadcastMessage();
            if (msg.contains("<player>")) {
                if (player != null) {
                   msg = ConfigManager.broadcastMessage().replace("<player>", player.getName());
                }
            }

            if (ConfigManager.oncePer() > 0) {
                if (debug) Bukkit.getLogger().log(Level.INFO, "[CCVote] once-per: " + ConfigManager.oncePer());
                if (player != null) {
                    //if (!done.contains(player)) {
                    String thenString = VotedManager.getVoted(player.getUniqueId());
                    long seconds;
                    Date now = new Date();
                    if (thenString != null && !thenString.equalsIgnoreCase("")) {
                        DateFormat format = new SimpleDateFormat(datePattern);
                        Date then;
                        try {
                            then = format.parse(thenString);
                            seconds = (now.getTime() - then.getTime())/1000;
                        } catch (ParseException e) {
                            seconds = Long.MAX_VALUE;
                        }
                    } else {
                        seconds = Long.MAX_VALUE;
                    }
                    if (debug) Bukkit.getLogger().log(Level.INFO, "[CCVote] Seconds: " + seconds);
                    if (ConfigManager.oncePer() < seconds) {
                        if (debug) Bukkit.getLogger().log(Level.INFO, "[CCVote] Broadcast enabled (once), sending: " + ConfigManager.broadcastCmd() + " " + msg);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + msg);
                        VotedManager.setVoted(player.getUniqueId(), now);
                        //done.add(player);
                    }
                }
            } else {
                if (debug) Bukkit.getLogger().log(Level.INFO, "[CCVote] Broadcast enabled (always), sending: " + ConfigManager.broadcastCmd() + " " + msg);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + msg);
            }
        }

        for (String cmd : commands) {
            String command = cmd;
            if (cmd.contains("<player>")) {
                if (player != null) {
                    command = cmd.replace("<player>", player.getName());
                }
            }
            if (debug) Bukkit.getLogger().log(Level.INFO, "[CCVote] Dispatching general command: " + command);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }

        if (!worlds.isEmpty()) {
            for(String world : worlds) {
                if (player != null) {
                    if (world.equalsIgnoreCase("general") || world.equalsIgnoreCase("broadcast") || world.equalsIgnoreCase("sites") || world.equalsIgnoreCase("debug")) continue;
                    if (player.getWorld().getName().equalsIgnoreCase(world)) {
                        List<String> worldCommands = ConfigManager.getWorldCommands(world);
                        for (String cmd : worldCommands) {
                            String command = cmd;
                            if (cmd.contains("<player>")) {
                                command = cmd.replace("<player>", player.getName());
                            }
                            if (debug) Bukkit.getLogger().log(Level.INFO, "[CCVote] Dispatching world (" + player.getWorld().getName() + ") command: " + command);
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                        }
                    }
                }
            }

        }

        if (!sites.isEmpty()) {
            for (String site : sites) {
                String[] split = site.split("_");
                String url = split[0];
                String domain = split[1];

                if (event.getVote().getServiceName().equalsIgnoreCase(url + "." + domain)) {
                    if (player != null) {
                        List<String> siteCommands = ConfigManager.getSiteCommands(site);
                        for (String cmd : siteCommands) {
                            String command = cmd;
                            if (cmd.contains("<player>")) {
                                command = cmd.replace("<player>", player.getName());
                            }
                            if (debug) Bukkit.getLogger().log(Level.INFO, "[CCVote] Dispatching site (" + url + "." + domain + ") command: " + command);
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                        }
                    }
                }
            }
        }
    }

}

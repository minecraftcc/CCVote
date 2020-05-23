package ml.bmlzootown.ccvote.commands;

import ml.bmlzootown.ccvote.CCVote;
import ml.bmlzootown.ccvote.config.VotedManager;
import ml.bmlzootown.ccvote.listeners.VoteListener;
import ml.bmlzootown.ccvote.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * Created by Brandon on 2/23/2016.
 */
public class Commander implements CommandExecutor{
    private Plugin plugin = CCVote.plugin;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("ccvote.reload")) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    VotedManager.reloadVoted();
                    VoteListener.commands = ConfigManager.getCommands();
                    sender.sendMessage(ChatColor.DARK_AQUA + "[CCVote] " + ChatColor.AQUA + "Config reloaded!");
                } else {
                    sender.sendMessage(ChatColor.DARK_AQUA + "[CCVote] " + ChatColor.AQUA + "Invalid syntax!" + ChatColor.ITALIC + " /ccvote reload");
                }
            }
        } else {
            sender.sendMessage(ChatColor.DARK_AQUA + "[CCVote] " + ChatColor.AQUA + "Invalid syntax!" + ChatColor.ITALIC + " /ccvote reload");
        }
        return false;
    }
}

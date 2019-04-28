package ml.bmlzootown.config;

import ml.bmlzootown.CCVote;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class VotedManager {
    private static FileConfiguration config;
    private static Plugin pl = CCVote.plugin;
    private static File vote = new File(pl.getDataFolder(), "voted");
    private static String datePattern = "yyyy-MM-dd HH:mm:ss";

    public static void createVoted() {
        try {
            vote.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadVoted() {
        config = YamlConfiguration.loadConfiguration(vote);
    }
    public static void reloadVoted() {
        config = new YamlConfiguration();
        loadVoted();
    }

    private static void saveVoted() {
        try {
            config.save(vote);
        } catch (IOException e) {
            // Stuff here
        }
    }

    public static void setVoted(UUID uuid, Date date) {
        DateFormat format = new SimpleDateFormat(datePattern);
        String time = format.format(date);
        config.set(uuid.toString(), time);
        saveVoted();
    }

    public static Date getVoted(UUID uuid) {
        String datestring = config.getString(uuid.toString());
        DateFormat format = new SimpleDateFormat(datePattern);
        try {
            return format.parse(datestring);
        } catch (ParseException e) {
            return null;
        }
    }
}

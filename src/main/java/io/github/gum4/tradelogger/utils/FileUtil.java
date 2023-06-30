package io.github.gum4.tradelogger.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FileUtil {
    /**
     * Create a data folder of this plugin.
     * @param plugin main class of this plugin
     * @return {@code true} if the data folder exists and successfully created; {@code false} otherwise.
     */
    public static boolean createPluginDataFolder(JavaPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        if (dataFolder.exists()) {
            return true;
        }

        if (dataFolder.mkdirs()) {
            Bukkit.getLogger().info("Successfully create a data folder of this plugin.");
            return true;
        }
        else {
            Bukkit.getLogger().severe("Failed to create a data folder of this plugin.");
            return false;
        }
    }
}

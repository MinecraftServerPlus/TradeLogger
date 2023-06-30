package io.github.gum4.tradelogger.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    /**
     * Create a csv file.
     * @param plugin main class of this plugin
     * @param fileName name of a file to create
     * @return the file instance
     */
    public static File createCsvFile(JavaPlugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName + ".csv");
        if (file.exists()) {
            return file;
        }

        try {
            if (file.createNewFile()) {
                Bukkit.getLogger().info("Successfully create a log file named '" + fileName + ".csv'.");
                return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().severe("Failed to create a log file named '" + fileName + ".csv'.");
        }
        return null;
    }

    /**
     * Get the line count of the file.
     * @param file a file to count its lines
     * @return line count or -1 (only if fails to load file)
     */
    public static long getLineCount(File file) {
        try {
            return Files.lines(file.toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().severe("Failed to load the '" + file.getName() + "'.");
            return -1;
        }
    }
}

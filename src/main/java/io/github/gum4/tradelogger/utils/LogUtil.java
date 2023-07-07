package io.github.gum4.tradelogger.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class LogUtil {
    /**
     * Create a data folder of this plugin.
     * @param plugin main class of this plugin
     * @return {@code true} if the data folder exists and successfully created; {@code false} otherwise.
     */
    public static File getPluginDataFolder(JavaPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        if (dataFolder.exists()) {
            return dataFolder;
        }

        if (dataFolder.mkdirs()) {
            Bukkit.getLogger().info("Successfully create a data folder of this plugin.");
            return dataFolder;
        }
        else {
            Bukkit.getLogger().severe("Failed to create a data folder of this plugin.");
            return null;
        }
    }

    public static File getLogFile(JavaPlugin plugin, Player player) {
        UUID uuid = player.getUniqueId();
        File dataFolder = getPluginDataFolder(plugin);
        if (Objects.isNull(dataFolder)) return null;
        File logFile = new File(dataFolder, uuid.toString() + ".csv");
        if (logFile.exists()) return logFile;
        try (InputStream in = LogUtil.class.getResourceAsStream("/log-template.csv")) {
            if (in == null) {
                plugin.getLogger().severe("Unable to find log-template.csv");
            } else {
                Files.copy(in, logFile.toPath());
            }
            Bukkit.getLogger().info("Successfully create a " + player.getName() + " log file of this plugin.");
            return logFile;
        } catch (IOException e) {
            // Handle any IO exceptions that might occur
            e.printStackTrace();
            Bukkit.getLogger().severe("Failed to create a " + player.getName() + " log file of this plugin.");
            return null;
        }
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

    public static boolean writeLog(
            File logFile,
            Player player,
            MerchantRecipe tradeInfo,
            AbstractVillager villager
    ) {
        String profession;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter);
        if (villager instanceof WanderingTrader) {
            profession = EntityType.WANDERING_TRADER.name();
        }
        else{
            profession = ((Villager) villager).getProfession().name();
        }
        String log = time + "," +
                player.getName() + "," +
                tradeInfo.getIngredients().get(0).getType().name() + "," +
                tradeInfo.getIngredients().get(0).getAmount() + "," +
                tradeInfo.getIngredients().get(1).getType().name() + "," +
                tradeInfo.getIngredients().get(1).getAmount() + "," +
                villager.getUniqueId() + "," +
                profession + "," +
                tradeInfo.getResult().getType().name() + "," +
                tradeInfo.getResult().getAmount() + "\n";
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(log);
            Bukkit.getLogger().info(log.replaceAll(",", " | "));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

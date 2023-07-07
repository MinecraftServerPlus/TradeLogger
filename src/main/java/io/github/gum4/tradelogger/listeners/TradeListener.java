package io.github.gum4.tradelogger.listeners;

import io.github.gum4.tradelogger.utils.LogUtil;
import io.papermc.paper.event.player.PlayerTradeEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class TradeListener implements Listener {
    private final JavaPlugin plugin;

    public TradeListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTrade(PlayerTradeEvent event) {
        if (!event.isCancelled()) {
            LogUtil.writeLog(
                    LogUtil.getLogFile(plugin, event.getPlayer()),
                    event.getPlayer(),
                    event.getTrade(),
                    event.getVillager()
            );
        }
    }
}

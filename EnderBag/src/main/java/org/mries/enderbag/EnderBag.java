package org.mries.enderbag;

import org.mries.enderbag.config.EnderBagConfig;

import net.md_5.bungee.api.ChatColor;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderBag extends JavaPlugin {
    private EnderBagConfig config = null;
    private final int bstatsPluginId = 17697;
    private final int spigotResourceId = 107889;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new EnderBagConfig(this.getConfig());
        ItemManager.Init(this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getCommand("enderbag").setExecutor(new CommandHandler());
        getCommand("enderbag").setTabCompleter(new CommandTabCompleter());
        // Enable bstats
        new Metrics(this, bstatsPluginId);
        // Enable update checks
        new UpdateChecker(this, spigotResourceId).getVersion(version -> {
            if (!this.getDescription().getVersion().equals(version)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[EnderBag] There is a new update available.");
            }
        });
    }

    public final EnderBagConfig getConfiguration() {
        return config;
    }
}
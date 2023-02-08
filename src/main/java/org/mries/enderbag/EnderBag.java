package org.mries.enderbag;

import org.mries.enderbag.config.EnderBagConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderBag extends JavaPlugin {
    private EnderBagConfig config = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new EnderBagConfig(this.getConfig());
        ItemManager.Init(this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getCommand("enderbag").setExecutor(new CommandHandler());
        getCommand("enderbag").setTabCompleter(new CommandTabCompleter());
    }

    public final EnderBagConfig getConfiguration() {
        return config;
    }
}
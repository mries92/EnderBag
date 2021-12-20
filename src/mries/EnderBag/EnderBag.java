package mries.EnderBag;

import mries.EnderBag.config.EnderBagConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderBag extends JavaPlugin {
    private EnderBagConfig config = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new EnderBagConfig(this.getConfig());
        ItemManager.Init(this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    public final EnderBagConfig getConfiguration() {
        return config;
    }
}
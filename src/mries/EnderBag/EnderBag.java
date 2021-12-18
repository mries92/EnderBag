package mries.EnderBag;

import mries.EnderBag.config.EnderBagConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderBag extends JavaPlugin {
    private EnderBagConfig config = null;

    @Override
    public void onEnable() {
        config();
        ItemManager.Init(this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    private void config() {
        saveDefaultConfig();
        config = new EnderBagConfig(this.getConfig());
    }

    public EnderBagConfig getConfiguration() {
        return config;
    }
}
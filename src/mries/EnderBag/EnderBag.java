package mries.EnderBag;

import org.bukkit.plugin.java.JavaPlugin;

public class EnderBag extends JavaPlugin {
    @Override
    public void onEnable() {
        ItemManager.Init(this);
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}
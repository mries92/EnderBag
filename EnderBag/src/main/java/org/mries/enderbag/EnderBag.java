package org.mries.enderbag;

import org.mries.enderbag.compatibility.NMS_1_19_RX;
import org.mries.enderbag.compatibility.NMS_LEGACY;
import org.mries.enderbag.compatibility.PacketHandler;
import org.mries.enderbag.config.EnderBagConfig;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderBag extends JavaPlugin {
    private EnderBagConfig config = null;
    private ItemManager itemManager = null;
    private PacketHandler handler = null;
    private final int bstatsPluginId = 17697;
    private final int spigotResourceId = 107889;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new EnderBagConfig(this.getConfig());
        itemManager = new ItemManager(this, handler);
        handler = resolvePacketHandler(this);

        // Register events
        getServer().getPluginManager().registerEvents(new EventListener(this, itemManager), this);
        // Enable commands
        getCommand("enderbag").setExecutor(new CommandHandler(itemManager));
        getCommand("enderbag").setTabCompleter(new CommandTabCompleter());
        // Enable bstats
        new Metrics(this, bstatsPluginId);
        // Enable update checks
        new UpdateChecker(this, spigotResourceId).getVersion(version -> {
            if (!this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is a new update available.");
            }
        });
    }

    public final EnderBagConfig getConfiguration() {
        return config;
    }

    /**
     * Create an instance of a packet handler compatibile with the curent version of
     * minecraft NMS.
     * 
     * If ProtocolLib is not installed, will return null.
     * 
     * @param plugin An instance of the plugin
     * @return An initialized packet handler.
     */
    private PacketHandler resolvePacketHandler(EnderBag plugin) {
        PacketHandler handler = null;
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
            String versionString = Bukkit.getServer().getClass().getPackage().getName();
            String nmsVersionString = versionString.substring(versionString.lastIndexOf('.') + 1);
            switch (nmsVersionString) {
                case "v1_16_R1":
                case "v1_16_R2":
                case "v1_16_R3":
                case "v1_17_R1":
                case "v1_18_R1":
                case "v1_18_R2":
                    handler = new NMS_LEGACY(plugin);
                    break;
                case "v1_19_R1":
                case "v1_19_R2":
                    handler = new NMS_1_19_RX(plugin);
                    break;
                default:
                    plugin.getLogger()
                            .info("No supported NMS version string detected. Plugin has not been updated yet.");
                    break;
            }
        } else {
            plugin.getLogger().info("ProtocolLib is not installed. Localized window titles will not work.");
        }
        return handler;
    }
}
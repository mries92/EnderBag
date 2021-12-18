package mries.EnderBag.config;

import mries.EnderBag.EnderBag;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class EnderBagConfig {
    public EnderBagConfig(FileConfiguration config) {
        itemName = config.getString("item-name");
        itemDescription = config.getString("item-description");
        showDescription = config.getBoolean("show-description");
        appearEnchanted = config.getBoolean("appear-enchanted");
        baseItem = config.getString("base-item");
        cooldown = config.getBoolean("cooldown");
        cooldownTime = config.getString("cooldown-time");
        cooldownMessage = config.getString("cooldown-message");
        durability = config.getBoolean("durability");
        maxDurability = config.getInt("max-durability");
        durabilityPerUse = config.getInt("durability-per-use");
        safeDurability = config.getBoolean("safe-durability");

        recipeValues = config.getStringList("recipe.values");
        recipeKeys = (List<List<String>>) config.getList("recipe.key");
    }

    public String itemName;
    public String itemDescription;
    public Boolean showDescription;
    public Boolean appearEnchanted;
    public String baseItem;
    public Boolean cooldown;
    public String cooldownTime;
    public String cooldownMessage;
    public Boolean durability;
    public Integer maxDurability;
    public Integer durabilityPerUse;
    public Boolean safeDurability;

    public List<String> recipeValues;
    public List<List<String>> recipeKeys;
}
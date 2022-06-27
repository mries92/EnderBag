package org.mries.enderbag.config;

import org.bukkit.configuration.file.FileConfiguration;

import lombok.Data;

import java.util.List;

@Data
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
        unbreakable = config.getBoolean("unbreakable");
        uid = config.getInt("uid");

        recipeValues = config.getStringList("recipe.values");
        recipeKeys = (List<List<String>>) config.getList("recipe.key");
    }

    public final String itemName;
    public final String itemDescription;
    public final Boolean showDescription;
    public final Boolean appearEnchanted;
    public final String baseItem;
    public final Boolean cooldown;
    public final String cooldownTime;
    public final String cooldownMessage;
    public final Boolean durability;
    public final Integer maxDurability;
    public final Integer durabilityPerUse;
    public final Boolean safeDurability;
    public final Boolean unbreakable;
    public final Integer uid;

    public final List<String> recipeValues;
    public final List<List<String>> recipeKeys;
}
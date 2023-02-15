package org.mries.enderbag.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@SuppressWarnings("unchecked")
public class EnderBagConfig {
    public EnderBagConfig(FileConfiguration config) {
        itemName = config.getString("item-name");
        itemDescription = config.getString("item-description");
        showDescription = config.getBoolean("show-description");
        appearEnchanted = config.getBoolean("appear-enchanted");
        baseItem = config.getString("base-item");
        uid = config.getInt("uid");

        recipeValues = config.getStringList("recipe.values");
        recipeKeys = (List<List<String>>) config.getList("recipe.key");
    }

    public final String itemName;
    public final String itemDescription;
    public final Boolean showDescription;
    public final Boolean appearEnchanted;
    public final String baseItem;
    public final Integer uid;

    public final List<String> recipeValues;
    public final List<List<String>> recipeKeys;
}
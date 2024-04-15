package org.mries.enderbag.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class EnderBagConfig {
    public EnderBagConfig(FileConfiguration config) {
        itemName = config.getString("item-name", "§aEnder Bag");
        itemDescription = config.getString("item-description", "§bOpen your ender chest");
        showDescription = config.getBoolean("show-description", true);
        appearEnchanted = config.getBoolean("appear-enchanted", false);
        baseItem = config.getString("base-item", "ender_eye");
        uid = config.getInt("uid", 8310000);

        config.addDefault("recipe.values", Arrays.asList("DED", "ECE", "DED"));
        recipeValues = config.getStringList("recipe.values");
        config.addDefault("recipe.key", Arrays.asList(
                Arrays.asList("D", "diamond"),
                Arrays.asList("E", "ender_eye"),
                Arrays.asList("C", "ender_chest")));
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
package mries.EnderBag;

import mries.EnderBag.config.EnderBagConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class ItemManager {
    private static NamespacedKey enderBagKey = null;
    private static EnderBagConfig enderBagConfig = null;

    public static void Init(EnderBag plugin) {
        enderBagKey = new NamespacedKey(plugin, "isEnderBag");
        enderBagConfig = plugin.getConfiguration();

        ItemStack stack = new ItemStack(Material.ENDER_EYE, 1);
        UpdateItemStack(stack);

        // TODO: Handle error cases
        ShapedRecipe recipe = new ShapedRecipe(enderBagKey, stack);
        recipe.shape(enderBagConfig.recipeValues.get(0), enderBagConfig.recipeValues.get(1), enderBagConfig.recipeValues.get(2));
        enderBagConfig.recipeKeys.forEach(pair -> {
            recipe.setIngredient(pair.get(0).charAt(0), Material.getMaterial(pair.get(1).toUpperCase()));
        });
        Bukkit.addRecipe(recipe);
    }

    public static NamespacedKey getEnderBagKey() { return enderBagKey; }

    public static ItemStack UpdateItemStack(ItemStack stack) {
        Material material = Material.getMaterial(enderBagConfig.baseItem);
        if(material == null)
            material = Material.ENDER_EYE;
        stack.setType(material);
        stack.setAmount(1);

        // META CONFIG
        ItemMeta meta = stack.getItemMeta();
        // Display name
        meta.setDisplayName(enderBagConfig.itemName);
        // Description
        if(enderBagConfig.showDescription)
            meta.setLore(Arrays.asList(enderBagConfig.itemDescription));
        // Enchanted appearance
        if(enderBagConfig.appearEnchanted) {
            meta.addEnchant(Enchantment.LURE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.LURE);
        }
        meta.setCustomModelData(10);
        meta.getPersistentDataContainer().set(enderBagKey, PersistentDataType.BYTE, (byte)1);
        stack.setItemMeta(meta);
        return stack;
    }
}
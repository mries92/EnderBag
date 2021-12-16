package mries.EnderBag;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {
    private static ItemStack enderBagItem = null;

    public static void Init(EnderBag plugin) {
        if(enderBagItem == null) {
            enderBagItem = new ItemStack(Material.ENDER_EYE, 1);
            ItemMeta meta = enderBagItem.getItemMeta();
            meta.setDisplayName("§aEnder Bag");
            meta.setLore(Arrays.asList("§bOpen your ender chest"));
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            enderBagItem.setItemMeta(meta);

            // Create the recipe
            NamespacedKey key = new NamespacedKey(plugin, "ender_bag");
            ShapedRecipe recipe = new ShapedRecipe(key, enderBagItem);
            recipe.shape("DED", "ECE", "DED");
            recipe.setIngredient('E', Material.ENDER_EYE);
            recipe.setIngredient('D', Material.DIAMOND);
            recipe.setIngredient('C', Material.CHEST);
            Bukkit.addRecipe(recipe);
        }
    }

    public static ItemStack getEnderBagItem() {
        return enderBagItem;
    }
}
package org.mries.enderbag;

import org.mries.enderbag.config.EnderBagConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private static NamespacedKey enderBagKey = null;
    private static EnderBagConfig enderBagConfig = null;

    public static void Init(EnderBag plugin) {
        enderBagKey = new NamespacedKey(plugin, "isEnderBag"); // Tag key to indicate ender bag
        enderBagConfig = plugin.getConfiguration();

        ItemStack stack = new ItemStack(Material.ENDER_EYE, 1);
        UpdateItemStack(stack);

        ShapedRecipe recipe = new ShapedRecipe(enderBagKey, stack);
        recipe.shape(enderBagConfig.recipeValues.get(0), enderBagConfig.recipeValues.get(1),
                enderBagConfig.recipeValues.get(2));
        enderBagConfig.recipeKeys.forEach(pair -> {
            recipe.setIngredient(pair.get(0).charAt(0), Material.getMaterial(pair.get(1).toUpperCase()));
        });
        Bukkit.addRecipe(recipe);
    }

    public static NamespacedKey getEnderBagKey() {
        return enderBagKey;
    }

    /**
     * Utility function to determine if an item stack is an ender bag.
     * 
     * @param stack An item stack to check.
     * @return true if the stack is an ender bag.
     */
    public static boolean isEnderChest(ItemStack stack) {
        if (stack == null)
            return false;
        Byte isEnderBag = stack.getItemMeta().getPersistentDataContainer().get(ItemManager.getEnderBagKey(),
                PersistentDataType.BYTE);
        if (isEnderBag != null && isEnderBag == 1)
            return true;
        else
            return false;
    }

    /**
     * Utility function that updates existing ender bags in players inventories
     * after the configuration has been changed on the server.
     *
     * @param stack An item stack. Will be updated to the current ender bag
     *              config values.
     * @return The updated item stack.
     */
    public static ItemStack UpdateItemStack(ItemStack stack) {
        // Base item
        stack.setType(Material.ENDER_EYE);
        stack.setAmount(1);

        // -- Meta config --
        ItemMeta meta = stack.getItemMeta();
        // Display name
        meta.setDisplayName(enderBagConfig.itemName);
        // Description
        List<String> lore = new ArrayList<>();
        if (enderBagConfig.showDescription)
            lore.add(enderBagConfig.itemDescription);
        meta.setLore(lore);
        // Enchanted appearance
        if (enderBagConfig.appearEnchanted) {
            meta.addEnchant(Enchantment.LURE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.LURE);
        }
        meta.getPersistentDataContainer().set(enderBagKey, PersistentDataType.BYTE, (byte) 1);
        meta.setCustomModelData(10);
        stack.setItemMeta(meta);
        return stack;
    }

    public static void openInventory(Player player) {
        player.openInventory(player.getEnderChest());
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, .50f, 1);
    }
}
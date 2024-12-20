package org.mries.enderbag.util;

import org.mries.enderbag.EnderBag;
import org.mries.enderbag.compatibility.*;
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
import java.util.UUID;

/**
 * Handles logic relating to the custom item, including registering the
 * keys and activating the custom functionality.
 */
public class ItemManager {
    private NamespacedKey enderBagKey = null;
    private EnderBagConfig enderBagConfig = null;
    private PacketHandler handler = null;

    public ItemManager(EnderBag plugin, PacketHandler handler) {
        enderBagKey = new NamespacedKey(plugin, "isEnderBag");
        enderBagConfig = plugin.getConfiguration();
        this.handler = handler;

        ItemStack stack = new ItemStack(Material.ENDER_EYE, 1);
        updateItemStack(stack);

        ShapedRecipe recipe = new ShapedRecipe(enderBagKey, stack);
        recipe.shape(enderBagConfig.recipeValues.get(0), enderBagConfig.recipeValues.get(1),
                enderBagConfig.recipeValues.get(2));
        enderBagConfig.recipeKeys.forEach(pair -> {
            recipe.setIngredient(pair.get(0).charAt(0), Material.getMaterial(pair.get(1).toUpperCase()));
        });
        Bukkit.addRecipe(recipe);
    }

    public NamespacedKey getEnderBagKey() {
        return enderBagKey;
    }

    /**
     * Utility function to determine if an item stack is an ender bag.
     * 
     * @param stack An item stack to check.
     * @return true if the stack is an ender bag.
     */
    public boolean isEnderBag(ItemStack stack) {
        if (stack == null || stack.getItemMeta() == null)
            return false;
        boolean isEnderBag = stack.getItemMeta().getPersistentDataContainer().has(enderBagKey, PersistentDataType.STRING);
        boolean isLegacyEnderBag = stack.getItemMeta().getPersistentDataContainer().has(enderBagKey, PersistentDataType.BYTE);
        if (isEnderBag || isLegacyEnderBag)
            return true;
        else
            return false;
    }

    /**
     * Utility function that updates an item stack to be an ender bag.
     *
     * @param stack An item stack. Will be updated to the current ender bag
     *              config values.
     */
    public void updateItemStack(ItemStack stack) {
        stack.setType(Material.ENDER_EYE);
        stack.setAmount(1);
        // Meta config
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
        // Add a key to the item to indicate it is an ender bag, use random UUID to prevent stacking
        meta.getPersistentDataContainer().set(enderBagKey, PersistentDataType.STRING, UUID.randomUUID().toString());
        meta.setCustomModelData(enderBagConfig.uid);
        stack.setItemMeta(meta);
    }

    public void openInventory(Player player) {
        player.openInventory(player.getEnderChest());
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, .50f, 1);
        if (handler != null) {
            handler.sendOpenPacket(player);
        }
    }
}
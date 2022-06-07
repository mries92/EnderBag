package org.mries.enderbag;

import org.mries.enderbag.config.EnderBagConfig;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EventListener implements Listener {
    // A list of player IDs to their "last used time" for the ender bag
    private static Map<String, Long> cooldowns = new HashMap<>();
    private static EnderBagConfig config = null;

    public EventListener(EnderBag plugin) {
        EventListener.config = plugin.getConfiguration();
    }

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() != null && ItemManager.isEnderChest(event.getItem())) {
                ItemMeta meta = event.getItem().getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();
                // TODO check if cooldown is enabled
                if(config.cooldown) {
                    Long timestamp = cooldowns.get(event.getPlayer().getUniqueId().toString());
                    // Not on cooldown
                    if(timestamp == null) {
                        // TODO set the model data to cooldown
                        
                    } else {
                        player.sendTitle("Cooldown", "The ender bag is on cooldown.", 1, 3, 1);
                    }
                } else {
                    player.openInventory(player.getEnderChest());
                    player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, .50f, 1);
                    container.set(ItemManager.getEnderBagOpenedKey(), PersistentDataType.BYTE, (byte)1);
                    event.getItem().setItemMeta(meta);
                    event.setCancelled(true);
                }
            }
        }
    }

    // Check if the ender bag needs to be destroyed when closed
    @EventHandler
    public static void inventoryClosed(InventoryCloseEvent event) {
        if(config.durability) {
            if(event.getInventory().getType() == InventoryType.ENDER_CHEST) {
                // Check players inventory
                updateDurabilityInInventory(event.getPlayer().getInventory());
                // Check ender chest inventory
                updateDurabilityInInventory(event.getInventory());
            }
        }
    }

    /**
     * Search through an inventory and update the durability of any open ender bags. This is
     * called after an inventory is closed, rather than when the item is used.
     * @param inv An inventory to check
     */
    private static void updateDurabilityInInventory(Inventory inv) {
        inv.forEach(stack -> {
            if(stack != null) {
                ItemMeta meta = stack.getItemMeta();
                Byte isOpen = meta.getPersistentDataContainer().get(ItemManager.getEnderBagOpenedKey(), PersistentDataType.BYTE);
                if(isOpen != null && isOpen == 1) {
                    // Check the durability
                    Integer currentHealth = meta.getPersistentDataContainer().get(ItemManager.getEnderBagDurabilityKey(), PersistentDataType.INTEGER);
                    if(currentHealth <= 1) {
                        inv.remove(stack);
                    } else {
                        currentHealth -= config.durabilityPerUse;
                        meta.getPersistentDataContainer().set(ItemManager.getEnderBagDurabilityKey(), PersistentDataType.INTEGER, currentHealth);
                        meta.setLore(Arrays.asList(config.itemDescription, String.format("Durability: %d/%d" , currentHealth, config.maxDurability)));
                        meta.getPersistentDataContainer().set(ItemManager.getEnderBagOpenedKey(), PersistentDataType.BYTE, (byte)0);
                        stack.setItemMeta(meta);
                        // TODO update model data based on current health
                    }
                }
            }
        });
    }

    // These handlers will update any old versions of the item when config values are changed
    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().forEach(EventListener::updateItemInInventory);
    }

    @EventHandler
    public static void inventoryOpen(InventoryOpenEvent event) {
        event.getInventory().forEach(EventListener::updateItemInInventory);
    }

    private static void updateItemInInventory(ItemStack stack) {
        if(ItemManager.isEnderChest(stack))
            ItemManager.UpdateItemStack(stack);
    }

    @EventHandler
    public static void entityPickup(EntityPickupItemEvent event) {
        if(ItemManager.isEnderChest(event.getItem().getItemStack()))
            ItemManager.UpdateItemStack(event.getItem().getItemStack());
    }
}

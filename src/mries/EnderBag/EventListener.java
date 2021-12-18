package mries.EnderBag;

import mries.EnderBag.config.EnderBagConfig;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class EventListener implements Listener {
    private static EnderBagConfig config = null;

    public EventListener(EnderBag plugin) {
        EventListener.config = plugin.getConfiguration();
    }

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() != null) {
                ItemMeta meta = event.getItem().getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();
                Byte isEnderBag = container.getOrDefault(ItemManager.getEnderBagKey(), PersistentDataType.BYTE, (byte)0);
                if(isEnderBag != 0) {
                    player.openInventory(player.getEnderChest());
                    player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, .50f, 1);
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
                event.getPlayer().getInventory().forEach(itemStack -> {
                    if(ItemManager.isEnderChest(itemStack)) {
                        ItemMeta meta = itemStack.getItemMeta();
                        // Check the durability
                        Integer currentHealth = meta.getPersistentDataContainer().get(ItemManager.getEnderBagDurabilityKey(), PersistentDataType.INTEGER);
                        if(currentHealth <= 0) {
                            event.getPlayer().getInventory().remove(itemStack);
                        } else {
                            currentHealth -= config.durabilityPerUse;
                            meta.getPersistentDataContainer().set(ItemManager.getEnderBagDurabilityKey(), PersistentDataType.INTEGER, currentHealth);
                            meta.setLore(Arrays.asList(config.itemDescription, String.format("Durability: %d/%d" , currentHealth, config.maxDurability)));
                            itemStack.setItemMeta(meta);
                        }
                    }
                });

                // Check in case they put the bag inside itself at 0 health
                event.getInventory().forEach(itemStack -> {
                    if(ItemManager.isEnderChest(itemStack)) {
                        // Check the durability
                        Integer currentHealth = itemStack.getItemMeta().getPersistentDataContainer().get(ItemManager.getEnderBagDurabilityKey(), PersistentDataType.INTEGER);
                        if(currentHealth <= 0) {
                            event.getInventory().remove(itemStack);
                        } else {
                            itemStack.getItemMeta().setLore(Arrays.asList(config.itemDescription, String.format("Durability: %d/%d" , currentHealth, config.maxDurability)));
                        }
                    }
                });
            }
        }
    }

    // If the correct items are placed into the anvil, put a repaired version of the ender bag in the output
    @EventHandler
    public static void repairEvent(PrepareAnvilEvent event) {

    }

    // When the player interacts with the repaired bag, we can do some stuff
    @EventHandler
    public static void interactEvent(InventoryInteractEvent event) {

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

    @EventHandler
    public static void entityPickup(EntityPickupItemEvent event) {
        if(ItemManager.isEnderChest(event.getItem().getItemStack()))
            ItemManager.UpdateItemStack(event.getItem().getItemStack());
    }

    private static void updateItemInInventory(ItemStack stack) {
        if(ItemManager.isEnderChest(stack))
            ItemManager.UpdateItemStack(stack);
    }
}

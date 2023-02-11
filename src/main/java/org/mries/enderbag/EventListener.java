package org.mries.enderbag;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.mries.enderbag.config.EnderBagConfig;

public class EventListener implements Listener {
    private static EnderBagConfig config = null;

    public EventListener(EnderBag plugin) {
        super();
        EventListener.config = plugin.getConfiguration();
    }

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && ItemManager.isEnderChest(item)) {
                if (player.hasPermission("enderbag.use")) {
                    // If the block is an interactable type, cancel opening the bag
                    Block clickedBlock = event.getClickedBlock();
                    if (clickedBlock != null) {
                        Material mat = clickedBlock.getType();
                        if (mat.isInteractable()
                                && !Tag.STAIRS.isTagged(mat) // Stairs are for some reason marked interactable
                                && mat != Material.JUKEBOX) // Default ender eye behavior is to use on jukeboxes
                            return;
                    }
                    event.setCancelled(true);
                    ItemManager.openInventory(player);
                } else {
                    player.sendMessage("§cYou do not have permission to use the " + config.itemName);
                }
            }
        }
    }

    // These handlers will update any old versions of the item when config values
    // are changed
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
        updateItemInInventory(event.getItem().getItemStack());
    }

    private static void updateItemInInventory(ItemStack stack) {
        if (ItemManager.isEnderChest(stack))
            ItemManager.UpdateItemStack(stack);
    }
}

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
    private EnderBagConfig config = null;
    private ItemManager itemManager = null;

    public EventListener(EnderBag plugin, ItemManager itemManager) {
        config = plugin.getConfiguration();
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && itemManager.isEnderChest(item)) {
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
                    itemManager.openInventory(player);
                } else {
                    player.sendMessage("§cYou do not have permission to use the " + config.itemName);
                }
            }
        }
    }

    // These handlers will update any old versions of the item when config values
    // are changed
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().forEach(e -> updateItemInInventory(e));
    }

    @EventHandler
    public void inventoryOpen(InventoryOpenEvent event) {
        event.getInventory().forEach(e -> updateItemInInventory(e));
    }

    @EventHandler
    public void entityPickup(EntityPickupItemEvent event) {
        updateItemInInventory(event.getItem().getItemStack());
    }

    private void updateItemInInventory(ItemStack stack) {
        if (itemManager.isEnderChest(stack))
            itemManager.UpdateItemStack(stack);
    }
}

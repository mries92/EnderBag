package org.mries.enderbag.event;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mries.enderbag.config.EnderBagConfig;
import org.mries.enderbag.util.ItemManager;

public class EventListener implements Listener {
    private EnderBagConfig config = null;
    private ItemManager itemManager = null;

    public EventListener(EnderBagConfig config, ItemManager itemManager) {
        this.config = config;
        this.itemManager = itemManager;
    }

    @EventHandler
    private void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        // Action check
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK || item == null
                || !itemManager.isEnderBag(item)) {
            return;
        }

        // Permission check
        if (!player.hasPermission("enderbag.use")) {
            player.sendMessage(String.format("§cYou do not have permission to use the %s", config.itemName));
            return;
        }

        // Check for the block interacted with
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock != null && clickedBlock.getType().isInteractable()) {
            Material mat = clickedBlock.getType();
            boolean shouldOpen = false;

            // Handle specific cases
            // Cauldrons and fences
            if (Tag.CAULDRONS.isTagged(mat) || Tag.FENCES.isTagged(mat)) {
                shouldOpen = true;
            }
            // Jukeboxes
            else if (mat == Material.JUKEBOX) {
                Jukebox jukebox = (Jukebox) clickedBlock.getState();
                if (!jukebox.hasRecord()) {
                    shouldOpen = true;
                }
            }

            // If it's a special case, cancel the event and open the inventory
            if (shouldOpen) {
                event.setCancelled(true);
                itemManager.openInventory(player);
                return;
            }
            // Otherwise, use the normal item behavior
            else {
                return;
            }
        }

        event.setCancelled(true);
        itemManager.openInventory(player);
    }

    @EventHandler
    private void craftEvent(PrepareItemCraftEvent event) {
        // Prevent using the ender bag as a crafting ingredient
        CraftingInventory inv = event.getInventory();
        boolean containsEnderBag = Arrays.stream(inv.getContents()).anyMatch(stack -> itemManager.isEnderBag(stack));
        if (containsEnderBag) {
            inv.setResult(null);
        }
    }

    // These handlers will update any old versions of the item when config values
    // are changed
    @EventHandler
    private void playerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().forEach(e -> updateItemInInventory(e));
    }

    @EventHandler
    private void inventoryOpen(InventoryOpenEvent event) {
        event.getInventory().forEach(e -> updateItemInInventory(e));
    }

    @EventHandler
    private void entityPickup(EntityPickupItemEvent event) {
        if (event.getEntityType() != EntityType.PLAYER)
            return;
        updateItemInInventory(event.getItem().getItemStack());
    }

    private void updateItemInInventory(ItemStack stack) {
        if (itemManager.isEnderBag(stack))
            itemManager.updateItemStack(stack);
    }
}

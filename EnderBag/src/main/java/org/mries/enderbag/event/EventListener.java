package org.mries.enderbag.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
            return;
        if (item == null || !itemManager.isEnderBag(item))
            return;

        if (player.hasPermission("enderbag.use")) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null) {
                Material mat = clickedBlock.getType();
                // If the player clicked an interactable block, return early
                if (mat.isInteractable())
                    return;
            }
            event.setCancelled(true);
            itemManager.openInventory(player);
        } else {
            player.sendMessage(String.format("§cYou do not have permission to use the %s", config.itemName));
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

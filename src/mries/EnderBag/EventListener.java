package mries.EnderBag;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EventListener implements Listener {
    private static EnderBag plugin = null;

    public EventListener(EnderBag plugin) {
        EventListener.plugin = plugin;
    }

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() != null) {
                PersistentDataContainer container = event.getItem().getItemMeta().getPersistentDataContainer();
                Byte isEnderBag = container.getOrDefault(ItemManager.getEnderBagKey(), PersistentDataType.BYTE, (byte)0);
                if(isEnderBag != 0) {
                    player.openInventory(player.getEnderChest());
                    player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, .50f, 1);
                    event.setCancelled(true);
                }
            }
        }
    }

    // These handlers will update any old versions of the item when config values are changed
    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().forEach(itemStack -> {
            if(itemStack != null) {
                Byte isEnderBag = itemStack.getItemMeta().getPersistentDataContainer().get(ItemManager.getEnderBagKey(), PersistentDataType.BYTE);
                if(isEnderBag != null && isEnderBag == 1)
                    ItemManager.UpdateItemStack(itemStack);
            }
        });
    }

    @EventHandler
    public static void inventoryOpen(InventoryOpenEvent event) {
        event.getInventory().forEach(itemStack -> {
            if(itemStack != null) {
                Byte isEnderBag = itemStack.getItemMeta().getPersistentDataContainer().get(ItemManager.getEnderBagKey(), PersistentDataType.BYTE);
                if(isEnderBag != null && isEnderBag == 1)
                    ItemManager.UpdateItemStack(itemStack);
            }
        });
    }

    @EventHandler
    public static void entityPickup(EntityPickupItemEvent event) {
        Byte isEnderBag = event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(ItemManager.getEnderBagKey(), PersistentDataType.BYTE);
        if(isEnderBag != null && isEnderBag == 1)
            ItemManager.UpdateItemStack(event.getItem().getItemStack());
    }
}

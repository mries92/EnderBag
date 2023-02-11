package org.mries.enderbag;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import net.minecraft.world.inventory.Containers;

public class PacketManager {
    private static ProtocolManager manager = ProtocolLibrary.getProtocolManager();
    // This is a map of players to their most recently opened inventories
    private static Map<UUID, Integer> playerMap = new HashMap<>();

    public static void Init(EnderBag plugin) {
        // Keep track of a players last opened inventory for renaming
        manager.addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.OPEN_WINDOW) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        Integer inventoryId = event.getPacket().getIntegers().read(0);
                        playerMap.put(event.getPlayer().getUniqueId(), inventoryId);
                    }
                });
    }

    /**
     * Resends the open window packet. This is necessary since localization
     * is not applied when opening the ender chest programaticaly.
     * 
     * @param player The player who opened the chest
     */
    public static void sendOpenPacket(Player player) {
        PacketContainer container = new PacketContainer(PacketType.Play.Server.OPEN_WINDOW);
        container.getIntegers().writeSafely(0, playerMap.get(player.getUniqueId()));
        container.getModifier().writeSafely(1, Containers.c);
        WrappedChatComponent wc = WrappedChatComponent.fromJson("{\"translate\":\"container.enderchest\"}");
        container.getChatComponents().writeSafely(0, wc);
        try {
            manager.sendServerPacket(player, container);
            playerMap.remove(player.getUniqueId());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

package org.mries.enderbag.compatibility;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.minecraft.world.inventory.Containers;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NMS_1_19_RX implements PacketHandler {
    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();
    // This is a map of players to their most recently opened inventories
    private Map<UUID, Integer> playerMap = new HashMap<>();

    public NMS_1_19_RX(JavaPlugin plugin) {
        manager.addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.OPEN_WINDOW) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        Integer inventoryId = event.getPacket().getIntegers().read(0);
                        playerMap.put(event.getPlayer().getUniqueId(), inventoryId);
                    }
                }
        );
    }

    @Override
    public void sendOpenPacket(Player player) {
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
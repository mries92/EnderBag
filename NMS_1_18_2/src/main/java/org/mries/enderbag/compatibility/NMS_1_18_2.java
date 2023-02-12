package org.mries.enderbag.compatibility;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NMS_1_18_2 implements PacketHandler {
    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();
    // This is a map of players to their most recently opened inventories
    private Map<UUID, Integer> playerMap = new HashMap<>();

    @Override
    public void sendOpenPacket(Player player) {
        PacketContainer container = new PacketContainer(PacketType.Play.Server.OPEN_WINDOW);
        container.getIntegers().writeSafely(0, playerMap.get(player.getUniqueId()));
        container.getIntegers().writeSafely(1, 2);
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
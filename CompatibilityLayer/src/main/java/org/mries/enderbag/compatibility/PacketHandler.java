package org.mries.enderbag.compatibility;

import org.bukkit.entity.Player;

public interface PacketHandler {
    /**
     * Send a packet that localizes the name of the opened ender chest.
     * 
     * @param player The player that opened the chest.
     */
    public void sendOpenPacket(Player player);
}
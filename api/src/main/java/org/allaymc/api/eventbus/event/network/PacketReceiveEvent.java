package org.allaymc.api.eventbus.event.network;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

/**
 * @author daoge_cmd
 */
public class PacketReceiveEvent extends PacketEvent {
    public PacketReceiveEvent(EntityPlayer player, BedrockPacket packet) {
        super(player, packet);
    }
}
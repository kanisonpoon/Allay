package org.allaymc.server.network.processor.impl.ingame;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.server.network.processor.PacketProcessor;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketType;
import org.cloudburstmc.protocol.bedrock.packet.RequestChunkRadiusPacket;

/**
 * @author Cool_Loong
 */
public class RequestChunkRadiusPacketProcessor extends PacketProcessor<RequestChunkRadiusPacket> {
    @Override
    public void handleSync(EntityPlayer player, RequestChunkRadiusPacket packet, long receiveTime) {
        var radius = packet.getRadius();
        var maxRadius = packet.getMaxRadius();
        if (radius > maxRadius) {
            radius = maxRadius;
        }
        player.setChunkLoadingRadius(radius);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REQUEST_CHUNK_RADIUS;
    }
}

package org.allaymc.server.network.processor;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.server.Server;
import org.allaymc.server.network.DataPacketProcessor;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketType;
import org.cloudburstmc.protocol.bedrock.packet.SetDefaultGameTypePacket;

/**
 * Allay 27/01/2024
 *
 * @author IWareQ
 */
public class SetDefaultGameTypePacketProcessor extends DataPacketProcessor<SetDefaultGameTypePacket> {

    @Override
    public void handle(EntityPlayer player, SetDefaultGameTypePacket pk) {
        if (!player.isOp()) return;
        Server.SETTINGS.genericSettings().defaultGameType(GameType.from(pk.getGamemode()));
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_DEFAULT_GAME_TYPE;
    }
}
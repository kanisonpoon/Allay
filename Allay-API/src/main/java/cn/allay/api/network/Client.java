package cn.allay.api.network;

import cn.allay.api.entity.impl.EntityPlayer;
import cn.allay.api.server.Server;
import cn.allay.api.world.chunk.ChunkLoader;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket;

/**
 * 描述一个玩家客户端，玩家客户端可以持有一个EntityHuman并操控它
 * <p>
 * Allay Project 2023/6/23
 *
 * @author daoge_cmd
 */
public interface Client extends ChunkLoader {
    Server getServer();

    boolean isFirstSpawned();

    void sendPacket(BedrockPacket packet);

    void sendPlayStatus(PlayStatusPacket.Status status);

    void sendPacketImmediately(BedrockPacket packet);

    void disconnect(String reason);

    void disconnect(String reason, boolean hideReason);

    void initializePlayer();

    String getName();

    EntityPlayer getPlayerEntity();
}

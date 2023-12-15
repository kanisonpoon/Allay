package org.allaymc.api.server;

import org.allaymc.api.ApiInstanceHolder;
import org.allaymc.api.client.info.DeviceInfo;
import org.allaymc.api.client.skin.Skin;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.i18n.I18nTranslator;
import org.allaymc.api.network.NetworkServer;
import org.allaymc.api.scheduler.taskcreator.TaskCreator;
import org.allaymc.api.world.World;
import org.allaymc.api.world.WorldPool;
import org.allaymc.api.world.storage.PlayerStorage;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The server interface
 */
public interface Server extends TaskCreator {
    ApiInstanceHolder<Server> INSTANCE = ApiInstanceHolder.of();

    static Server getInstance() {
        return INSTANCE.get();
    }

    /**
     * Start the server
     */
    void start(long timeMillis);

    void shutdown();

    boolean isRunning();

    /**
     * Get the server settings
     *
     * @return the server settings
     */
    ServerSettings getServerSettings();

    PlayerStorage getPlayerStorage();

    int getOnlinePlayerCount();

    NetworkServer getNetworkServer();

    @UnmodifiableView
    Map<UUID, EntityPlayer> getOnlinePlayers();

    void onConnect(BedrockServerSession session);

    void onLoggedIn(EntityPlayer player);

    void onDisconnect(EntityPlayer player);

    WorldPool getWorldPool();

    default World getDefaultWorld() {
        return getWorldPool().getDefaultWorld();
    }

    void addToPlayerList(EntityPlayer player);

    void addToPlayerList(UUID uuid, long entityId, String name, DeviceInfo deviceInfo, String xuid, Skin skin);

    void removeFromPlayerList(EntityPlayer player);

    void removeFromPlayerList(UUID uuid);

    Map<UUID, PlayerListPacket.Entry> getPlayerListEntryMap();

    default void sendFullPlayerListInfoTo(EntityPlayer player) {
        var playerListPacket = new PlayerListPacket();
        playerListPacket.setAction(PlayerListPacket.Action.ADD);
        getPlayerListEntryMap().forEach((uuid, entry) -> {
            if (uuid != player.getUUID()) {
                playerListPacket.getEntries().add(entry);
            }
        });
        player.sendPacket(playerListPacket);
    }

    void broadcastPacket(BedrockPacket packet);

    ThreadPoolExecutor getComputeThreadPool();

    ExecutorService getVirtualThreadPool();

    default void broadcastChat(EntityPlayer sender, String message) {
        getOnlinePlayers().values().forEach(player -> player.sendChat(sender, message));
    }

    void broadcastTr(String tr);

    void broadcastTr(String tr, String... args);

    void sendTr(String tr);

    void sendTr(String tr, String... args);

    I18nTranslator getI18nTranslator();
}

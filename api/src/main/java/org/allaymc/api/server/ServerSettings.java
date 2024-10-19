package org.allaymc.api.server;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import io.netty.util.ResourceLeakDetector;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.allaymc.api.i18n.LangCode;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;

import java.util.UUID;

/**
 * ServerSettings is used to store the settings of the server.
 *
 * @author daoge_cmd
 */
@Getter
@Accessors(fluent = true)
public class ServerSettings extends OkaeriConfig {

    @CustomKey("generic-settings")
    private GenericSettings genericSettings = new GenericSettings();
    @CustomKey("network-settings")
    private NetworkSettings networkSettings = new NetworkSettings();
    @CustomKey("world-settings")
    private WorldConfig worldSettings = new WorldConfig();
    @CustomKey("entity-settings")
    private EntitySettings entitySettings = new EntitySettings();
    @CustomKey("storage-settings")
    private StorageSettings storageSettings = new StorageSettings();
    @CustomKey("resource-pack-settings")
    private ResourcePackSettings resourcePackSettings = new ResourcePackSettings();
    @CustomKey("bstats-settings")
    private BStatsSettings bStatsSettings = new BStatsSettings();

    @Getter
    @Setter
    @Accessors(fluent = true)
    public static class GenericSettings extends OkaeriConfig {

        private String motd = "An allay-powered server";

        @CustomKey("sub-motd")
        @Comment("Usually only visible on the LAN interface")
        private String subMotd = "https://github.com/AllayMC/Allay";

        @CustomKey("max-client-count")
        private int maxClientCount = 20;

        @CustomKey("game-type")
        @Comment("Possible values: SURVIVAL, CREATIVE, SPECTATOR")
        private GameType defaultGameType = GameType.CREATIVE;

        @CustomKey("default-permission")
        @Comment("Possible values: VISITOR, MEMBER, OPERATOR")
        private PlayerPermission defaultPermission = PlayerPermission.OPERATOR;

        @Comment("The language used by console")
        private LangCode language = LangCode.en_US;

        @Comment("Open debug mode")
        @Comment("If debug mode is enabled, the console will outputs more detailed information")
        private boolean debug = false;

        @CustomKey("is-whitelisted")
        private boolean isWhitelisted = false;

        @Comment("Whether to display the GUI")
        private boolean enableGui = true;

        @CustomKey("max-compute-thread-count")
        @Comment("Decide the maximum count of threads in compute thread pool")
        @Comment("If the value <= 0, the count will be same to the count of available processors")
        private int maxComputeThreadCount = 0;
    }

    @Getter
    @Accessors(fluent = true)
    public static class NetworkSettings extends OkaeriConfig {
        @Comment("Server ip")
        private String ip = "127.0.0.1";

        @Comment("Server port")
        private int port = 19132;

        @CustomKey("xbox-auth")
        private boolean xboxAuth = true;

        @Comment("Turning this on is highly recommended for security reasons")
        @CustomKey("enable-network-encryption")
        private boolean enableNetworkEncryption = true;

        @Comment("Possible values: ZLIB, SNAPPY")
        @CustomKey("compression-algorithm")
        private PacketCompressionAlgorithm compressionAlgorithm = PacketCompressionAlgorithm.ZLIB;

        @CustomKey("network-thread-number")
        @Comment("0 == the server will automatically determine the number of netty threads")
        private int networkThreadNumber = 0;

        @CustomKey("max-synced-packets-handle-count-once")
        @Comment("The maximum number of packets that can be processed at once")
        private int maxSyncedPacketsHandleCountAtOnce = 128;

        @CustomKey("enable-independent-network-thread")
        @Comment("If set to true, the network thread will be independent of the main world thread")
        @Comment("Which will reduce packet processing delay significantly")
        @Comment("However, independent network threads will increase CPU usage to a certain extent")
        private boolean enableIndependentNetworkThread = Runtime.getRuntime().availableProcessors() > 8;

        @Comment("Represents the level of resource leak detection.")
        @Comment("Possible values: DISABLED, SIMPLE, ADVANCED, PARANOID")
        @CustomKey("resource-leak-detector-level")
        private ResourceLeakDetector.Level resourceLeakDetectorLevel = ResourceLeakDetector.Level.DISABLED;
    }

    @Getter
    @Accessors(fluent = true)
    public static class WorldConfig extends OkaeriConfig {

        @CustomKey("ticking-radius")
        private int tickingRadius = 8;

        @CustomKey("view-distance")
        private int viewDistance = 8;

        @CustomKey("chunk-try-send-count-per-tick")
        private int chunkTrySendCountPerTick = 16;

        @CustomKey("use-sub-chunk-sending-system")
        private boolean useSubChunkSendingSystem = false;

        @Comment("Possible values: ASYNC, SYNC")
        @Comment("This only works if sub-chunk sending system is not enabled")
        @Comment("And will be forced to SYNC if sub-chunk sending system is enabled")
        @CustomKey("chunk-sending-strategy")
        private ChunkSendingStrategy chunkSendingStrategy = ChunkSendingStrategy.ASYNC;

        @CustomKey("fully-join-chunk-threshold")
        private int fullyJoinChunkThreshold = 56;

        @Comment("Determines how long a chunk without chunk loaders will remain loaded (gt)")
        @CustomKey("remove-unneeded-chunk-cycle")
        private int removeUnneededChunkCycle = 1200;

        @Comment("If set to true, the server will load chunks around the spawn point")
        @Comment("Which will reduce the time on joining server")
        @Comment("However, this will increase the server's memory usage")
        @CustomKey("load-spawn-point-chunks")
        private boolean loadSpawnPointChunks = true;

        public enum ChunkSendingStrategy {
            ASYNC,
            SYNC
        }
    }

    @Getter
    @Accessors(fluent = true)
    public static class EntitySettings extends OkaeriConfig {

        @Comment("Entity physics engine settings")
        @Comment("Do not change them if you don't know what you are doing!")
        @CustomKey("physics-engine-settings")
        private PhysicsEngineSettings physicsEngineSettings = new PhysicsEngineSettings();

        @Getter
        @Accessors(fluent = true)
        public static class PhysicsEngineSettings extends OkaeriConfig {
            @Comment("Send packets to the client when the amount of position change accumulates")
            @Comment("This threshold acts on each axis individually")
            @Comment("Increasing this threshold will reduce bandwidth pressure, but may result in untimely motion updates")
            @CustomKey("diff-position-threshold")
            private float diffPositionThreshold = 0.0001f;

            @Comment("Similar to \"diffPositionThreshold\"")
            @CustomKey("diff-rotation-threshold")
            private float diffRotationThreshold = 0.1f;

            @Comment("When the motion falls below this value, its motion is zeroed")
            @CustomKey("motion-threshold")
            private float motionThreshold = 0.003f;

            @Comment("Walk assist offset")
            @CustomKey("stepping-offset")
            private float steppingOffset = 0.05f;

            @Comment("This usually determines how quickly an entity item is moved when getting stuck in a block")
            @CustomKey("block-collision-motion")
            private float blockCollisionMotion = 0.2f;

            @CustomKey("fat-aabb-margin")
            private float fatAABBMargin = 0.0005f;

            @Comment("Delta move packet will reduce the network pressure if there are a lot of entities")
            @CustomKey("use-delta-move-packet")
            private boolean useDeltaMovePacket = false;
        }
    }

    @Getter
    @Accessors(fluent = true)
    public static class StorageSettings extends OkaeriConfig {
        @CustomKey("save-player-data")
        @Comment("If set to false, the player's data will not be saved")
        private boolean savePlayerData = true;

        @CustomKey("player-data-auto-save-cycle")
        @Comment("Determines the cycle of player data auto saving")
        private int playerDataAutoSaveCycle = 20 * 60 * 5;

        @CustomKey("chunk-auto-save-cycle")
        @Comment("Determines the cycle of chunk auto saving")
        private int chunkAutoSaveCycle = 20 * 60 * 5;
    }

    @Getter
    @Accessors(fluent = true)
    public static class ResourcePackSettings extends OkaeriConfig {

        @CustomKey("auto-encrypt")
        @Comment("If set to true, packs will be automatically encrypted")
        private boolean autoEncrypt = true;

        @CustomKey("max-chunk-size")
        @Comment("The maximum size of a resource pack chunk (unit: KB)")
        @Comment("Decrease this value may reduce the pressure on the network when sending packs to multiple clients")
        @Comment("However, it may also increase the time it takes to send the packs")
        private int maxChunkSize = 100; // 100KB, from BDS

        @Comment("true - the player must accept resource packs, otherwise he will not log in to the server")
        @Comment("false - the player can log in to the server without accepting resource packs")
        @CustomKey("force-resource-packs")
        private boolean forceResourcePacks = false;

        @Comment("If set to true, the client resource packs will be allowed")
        @CustomKey("allow-client-resource-packs")
        private boolean allowClientResourcePacks = false;

        @Comment("If set to true, all skins will be marked as trusted")
        @CustomKey("trust-all-skins")
        private boolean trustAllSkins = true;

        // TODO: URL packs configuration
    }

    @Getter
    @Accessors(fluent = true)
    public static class BStatsSettings extends OkaeriConfig {
        @Comment("bStats (https://bStats.org) collects some basic information for plugin authors, like how")
        @Comment("many people use their plugin and their total player count. It's recommended to keep bStats")
        @Comment("enabled, but if you're not comfortable with this, you can turn this setting off. There is no")
        @Comment("performance penalty associated with having metrics enabled, and data sent to bStats is fully anonymous.")
        private boolean enable = true;

        @CustomKey("server-uuid")
        private String serverUUID = UUID.randomUUID().toString();

        @CustomKey("log-failed-requests")
        private boolean logFailedRequests = false;

        @CustomKey("log-sent-data")
        private boolean logSentData = false;

        @CustomKey("log-response-status-text")
        private boolean logResponseStatusText = false;
    }
}

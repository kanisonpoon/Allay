package org.allaymc.api.eventbus.event.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.eventbus.event.CancellableEvent;
import org.allaymc.api.math.location.Location3f;
import org.allaymc.api.math.location.Location3fc;
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket;

/**
 * @author daoge_cmd
 */
@Getter
public class EntityTeleportEvent extends EntityEvent implements CancellableEvent {
    protected Location3fc from;
    @Setter
    protected Location3f to;
    @Getter
    protected Reason reason;

    public EntityTeleportEvent(Entity entity, Location3fc from, Location3f to, Reason reason) {
        super(entity);
        this.from = from;
        this.to = to;
        this.reason = reason;
    }

    public boolean isTeleportBetweenWorlds() {
        return from.dimension().getWorld() != to.dimension().getWorld();
    }

    @Getter
    @AllArgsConstructor
    public enum Reason {
        // Unknown reason, may be caused by plugin
        UNKNOWN(MovePlayerPacket.TeleportationCause.UNKNOWN),
        // Ender pearl
        PROJECTILE(MovePlayerPacket.TeleportationCause.PROJECTILE),
        // Chorus fruit
        CHORUS_FRUIT(MovePlayerPacket.TeleportationCause.CHORUS_FRUIT),
        // Command
        COMMAND(MovePlayerPacket.TeleportationCause.COMMAND);

        private final MovePlayerPacket.TeleportationCause networkValue;
    }
}

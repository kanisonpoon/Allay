package org.allaymc.server.world.service;

import io.netty.util.internal.PlatformDependent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.eventbus.event.entity.EntityDespawnEvent;
import org.allaymc.api.eventbus.event.entity.EntitySpawnEvent;
import org.allaymc.api.world.service.EntityService;
import org.allaymc.server.entity.component.EntityBaseComponentImpl;
import org.allaymc.server.entity.impl.EntityImpl;
import org.allaymc.server.world.chunk.AllayChunk;

import java.util.Queue;

/**
 * @author Cool_Loong
 */
@Slf4j
@RequiredArgsConstructor
public class AllayEntityService implements EntityService {
    protected final AllayEntityPhysicsService entityPhysicsService;
    protected final Queue<EntityUpdateOperation> entityUpdateOperationQueue = PlatformDependent.newMpscQueue();

    public void tick() {
        while (!entityUpdateOperationQueue.isEmpty()) {
            var operation = entityUpdateOperationQueue.poll();
            var entity = operation.entity();
            switch (operation.type()) {
                case ADD -> addEntityImmediately(entity);
                case REMOVE -> removeEntityImmediately(entity);
            }

            operation.callback().run();
        }
    }

    private void removeEntityImmediately(Entity entity) {
        var event = new EntityDespawnEvent(entity);
        event.call();

        var chunk = (AllayChunk) entity.getCurrentChunk();
        if (chunk == null)
            throw new IllegalStateException("Trying to despawn an entity from an unload chunk!");

        chunk.removeEntity(entity.getRuntimeId());
        entityPhysicsService.removeEntity(entity);

        entity.despawnFromAll();
        ((EntityBaseComponentImpl) ((EntityImpl) entity).getBaseComponent()).setWillBeDespawnedNextTick(false);
        ((EntityBaseComponentImpl) ((EntityImpl) entity).getBaseComponent()).setSpawned(false);
    }

    private void addEntityImmediately(Entity entity) {
        var event = new EntitySpawnEvent(entity);
        event.call();

        var chunk = (AllayChunk) entity.getCurrentChunk();
        if (chunk == null)
            throw new IllegalStateException("Entity can't spawn in unloaded chunk!");

        chunk.addEntity(entity);
        entity.spawnTo(chunk.getPlayerChunkLoaders());

        entityPhysicsService.addEntity(entity);
        ((EntityBaseComponentImpl) ((EntityImpl) entity).getBaseComponent()).setWillBeSpawnedNextTick(false);
        ((EntityBaseComponentImpl) ((EntityImpl) entity).getBaseComponent()).setSpawned(true);
    }

    @Override
    public void addEntity(Entity entity, Runnable callback) {
        if (!entity.canBeSpawned()) {
            log.warn("Trying to add an entity which can't be added! Entity: {}", entity);
            return;
        }

        ((EntityBaseComponentImpl) ((EntityImpl) entity).getBaseComponent()).setWillBeSpawnedNextTick(true);
        entityUpdateOperationQueue.add(new EntityUpdateOperation(
                entity,
                EntityUpdateType.ADD,
                callback
        ));
    }

    @Override
    public void removeEntity(Entity entity, Runnable callback) {
        if (entity.willBeDespawnedNextTick()) {
            log.warn("Trying to remove an entity twice! Entity: {}", entity);
            return;
        }

        ((EntityBaseComponentImpl) ((EntityImpl) entity).getBaseComponent()).setWillBeDespawnedNextTick(true);
        entityUpdateOperationQueue.add(new EntityUpdateOperation(
                entity,
                EntityUpdateType.REMOVE,
                callback
        ));
    }

    protected enum EntityUpdateType {
        ADD,
        REMOVE
    }

    protected record EntityUpdateOperation(Entity entity, EntityUpdateType type, Runnable callback) {}
}

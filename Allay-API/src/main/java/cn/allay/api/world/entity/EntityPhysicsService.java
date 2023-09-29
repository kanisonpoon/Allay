package cn.allay.api.world.entity;

import cn.allay.api.entity.Entity;
import cn.allay.api.math.location.Location3fc;
import cn.allay.api.math.voxelshape.VoxelShape;
import org.joml.primitives.AABBfc;

import java.util.Collections;
import java.util.List;

/**
 * Allay Project 2023/8/3
 *
 * @author daoge_cmd
 */
public interface EntityPhysicsService {

    void tick();

    void updateEntity(Entity entity);

    void addEntity(Entity entity);

    void removeEntity(Entity entity);

    boolean containEntity(Entity entity);

    void offerScheduledMove(Entity entity, Location3fc newLoc);

    default List<Entity> computeCollidingEntities(Entity entity, boolean ignoreEntityHasCollision) {
        var entities = computeCollidingEntities(entity.getOffsetAABB(), ignoreEntityHasCollision);
        entities.removeIf(e -> e.getUniqueId() == entity.getUniqueId());
        return entities;
    }

    default List<Entity> computeCollidingEntities(Entity entity) {
        return computeCollidingEntities(entity, false);
    }

    default List<Entity> computeCollidingEntities(AABBfc aabb) {
        return computeCollidingEntities(aabb, false);
    }

    List<Entity> computeCollidingEntities(AABBfc aabb, boolean ignoreEntityHasCollision);

    default List<Entity> computeCollidingEntities(VoxelShape voxelShape) {
        return computeCollidingEntities(voxelShape, false);
    }

    List<Entity> computeCollidingEntities(VoxelShape voxelShape, boolean ignoreEntityHasCollision);

    default List<Entity> getCachedEntityCollidingResult(Entity entity) {
        return getCachedEntityCollidingResult(entity, false);
    }

    List<Entity> getCachedEntityCollidingResult(Entity entity, boolean ignoreEntityHasCollision);
}

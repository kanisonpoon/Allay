package cn.allay.api.world.entity;

import cn.allay.api.entity.Entity;
import org.joml.primitives.AABBdc;

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

    void containEntity(Entity entity);

    default List<Entity> getCollidingEntities(Entity entity) {
        return getCollidingEntities(entity.)
    }

    List<Entity> getCollidingEntities(AABBdc aabb);


}
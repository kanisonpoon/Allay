package cn.allay.api.entity.interfaces;

import cn.allay.api.data.VanillaEntityId;
import cn.allay.api.entity.Entity;
import cn.allay.api.entity.type.EntityType;
import cn.allay.api.entity.type.EntityTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface EntityZombie extends Entity {
  EntityType<EntityZombie> ZOMBIE_TYPE = EntityTypeBuilder
          .builder(EntityZombie.class)
          .vanillaEntity(VanillaEntityId.ZOMBIE)
          .build();
}
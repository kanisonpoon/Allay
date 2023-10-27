package cn.allay.api.entity.interfaces.guardian;

import cn.allay.api.data.VanillaEntityId;
import cn.allay.api.entity.Entity;
import cn.allay.api.entity.type.EntityType;
import cn.allay.api.entity.type.EntityTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface EntityGuardian extends Entity {
  EntityType<EntityGuardian> GUARDIAN_TYPE = EntityTypeBuilder
          .builder(EntityGuardian.class)
          .vanillaEntity(VanillaEntityId.GUARDIAN)
          .build();
}
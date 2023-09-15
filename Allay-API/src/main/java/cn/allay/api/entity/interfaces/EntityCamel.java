package cn.allay.api.entity.interfaces;

import cn.allay.api.data.VanillaEntityId;
import cn.allay.api.entity.Entity;
import cn.allay.api.entity.type.EntityType;
import cn.allay.api.entity.type.EntityTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface EntityCamel extends Entity {
  EntityType<EntityCamel> CAMEL_TYPE = EntityTypeBuilder
          .builder(EntityCamel.class)
          .vanillaEntity(VanillaEntityId.CAMEL)
          .build();
}
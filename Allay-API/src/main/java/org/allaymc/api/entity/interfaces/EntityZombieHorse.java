package org.allaymc.api.entity.interfaces;

import org.allaymc.api.data.VanillaEntityId;
import org.allaymc.api.entity.Entity;
import org.allaymc.api.entity.type.EntityType;
import org.allaymc.api.entity.type.EntityTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface EntityZombieHorse extends Entity {
  EntityType<EntityZombieHorse> ZOMBIE_HORSE_TYPE = EntityTypeBuilder
          .builder(EntityZombieHorse.class)
          .vanillaEntity(VanillaEntityId.ZOMBIE_HORSE)
          .build();
}
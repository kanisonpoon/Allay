package org.allaymc.api.data;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import org.allaymc.api.block.material.MaterialType;

/**
 * Automatically generated by {@code org.allaymc.codegen.VanillaMaterialTypeGen} <br>
 * Allay Project <p>
 * @author daoge_cmd
 */
public interface VanillaMaterialTypes {
  Map<String, MaterialType> NAME_TO_MATERIAL_TYPE = new HashMap<>();

  MaterialType WATER = create("Water");

  MaterialType SAND = create("Sand");

  MaterialType SCULK = create("Sculk");

  MaterialType SNOW = create("Snow");

  MaterialType SOLIDPLANT = create("SolidPlant");

  MaterialType DIRT = create("Dirt");

  MaterialType CARPET = create("Carpet");

  MaterialType METAL = create("Metal");

  MaterialType DENY = create("Deny");

  MaterialType STONE = create("Stone");

  MaterialType AMETHYST = create("Amethyst");

  MaterialType SURFACETYPETOTAL = create("SurfaceTypeTotal");

  MaterialType BUBBLE = create("Bubble");

  MaterialType EXPLOSIVE = create("Explosive");

  MaterialType CACTUS = create("Cactus");

  MaterialType CLOTH = create("Cloth");

  MaterialType PISTON = create("Piston");

  MaterialType TOPSNOW = create("TopSnow");

  MaterialType CLAY = create("Clay");

  MaterialType LAVA = create("Lava");

  MaterialType ROOT = create("Root");

  MaterialType AIR = create("Air");

  MaterialType SCULKVEIN = create("SculkVein");

  MaterialType DECORATION = create("Decoration");

  MaterialType CLIENTREQUESTPLACEHOLDER = create("ClientRequestPlaceholder");

  MaterialType WEB = create("Web");

  MaterialType SOFTEGG = create("SoftEgg");

  MaterialType DRIPSTONE = create("Dripstone");

  MaterialType PACKEDICE = create("PackedIce");

  MaterialType GLASS = create("Glass");

  MaterialType CAKE = create("Cake");

  MaterialType BARRIER = create("Barrier");

  MaterialType NETHERWART = create("Netherwart");

  MaterialType LEAVES = create("Leaves");

  MaterialType PLANT = create("Plant");

  MaterialType STONEDECORATION = create("StoneDecoration");

  MaterialType PORTAL = create("Portal");

  MaterialType STRUCTUREVOID = create("StructureVoid");

  MaterialType VEGETABLE = create("Vegetable");

  MaterialType BED = create("Bed");

  MaterialType REDSTONEWIRE = create("RedstoneWire");

  MaterialType WOOD = create("Wood");

  MaterialType EGG = create("Egg");

  MaterialType DECORATIONSOLID = create("DecorationSolid");

  MaterialType ICE = create("Ice");

  MaterialType SLIME = create("Slime");

  MaterialType ALLOW = create("Allow");

  MaterialType CORAL = create("Coral");

  MaterialType REINFORCEDSTONE = create("ReinforcedStone");

  MaterialType FIRE = create("Fire");

  MaterialType SPONGE = create("Sponge");

  MaterialType BUILDABLEGLASS = create("BuildableGlass");

  MaterialType POWDERSNOW = create("PowderSnow");

  static MaterialType create(String name) {
    var tag = new MaterialType(name);
    NAME_TO_MATERIAL_TYPE.put(name, tag);
    return tag;
  }

  static MaterialType getMaterialTypeByName(String name) {
    return NAME_TO_MATERIAL_TYPE.get(name);
  }
}
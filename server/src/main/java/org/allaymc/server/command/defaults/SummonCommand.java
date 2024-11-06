package org.allaymc.server.command.defaults;

import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.SimpleCommand;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.entity.initinfo.EntityInitInfo;
import org.allaymc.api.entity.type.EntityType;
import org.allaymc.api.entity.type.EntityTypes;
import org.allaymc.api.i18n.TrKeys;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/**
 * @author daoge_cmd
 */
public class SummonCommand extends SimpleCommand {

    public SummonCommand() {
        super("summon", TrKeys.M_COMMANDS_SUMMON_DESCRIPTION);
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .entityType("entityType")
                .pos("pos")
                .optional()
                .intNum("count", 1)
                .optional()
                .exec((context, sender) -> {
                    var dim = sender.getCmdExecuteLocation().dimension();
                    EntityType<?> entityType = context.getResult(0);
                    if (entityType == EntityTypes.PLAYER) {
                        context.addError("%" + TrKeys.M_COMMANDS_SUMMON_FAILED);
                        return context.fail();
                    }
                    Vector3fc pos = context.getResult(1);
                    if (pos == null) {
                        pos = sender.getCmdExecuteLocation();
                    }
                    var floorPos = pos.floor(new Vector3f());
                    if (dim.getChunkService().getChunkByDimensionPos((int) floorPos.x(), (int) floorPos.z()) == null) {
                        context.addError("%" + TrKeys.M_COMMANDS_SUMMON_OUTOFWORLD);
                        return context.fail();
                    }
                    int count = context.getResult(2);

                    for (var i = 1; i <= count; i++) {
                        var entity = entityType.createEntity(
                                EntityInitInfo.builder()
                                        .dimension(dim)
                                        .pos(pos)
                                        .build()
                        );
                        dim.getEntityService().addEntity(entity);
                    }

                    context.addOutput(TrKeys.M_COMMANDS_SUMMON_SUCCESS);
                    return context.success();
                }, SenderType.ENTITY);
    }
}
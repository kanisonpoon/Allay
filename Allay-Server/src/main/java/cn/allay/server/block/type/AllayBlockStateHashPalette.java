package cn.allay.server.block.type;

import cn.allay.api.block.palette.BlockStateHashPalette;
import cn.allay.api.block.type.BlockState;
import cn.allay.api.registry.SimpleMappedRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cool_Loong <br>
 * @date 2023/5/29 <br>
 * Allay Project
 */
@Slf4j
public class AllayBlockStateHashPalette extends SimpleMappedRegistry<Integer, BlockState, Map<Integer, BlockState>> implements BlockStateHashPalette {
    public AllayBlockStateHashPalette() {
        super(null, input -> new ConcurrentHashMap<>());
    }
}
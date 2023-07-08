package cn.allay.server.world;

import cn.allay.api.world.World;
import cn.allay.api.world.WorldPool;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Allay Project 2023/7/8
 *
 * @author daoge_cmd
 */
public class AllayWorldPool implements WorldPool {

    private final Map<String, World> worlds = new ConcurrentHashMap<>();
    private World defaultWorld;

    @Override
    public World getWorld(String name) {
        return worlds.get(name);
    }

    @Override
    public Map<String, World> getWorlds() {
        return Collections.unmodifiableMap(worlds);
    }

    @Override
    public void addWorld(World world) {
        if (worlds.containsKey(world.getName()))
            throw new IllegalArgumentException("World " + world.getName() + " already exists");
        worlds.put(world.getName(), world);
        world.startTick();
    }

    @Override
    public void setDefaultWorld(World world) {
        if (!worlds.containsKey(world.getName()))
            addWorld(world);
        this.defaultWorld = world;
    }

    @Override
    public World getDefaultWorld() {
        return defaultWorld;
    }
}

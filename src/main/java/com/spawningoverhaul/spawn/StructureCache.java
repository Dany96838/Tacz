package com.spawningoverhaul.spawn;

import com.spawningoverhaul.config.SpawningConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class StructureCache {
    private static final Set<String> DANGEROUS=Set.of("minecraft:stronghold","minecraft:fortress","minecraft:monument","minecraft:mansion","minecraft:mineshaft","minecraft:dungeon","minecraft:ancient_city");
    private static final Set<String> SAFE=Set.of("minecraft:village");
    private static final Map<Key,Entry> CACHE=new ConcurrentHashMap<>();
    private StructureCache(){}
    public static boolean isDangerous(Level level,BlockPos pos){
        if(!(level instanceof ServerLevel server))return false;
        long chunk=(((long)(pos.getX()>>4))<<32)^((pos.getZ()>>4)&0xffffffffL);
        Key key=new Key(server.dimension(),chunk); long now=System.nanoTime();
        Entry cached=CACHE.get(key);
        if(cached!=null&&now-cached.time<10_000_000_000L)return cached.value;
        boolean result=check(server,pos);
        CACHE.put(key,new Entry(result,now));
        if(CACHE.size()>4096)CACHE.entrySet().removeIf(e->now-e.getValue().time>10_000_000_000L);
        return result;
    }
    private static boolean check(ServerLevel level,BlockPos pos){
        var lookup=level.registryAccess().registryOrThrow(Registries.STRUCTURE);
        var manager=level.structureManager();
        for(var entry:lookup.entrySet()){
            String id=entry.getKey().location().toString();
            if(SAFE.contains(id)||SpawningConfig.additionalSafeStructures.contains(id))continue;
            if(!(DANGEROUS.contains(id)||SpawningConfig.additionalDangerousStructures.contains(id)))continue;
            if(manager.getStructureAt(pos,entry.getValue()).isValid())return true;
        }
        return false;
    }
    private record Key(ResourceKey<Level> dimension,long chunk){}
    private record Entry(boolean value,long time){}
}

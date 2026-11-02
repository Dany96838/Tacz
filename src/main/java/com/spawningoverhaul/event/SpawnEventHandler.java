package com.spawningoverhaul.event;

import com.spawningoverhaul.SpawningOverhaul;
import com.spawningoverhaul.config.SpawningConfig;
import com.spawningoverhaul.spawn.SpawnContext;
import com.spawningoverhaul.spawn.SpawnMultiplierCalculator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

public final class SpawnEventHandler {
    @SubscribeEvent
    public void onPositionCheck(MobSpawnEvent.PositionCheck event) {
        if(!SpawningConfig.enabled || !(event.getLevel() instanceof ServerLevel level))return;
        MobSpawnType type=event.getSpawnType();
        if(type!=MobSpawnType.NATURAL && type!=MobSpawnType.CHUNK_GENERATION)return;
        var mob=event.getEntity();if(mob==null)return;
        if(!SpawningConfig.isDimensionEnabled(level.dimension().location().toString()))return;
        BlockPos pos=BlockPos.containing(event.getX(),event.getY(),event.getZ());
        SpawnContext context=new SpawnContext(level,pos,mob.getType(),type);
        double multiplier=SpawnMultiplierCalculator.calculate(context);
        if(!SpawnMultiplierCalculator.shouldAllow(multiplier,level.getRandom())){
            event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
            if(SpawningConfig.debug)SpawningOverhaul.LOGGER.info("Rejected {} at {} multiplier={} cave={} forest={} structure={}",mob.getType(),pos,multiplier,context.isCave(),context.forestDensity(),context.isDangerousStructure());
        }
    }
}

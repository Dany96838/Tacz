package com.spawningoverhaul.event;

import com.spawningoverhaul.SpawningOverhaul;
import com.spawningoverhaul.config.SpawningConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.bus.api.SubscribeEvent;

public final class SpawnEventHandler {
    @SubscribeEvent
    public void onPositionCheck(MobSpawnEvent.PositionCheck event) {
        if (!SpawningConfig.enabled || !(event.getLevel() instanceof ServerLevel level)) return;
        MobSpawnType type = event.getSpawnType();
        if (type != MobSpawnType.NATURAL && type != MobSpawnType.CHUNK_GENERATION) return;
        var mob = event.getEntity();
        if (mob == null) return;
        BlockPos pos = BlockPos.containing(event.getX(), event.getY(), event.getZ());

        if (SpawningConfig.spiderOnlyInCaves && (mob.getType() == EntityType.SPIDER || mob.getType() == EntityType.CAVE_SPIDER)
                && !isCave(level, pos)) {
            event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
            return;
        }

        double multiplier = calculateMultiplier(level, pos);
        double reference = Math.max(1.0, Math.max(SpawningConfig.maxCaveMultiplier,
                Math.max(SpawningConfig.maxForestMultiplier, SpawningConfig.dangerousStructureMultiplier)));
        double acceptance = Math.max(0.0, Math.min(1.0, multiplier / reference));
        if (level.getRandom().nextDouble() >= acceptance) {
            event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
            if (SpawningConfig.debug) SpawningOverhaul.LOGGER.debug("Rejected natural spawn {} at {} (multiplier={}, acceptance={})", mob.getType(), pos, multiplier, acceptance);
        }
    }

    private static boolean isCave(ServerLevel level, BlockPos pos) {
        return pos.getY() < 60 && !level.canSeeSky(pos);
    }

    private static double calculateMultiplier(ServerLevel level, BlockPos pos) {
        double result = SpawningConfig.baseMultiplier;
        if (isCave(level, pos)) {
            double depth = Math.min(5.0, (60.0 - pos.getY()) / 31.0 + 1.0);
            double ratio = Math.max(0.0, Math.min(1.0, (depth - 1.0) / 4.0));
            result = Math.max(result, SpawningConfig.minCaveMultiplier + (SpawningConfig.maxCaveMultiplier - SpawningConfig.minCaveMultiplier) * ratio);
        }
        int logs = countLogs(level, pos, 10, 50);
        if (logs > 0) {
            double density = Math.min(1.0, logs / 50.0);
            result = Math.max(result, SpawningConfig.minForestMultiplier + (SpawningConfig.maxForestMultiplier - SpawningConfig.minForestMultiplier) * density);
        }
        if (SpawningConfig.structuresEnabled && inDangerousStructure(level, pos)) result *= SpawningConfig.dangerousStructureMultiplier;
        return result;
    }

    private static int countLogs(ServerLevel level, BlockPos center, int radius, int limit) {
        int count = 0;
        for (int dx = -radius; dx <= radius; dx += 2) for (int dz = -radius; dz <= radius; dz += 2) {
            if (dx * dx + dz * dz > radius * radius) continue;
            for (int dy = -2; dy <= 2; dy += 2) {
                if (level.getBlockState(center.offset(dx, dy, dz)).is(BlockTags.LOGS) && ++count >= limit) return count;
            }
        }
        return count;
    }

    private static boolean inDangerousStructure(ServerLevel level, BlockPos pos) {
        var manager = level.structureManager();
        var registry = level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.STRUCTURE);
        for (var entry : registry.entrySet()) {
            String id = entry.getKey().location().toString();
            if (id.equals("minecraft:village")) continue;
            if (!(id.equals("minecraft:stronghold") || id.equals("minecraft:fortress") || id.equals("minecraft:monument")
                    || id.equals("minecraft:mansion") || id.equals("minecraft:mineshaft") || id.equals("minecraft:ancient_city")
                    || id.equals("minecraft:ruined_portal") || id.equals("minecraft:dungeon"))) continue;
            if (manager.getStructureAt(pos, entry.getValue()).isValid()) return true;
        }
        return false;
    }
}

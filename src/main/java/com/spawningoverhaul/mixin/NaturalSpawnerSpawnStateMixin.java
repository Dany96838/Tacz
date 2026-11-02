package com.spawningoverhaul.mixin;

import com.spawningoverhaul.config.SpawningConfig;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Scales vanilla's per-category natural spawn cap without bypassing the
 * vanilla local-player mob cap checks.
 */
@Mixin(NaturalSpawner.SpawnState.class)
public abstract class NaturalSpawnerSpawnStateMixin {
    @Redirect(
        method = "canSpawnForCategory",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/MobCategory;getMaxInstancesPerChunk()I"
        )
    )
    private int spawningoverhaul$scaleMobCap(MobCategory category) {
        int vanillaCap = category.getMaxInstancesPerChunk();
        if (vanillaCap <= 0) return vanillaCap;
        double multiplier = SpawningConfig.mobCapMultiplier;
        return Math.max(vanillaCap, (int)Math.ceil(vanillaCap * multiplier));
    }
}

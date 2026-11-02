package com.spawningoverhaul.spawn;

import com.spawningoverhaul.config.SpawningConfig;
import com.spawningoverhaul.spawn.rules.MobRuleRegistry;
import net.minecraft.util.RandomSource;

public final class SpawnMultiplierCalculator {
    private SpawnMultiplierCalculator(){}
    public static double calculate(SpawnContext c){
        double base=SpawningConfig.baseMultiplier;
        double cave=0,forest=0;
        if(c.isCave()){
            double ratio=Math.max(0,Math.min(1,(c.caveDepth()-1.0)/4.0));
            cave=SpawningConfig.minCaveMultiplier+(SpawningConfig.maxCaveMultiplier-SpawningConfig.minCaveMultiplier)*ratio;
        }
        double density=c.forestDensity();
        if(density>0)forest=SpawningConfig.minForestMultiplier+(SpawningConfig.maxForestMultiplier-SpawningConfig.minForestMultiplier)*density;
        double value=Math.max(base,Math.max(cave,forest));
        if(SpawningConfig.structuresEnabled&&c.isDangerousStructure())value*=SpawningConfig.dangerousStructureMultiplier;
        return value*MobRuleRegistry.multiplier(c);
    }
    public static boolean shouldAllow(double multiplier,RandomSource random){
        if(multiplier<=0)return false;
        double max=Math.max(SpawningConfig.maxCaveMultiplier,Math.max(SpawningConfig.maxForestMultiplier,SpawningConfig.dangerousStructureMultiplier));
        double reference=Math.max(1.0,max);
        return random.nextDouble()<Math.min(1.0,multiplier/reference);
    }
}

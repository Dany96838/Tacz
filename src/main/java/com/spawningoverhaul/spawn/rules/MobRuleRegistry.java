package com.spawningoverhaul.spawn.rules;

import com.spawningoverhaul.config.SpawningConfig;
import com.spawningoverhaul.spawn.SpawnContext;
import net.minecraft.world.entity.EntityType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MobRuleRegistry {
    private static final Map<EntityType<?>,MobSpawnRule> RULES=new ConcurrentHashMap<>();
    private MobRuleRegistry(){}
    public static void register(EntityType<?> type,MobSpawnRule rule){RULES.put(type,rule);}
    public static double multiplier(SpawnContext c){
        MobSpawnRule rule=RULES.get(c.entityType());
        double value=rule==null?1.0:rule.multiplier(c);
        String id=net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(c.entityType()).toString();
        return value*SpawningConfig.mobSpecificMultipliers.getOrDefault(id,1.0);
    }
    public static void registerDefaults(){
        register(EntityType.SPIDER,c->SpawningConfig.spiderOnlyInCaves&&!c.isCave()?0.0:1.0);
        register(EntityType.CAVE_SPIDER,c->SpawningConfig.spiderOnlyInCaves&&!c.isCave()?0.0:1.0);
        register(EntityType.CREEPER,c->SpawningConfig.disableCreeperNearStructure&&c.isDangerousStructure()?0.0:1.0);
    }
}

package com.spawningoverhaul.spawn.rules;
import com.spawningoverhaul.spawn.SpawnContext;
@FunctionalInterface public interface MobSpawnRule { double multiplier(SpawnContext context); }

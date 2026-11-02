package com.spawningoverhaul;

import com.spawningoverhaul.command.CommandRegistrationHandler;
import com.spawningoverhaul.config.SpawningConfig;
import com.spawningoverhaul.event.SpawnEventHandler;
import com.spawningoverhaul.spawn.rules.MobRuleRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SpawningOverhaul.MOD_ID)
public final class SpawningOverhaul {
    public static final String MOD_ID="spawningoverhaul";
    public static final Logger LOGGER=LoggerFactory.getLogger(MOD_ID);
    public SpawningOverhaul(){
        SpawningConfig.load(FMLPaths.CONFIGDIR.get().resolve("spawningoverhaul.properties"));
        MobRuleRegistry.registerDefaults();
        MinecraftForge.EVENT_BUS.register(new SpawnEventHandler());
        MinecraftForge.EVENT_BUS.register(new CommandRegistrationHandler());
        LOGGER.info("Spawning Overhaul initialized for Minecraft 1.20.1 / Forge 47.3.0");
    }
}

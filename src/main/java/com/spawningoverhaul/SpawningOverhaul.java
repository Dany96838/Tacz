package com.spawningoverhaul;

import com.spawningoverhaul.config.SpawningConfig;
import com.spawningoverhaul.event.SpawnEventHandler;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SpawningOverhaul.MOD_ID)
public final class SpawningOverhaul {
    public static final String MOD_ID = "spawningoverhaul";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public SpawningOverhaul() {
        SpawningConfig.load(FMLPaths.CONFIGDIR.get().resolve("spawningoverhaul.properties"));
        NeoForge.EVENT_BUS.register(new SpawnEventHandler());
        LOGGER.info("Spawning Overhaul 1.20.1 NeoForge port initialized");
    }
}

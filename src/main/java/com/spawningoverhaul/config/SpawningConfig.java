package com.spawningoverhaul.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class SpawningConfig {
    private static final Properties VALUES = new Properties();
    public static boolean enabled = true;
    public static double baseMultiplier = 0.2;
    public static double minCaveMultiplier = 1.0;
    public static double maxCaveMultiplier = 5.0;
    public static double minForestMultiplier = 1.0;
    public static double maxForestMultiplier = 3.0;
    public static double dangerousStructureMultiplier = 3.0;
    public static boolean structuresEnabled = true;
    public static boolean spiderOnlyInCaves = true;
    public static boolean debug = false;

    private SpawningConfig() {}

    public static void load(Path path) {
        try {
            if (Files.exists(path)) try (InputStream in = Files.newInputStream(path)) { VALUES.load(in); }
            enabled = bool("enabled", true);
            baseMultiplier = number("baseMultiplier", 0.2);
            minCaveMultiplier = number("minCaveMultiplier", 1.0);
            maxCaveMultiplier = number("maxCaveMultiplier", 5.0);
            minForestMultiplier = number("minForestMultiplier", 1.0);
            maxForestMultiplier = number("maxForestMultiplier", 3.0);
            dangerousStructureMultiplier = number("dangerousStructureMultiplier", 3.0);
            structuresEnabled = bool("structuresEnabled", true);
            spiderOnlyInCaves = bool("spiderOnlyInCaves", true);
            debug = bool("debug", false);
            save(path);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load spawningoverhaul.properties", e);
        }
    }

    private static boolean bool(String key, boolean fallback) {
        return Boolean.parseBoolean(VALUES.getProperty(key, Boolean.toString(fallback)));
    }
    private static double number(String key, double fallback) {
        try { return Double.parseDouble(VALUES.getProperty(key, Double.toString(fallback))); }
        catch (NumberFormatException ignored) { return fallback; }
    }
    private static void save(Path path) throws IOException {
        VALUES.setProperty("enabled", Boolean.toString(enabled));
        VALUES.setProperty("baseMultiplier", Double.toString(baseMultiplier));
        VALUES.setProperty("minCaveMultiplier", Double.toString(minCaveMultiplier));
        VALUES.setProperty("maxCaveMultiplier", Double.toString(maxCaveMultiplier));
        VALUES.setProperty("minForestMultiplier", Double.toString(minForestMultiplier));
        VALUES.setProperty("maxForestMultiplier", Double.toString(maxForestMultiplier));
        VALUES.setProperty("dangerousStructureMultiplier", Double.toString(dangerousStructureMultiplier));
        VALUES.setProperty("structuresEnabled", Boolean.toString(structuresEnabled));
        VALUES.setProperty("spiderOnlyInCaves", Boolean.toString(spiderOnlyInCaves));
        VALUES.setProperty("debug", Boolean.toString(debug));
        Files.createDirectories(path.getParent());
        try (OutputStream out = Files.newOutputStream(path)) { VALUES.store(out, "Spawning Overhaul natural spawn settings"); }
    }
}

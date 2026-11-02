package com.spawningoverhaul.config;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class SpawningConfig {
    private static final Properties P=new Properties();
    public static boolean enabled=true, structuresEnabled=true, spiderOnlyInCaves=true, disableCreeperNearStructure=false, debug=false;
    public static double baseMultiplier=0.2,minCaveMultiplier=1,maxCaveMultiplier=5,minForestMultiplier=1,maxForestMultiplier=3,dangerousStructureMultiplier=3;
    public static int denseForestLogThreshold=50,denseForestScanRadius=10;
    public static final Set<String> enabledDimensions=ConcurrentHashMap.newKeySet();
    public static final Set<String> additionalDangerousStructures=ConcurrentHashMap.newKeySet();
    public static final Set<String> additionalSafeStructures=ConcurrentHashMap.newKeySet();
    public static final Map<String,Double> mobSpecificMultipliers=new ConcurrentHashMap<>();
    private SpawningConfig(){}
    public static boolean isDimensionEnabled(String id){return enabledDimensions.contains(id);}
    public static void load(Path path){
        try{
            if(Files.exists(path))try(InputStream in=Files.newInputStream(path)){P.load(in);}
            enabled=b("enabled",true); structuresEnabled=b("enableStructureModifications",true);
            spiderOnlyInCaves=b("spiderOnlyInCave",true); disableCreeperNearStructure=b("disableCreeperNearStructure",false); debug=b("debugmode",false);
            baseMultiplier=d("baseMultiplier",0.2); minCaveMultiplier=d("minCaveMultiplier",1); maxCaveMultiplier=d("maxCaveMultiplier",5);
            minForestMultiplier=d("minForestMultiplier",1); maxForestMultiplier=d("maxForestMultiplier",3); dangerousStructureMultiplier=d("dangerousStructureMultiplier",3);
            denseForestLogThreshold=i("denseForestLogThreshold",50); denseForestScanRadius=i("denseForestScanRadius",10);
            fill(enabledDimensions,"enabledDimensions","minecraft:overworld");
            fill(additionalDangerousStructures,"additionalDangerousStructures","");
            fill(additionalSafeStructures,"additionalSafeStructures","");
            mobSpecificMultipliers.clear();
            String raw=P.getProperty("mobSpecificMultipliers","");
            for(String item:raw.split(",")){String[] pair=item.trim().split("=",2);if(pair.length==2)try{mobSpecificMultipliers.put(pair[0].trim(),Double.parseDouble(pair[1].trim()));}catch(NumberFormatException ignored){}}
            save(path);
        }catch(IOException e){throw new IllegalStateException("Failed to load Spawning Overhaul config",e);}
    }
    private static boolean b(String k,boolean v){return Boolean.parseBoolean(P.getProperty(k,Boolean.toString(v)));}
    private static double d(String k,double v){try{return Double.parseDouble(P.getProperty(k,Double.toString(v)));}catch(Exception e){return v;}}
    private static int i(String k,int v){try{return Integer.parseInt(P.getProperty(k,Integer.toString(v)));}catch(Exception e){return v;}}
    private static void fill(Set<String> out,String key,String defaults){out.clear();String raw=P.getProperty(key,defaults);for(String s:raw.split(","))if(!s.isBlank())out.add(s.trim());}
    public static void save(Path path)throws IOException{
        P.setProperty("enabled",Boolean.toString(enabled));P.setProperty("baseMultiplier",Double.toString(baseMultiplier));
        P.setProperty("minCaveMultiplier",Double.toString(minCaveMultiplier));P.setProperty("maxCaveMultiplier",Double.toString(maxCaveMultiplier));
        P.setProperty("minForestMultiplier",Double.toString(minForestMultiplier));P.setProperty("maxForestMultiplier",Double.toString(maxForestMultiplier));
        P.setProperty("dangerousStructureMultiplier",Double.toString(dangerousStructureMultiplier));P.setProperty("enableStructureModifications",Boolean.toString(structuresEnabled));
        P.setProperty("spiderOnlyInCave",Boolean.toString(spiderOnlyInCaves));P.setProperty("disableCreeperNearStructure",Boolean.toString(disableCreeperNearStructure));
        P.setProperty("debugmode",Boolean.toString(debug));P.setProperty("denseForestLogThreshold",Integer.toString(denseForestLogThreshold));P.setProperty("denseForestScanRadius",Integer.toString(denseForestScanRadius));
        P.setProperty("enabledDimensions",String.join(",",enabledDimensions));P.setProperty("additionalDangerousStructures",String.join(",",additionalDangerousStructures));
        P.setProperty("additionalSafeStructures",String.join(",",additionalSafeStructures));
        List<String> pairs=new ArrayList<>();mobSpecificMultipliers.forEach((k,v)->pairs.add(k+"="+v));P.setProperty("mobSpecificMultipliers",String.join(",",pairs));
        Files.createDirectories(path.getParent());try(OutputStream out=Files.newOutputStream(path)){P.store(out,"Spawning Overhaul — edit values, then restart the world/server");}
    }
}

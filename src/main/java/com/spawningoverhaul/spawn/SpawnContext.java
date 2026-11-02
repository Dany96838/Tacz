package com.spawningoverhaul.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;

public final class SpawnContext {
    private final Level level;
    private final BlockPos pos;
    private final EntityType<?> entityType;
    private final MobSpawnType spawnType;
    private Boolean cave, outside, structure;
    private Double forest;

    public SpawnContext(Level level, BlockPos pos, EntityType<?> entityType, MobSpawnType spawnType) {
        this.level=level; this.pos=pos.immutable(); this.entityType=entityType; this.spawnType=spawnType;
    }
    public Level level(){return level;}
    public BlockPos pos(){return pos;}
    public EntityType<?> entityType(){return entityType;}
    public MobSpawnType spawnType(){return spawnType;}
    public boolean isCave(){if(cave==null)cave=LocationDetector.isCave(level,pos);return cave;}
    public boolean isOutside(){if(outside==null)outside=level.canSeeSky(pos.above());return outside;}
    public boolean isDangerousStructure(){if(structure==null)structure=StructureCache.isDangerous(level,pos);return structure;}
    public double forestDensity(){if(forest==null)forest=LocationDetector.forestDensity(level,pos);return forest;}
    public double caveDepth(){return LocationDetector.caveDepth(pos);}
    public void detectAll(){isCave();isOutside();isDangerousStructure();forestDensity();}
}

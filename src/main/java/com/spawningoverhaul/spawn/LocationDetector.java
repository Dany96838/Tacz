package com.spawningoverhaul.spawn;

import com.spawningoverhaul.config.SpawningConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;

public final class LocationDetector {
    private LocationDetector(){}
    public static boolean isCave(Level level, BlockPos pos){return pos.getY()<60 && !level.canSeeSky(pos);}
    public static double caveDepth(BlockPos pos){return pos.getY()>=60?1.0:Math.min(5.0,(60.0-pos.getY())/31.0+1.0);}
    public static double forestDensity(Level level, BlockPos pos){
        int radius=SpawningConfig.denseForestScanRadius, threshold=SpawningConfig.denseForestLogThreshold, count=0;
        if(radius<1||threshold<1)return 0;
        BlockPos.MutableBlockPos cursor=new BlockPos.MutableBlockPos();
        for(int dx=-radius;dx<=radius;dx++) for(int dz=-radius;dz<=radius;dz++){
            if(dx*dx+dz*dz>radius*radius)continue;
            for(int dy=-3;dy<=3;dy++){
                cursor.set(pos.getX()+dx,pos.getY()+dy,pos.getZ()+dz);
                if(level.getBlockState(cursor).is(BlockTags.LOGS) && ++count>=threshold)return 1.0;
            }
        }
        return Math.min(1.0,(double)count/threshold);
    }
}

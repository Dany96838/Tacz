package com.spawningoverhaul.command;

import com.mojang.brigadier.CommandDispatcher;
import com.spawningoverhaul.config.SpawningConfig;
import com.spawningoverhaul.spawn.SpawnContext;
import com.spawningoverhaul.spawn.SpawnMultiplierCalculator;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;

public final class SpawnDebugCommand {
    private SpawnDebugCommand(){}
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("spawndebug").requires(s->s.hasPermission(2)).executes(ctx->{
            CommandSourceStack source=ctx.getSource();
            if(!(source.getLevel() instanceof ServerLevel level)){source.sendFailure(Component.literal("Use this command in a server world."));return 0;}
            BlockPos pos=BlockPos.containing(source.getPosition());
            SpawnContext c=new SpawnContext(level,pos,EntityType.ZOMBIE,MobSpawnType.NATURAL);c.detectAll();
            double multiplier=SpawnMultiplierCalculator.calculate(c);
            source.sendSuccess(()->Component.literal("§6[Spawning Overhaul] §ePosição: §f"+pos+" | Dimensão: "+level.dimension().location()),false);
            source.sendSuccess(()->Component.literal("§eAtivo: §f"+SpawningConfig.enabled+" | Caverna: "+c.isCave()+" | Profundidade: "+String.format("%.2f",c.caveDepth())),false);
            source.sendSuccess(()->Component.literal("§eFloresta: §f"+String.format("%.1f%%",c.forestDensity()*100)+" | Estrutura perigosa: "+c.isDangerousStructure()),false);
            source.sendSuccess(()->Component.literal("§eMultiplicador final estimado: §a"+String.format("%.2fx",multiplier)),false);
            return 1;
        }));
    }
}

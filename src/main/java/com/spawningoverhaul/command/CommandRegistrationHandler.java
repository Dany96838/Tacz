package com.spawningoverhaul.command;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
public final class CommandRegistrationHandler {
    @SubscribeEvent public void onRegister(RegisterCommandsEvent event){SpawnDebugCommand.register(event.getDispatcher());}
}

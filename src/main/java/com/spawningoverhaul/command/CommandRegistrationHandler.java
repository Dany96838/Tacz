package com.spawningoverhaul.command;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
public final class CommandRegistrationHandler {
    @SubscribeEvent public void onRegister(RegisterCommandsEvent event){SpawnDebugCommand.register(event.getDispatcher());}
}

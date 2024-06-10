package net.idolfan.testmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.idolfan.testmod.TestMod;
import net.idolfan.testmod.util.StateSaverAndLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class GetWorldHandler implements ServerWorldEvents.Load {
    @Override
    public void onWorldLoad(MinecraftServer server, ServerWorld world) {
        StateSaverAndLoader.getServerState(world.getServer());
        TestMod.server_world.add(world);
    }
}

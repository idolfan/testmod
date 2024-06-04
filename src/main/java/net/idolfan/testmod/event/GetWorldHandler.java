package net.idolfan.testmod.event;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.idolfan.testmod.TestMod;
import net.idolfan.testmod.util.StateSaverAndLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.WorldBorderCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.ArrayList;

public class GetWorldHandler implements ServerWorldEvents.Load {
    @Override
    public void onWorldLoad(MinecraftServer server, ServerWorld world) {
        StateSaverAndLoader.getServerState(world.getServer());
        TestMod.server_world.add(world);
        System.out.println(world.getDimension() + " ? == " + DimensionTypes.OVERWORLD);
        for(World w : TestMod.server_world){
            System.out.println("Contains:" + world);
        }
    }
}

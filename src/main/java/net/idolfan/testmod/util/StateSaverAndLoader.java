package net.idolfan.testmod.util;

import net.idolfan.testmod.TestMod;
import net.idolfan.testmod.event.ExtendWorldBorderHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;

public class StateSaverAndLoader extends PersistentState {

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        for (String s : ExtendWorldBorderHandler.sacrificedAmounts.keySet()) {
            nbt.putInt(s, ExtendWorldBorderHandler.sacrificedAmounts.get(s));
        }
        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        ExtendWorldBorderHandler.sacrificedAmounts = new HashMap<>();
        for (String s : tag.getKeys()) {
            ExtendWorldBorderHandler.sacrificedAmounts.put(s, tag.getInt(s));
        }
        return state;
    }

    private static Type<StateSaverAndLoader> type = new Type<>(
            StateSaverAndLoader::new,
            StateSaverAndLoader::createFromNbt,
            null
    );

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(type, TestMod.MOD_ID);
        state.markDirty();

        return state;
    }
}

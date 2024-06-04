package net.idolfan.testmod.event;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class DisableSleepHandler implements EntitySleepEvents.AllowSleeping {
    @Nullable
    @Override
    public PlayerEntity.SleepFailureReason allowSleep(PlayerEntity player, BlockPos sleepingPos) {
        player.sendMessage(Text.literal("Du kannst nicht schlafen"));
        return PlayerEntity.SleepFailureReason.OTHER_PROBLEM;
    }
}

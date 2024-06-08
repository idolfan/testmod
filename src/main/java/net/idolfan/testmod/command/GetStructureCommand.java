package net.idolfan.testmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class GetStructureCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        System.out.println("hat geklappt");

        dispatcher.register(CommandManager.literal("getstructure").then(
                CommandManager.argument("from", BlockPosArgumentType.blockPos())
                        .then(CommandManager.argument("to", BlockPosArgumentType.blockPos())
                                .executes(context -> execute(context, BlockPosArgumentType.getBlockPos(context, "from"),
                                        BlockPosArgumentType.getBlockPos(context, "to"))))));

    }

    private static int execute(CommandContext<ServerCommandSource> context, BlockPos fromPos, BlockPos toPos)
            throws CommandSyntaxException {
        PlayerEntity player = context.getSource().getPlayer();
        World world = context.getSource().getWorld();
        Map<Vec3i, String> structure = new HashMap<>();

        Box box = new Box(fromPos.getX(), fromPos.getY(), fromPos.getZ(), toPos.getX(), toPos.getY(), toPos.getZ());
        BlockPos.stream(box).forEach(pos -> {
            structure.put(pos, world.getBlockState(pos).getBlock().getName().getString());
        });
        for (Vec3i pos : structure.keySet()) {
            System.out.println(pos.getX() + " " + structure.get(pos));
        }
        return 1;
    }
}

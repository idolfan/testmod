package net.idolfan.testmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.idolfan.testmod.structures.Structure;
import net.idolfan.testmod.structures.StructureHandler;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class GetStructureCommand {



    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        System.out.println("hat geklappt");

        dispatcher.register(CommandManager.literal("getstructure").then(
                CommandManager.argument("from", BlockPosArgumentType.blockPos())
                        .then(CommandManager.argument("to", BlockPosArgumentType.blockPos()).then(CommandManager.argument("name", StringArgumentType.string())
                                .executes(context -> execute(context, BlockPosArgumentType.getBlockPos(context, "from"),
                                        BlockPosArgumentType.getBlockPos(context, "to"), StringArgumentType.getString(context, "name")))))));

    }

    private static int execute(CommandContext<ServerCommandSource> context, BlockPos fromPos, BlockPos toPos, String name)
            throws CommandSyntaxException {
        PlayerEntity player = context.getSource().getPlayer();
        World world = context.getSource().getWorld();
        Structure structure = StructureHandler.getStructure(world, new int[]{fromPos.getX(), fromPos.getY(), fromPos.getZ()}, new int[]{
                toPos.getX(), toPos.getY(), toPos.getZ()}, new String[]{"Air"});
        StructureHandler.structures.put(name, structure);

        return 1;
    }
}

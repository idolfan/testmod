package net.idolfan.testmod.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.idolfan.testmod.event.ExtendWorldBorderHandler;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.HashMap;

public class GetSacrificeCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("sacrifice").then(
                CommandManager.argument("name", StringArgumentType.string()).executes(
                        context -> GetSacrificeCommand.execute(context, StringArgumentType.getString(context, "name")))));
    }

    private static int execute(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
        name = name.toLowerCase();
        PlayerEntity player = context.getSource().getPlayer();
        String itemName;
        Integer count = null;
        if (name.equals("keys") || name.equals("values")) {
            boolean values = name.equals("values");
            String message = "-------\n";
            HashMap<String, Integer> map = new HashMap<>();
            map.putAll(ExtendWorldBorderHandler.sacrificedAmounts);
            for (String s : map.keySet()) {
                int value = map.get(s);
                message += s;
                if (values) message += " " + value;
                message += "\n";
            }
            player.sendMessage(Text.literal(message));
            return 1;
        }

        if (name.equals("hand")) {
            ItemStack itemStack = player.getStackInHand(player.getActiveHand());
            if (itemStack == null) return 1;
            itemName = itemStack.getItem().getName().getString().toLowerCase().replaceAll("\\s", "");
            count = ExtendWorldBorderHandler.sacrificedAmounts.get(itemName);
        } else {
            HashMap<String, Integer> map = new HashMap<>();
            map.putAll(ExtendWorldBorderHandler.sacrificedAmounts);
            itemName = name;
            count = map.get(name);
        }
        if (count == null) {
            player.sendMessage(Text.literal("Never sacrificed '" + itemName + "'"));
            return 1;
        }

        double increase = ExtendWorldBorderHandler.calculateIncrease(count+1);
        player.sendMessage(Text.literal(count + " times sacrificed: '" + itemName + "' Next Increase: " + String.format("%.6f", increase)));
        return 1;
    }
}

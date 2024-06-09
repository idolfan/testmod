package net.idolfan.testmod.structures;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class sacrificeAltar implements UseBlockCallback {

    public static HashMap<int[], String> altarStructure;
    public static ArrayList<double[]> existingAltars = new ArrayList<>();

    public static String requiredItemName = Items.STICK.getName().getString();

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if(altarStructure == null)
            return ActionResult.PASS;

        ItemStack itemStack = player.getStackInHand(player.getActiveHand());
        if (itemStack == null)
            return ActionResult.PASS;

        String heldItemName = itemStack.getItem().getName().getString();
        if (!heldItemName.equals(requiredItemName))
            return ActionResult.PASS;



        return ActionResult.PASS;
    }
}
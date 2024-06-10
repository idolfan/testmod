package net.idolfan.testmod.structures;

import com.jcraft.jorbis.Block;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sacrificeAltar implements UseBlockCallback {

    //public static HashMap<int[], String> altarStructure;
    public static Structure altarStructure;
    public static ArrayList<Altar> existingAltars = new ArrayList<>();

    public static String requiredItemName = Items.STICK.getName().getString();

    private static int[][] rotationArrayPositions = new int[][]{
            {0, 1, 2},
            {2, 1, 0},
            {0, 1, 2},
            {2, 1, 0}
    };

    private static int[][] rotationFactors = new int[][]{
            {1, 1, 1},
            {1, 1, -1},
            {-1, 1, -1},
            {-1, 1, 1}
    };

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (world.isClient)
            return ActionResult.PASS;
        if (hand == Hand.OFF_HAND)
            return ActionResult.PASS;
        if (altarStructure == null)
            return ActionResult.PASS;

        ItemStack itemStack = player.getStackInHand(player.getActiveHand());
        if (itemStack == null)
            return ActionResult.PASS;

        String heldItemName = itemStack.getItem().getName().getString();
        if (!heldItemName.equals(requiredItemName))
            return ActionResult.PASS;


        BlockPos blockPosition = hitResult.getBlockPos();
        String hitBlockName = world.getBlockState(blockPosition).getBlock().getName().getString();

        List<int[]> possiblePositions = altarStructure.blocks.keySet().stream().filter((position) -> {
            return altarStructure.blocks.get(position).equals(hitBlockName);
        }).toList();

        if (possiblePositions.isEmpty()) {
            player.sendMessage(Text.literal("Block does not belong to altar"));
            return ActionResult.PASS;
        }

        //Altar foundAltar = null;
        boolean match = false;

        int[] absolutPositionOfHitBlock = new int[]{blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()};

        HashMap<int[], String[]>[] changesNeededForPossiblePositions = new HashMap[possiblePositions.size() * 4];

        System.out.println("Starting to search for match...");

        for (int i = 0; i < possiblePositions.size(); i++) {

            for (int d = 0; d < 4; d++) {

                System.out.println("Checking position " + i);
                changesNeededForPossiblePositions[i * 4 + d] = new HashMap<>();

                HashMap<int[], String[]> changesNeeded = changesNeededForPossiblePositions[i * 4 + d];

                int[] relativePosition = possiblePositions.get(i);
                int[] rotationIndexes = rotationArrayPositions[d];
                int[] rotationFactor = rotationFactors[d];

                int[] absolutStartPosition = new int[]{
                        absolutPositionOfHitBlock[0] - relativePosition[rotationIndexes[0]]  * rotationFactor[0],
                        absolutPositionOfHitBlock[1] - relativePosition[rotationIndexes[1]] * rotationFactor[1],
                        absolutPositionOfHitBlock[2] - relativePosition[rotationIndexes[2]]  * rotationFactor[2]
                };

                for (int[] position : altarStructure.blocks.keySet()) {
                    int[] absolutPosition = new int[]{
                            position[rotationIndexes[0]] * rotationFactor[0] + absolutStartPosition[0],
                            position[rotationIndexes[1]] * rotationFactor[1] + absolutStartPosition[1],
                            position[rotationIndexes[2]] * rotationFactor[2] + absolutStartPosition[2]
                    };
                    BlockPos foundBlockPosition = new BlockPos(absolutPosition[0], absolutPosition[1], absolutPosition[2]);
                    String foundBlock = world.getBlockState(foundBlockPosition).getBlock().getName().getString();
                    String requiredBlock = altarStructure.blocks.get(position);
                    if (requiredBlock == null)
                        continue;

                    boolean blocksAreDifferent = !foundBlock.equals(requiredBlock);
                    if (blocksAreDifferent)
                        changesNeeded.put(absolutPosition, new String[]{requiredBlock, foundBlock});
                }

                boolean matchesStructure = changesNeeded.isEmpty();
                if (matchesStructure) {
                    match = true;
                    System.out.println("Matches Structure with rotation " + d);
                    i = possiblePositions.size();
                    d = 4;
                    break;
                }
            }
        }

        if (match) {
            player.sendMessage(Text.literal("Altar recognized and activated."));
            /// Add altar
            return ActionResult.PASS;
        }

        HashMap<int[], String[]> leastChangesNeeded = null;
        for (HashMap<int[], String[]> changes : changesNeededForPossiblePositions) {
            if (leastChangesNeeded == null)
                leastChangesNeeded = changes;
            else if (leastChangesNeeded.size() > changes.size())
                leastChangesNeeded = changes;
        }

        String text = "Altar not complete, changes required:";
        for (int[] position : leastChangesNeeded.keySet()) {
            String requiredBlock = leastChangesNeeded.get(position)[0];
            String foundBlock = leastChangesNeeded.get(position)[1];

            text += "\n [" + position[0] + "," + position[1] + "," + position[2] + "]: " + foundBlock + " -> " + requiredBlock;
        }

        player.sendMessage(Text.literal(text));


        return ActionResult.PASS;
    }
}
package net.idolfan.testmod.structures;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StructureHandler {

    public static HashMap<String, Structure> structures = new HashMap<>();

    /**
     * @return A <code>Map(position, blockname)</code> relative to <code>from</code> with positions in direction <code>to</code>
     */
    public static Structure getStructure(World world, int[] from, int[] to, String[] ignoreBlocks) {
        HashMap<int[], String> structureBlocks = new HashMap<>();
        ArrayList<String> ignoreBlockList = new ArrayList<>(Arrays.asList(ignoreBlocks));

        int[] startPosition = new int[]{
                Math.min(to[0], from[0]),
                Math.min(to[1], from[1]),
                Math.min(to[2], from[2])
        };
        int[] relativePos = new int[]{
                Math.abs(to[0] - from[0]),
                Math.abs(to[1] - from[1]),
                Math.abs(to[2] - from[2])
        };

        System.out.println("Blocks of structure: ");

        for (int x = 0; x <= relativePos[0]; x++) {
            for (int y = 0; y <= relativePos[1]; y++) {
                for (int z = 0; z <= relativePos[2]; z++) {
                    String blockname = world.getBlockState(new BlockPos(x + startPosition[0], y + startPosition[1], z + startPosition[2])).getBlock().getName().getString();

                    if (!ignoreBlockList.contains(blockname)) {

                        structureBlocks.put(new int[]{x, y, z}, blockname);
                        System.out.println("[" + x + "," + y + "," + z + "]: " + blockname);
                    }

                }
            }
        }
        System.out.println("-------");

        // TODO: Create seperate Command for this
        Structure structure = new Structure(structureBlocks);
        activateAltarHandler.altarStructure = structure;

        return structure;
    }

    public static boolean findStructure(HashMap<int[], String> structure, int[] pos, String name){
        return false;
    };

}

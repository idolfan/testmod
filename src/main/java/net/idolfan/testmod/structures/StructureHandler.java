package net.idolfan.testmod.structures;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StructureHandler {

    public static HashMap<String, HashMap<int[], String>> structures = new HashMap<>();

    /**
     * @return A <code>Map(position, blockname)</code> relative to <code>from</code> with positions in direction <code>to</code>
     */
    public static HashMap<int[], String> getStructure(World world, int[] from, int[] to, String[] ignoreBlocks) {
        HashMap<int[], String> structure = new HashMap<>();
        ArrayList<String> ignoreBlockList = new ArrayList<>(Arrays.asList(ignoreBlocks));

        int[] relativePos = new int[]{
                to[0] - from[0],
                to[1] - from[1],
                to[2] - from[2],
        };

        System.out.println("Blocks of structure: ");

        for (int i = 0; i <= Math.abs(relativePos[0]); i++) {
            int x = (int) (Math.signum(relativePos[0])) * i;
            for (int j = 0; j <= Math.abs(relativePos[1]); j++) {
                int y = (int) (Math.signum(relativePos[1])) * j;
                for (int k = 0; k <= Math.abs(relativePos[2]); k++) {

                    int z = (int) (Math.signum(relativePos[2])) * k;
                    String blockname = world.getBlockState(new BlockPos(x + from[0], y + from[1], z + from[2])).getBlock().getName().getString();

                    if (!ignoreBlockList.contains(blockname)) {
                        structure.put(new int[]{x, y, z}, blockname);
                        System.out.println("[" + x + "," + y + "," + z + "]: " + blockname);
                    }

                }
            }
        }
        System.out.println("-------");
        return structure;
    }

    public static boolean findStructure(HashMap<int[], String> structure, int[] pos, String name){
        return false;
    };

}

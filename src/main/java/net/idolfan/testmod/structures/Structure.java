package net.idolfan.testmod.structures;

import java.util.HashMap;

public class Structure {

    public HashMap<int[], String> blocks;
    public boolean allowYRotataion = false;
    public int[] sizes = new int[]{0,0,0};

    public Structure(HashMap<int[], String> blocks, boolean allowYRotation){
        this.blocks = blocks;
        this.allowYRotataion = allowYRotation;
        for(int[] block : this.blocks.keySet()){
            sizes[0] = Math.max(sizes[0], Math.abs(block[0]));
            sizes[1] = Math.max(sizes[1], Math.abs(block[1]));
            sizes[2] = Math.max(sizes[2], Math.abs(block[2]));
        }
    }

    public Structure(HashMap<int[], String> blocks) {
        this(blocks, false);
    }

}

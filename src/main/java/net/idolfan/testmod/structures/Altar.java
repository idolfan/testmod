package net.idolfan.testmod.structures;

public class Altar {

    public double[] middle;
    public double sacrificeRadius;

    public Altar(double[] middle, double sacrificeRadius){
        this.middle = middle;
        this.sacrificeRadius = sacrificeRadius;
        System.out.println("Altar created: [" + middle[0] + "," + middle[1] + "," + middle[2] + "]: " + sacrificeRadius);
    }

    public boolean compare(Altar otherAltar) {
        return middle[0] == otherAltar.middle[0] && middle[1] == otherAltar.middle[1] && middle[2] == otherAltar.middle[2];
    }



}

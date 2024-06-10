package net.idolfan.testmod.util;

public class toStrings {

    public static String intArray(int[] array){
        String result = "[";
        for(int i = 0; i < array.length; i++){
            result += array[i];
            if(i != array.length - 1)
                result += ",";
            else
                result += "]";
        }
        return result;
    }
}

package Medium.ArrayOfMultiplies;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(arrayOfMultiples(12, 10)));
    }

    public static int[] arrayOfMultiples(int num, int length) {
        int[] newArray = new int[length];
        for (int i = 0; i < length; i++) {
            newArray[i] = num * (i + 1);
        }
        return newArray;
    }
}

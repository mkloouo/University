package TKITP;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

class LR4 {

    private Integer[] array;
    private int n;
    private int k;

    LR4(BufferedReader input) throws IOException {
        System.out.print("Enter array size: ");
        try {
            n = Integer.parseInt(input.readLine());
            array = new Integer[n];
            for (int i = 0; i < n; i++) {
                System.out.printf("Enter array[%d]: ", i);
                array[i] = Integer.parseInt(input.readLine());
            }
            System.out.print("Enter k: ");
            k = Integer.parseInt(input.readLine());
            workOnArray();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number: " + ex.getLocalizedMessage().split(":\\s+")[1]);
        }
    }

    private void printArray(String msg) {
        System.out.println(msg);
        for (int i = 0; i < n; i++) {
            System.out.printf("array[%d]: %d\n", i, array[i]);
        }
    }

    private void workOnArray() {
        printArray("Initial array:");
        for (int i = 0; i < n; i++)
            array[i] += k;
        printArray("After adding k:");
        Arrays.sort(array, (Integer a, Integer b) -> (Math.abs(b) - Math.abs(a)));
        printArray("After sort:");
        int evenIdSum = 0;
        for (int i = 0; i < n; i++)
            evenIdSum += (i % 2 == 0) ? i : 0;
        int negativeNumCount = 0;
        int lastNeg = 0;
        for (int i = 0; i < n; i++) {
            lastNeg = (array[i] < 0) ? array[i] : lastNeg;
            negativeNumCount += (array[i] < 0) ? 1 : 0;
        }
        System.out.printf("Even id summ: %d\nNegative numbers count: %d\nLast negative number: %s\n",
                evenIdSum, negativeNumCount, (lastNeg == 0) ? ("no negative numbers") : (String.valueOf(lastNeg)));
    }

}

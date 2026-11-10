package com.example.pr2;

import java.util.Arrays;
import java.util.Random;

public class Task1Arrays {
    public static void main(String[] args) {
        System.out.println("=== ZADANIE 1: Rabota s massivami ===");
        Random generator = new Random();

        int[] data = new int[15];
        for (int idx = 0; idx < data.length; idx++) {
            data[idx] = generator.nextInt(101) - 50;
        }

        System.out.println("Sgenerirovannyj massiv: " + Arrays.toString(data));

        if (data.length == 0) {
            System.out.println("Massiv ne soderzhit elementov.");
            return;
        }

        int minVal = data[0];
        int maxVal = data[0];
        long totalSum = 0;
        int countPos = 0;
        int countNeg = 0;
        int countZero = 0;

        for (int element : data) {
            if (element < minVal) minVal = element;
            if (element > maxVal) maxVal = element;
            totalSum += element;

            if (element > 0) countPos++;
            else if (element < 0) countNeg++;
            else countZero++;
        }

        double mean = (double) totalSum / data.length;
        System.out.printf("Minimun: %d | Maksimum: %d | Summa: %d | Srednee: %.2f%n",
                minVal, maxVal, totalSum, mean);
        System.out.printf("Polozhitel'nyh: %d | Otricatel'nyh: %d | Nulevyh: %d%n",
                countPos, countNeg, countZero);

        int[] onlyPositives = new int[countPos];
        int posCursor = 0;
        for (int element : data) {
            if (element > 0) {
                onlyPositives[posCursor++] = element;
            }
        }
        System.out.println("Otfil'trovannye polozhitel'nye elementy: " + Arrays.toString(onlyPositives));

        Arrays.sort(data);
        System.out.println("Massiv posle sortirovki: " + Arrays.toString(data));

        System.out.println("\nMatrica 4x5 i summy po strokam:");
        int[][] grid = new int[4][5];
        for (int r = 0; r < grid.length; r++) {
            int lineTotal = 0;
            System.out.print("Stroka " + (r + 1) + ": ");
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = generator.nextInt(10);
                lineTotal += grid[r][c];
                System.out.printf("%2d ", grid[r][c]);
            }
            System.out.println("| Summa = " + lineTotal);
        }
    }
}

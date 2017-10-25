package ru.otus.homework14;

import java.util.*;

public class Main {
    private static final int ARRAY_SIZE = 10_000_000;
    private static final int ARRAY_BOUND = 10_000_000;
    public static volatile int completeThreadCount;

    public static void main(String[] args) {
        int threadCount = 1;
        System.out.printf("Sorting array with %1$,d elements in %2$,d thread(s).\n", ARRAY_SIZE, threadCount);
        int[] array = getRandomArray(ARRAY_SIZE, ARRAY_BOUND);
        long start = System.currentTimeMillis();
        array = multiThreadSorting(array, threadCount);
        long end = System.currentTimeMillis();
        System.out.printf("Time spent: %,d ms.\n", end - start);

        threadCount = 4;
        System.out.printf("\nSorting array with %1$,d elements in %2$,d thread(s).\n", ARRAY_SIZE, threadCount);
        array = getRandomArray(ARRAY_SIZE, ARRAY_BOUND);
        start = System.currentTimeMillis();
        array = multiThreadSorting(array, threadCount);
        end = System.currentTimeMillis();
        System.out.printf("Time spent: %,d ms.\n", end - start);

        threadCount = 10;
        System.out.printf("\nSorting array with %1$,d elements in %2$,d thread(s).\n", ARRAY_SIZE, threadCount);
        array = getRandomArray(ARRAY_SIZE, ARRAY_BOUND);
        start = System.currentTimeMillis();
        array = multiThreadSorting(array, threadCount);
        end = System.currentTimeMillis();
        System.out.printf("Time spent: %,d ms.\n", end - start);
    }

    private static int[] multiThreadSorting(int[] array, int threadCount) {
        int[][] subArrays = splitArray(array, threadCount);
        completeThreadCount = 0;

        Thread[] sortThreads = new Thread[threadCount];
        for (int i = 0; i < sortThreads.length; i++) {
            int[] subArray = subArrays[i];
            sortThreads[i] = new Thread(() -> {
                Arrays.sort(subArray);
                completeThreadCount++;
            });
            sortThreads[i].start();
        }

        while (completeThreadCount != threadCount) {
        }

        return mergeArrays(subArrays);
    }

    private static int[] getRandomArray(int size, int bound) {
        Random random = new Random();
        int[] result = new int[size];
        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextInt(bound);
        }
        return result;
    }

    private static int[][] splitArray(int[] array, int partCount) {
        int[][] subArrays = new int[partCount][];
        int copyFromPosition = 0;
        int subArrayLength = (int) Math.ceil((double) array.length / partCount--);
        for (int i = 0; i < subArrays.length; i++) {
            subArrays[i] = Arrays.copyOfRange(array, copyFromPosition, Math.min(copyFromPosition + subArrayLength, array.length));
            copyFromPosition += subArrayLength;
            subArrayLength = (int) Math.ceil((double) (array.length - copyFromPosition) / partCount--);
        }
        return subArrays;
    }

    private static int[] mergeArrays(int[][] arrayList) {
        int[] result = new int[ARRAY_SIZE];
        int[] indexes = new int[arrayList.length];
        int subArrayNumber;

        for (int i = 0; i < result.length; i++) {
            subArrayNumber = 0;
            while (indexes[subArrayNumber] >= arrayList[subArrayNumber].length) {
                subArrayNumber++;
            }
            for (int j = subArrayNumber + 1; j < indexes.length; j++) {
                if (indexes[j] < arrayList[j].length && arrayList[j][indexes[j]] < arrayList[subArrayNumber][indexes[subArrayNumber]]) {
                    subArrayNumber = j;
                }
            }
            result[i] = arrayList[subArrayNumber][indexes[subArrayNumber]];
            indexes[subArrayNumber]++;
        }

        return result;
    }
}

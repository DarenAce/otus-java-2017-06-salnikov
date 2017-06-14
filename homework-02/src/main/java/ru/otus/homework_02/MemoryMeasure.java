package ru.otus.homework_02;

import java.util.function.Supplier;

public class MemoryMeasure {
    private static final Runtime runtime = Runtime.getRuntime();

    public static void getObjectSize(Supplier<Object> supplier) {
        final int objectsCount = 10_000;
        final int referenceSize;
        final int objectSize;
        long usedMemory_0, usedMemory_1, usedMemory_2;
        Object[] array;

        runGC();
        usedMemory_0 = getUsedMemory();
        array = new Object[objectsCount];
        runGC();
        usedMemory_1 = getUsedMemory();
        referenceSize = Math.round(((float) (usedMemory_1 - usedMemory_0)) / objectsCount);
        for (int i = 0; i < objectsCount; i++) {
            array[i] = supplier.get();
        }
        runGC();
        usedMemory_2 = getUsedMemory();
        objectSize = Math.round(((float) (usedMemory_2 - usedMemory_1)) / objectsCount);
        System.out.printf("%,d objects of %s was created.%n", objectsCount, array[0].getClass());
        System.out.printf("Empty reference size: %,d bytes.%n", referenceSize);
        System.out.printf("Total memory usage: %,d bytes.%n", usedMemory_2 - usedMemory_1);
        System.out.printf("Memory usage per one object: %,d bytes.%n", objectSize);
    }

    private static void runGC() {
        long usedMem1 = getUsedMemory(), usedMem2 = Long.MAX_VALUE;

        for (int i = 0; (usedMem1 < usedMem2) && i < 500; i++) {
            runtime.runFinalization();
            runtime.gc();

            usedMem2 = usedMem1;
            usedMem1 = getUsedMemory();
        }
    }

    private static long getUsedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }
}

package ru.otus.homework_04;

import java.io.IOException;

/*
 * JVM options for Copy + MarkSweepCompact GCs:
 * -Xms512m -Xmx512m -XX:UseSerialGC
 * JVM options for PS Scavenge + PS MarkSweep GCs:
 * -Xms512m -Xmx512m -XX:+UseParallelGC -XX:+UseParallelOldGC
 * JVM options for ParNew + ConcurrentMarkSweep GCs:
 * -Xms512m -Xmx512m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
 * JVM options for G1 GC:
 * -Xms512m -Xmx512m -XX:+UseG1GC
 */

public class Main {
    private static final String LOG_FILE_PATH = "log.txt";

    public static void main(String... args) throws InterruptedException {
        final int size = 20_000_000;
        System.out.println("The application is running...");

        GCMonitor gcMonitor = new GCMonitor(LOG_FILE_PATH);
        gcMonitor.setDaemon(true);
        gcMonitor.start();

        Object[] array = new Object[size];

        Thread.sleep(5 * 1000);
        fillArray(array);
    }

    private static void fillArray(Object[] array) throws InterruptedException {
        int n = array.length / 20;
        int size = 0;

        String message = "Starting the loop.";
        pushToLog(message, false);

        while (size < array.length) {
            for (int i = 0; i < n; i++) {
                array[size++] = new String(new char[0]);
            }
            Thread.sleep(5 * 1000);
            message = String.format("%,d empty String objects was added to array.", n);
            pushToLog(message, true);
            message = String.format("Objects in array total amount: %,d.", size);
            pushToLog(message, true);

            for (int i = 0; i < n / 2; i++) {
                array[size--] = null;
            }
            Thread.sleep(5 * 1000);
            message = String.format("%,d objects was removed from array.", n / 2);
            pushToLog(message, true);
            message = String.format("Objects in array total amount: %,d.", size);
            pushToLog(message, true);

            Thread.sleep(5 * 1000);
        }
    }

    private static void pushToLog(String message, boolean append) {
        try {
            SimpleLogger.write(LOG_FILE_PATH, message, append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

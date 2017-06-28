package ru.otus.homework_04;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class GCMonitor extends Thread {
    private String logFilePath;

    public GCMonitor(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public void run() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    long duration = info.getGcInfo().getDuration();
                    String gcAction = info.getGcAction();

                    String message = "GC Action: " + gcAction + ". "
                            + "GC ID: " + info.getGcInfo().getId() + ", "
                            + "GC Name: " + info.getGcName() + ", "
                            + "GC cause: " + info.getGcCause() + ", "
                            + "duration: " + String.format("%,d", duration) + " milliseconds.";

                    try {
                        SimpleLogger.write(logFilePath, message, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }
}

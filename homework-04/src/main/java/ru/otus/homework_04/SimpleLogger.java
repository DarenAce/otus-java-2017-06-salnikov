package ru.otus.homework_04;

import java.io.*;
import java.util.Date;

public final class SimpleLogger {
    private SimpleLogger() {
    }

    public static void write(String filePath, String text, boolean append) throws IOException {
        File file = new File(filePath);
        FileWriter out;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("Can't create file on " + filePath);
            }
        }
        try {
            out = new FileWriter(file.getAbsoluteFile(), append);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(filePath);
        }
        out.write(getFormattedDateTime() + ". " + text + "\n");
        out.close();
    }

    private static String getFormattedDateTime() {
        return String.format("%1$tY.%1$tm.%1$td %1$tT,%1$tL", new Date());
    }
}

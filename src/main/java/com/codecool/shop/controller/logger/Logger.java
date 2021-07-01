package com.codecool.shop.controller.logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Logger {
    private static final String LOG_PATH = "shop.log";
    private static FileOutputStream out = null;

    static {
        try {
            out = new FileOutputStream(LOG_PATH, true);
        } catch (IOException ignored) {}
    }

    public static void log(String message) {
        Calendar calendar = new GregorianCalendar();
        if (out != null) {
            try {
                out.write(String.format(
                        "[%s.%s.%s-%s:%s:%s.%s]: ",
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND)
                ).getBytes(StandardCharsets.UTF_8));
                out.write(message.getBytes(StandardCharsets.UTF_8));
                out.write('\n');
            } catch (IOException ignored) {}
        }
    }

    public static void flush() {
        try {
            out.close();
            out = new FileOutputStream(LOG_PATH, false);
            out.write(new byte[]{});
            out.close();
            out = new FileOutputStream(LOG_PATH, true);
        } catch (IOException ignored) {}
    }
}

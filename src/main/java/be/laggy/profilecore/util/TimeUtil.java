package be.laggy.profilecore.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeUtil {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private TimeUtil() {}

    public static String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return hours + "h " + minutes + "m " + remainingSeconds + "s";
    }

    public static String formatTimestamp(long millis) {
        return FORMAT.format(new Date(millis));
    }
}
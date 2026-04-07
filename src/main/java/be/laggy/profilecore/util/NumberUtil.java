package be.laggy.profilecore.util;

public final class NumberUtil {

    private NumberUtil() {}

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
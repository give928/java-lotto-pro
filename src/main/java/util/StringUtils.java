package util;

import java.text.NumberFormat;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static String formatThousandsSeparators(int value) {
        return NumberFormat.getInstance().format(value);
    }
}

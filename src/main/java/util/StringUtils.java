package util;

public class StringUtils {

    public static String stringToLower(String name) {
        if (name == null) {
            return null;
        }
        return name.toLowerCase();
    }
}
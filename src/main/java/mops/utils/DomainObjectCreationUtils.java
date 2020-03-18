package mops.utils;

public final class DomainObjectCreationUtils {

    private DomainObjectCreationUtils() {
    }

    public static String convertNullToEmptyAndTrim(String input) {
        String result;
        if (input == null) {
            result = "";
        } else {
            result = input.trim();
        }
        return result;
    }
}

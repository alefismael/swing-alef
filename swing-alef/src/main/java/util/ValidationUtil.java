package util;

import java.util.regex.Pattern;

public final class ValidationUtil {
    private ValidationUtil() {}

    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern CEP = Pattern.compile("^\\d{5}-?\\d{3}$");

    public static boolean required(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean email(String s) {
        return s != null && EMAIL.matcher(s).matches();
    }

    public static boolean cep(String s) {
        return s != null && CEP.matcher(s).matches();
    }
}

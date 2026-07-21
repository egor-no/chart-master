package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Themes {

    public static final String WIN95 = "win95";
    public static final String ALTERNATIVE = "alternative";

    private static final Set<String> ALLOWED =
            new HashSet<>(Arrays.asList(
                    WIN95,
                    ALTERNATIVE
            ));

    private Themes() {
    }

    public static boolean isAllowed(String theme) {
        return ALLOWED.contains(theme);
    }
}
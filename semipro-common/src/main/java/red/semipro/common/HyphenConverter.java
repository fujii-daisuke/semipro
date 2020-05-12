package red.semipro.common;

import javax.annotation.Nonnull;

public class HyphenConverter {

    public static String convert(@Nonnull final String value) {
        String hyphonens = "-";
        String target = "[ー‐‑–—―−ｰ]";

        return value.replaceAll(target, hyphonens);

    }
}

package red.semipro.domain.enums;

import java.util.Arrays;

/**
 * 会場手配 - enum
 */
public enum PlaceArrangement {

    MYSELF("myself"),
    REQUEST("request");

    private String value;

    PlaceArrangement(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static PlaceArrangement getPlaceArrangement(String value) {
        return Arrays.stream(values())
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElse(null);
    }
}

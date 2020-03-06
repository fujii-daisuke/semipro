package red.semipro.domain.enums;

import java.util.Arrays;

/**
 * 性別 - enum
 */
public enum Gender {

    MALE("male"),
    FEMALE("female");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Gender getGender(String value) {
        return Arrays.stream(values())
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElseThrow();
    }
}

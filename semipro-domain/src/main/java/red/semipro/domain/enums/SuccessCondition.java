package red.semipro.domain.enums;

import java.util.Arrays;

/**
 * 募集方式 - enum
 */
public enum SuccessCondition {

    ALL_IN("all-in"),
    ALL_OR_NOTHING("all-or-nothing");

    private String value;

    SuccessCondition(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static SuccessCondition getSuccessCondition(String value) {
        return Arrays.stream(values())
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElse(null);
    }

    public boolean isAllIn() {
        return ALL_IN.value.equals(this.value);
    }

    public boolean isAllOrNothing() {
        return ALL_OR_NOTHING.value.equals(this.value);
    }
}

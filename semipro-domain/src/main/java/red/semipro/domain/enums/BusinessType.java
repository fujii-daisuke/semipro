package red.semipro.domain.enums;

import java.util.Arrays;

/**
 * ビジネスタイプ - enum
 */
public enum BusinessType {

    INDIVIDUAL("individual"),
    COMPANY("company");

    private String value;

    BusinessType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static BusinessType getBusinessType(String value) {
        return Arrays.stream(values())
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElseThrow();
    }
}

package red.semipro.domain.enums;

import java.util.Arrays;

/**
 * セミナータイプ - enum
 */
public enum SeminarType {

    OFFLINE("offline"),
    ONLINE("online"),
    BOTH("both");

    private String value;

    SeminarType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static SeminarType getSeminarType(String value) {
        return Arrays.stream(values())
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElse(null);
    }

}

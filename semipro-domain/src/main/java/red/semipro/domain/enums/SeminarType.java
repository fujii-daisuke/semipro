package red.semipro.domain.enums;

import java.util.Arrays;

public enum SeminarType {

    OFFLINE(1), ONLINE(2);

    private int value;

    SeminarType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SeminarType valueOf(int value) {
        return Arrays.stream(values()).filter(v -> v.getValue() == value).findFirst().get();
    }
}

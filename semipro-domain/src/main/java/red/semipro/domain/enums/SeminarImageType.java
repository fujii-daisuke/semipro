package red.semipro.domain.enums;

import java.util.Arrays;

/**
 * セミナー画像タイプ
 */
public enum SeminarImageType {

    MAIN("main"),
    CONTENTS("contents");

    private String value;

    SeminarImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static SeminarImageType getSeminarImageType(String value) {
        return Arrays.stream(values())
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElseThrow();
    }

}

package red.semipro.domain.enums;

import java.util.Arrays;

/**
 * 登録ステータス - enum
 */
public enum RegisterStatus {

    PROVISIONAL("provisional"),
    REGULAR("regular");

    private String value;

    RegisterStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static RegisterStatus getRegisterStatus(String value) {
        return Arrays.stream(values())
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElseThrow();
    }
}

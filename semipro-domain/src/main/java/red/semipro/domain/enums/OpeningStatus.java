package red.semipro.domain.enums;

import java.util.Arrays;

/**
 * 公開ステータス - enum
 */
public enum OpeningStatus {
    DRAFT("draft"),
    APPLYING("applying"),
    STRIPE_REGISTERED("stripe_registered"),
    OPENING("opening"),
    CLOSED("closed");

    private String value;

    OpeningStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static OpeningStatus getOpeningStatus(String value) {
        return Arrays.stream(values())
            .filter(v -> v.getValue().equals(value))
            .findFirst()
            .orElseThrow();
    }
}

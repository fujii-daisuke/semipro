package red.semipro.domain.enums;

public enum OpeningStatus {
    DRAFT(0), OPENING(1);

    private int value;

    OpeningStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static OpeningStatus valueOf(final int value) {
        OpeningStatus[] statuses = OpeningStatus.values();
        for (OpeningStatus status : statuses) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}

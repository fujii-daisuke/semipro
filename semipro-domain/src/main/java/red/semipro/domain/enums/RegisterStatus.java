package red.semipro.domain.enums;

public enum RegisterStatus {

    PRE(1), REGULAR(2);

    private int value;

    RegisterStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static RegisterStatus valueOf(final int value) {
        RegisterStatus[] statuses = RegisterStatus.values();
        for (RegisterStatus status : statuses) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}

package red.semipro.domain.enums;

import java.util.Arrays;

public enum PaymentType {

    VENUE(1, "会場払い"),
    BANK(2, "銀行振込"),
    CREDIT(3, "クレジット決済");

    private int value;
    private String name;
    
    PaymentType(int value) {
        this.value = value;
    }
    PaymentType(int value, String name) {
        this.value = value;
        this.name= name;
    }

    public int getValue() {
        return this.value;
    }
    public String getName() {
        return this.name;
    }

    public static PaymentType valueOf(int value) {
        return Arrays.stream(values()).filter(v -> v.getValue() == value).findFirst().get();
    }

    public static PaymentType nameOf(String name) {
        return Arrays.stream(values()).filter(v -> v.getName().equals(name)).findFirst().get();
    }
}

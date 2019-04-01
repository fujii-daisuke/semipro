package red.semipro.domain.enums;

public enum ProviderId {

    SEMIPRO(1),
    EVENTON(2);
    
    private int value;
    
    ProviderId(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public static ProviderId valueOf(final int value) {
        ProviderId[] ids = ProviderId.values();
        for (ProviderId id : ids) {
            if (id.getValue() == value) {
                return id;
            }
        }
        return null;
    }
}

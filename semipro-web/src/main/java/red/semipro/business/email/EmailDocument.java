package red.semipro.business.email;

public enum EmailDocument {
    MEMBER_REGISTER("memberRegister"),
    MEMBER_ACTIVATION("memberActivation");
    
    private String key;

    private EmailDocument(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
}

package red.semipro.business.email;

public enum EmailDocument {
    SIGNUPED("signuped"),
    ACTIVATED("activated");
    
    private String key;

    private EmailDocument(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
}

package red.semipro.share.email;

/**
 * メール種類 - enum
 */
public enum EmailDocument {
    SIGN_UP("sign_up"),
    ACTIVATE("activate");
    
    private String key;

    EmailDocument(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
}

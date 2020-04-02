package red.semipro.domain.service.email;

/**
 * メール種類 - enum
 */
public enum EmailDocumentType {

    ACTIVATION("activation"),
    ACTIVATED("activated"),
    ESTABLISH_APPLICANT("establish_applicant"),
    ESTABLISH_SPONSOR("establish_sponsor"),
    NOT_ESTABLISH_APPLICANT("not_establish_applicant"),
    NOT_ESTABLISH_SPONSOR("not_establish_sponsor");
    
    private String key;

    EmailDocumentType(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
}

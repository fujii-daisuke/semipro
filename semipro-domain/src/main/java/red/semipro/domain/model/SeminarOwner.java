package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SeminarOwner implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long seminarId;
    private Long memberId;
    private Integer providerOwnerId;
    private String name;
    private String url;
    
    public SeminarOwner() {
    }
    
    public SeminarOwner(Seminar seminar, Long memberId) {
        this.seminarId = seminar.getId();
        this.memberId = memberId;
    }
    
    public SeminarOwner(Integer providerOwnerId, String name, String url) {
        this.providerOwnerId = providerOwnerId;
        this.name = name;
        this.url = url;
    }
}

package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SeminarOwner implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long seminarId;
    private Member member;
    private Integer providerOwnerId;
    private String name;
    private String url;
    
    public SeminarOwner() {
    }
    
    public SeminarOwner(Seminar seminar, Member member) {
        this.seminarId = seminar.getId();
        this.member = member;
    }
    
    public SeminarOwner(Integer providerOwnerId, String name, String url) {
        this.providerOwnerId = providerOwnerId;
        this.name = name;
        this.url = url;
    }
}

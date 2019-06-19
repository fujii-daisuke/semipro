package red.semipro.domain.service.seminar;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import red.semipro.domain.model.Member;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.SeminarSearchCriteria;

public interface SeminarService {

    public Page<Seminar> searchSeminar(SeminarSearchCriteria criteria, Pageable pageable);
    
    public Seminar findOneWithDetails(Long id);
    
    public void registerByProvider(Seminar seminar);
    
    public Seminar save(Seminar seminar, Member member);
    
    public void update(Seminar seminar);
    
    public Seminar findOneByOwner(Long id, Long memberId);
    
    public List<Seminar> findAllByOwner(Long memberId);
    
    public void publish(Long id, Member member);
}

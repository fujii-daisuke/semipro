package red.semipro.domain.service.seminar;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import red.semipro.domain.model.Account;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.SeminarSearchCriteria;

public interface SeminarService {

    public Page<Seminar> searchSeminar(SeminarSearchCriteria criteria, Pageable pageable);
    
    public Seminar findOneWithDetails(Long id);
    
    public void registerByProvider(Seminar seminar);
    
    public Seminar save(Seminar seminar, Account account);
    
    public void update(Seminar seminar);
    
    public Seminar findOneByOwner(Long id, Long accountId);
    
    public List<Seminar> findAllByOwner(Long accountId);
    
    public void publish(Long id, Account account);
}

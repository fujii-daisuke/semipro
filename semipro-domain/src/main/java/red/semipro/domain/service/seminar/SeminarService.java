package red.semipro.domain.service.seminar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import red.semipro.domain.model.Member;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.repository.seminar.SeminarSearchCriteria;

public interface SeminarService {

    public Page<Seminar> searchSeminar(SeminarSearchCriteria criteria, Pageable pageable);
    public Seminar findOneWithDetails(Long id);
    public void registerByProvider(Seminar seminar);
    public Seminar basicRegister(Seminar seminar, Member member);
    public void update(Seminar seminar);
}

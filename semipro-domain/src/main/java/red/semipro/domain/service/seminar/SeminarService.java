package red.semipro.domain.service.seminar;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.eventon.Response;
import red.semipro.domain.repository.seminar.SeminarSearchCriteria;

public interface SeminarService {

    Page<Seminar> searchSeminar(SeminarSearchCriteria criteria, Pageable pageable);
    List<Seminar> findAll();
    Seminar findOneWithDetails(Long id);
    void eventonRegister(Response response);
    Seminar register(Seminar seminar);
}

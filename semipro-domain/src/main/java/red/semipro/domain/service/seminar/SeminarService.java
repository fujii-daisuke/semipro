package red.semipro.domain.service.seminar;

import java.util.List;

import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.eventon.Response;

public interface SeminarService {

    List<Seminar> findAll();
    Seminar findOneWithDetails(Long id);
    void eventonRegister(Response response);
    Seminar register(Seminar seminar);
}

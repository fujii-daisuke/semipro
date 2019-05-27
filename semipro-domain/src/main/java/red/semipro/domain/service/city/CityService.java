package red.semipro.domain.service.city;

import java.util.List;

import red.semipro.domain.model.City;

public interface CityService {

    List<City> findByPrefectureId(Integer prefectureId);
}

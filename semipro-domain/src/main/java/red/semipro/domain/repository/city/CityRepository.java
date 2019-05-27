package red.semipro.domain.repository.city;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import red.semipro.domain.model.City;

@Mapper
public interface CityRepository {

    List<City> findAllByPrefectureId(Integer prefectureId);
}

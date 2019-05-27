package red.semipro.domain.service.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import red.semipro.domain.model.City;
import red.semipro.domain.repository.city.CityRepository;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;
    
    @Override
    public List<City> findByPrefectureId(Integer prefectureId) {
        return cityRepository.findAllByPrefectureId(prefectureId);
    }

}

package red.semipro.domain.service.address;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.semipro.domain.model.address.City;
import red.semipro.domain.repository.address.CityRepository;

/**
 * 市区町村 - service
 */
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    /**
     * 都道府県IDから市区町村一覧を取得します
     *
     * @param prefectureId 都道府県ID
     * @return 市区町村一覧
     */
    public List<City> findByPrefectureId(Integer prefectureId) {
        return cityRepository.findAllByPrefectureId(prefectureId);
    }

}

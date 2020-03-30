package red.semipro.domain.service.address;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.semipro.domain.model.address.Prefecture;
import red.semipro.domain.repository.address.PrefectureRepository;

/**
 * 都道府県 - service
 */
@Service
@RequiredArgsConstructor
public class PrefectureService {

    private final PrefectureRepository prefectureRepository;

    /**
     * 都道府県一覧を取得します
     *
     * @return 都道府県一覧
     */
    public List<Prefecture> findAll() {
        return prefectureRepository.findAll();
    }

}

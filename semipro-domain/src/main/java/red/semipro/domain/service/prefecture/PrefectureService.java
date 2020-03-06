package red.semipro.domain.service.prefecture;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.semipro.domain.model.Prefecture;
import red.semipro.domain.repository.prefecture.PrefectureRepository;

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

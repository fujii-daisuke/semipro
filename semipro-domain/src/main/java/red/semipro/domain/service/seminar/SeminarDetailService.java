package red.semipro.domain.service.seminar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.seminar.SeminarDetail;
import red.semipro.domain.repository.seminar.SeminarDetailRepository;

/**
 * セミナー詳細 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarDetailService {

    private final SeminarDetailRepository seminarDetailRepository;

    /**
     * セミナー詳細を取得します
     *
     * @param seminarId セミナーID
     * @return セミナー詳細
     */
    public SeminarDetail findOneWithDetails(Long seminarId) {
        return seminarDetailRepository.findOneWithDetails(seminarId);
    }
}

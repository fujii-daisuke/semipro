package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.seminar.SeminarSupport;
import red.semipro.domain.repository.seminar.SeminarSupportRepository;

/**
 * セミナーサポート - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarSupportService {

    private final SeminarSupportRepository seminarSupportRepository;

    /**
     * セミナーサポートを取得します
     *
     * @param seminarId セミナーID
     * @return セミナーサポート
     */
    public SeminarSupport findOne(@Nonnull final Long seminarId) {
        return seminarSupportRepository.findOne(seminarId);
    }

    /**
     * セミナーサポートを登録します
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    public int initialize(@Nonnull final Long seminarId) {
        return seminarSupportRepository.initialize(seminarId);
    }

    /**
     * セミナーサポートを更新します
     *
     * @param seminarSupport セミナーサポート
     * @return 更新件数
     */
    public int update(@Nonnull final SeminarSupport seminarSupport) {
        return seminarSupportRepository.update(seminarSupport);
    }
}

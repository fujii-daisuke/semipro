package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.repository.seminar.SeminarEntrySummaryRepository;

/**
 * セミナー申込者サマリ - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarEntrySummaryService {

    private final SeminarEntrySummaryRepository seminarEntrySummaryRepository;

    /**
     * セミナー申込者サマリを登録します
     *
     * @param seminarId 　セミナーID
     * @return 登録件数
     */
    public int initialize(@Nonnull final Long seminarId) {
        return seminarEntrySummaryRepository.initialize(seminarId);
    }

}

package red.semipro.app.createseminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.Account;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.service.seminar.SeminarContentsService;
import red.semipro.domain.service.seminar.SeminarEntrySummaryService;
import red.semipro.domain.service.seminar.SeminarOverviewService;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.seminar.SeminarSupportService;

/**
 * セミナー作成 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarHelper {

    private final SeminarService seminarService;
    private final SeminarOverviewService seminarOverviewService;
    private final SeminarContentsService seminarContentsService;
    private final SeminarSupportService seminarSupportService;
    private final SeminarEntrySummaryService seminarEntrySummaryService;

    /**
     * セミナーを初期化します
     *
     * @param account アカウント
     * @return セミナー
     */
    public Seminar initialize(@Nonnull final Account account) {
        Seminar seminar = seminarService.initialize(account);
        seminarOverviewService.initialize(seminar.getId());
        seminarContentsService.initialize(seminar.getId());
        seminarSupportService.initialize(seminar.getId());
        seminarEntrySummaryService.initialize(seminar.getId());
        return seminar;
    }

}

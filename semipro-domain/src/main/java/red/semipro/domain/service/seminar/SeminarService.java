package red.semipro.domain.service.seminar;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;
import red.semipro.domain.repository.identification.IdentificationRepository;
import red.semipro.domain.repository.seminar.SeminarContentsRepository;
import red.semipro.domain.repository.seminar.SeminarEntrySummaryRepository;
import red.semipro.domain.repository.seminar.SeminarGoalRepository;
import red.semipro.domain.repository.seminar.SeminarOptionRepository;
import red.semipro.domain.repository.seminar.SeminarOverviewRepository;
import red.semipro.domain.repository.seminar.SeminarPlaceRepository;
import red.semipro.domain.repository.seminar.SeminarRepository;

/**
 * セミナー - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarService {

    private final SeminarRepository seminarRepository;
    private final SeminarGoalRepository seminarGoalRepository;
    private final SeminarPlaceRepository seminarPlaceRepository;
    private final SeminarOverviewRepository seminarOverviewRepository;
    private final SeminarContentsRepository seminarContentsRepository;
    private final SeminarOptionRepository seminarOptionRepository;
    private final IdentificationRepository identificationRepository;
    private final SeminarEntrySummaryRepository seminarEntrySummaryRepository;

    /**
     * セミナー初期状態を登録します
     *
     * @param accountId アカウントID
     * @return seminar セミナー
     */
    public Seminar initialize(@Nonnull final Long accountId) {
        Seminar seminar = Seminar.builder()
            .openingStatus(OpeningStatus.DRAFT)
            .account(Account.builder().id(accountId).build())
            .build();
        seminarRepository.insert(seminar);
        seminarGoalRepository.initialize(seminar.getId());
        seminarPlaceRepository.initialize(seminar.getId());
        seminarOverviewRepository.initialize(seminar.getId());
        seminarContentsRepository.initialize(seminar.getId());
        seminarOptionRepository.initialize(seminar.getId());
        identificationRepository.initialize(seminar.getId());
        seminarEntrySummaryRepository.initialize(seminar.getId());
        return seminar;
    }

    /**
     * セミナー一覧を取得します
     *
     * @param criteria 検索条件
     * @param pageable ページネーション
     * @return セミナー一覧
     */
    public Page<Seminar> search(SeminarSearchCriteria criteria, Pageable pageable) {

        long total = seminarRepository.countByCriteria(criteria);
        List<Seminar> content;
        if (0 < total) {
            content = seminarRepository.findPageByCriteria(criteria, pageable);
        } else {
            content = Collections.emptyList();
        }
        return new PageImpl<Seminar>(content, pageable, total);
    }

}

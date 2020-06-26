package red.semipro.domain.service.seminar;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.eventon.EventonSeminar;
import red.semipro.domain.model.eventon.EventonSeminarCriteria;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.eventon.EventonSeminarRepository;
import red.semipro.domain.repository.identification.IdentificationRepository;
import red.semipro.domain.repository.seminar.SearchSeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarContentsRepository;
import red.semipro.domain.repository.seminar.SeminarEntrySummaryRepository;
import red.semipro.domain.repository.seminar.SeminarGoalRepository;
import red.semipro.domain.repository.seminar.SeminarOptionRepository;
import red.semipro.domain.repository.seminar.SeminarOverviewRepository;
import red.semipro.domain.repository.seminar.SeminarPlaceRepository;
import red.semipro.domain.repository.seminar.SeminarRepository;

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
    private final EventonSeminarRepository eventonSeminarRepository;

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
    public Page<Seminar> search(@Nonnull final SearchSeminarCriteria criteria,
        @Nonnull final Pageable pageable) {

        long total = seminarRepository.countByCriteria(criteria);
        List<Seminar> content;
        if (0 < total) {
            content = seminarRepository.findPageBySearchCriteria(criteria, pageable);
        } else {
            content = Collections.emptyList();
        }
        return new PageImpl<Seminar>(content, pageable, total);
    }

    public PagedListHolder<SeminarSearchOutput> search(@Nonnull final SearchSeminarCriteria criteria) {

        List<SeminarSearchOutput> list = Lists.newArrayList();

        List<Seminar> seminarList = seminarRepository.findAllBySearchCriteria(criteria);
        seminarList.forEach(s -> list.add(SeminarSearchOutput.builder().seminar(s).build()));

        List<EventonSeminar> eventonSeminarList =
            eventonSeminarRepository.findAllByCriteria(
                EventonSeminarCriteria.builder()
                    .isOpening(true)
                    .build(),
                Sort.by(Direction.ASC, "eventon_seminar.entry_ended_at"));

        eventonSeminarList.addAll(eventonSeminarRepository.findAllByCriteria(
            EventonSeminarCriteria.builder()
                .isOpening(false)
                .build(),
            Sort.by(Direction.DESC, "eventon_seminar.entry_ended_at")));

        eventonSeminarList
            .forEach(e -> list.add(SeminarSearchOutput.builder().eventonSeminar(e).build()));

        return new PagedListHolder<>(list);
    }

}

package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.SeminarGoal;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarGoalRepository;
import red.semipro.domain.repository.seminar.SeminarPlaceRepository;

/**
 * セミナー - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarGoalService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarGoalRepository seminarGoalRepository;
    private final SeminarPlaceRepository seminarPlaceRepository;

    /**
     * セミナー目標を更新します
     *
     * @param seminarGoal セミナー目標
     * @param account     アカウント
     */
    public void save(@Nonnull final SeminarGoal seminarGoal, @Nonnull final Account account) {

        seminarSharedService.findOneWithDetailsForUpdate(SeminarCriteria.builder()
            .id(seminarGoal.getSeminarId())
            .openingStatus(OpeningStatus.DRAFT)
            .accountId(account.getId())
            .build());

        seminarGoalRepository.update(seminarGoal);
        seminarPlaceRepository.update(seminarGoal.getPlace());
    }
}

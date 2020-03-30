package red.semipro.domain.service.seminar;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.model.seminar.SeminarGoal;
import red.semipro.domain.repository.seminar.SeminarGoalRepository;
import red.semipro.domain.repository.seminar.SeminarPlaceRepository;

/**
 * セミナー - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarGoalService {

    private final SeminarGoalRepository seminarGoalRepository;
    private final SeminarPlaceRepository seminarPlaceRepository;
    private final EditableSeminarSharedService editableSeminarSharedService;

    /**
     * セミナー目標を取得します
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナー目標
     */
    public SeminarGoal findOneWithPlaceEditable(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        if (Objects.isNull(editableSeminarSharedService.findOne(seminarId, accountId))) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }

        SeminarGoal seminarGoal = seminarGoalRepository.findOneWithPlace(seminarId);
        if (Objects.isNull(seminarGoal)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }
        return seminarGoal;
    }

    /**
     * セミナー目標を更新します
     *
     * @param seminarGoal セミナー目標
     * @param accountId   アカウントID
     */
    public void save(@NotNull final SeminarGoal seminarGoal,
        @NotNull final Long accountId) {

        findOneWithPlaceEditable(seminarGoal.getSeminarId(), accountId);
        seminarGoalRepository.update(seminarGoal);
        seminarPlaceRepository.update(seminarGoal.getPlace());
    }
}

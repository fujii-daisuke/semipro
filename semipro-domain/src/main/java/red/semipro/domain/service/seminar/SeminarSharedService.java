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
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarRepository;

/**
 * セミナー - shared service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarSharedService {

    private final SeminarRepository seminarRepository;

    public Seminar findOneWithDetails(@Nonnull final SeminarCriteria criteria) {

        final Seminar seminar = seminarRepository.findOneWithDetails(criteria);

        if (Objects.isNull(seminar)) {
            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0404);
            throw new BusinessException(message);
        }
        return seminar;
    }

    /**
     * セミナー詳細を取得します
     *
     * @param criteria セミナー
     * @return セミナー詳細
     */
    public Seminar findOneWithDetailsForUpdate(@Nonnull final SeminarCriteria criteria) {

        final Seminar seminar = seminarRepository.findOneWithDetailsForUpdate(criteria);

        if (Objects.isNull(seminar)) {
            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0404);
            throw new BusinessException(message);
        }
        return seminar;
    }

    /**
     * 公開ステータスを更新します
     *
     * @param seminarId     セミナーID
     * @param openingStatus 公開ステータス
     * @return 更新件数
     */
    public int save(@Nonnull final Long seminarId, @NotNull final OpeningStatus openingStatus) {
        return seminarRepository.updateOpeningStatus(seminarId, openingStatus);
    }

}

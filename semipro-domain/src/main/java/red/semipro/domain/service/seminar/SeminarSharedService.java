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
import red.semipro.domain.repository.seminar.SeminarRepository;

/**
 * セミナー - shared service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarSharedService {

    private final SeminarRepository seminarRepository;

    public Seminar findOneWithDetails(@Nonnull final Long id) {

        Seminar seminar = seminarRepository.findOneWithDetails(id);

        if (Objects.isNull(seminar)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }
        return seminar;
    }

    public Seminar findOneWithDetailsByIdAndAccountId(
        @Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        Seminar seminar =
            seminarRepository.findOneWithDetailsByIdAndAccountId(
                seminarId,
                accountId);

        if (Objects.isNull(seminar)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }
        return seminar;
    }

    public Seminar findOneWithDetailsByIdAndOpeningStatusAndAccountId(
        @Nonnull final Long seminarId,
        @Nonnull final OpeningStatus openingStatus,
        @Nonnull final Long accountId) {

        Seminar seminar =
            seminarRepository.findOneWithDetailsByIdAndOpeningStatusAndAccountId(
                seminarId,
                openingStatus,
                accountId);

        if (Objects.isNull(seminar)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }
        return seminar;
    }

    /**
     * セミナー詳細を取得します
     *
     * @param seminarId セミナーID
     * @return セミナー詳細
     */
    public Seminar findOneWithDetailsForUpdate(@Nonnull final Long seminarId,
        @Nonnull final OpeningStatus openingStatus) {

        Seminar seminar = seminarRepository
            .findOneWithDetailsForUpdate(seminarId, openingStatus);

        if (Objects.isNull(seminar)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
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
    public int save(@Nonnull final Long seminarId,
        @NotNull final OpeningStatus openingStatus) {
        return seminarRepository.updateOpeningStatus(seminarId, openingStatus);
    }

}

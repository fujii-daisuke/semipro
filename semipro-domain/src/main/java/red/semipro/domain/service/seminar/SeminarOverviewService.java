package red.semipro.domain.service.seminar;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.model.seminar.SeminarOverview;
import red.semipro.domain.repository.seminar.SeminarOverviewRepository;

/**
 * セミナー概要 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarOverviewService {

    private final SeminarOverviewRepository seminarOverviewRepository;
    private final EditableSeminarSharedService editableSeminarSharedService;

    /**
     * セミナー概要を取得します
     *
     * @param seminarId 　セミナーID
     * @param accountId アカウントID
     * @return セミナー概要
     */
    public SeminarOverview findOneEditable(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        if (Objects
            .isNull(editableSeminarSharedService.findOne(seminarId, accountId))) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }

        SeminarOverview seminarOverview = seminarOverviewRepository.findOne(seminarId);
        if (Objects.isNull(seminarOverview)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }
        return seminarOverview;
    }

    /**
     * セミナー概要を更新します
     *
     * @param seminarOverview 　セミナー概要
     */
    public void save(@Nonnull final SeminarOverview seminarOverview,
        @Nonnull final Long accountId) {

        findOneEditable(seminarOverview.getSeminarId(), accountId);
        seminarOverviewRepository.update(seminarOverview);
    }

    /**
     * メイン画像拡張子を更新します
     *
     * @param seminarId          セミナーID
     * @param accountId          アカウントID
     * @param mainImageExtension メイン画像拡張子
     * @return 更新件数
     */
    public void saveMainImageExtension(@NotNull final Long seminarId,
        @Nonnull final Long accountId,
        @Nullable final String mainImageExtension) {

        findOneEditable(seminarId, accountId);
        seminarOverviewRepository.updateMainImageExtension(seminarId, mainImageExtension);
    }
}

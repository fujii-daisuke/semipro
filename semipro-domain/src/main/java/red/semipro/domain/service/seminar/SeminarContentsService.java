package red.semipro.domain.service.seminar;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.model.seminar.SeminarContents;
import red.semipro.domain.repository.seminar.SeminarContentsRepository;

/**
 * セミナーコンテンツ - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarContentsService {

    private final SeminarContentsRepository seminarContentsRepository;
    private final EditableSeminarSharedService editableSeminarSharedService;

    /**
     * セミナーコンテンツを取得します
     *
     * @param seminarId     　セミナーID
     * @param accountId     アカウントID
     * @return セミナーコンテンツ
     */
    public SeminarContents findOneEditable(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        if (Objects
            .isNull(editableSeminarSharedService.findOne(seminarId, accountId))) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }

        SeminarContents seminarContents = seminarContentsRepository.findOne(seminarId);
        if (Objects.isNull(seminarContents)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }
        return seminarContents;
    }

    /**
     * セミナーコンテンツを更新します
     *
     * @param seminarContents 　セミナーコンテンツ
     * @return 更新件数
     */
    public int save(@Nonnull final SeminarContents seminarContents,
        @Nonnull final Long accountId) {

        findOneEditable(seminarContents.getSeminarId(), accountId);
        return seminarContentsRepository.update(seminarContents);
    }
}

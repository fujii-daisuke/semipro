package red.semipro.domain.service.seminar;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.model.seminar.SeminarOption;
import red.semipro.domain.repository.seminar.SeminarOptionRepository;

/**
 * セミナーサポート - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarOptionService {

    private final SeminarOptionRepository seminarOptionRepository;
    private final EditableSeminarSharedService editableSeminarSharedService;

    /**
     * セミナーサポートを取得します
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナーサポート
     */
    public SeminarOption findOneEditable(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        if (Objects
            .isNull(editableSeminarSharedService.findOne(seminarId, accountId))) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }

        SeminarOption seminarOption = seminarOptionRepository.findOne(seminarId);
        if (Objects.isNull(seminarOption)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }
        return seminarOption;
    }

    /**
     * セミナーサポートを更新します
     *
     * @param seminarOption セミナーサポート
     * @return 更新件数
     */
    public int save(@Nonnull final SeminarOption seminarOption,
        @Nonnull final Long accountId) {

        findOneEditable(seminarOption.getSeminarId(), accountId);
        return seminarOptionRepository.update(seminarOption);
    }
}

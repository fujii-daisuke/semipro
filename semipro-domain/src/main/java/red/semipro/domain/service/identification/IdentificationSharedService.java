package red.semipro.domain.service.identification;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.repository.identification.IdentificationRepository;

/**
 * 本人確認 - shared service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IdentificationSharedService {

    private final IdentificationRepository identificationRepository;

    /**
     * 本人確認を取得します
     *
     * @param seminarId 　セミナーID
     * @return 本人確認
     */
    public Identification findOneWithDetails(@Nonnull final Long seminarId) {

        Identification identification = identificationRepository.findOneWithDetails(seminarId);
        if (Objects.isNull(identification)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }
        return identification;
    }
}

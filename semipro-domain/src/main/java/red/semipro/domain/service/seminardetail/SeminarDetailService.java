package red.semipro.domain.service.seminardetail;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarEntryRepository;
import red.semipro.domain.repository.seminar.SeminarRepository;

/**
 * セミナー詳細 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarDetailService {

    private final SeminarRepository seminarRepository;
    private final SeminarEntryRepository seminarEntryRepository;

    public SeminarDetailOutput findDetail(
        @Nonnull final Long seminarId,
        @Nonnull final List<OpeningStatus> openingStatusList,
        @Nullable final Long accountId) {

        Seminar seminar =
            seminarRepository.findOneWithDetailsByIdAndOpeningStatusList(
                seminarId, openingStatusList);

        if (Objects.isNull(seminar)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }

        SeminarDetailOutput.SeminarDetailOutputBuilder builder = SeminarDetailOutput.builder().seminar(seminar);

        if (Objects.nonNull(accountId)) {
            builder.enteredTicketIds(seminarEntryRepository.findAllBySeminarIdAndAccountId(seminarId, accountId));
        }

        return builder.build();
    }

}

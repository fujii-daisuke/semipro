package red.semipro.domain.service.seminar;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarEntryRepository;
import red.semipro.domain.repository.seminar.SeminarCriteria;

/**
 * セミナー詳細 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarDetailService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarEntryRepository seminarEntryRepository;

    public SeminarDetailOutput findDetail(
        @Nonnull final Long seminarId,
        @Nonnull final List<OpeningStatus> openingStatusList,
        @Nullable final Long accountId) {

        final Seminar seminar =
            seminarSharedService.findOneWithDetails(SeminarCriteria.builder()
                .id(seminarId)
                .openingStatusList(openingStatusList)
                .accountId(accountId)
                .build());

        SeminarDetailOutput.SeminarDetailOutputBuilder builder =
            SeminarDetailOutput.builder().seminar(seminar);

        if (Objects.nonNull(accountId)) {
            builder.enteredTicketIds(
                seminarEntryRepository.findAllBySeminarIdAndAccountId(seminarId, accountId));
        }

        return builder.build();
    }

}

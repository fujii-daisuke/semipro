package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarRepository;

/**
 * 編集可能セミナー - shared service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EditableSeminarSharedService {

    private final SeminarRepository seminarRepository;

    public Seminar findOne(@Nonnull final Long id,
        @Nonnull final Long accountId) {
        return seminarRepository
            .findByIdAndOpeningStatusAndAccountId(id, OpeningStatus.DRAFT, accountId);
    }
}

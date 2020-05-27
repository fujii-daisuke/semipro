package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.SeminarContents;
import red.semipro.domain.repository.seminar.SeminarContentsRepository;
import red.semipro.domain.repository.seminar.SeminarCriteria;

/**
 * セミナーコンテンツ - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarContentsService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarContentsRepository seminarContentsRepository;

    /**
     * セミナーコンテンツを更新します
     *
     * @param seminarContents 　セミナーコンテンツ
     * @param account         アカウント
     * @return 更新件数
     */
    public int save(@Nonnull final SeminarContents seminarContents,
        @Nonnull final Account account) {

        seminarSharedService.findOneWithDetailsForUpdate(SeminarCriteria.builder()
            .id(seminarContents.getSeminarId())
            .openingStatus(OpeningStatus.DRAFT)
            .accountId(account.getId())
            .build());
        return seminarContentsRepository.update(seminarContents);
    }
}

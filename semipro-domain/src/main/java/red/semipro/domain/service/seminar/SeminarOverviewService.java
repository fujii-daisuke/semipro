package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.SeminarOverview;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarOverviewRepository;

/**
 * セミナー概要 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarOverviewService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarOverviewRepository seminarOverviewRepository;

    /**
     * セミナー概要を更新します
     *
     * @param seminarOverview 　セミナー概要
     * @param account         アカウント
     */
    public void save(@Nonnull final SeminarOverview seminarOverview,
        @Nonnull final Account account) {

        seminarSharedService.findOneWithDetailsForUpdate(SeminarCriteria.builder()
            .id(seminarOverview.getSeminarId())
            .openingStatus(OpeningStatus.DRAFT)
            .accountId(account.getId())
            .build());
        seminarOverviewRepository.update(seminarOverview);
    }

    /**
     * メイン画像拡張子を更新します
     *
     * @param seminarId          セミナーID
     * @param account            アカウント
     * @param mainImageExtension メイン画像拡張子
     */
    public void saveMainImageExtension(@NotNull final Long seminarId,
        @Nonnull final Account account,
        @Nullable final String mainImageExtension) {

        seminarSharedService.findOneWithDetailsForUpdate(SeminarCriteria.builder()
            .id(seminarId)
            .openingStatus(OpeningStatus.DRAFT)
            .accountId(account.getId())
            .build());
        seminarOverviewRepository.updateMainImageExtension(seminarId, mainImageExtension);
    }
}

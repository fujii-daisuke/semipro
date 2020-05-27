package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.SeminarOption;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarOptionRepository;

/**
 * セミナーオプション - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarOptionService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarOptionRepository seminarOptionRepository;

    /**
     * セミナーオプションを更新します
     *
     * @param seminarOption セミナーオプション
     * @param account       アカウント
     * @return 更新件数
     */
    public void save(@Nonnull final SeminarOption seminarOption,
        @Nonnull final Account account) {

        seminarSharedService.findOneWithDetailsForUpdate(SeminarCriteria.builder()
            .id(seminarOption.getSeminarId())
            .openingStatus(OpeningStatus.DRAFT)
            .accountId(account.getId())
            .build());
        seminarOptionRepository.update(seminarOption);
    }
}

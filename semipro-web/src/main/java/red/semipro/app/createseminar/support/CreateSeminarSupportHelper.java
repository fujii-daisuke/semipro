package red.semipro.app.createseminar.support;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarSupport;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.seminar.SeminarSupportService;

/**
 * セミナーサポート - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarSupportHelper {

    private final SeminarService seminarService;
    private final SeminarSupportService seminarSupportService;
    private final CreateSeminarSupportFormConverter createSeminarSupportFormConverter;

    /**
     * セミナーサポートフォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナーサポートフォーム
     */
    public CreateSeminarSupportForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) throws IllegalAccessException {
        Seminar seminar = seminarService.findOneBy(seminarId, accountId, OpeningStatus.DRAFT);
        if (Objects.isNull(seminar)) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        SeminarSupport seminarSupport = seminarSupportService.findOne(seminarId);
        if (Objects.isNull(seminarSupport)) {
            seminarSupport = SeminarSupport.builder()
                .seminarId(seminarId)
                .build();
        }
        return createSeminarSupportFormConverter.convert(seminarSupport);
    }

    /**
     * セミナーサポートを更新します
     *
     * @param form セミナーサポートフォーム
     */
    public void save(@Nonnull final CreateSeminarSupportForm form, @Nonnull final Long accountId)
        throws IllegalAccessException {
        if (Objects.isNull(seminarService
            .findOneBy(form.getSeminarId(), accountId, OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }
        SeminarSupport seminarSupport = createSeminarSupportFormConverter.convert(form);
        seminarSupportService.update(seminarSupport);
    }
}

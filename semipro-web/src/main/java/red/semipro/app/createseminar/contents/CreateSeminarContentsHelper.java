package red.semipro.app.createseminar.contents;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarContents;
import red.semipro.domain.service.seminar.SeminarContentsService;
import red.semipro.domain.service.seminar.SeminarService;

/**
 * セミナーコンテンツ - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarContentsHelper {

    private final SeminarService seminarService;
    private final SeminarContentsService seminarContentsService;
    private final CreateSeminarContentsFormConverter createSeminarContentsFormConverter;

    /**
     * セミナーコンテンツフォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナーコンテンツフォーム
     */
    public CreateSeminarContentsForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) throws IllegalAccessException {
        Seminar seminar = seminarService.findOneBy(seminarId, accountId, OpeningStatus.DRAFT);
        if (Objects.isNull(seminar)) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        SeminarContents seminarContents = seminarContentsService.findOne(seminarId);
        if (Objects.isNull(seminarContents)) {
            seminarContents = SeminarContents.builder()
                .seminarId(seminarId)
                .build();
        }
        return createSeminarContentsFormConverter.convert(seminarContents);
    }

    /**
     * セミナーコンテンツを更新します
     *
     * @param form セミナーコンテンツフォーム
     */
    public void save(@Nonnull final CreateSeminarContentsForm form, @Nonnull final Long accountId)
        throws IllegalAccessException {
        if (Objects.isNull(seminarService
            .findOneBy(form.getSeminarId(), accountId, OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }
        SeminarContents seminarContents = createSeminarContentsFormConverter.convert(form);
        seminarContentsService.update(seminarContents);
    }
}

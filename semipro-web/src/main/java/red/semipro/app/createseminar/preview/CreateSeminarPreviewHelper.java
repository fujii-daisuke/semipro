package red.semipro.app.createseminar.preview;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarDetail;
import red.semipro.domain.service.seminar.SeminarDetailService;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * セミナープレビュー - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarPreviewHelper {

    private final SeminarService seminarService;
    private final SeminarDetailService seminarDetailService;
    private final SeminarImageHelper seminarImageHelper;

    /**
     * セミナー詳細を取得します
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナー詳細
     */
    public SeminarDetail findSeminarDetail(@NotNull final Long seminarId,
        @NotNull final Long accountId)
        throws IllegalAccessException {
        Seminar seminar = seminarService.findOneBy(seminarId, accountId, null);
        if (Objects.isNull(seminar)) {
            throw new IllegalAccessException("seminar illegal access.");
        }
        SeminarDetail seminarDetail = seminarDetailService.findOneWithDetails(seminarId);
        seminarDetail
            .setMainImageUrl(seminarImageHelper.getMainImageUrl(seminarDetail.getSeminar().getId(),
                seminarDetail.getSeminarOverview().getMainImageExtension()));
        return seminarDetail;
    }

}

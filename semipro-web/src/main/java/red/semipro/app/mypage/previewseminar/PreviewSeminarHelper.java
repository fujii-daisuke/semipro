package red.semipro.app.mypage.previewseminar;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * セミナープレビュー - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class PreviewSeminarHelper {

    private final SeminarSharedService seminarSharedService;
    private final SeminarImageHelper seminarImageHelper;

    /**
     * セミナー詳細を取得します
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナー詳細
     */
    public Seminar findSeminarDetail(@NotNull final Long seminarId,
        @NotNull final Long accountId) {

        Seminar seminar = seminarSharedService.findOneWithDetailsByIdAndAccountId(
            seminarId, accountId);

        seminar.setMainImageUrl(
            seminarImageHelper.getMainImageUrl(seminar.getId(),
                seminar.getOverview().getMainImageExtension()));
        return seminar;
    }

}

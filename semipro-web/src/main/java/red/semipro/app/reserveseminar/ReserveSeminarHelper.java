package red.semipro.app.reserveseminar;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarDetail;
import red.semipro.domain.service.seminar.SeminarDetailService;
import red.semipro.domain.service.seminar.SeminarService;

/**
 * セミナー予約 - helper
 */
@Component
@RequiredArgsConstructor
public class ReserveSeminarHelper {

    private final SeminarService seminarService;
    private final SeminarDetailService seminarDetailService;

    /**
     * セミナー詳細を取得します
     *
     * @param seminarId セミナーID
     * @return セミナー詳細
     */
    public SeminarDetail findSeminarDetail(Long seminarId) throws IllegalAccessException {
        Seminar seminar = seminarService.findOneBy(seminarId, null, OpeningStatus.OPENING);
        if (Objects.isNull(seminar)) {
            throw new IllegalAccessException("seminar illegal access.");
        }
        return seminarDetailService.findOneWithDetails(seminarId);
    }
}

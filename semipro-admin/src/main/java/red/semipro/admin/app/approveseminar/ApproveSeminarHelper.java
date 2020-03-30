package red.semipro.admin.app.approveseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.service.seminar.SeminarSharedService;

/**
 * セミナー承認 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class ApproveSeminarHelper {

    private final SeminarSharedService seminarSharedService;

    /**
     * セミナーを承認します
     *
     * @param seminarId セミナーID
     */
    public void approve(Long seminarId) {

        seminarSharedService
            .findOneWithDetailsForUpdate(seminarId, OpeningStatus.STRIPE_REGISTERED);
        seminarSharedService.save(seminarId, OpeningStatus.OPENING);

        // TODO セミナー主催者へメール送信
    }
}

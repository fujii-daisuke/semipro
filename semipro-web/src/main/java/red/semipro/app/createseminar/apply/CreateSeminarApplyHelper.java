package red.semipro.app.createseminar.apply;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarDetail;
import red.semipro.domain.service.seminar.SeminarDetailService;
import red.semipro.domain.service.seminar.SeminarService;

/**
 * セミナー申請 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarApplyHelper {

    private final SeminarService seminarService;
    private final SeminarDetailService seminarDetailService;
    private final CreateSeminarApplyValidator createSeminarApplyValidator;

    /**
     * セミナー申請を行います
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     */
    public BindingResult apply(@Nonnull final Long seminarId, @Nonnull Long accountId) {
        Seminar seminar = seminarService.findOneBy(seminarId, accountId, OpeningStatus.DRAFT);
        if (Objects.isNull(seminar)) {
            throw new IllegalStateException("invalid seminar status.");
        }

        SeminarDetail seminarDetail = seminarDetailService.findOneWithDetails(seminarId);
        if (Objects.isNull(seminarDetail)) {
            throw new IllegalStateException("invalid seminar status.");
        }

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(seminarDetail,
            "seminarDetail");
        createSeminarApplyValidator.validate(seminarDetail, bindingResult);

        if (!bindingResult.hasErrors()) {
            seminarService.updateOpeningStatus(seminar.getId(), OpeningStatus.APPLYING);
        }
        return bindingResult;
    }
}

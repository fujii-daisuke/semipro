package red.semipro.app.mypage.applyseminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.service.seminar.SeminarSharedService;

/**
 * セミナー申請 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class ApplySeminarHelper {

    private final SeminarSharedService seminarSharedService;
    private final ApplySeminarValidator applySeminarValidator;

    /**
     * セミナー申請を行います
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     */
    public BindingResult apply(@Nonnull final Long seminarId, @Nonnull Long accountId) {

        Seminar seminar =
            seminarSharedService.findOneWithDetailsByIdAndOpeningStatusAndAccountId(
                seminarId, OpeningStatus.DRAFT, accountId);

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(seminar,
            "seminarDetail");
        applySeminarValidator.validate(seminar, bindingResult);

        if (!bindingResult.hasErrors()) {
            seminarSharedService.save(seminar.getId(), OpeningStatus.APPLYING);
        }
        return bindingResult;
    }
}

package red.semipro.app.createseminar.identification;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.BusinessType;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Account;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.model.identification.IdentificationDetail;
import red.semipro.domain.service.account.AccountService;
import red.semipro.domain.service.identification.IdentificationAddressService;
import red.semipro.domain.service.identification.IdentificationCompanyService;
import red.semipro.domain.service.identification.IdentificationDetailService;
import red.semipro.domain.service.identification.IdentificationIndividualService;
import red.semipro.domain.service.identification.IdentificationService;
import red.semipro.domain.service.seminar.SeminarService;

/**
 * 本人確認 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarIdentificationHelper {

    private final SeminarService seminarService;
    private final AccountService accountService;
    private final IdentificationService identificationService;
    private final IdentificationIndividualService individualService;
    private final IdentificationCompanyService companyService;
    private final IdentificationAddressService addressService;
    private final IdentificationDetailService identificationDetailService;
    private final CreateSeminarIdentificationFormConverter formConverter;
    private final HttpServletRequest request;

    /**
     * 本人確認フォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return 本人確認フォーム
     */
    public CreateSeminarIdentificationForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) throws IllegalAccessException {
        Seminar seminar = seminarService.findOneBy(seminarId, accountId, OpeningStatus.DRAFT);
        if (Objects.isNull(seminar)) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        Account account = accountService.findOne(accountId);
        if (Objects.isNull(account.getStripeAccountId())) {
            return initForm(seminarId);
        }

        IdentificationDetail detail = identificationDetailService.findOneBySeminarId(seminarId);
        if (Objects.isNull(detail)) {
            return initForm(seminarId);
        }

        return formConverter.convert(seminarId, detail);
    }

    /**
     * 本人確認を更新します
     *
     * @param form 本人確認フォーム
     */
    public void save(@Nonnull final CreateSeminarIdentificationForm form,
        @Nonnull final Long accountId)
        throws IllegalAccessException {
        if (Objects.isNull(seminarService
            .findOneBy(form.getSeminarId(), accountId, OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        final IdentificationDetail identificationDetail = identificationDetailService
            .findOneBySeminarId(form.getSeminarId());
        Identification identification;
        if (Objects.isNull(identificationDetail)) {
            identification = formConverter.convert(null, form, request.getRemoteAddr());
            identification = identificationService.insert(identification);
        } else {

            identification = formConverter.convert(identificationDetail.getIdentification().getId(),
                form,
                request.getRemoteAddr());
            identificationService.update(identification);

            individualService.delete(identification.getId());
            companyService.delete(identification.getId());
            addressService.delete(identification.getId());
        }

        if (BusinessType.INDIVIDUAL.equals(identification.getBusinessType())) {
            individualService
                .insert(formConverter.convert(identification.getId(), form.getIndividualForm()));
        } else {
            companyService.insert(formConverter.convert(identification.getId(), form.getCompanyForm()));
        }
        addressService.insert(formConverter.convert(identification.getId(), form.getAddressForm()));
    }

    private CreateSeminarIdentificationForm initForm(@Nonnull final Long seminarId) {
        return CreateSeminarIdentificationForm.builder()
            .seminarId(seminarId)
            .businessType(BusinessType.INDIVIDUAL)
            .individualForm(IndividualForm.builder().build())
            .companyForm(CompanyForm.builder().build())
            .addressForm(AddressForm.builder().build())
            .build();
    }
}

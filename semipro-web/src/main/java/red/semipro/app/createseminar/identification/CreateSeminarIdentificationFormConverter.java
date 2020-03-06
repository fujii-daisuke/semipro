package red.semipro.app.createseminar.identification;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.BusinessType;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.model.identification.IdentificationAddress;
import red.semipro.domain.model.identification.IdentificationCompany;
import red.semipro.domain.model.identification.IdentificationDetail;
import red.semipro.domain.model.identification.IdentificationIndividual;

/**
 * 本人確認 - converter
 */
@Component
@RequiredArgsConstructor
public class CreateSeminarIdentificationFormConverter {

    /**
     * 本人確認フォームにコンバートします
     *
     * @param detail 本人確認（詳細）
     * @return 本人確認フォーム
     */
    public CreateSeminarIdentificationForm convert(@Nonnull final Long seminarId,
        @NotNull final IdentificationDetail detail) {
        CreateSeminarIdentificationForm.CreateSeminarIdentificationFormBuilder builder =
            CreateSeminarIdentificationForm.builder()
                .seminarId(seminarId)
                .businessType(detail.getIdentification().getBusinessType());

        if (BusinessType.INDIVIDUAL.equals(detail.getIdentification().getBusinessType())) {
            return builder.individualForm(convert(detail.getIndividual()))
                .addressForm(convert(detail.getAddress()))
                .companyForm(CompanyForm.builder().build())
                .build();
        } else {
            return builder.companyForm(convert(detail.getCompany()))
                .addressForm(convert(detail.getAddress()))
                .individualForm(IndividualForm.builder().build())
                .build();
        }

    }

    /**
     * 個人フォームにコンバートします
     *
     * @param individual 本人確認（個人）
     * @return 個人フォーム
     */
    private IndividualForm convert(@Nullable final IdentificationIndividual individual) {
        if (Objects.isNull(individual)) {
            return IndividualForm.builder().build();
        }
        return IndividualForm.builder()
            .lastName(individual.getLastName())
            .firstName(individual.getFirstName())
            .lastNameKana(individual.getLastNameKana())
            .firstNameKana(individual.getFirstNameKana())
            .gender(individual.getGender())
            .dobYear(individual.getDobYear())
            .dobMonth(individual.getDobMonth())
            .dobDay(individual.getDobDay())
            .phone(individual.getPhone())
            .build();
    }

    /**
     * 法人フォームにコンバートします
     *
     * @param company 本人確認（法人）
     * @return 法人フォーム
     */
    private CompanyForm convert(@NotNull final IdentificationCompany company) {
        return CompanyForm.builder()
            .name(company.getCompanyName())
            .nameKana(company.getCompanyNameKana())
            .phone(company.getPhone())
            .tax(company.getTax())
            .build();
    }

    /**
     * 住所フォームにコンバートします
     *
     * @param address 本人確認（住所）
     * @return 住所フォーム
     */
    private AddressForm convert(@NotNull final IdentificationAddress address) {
        return AddressForm.builder()
            .postalCode(address.getPostalCode())
            .state(address.getState())
            .city(address.getCity())
            .town(address.getTown())
            .line1(address.getLine1())
            .line2(address.getLine2())
            .stateKana(address.getStateKana())
            .cityKana(address.getCityKana())
            .townKana(address.getTownKana())
            .line1Kana(address.getLine1Kana())
            .line2Kana(address.getLine2Kana())
            .build();
    }

    /**
     * 本人確認にコンバートします
     *
     * @param identificationId 本人確認ID
     * @param form             本人確認フォーム
     * @return 本人確認
     */
    public Identification convert(@Nullable final Long identificationId,
        @NotNull final CreateSeminarIdentificationForm form, @NotNull final String ip) {
        return Identification.builder()
            .id(identificationId)
            .seminarId(form.getSeminarId())
            .businessType(form.getBusinessType())
            .ip(ip)
            .build();
    }

    /**
     * 本人確認（個人）にコンバートします
     *
     * @param identificationId 本人確認ID
     * @param form             本人確認（個人）フォーム
     * @return 本人確認（個人）
     */
    public IdentificationIndividual convert(@Nullable final Long identificationId,
        @NotNull final IndividualForm form) {
        return IdentificationIndividual.builder()
            .identificationId(identificationId)
            .lastName(form.getLastName())
            .firstName(form.getFirstName())
            .lastNameKana(form.getLastNameKana())
            .firstNameKana(form.getFirstNameKana())
            .dobYear(form.getDobYear())
            .dobMonth(form.getDobMonth())
            .dobDay(form.getDobDay())
            .gender(form.getGender())
            .phone(form.getPhone())
            .build();
    }

    /**
     * 本人確認（法人）にコンバートします
     *
     * @param identificationId 本人確認ID
     * @param form             本人確認（法人）フォーム
     * @return 本人確認（法人）
     */
    public IdentificationCompany convert(@Nullable final Long identificationId,
        @NotNull final CompanyForm form) {
        return IdentificationCompany.builder()
            .identificationId(identificationId)
            .companyName(form.getName())
            .companyNameKana(form.getNameKana())
            .tax(form.getTax())
            .phone(form.getPhone())
            .build();
    }

    /**
     * 本人確認（住所）にコンバートします
     *
     * @param identificationId 本人確認ID
     * @param form             本人確認（住所）フォーム
     * @return 本人確認（住所）
     */
    public IdentificationAddress convert(@Nullable final Long identificationId,
        @NotNull final AddressForm form) {
        return IdentificationAddress.builder()
            .identificationId(identificationId)
            .postalCode(form.getPostalCode())
            .state(form.getState())
            .city(form.getCity())
            .town(form.getTown())
            .line1(form.getLine1())
            .line2(form.getLine2())
            .stateKana(form.getStateKana())
            .cityKana(form.getCityKana())
            .townKana(form.getTownKana())
            .line1Kana(form.getLine1Kana())
            .line2Kana(form.getLine2Kana())
            .build();
    }
}

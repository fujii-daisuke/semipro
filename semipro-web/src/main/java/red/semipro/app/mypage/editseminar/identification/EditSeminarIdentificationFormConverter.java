package red.semipro.app.mypage.editseminar.identification;

import com.ibm.icu.text.Transliterator;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.common.HyphenConverter;
import red.semipro.domain.enums.BusinessType;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.model.identification.IdentificationAddress;
import red.semipro.domain.model.identification.IdentificationCompany;
import red.semipro.domain.model.identification.IdentificationIndividual;

/**
 * 本人確認 - converter
 */
@Component
@RequiredArgsConstructor
public class EditSeminarIdentificationFormConverter {

    /**
     * 本人確認フォームにコンバートします
     *
     * @param identification 本人確認
     * @return 本人確認フォーム
     */
    public EditSeminarIdentificationForm convert(@Nonnull final Identification identification) {

        EditSeminarIdentificationForm.EditSeminarIdentificationFormBuilder builder =
            EditSeminarIdentificationForm.builder()
                .id(identification.getId())
                .seminarId(identification.getSeminarId())
                .businessType(
                    Objects.isNull(identification.getBusinessType()) ? BusinessType.INDIVIDUAL
                        : identification.getBusinessType());

        if (BusinessType.COMPANY.equals(identification.getBusinessType())) {
            return builder.companyForm(convert(identification.getCompany()))
                .addressForm(convert(identification.getAddress()))
                .individualForm(IndividualForm.builder().build())
                .build();
        } else {
            return builder.individualForm(convert(identification.getIndividual()))
                .addressForm(convert(identification.getAddress()))
                .companyForm(CompanyForm.builder().build())
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
        return Optional.ofNullable(individual)
            .map(i -> IndividualForm.builder()
                .lastName(i.getLastName())
                .firstName(i.getFirstName())
                .lastNameKana(i.getLastNameKana())
                .firstNameKana(i.getFirstNameKana())
                .gender(i.getGender())
                .dobYear(i.getDobYear())
                .dobMonth(i.getDobMonth())
                .dobDay(i.getDobDay())
                .phone(i.getPhone())
                .build())
            .orElse(IndividualForm.builder().build());
    }

    /**
     * 法人フォームにコンバートします
     *
     * @param company 本人確認（法人）
     * @return 法人フォーム
     */
    private CompanyForm convert(@Nullable final IdentificationCompany company) {
        return Optional.ofNullable(company)
            .map(c -> CompanyForm.builder()
                .name(c.getCompanyName())
                .nameKana(c.getCompanyNameKana())
                .phone(c.getPhone())
                .tax(c.getTax())
                .build())
            .orElse(CompanyForm.builder().build());
    }

    /**
     * 住所フォームにコンバートします
     *
     * @param address 本人確認（住所）
     * @return 住所フォーム
     */
    private AddressForm convert(@Nullable final IdentificationAddress address) {
        return Optional.ofNullable(address)
            .map(a -> AddressForm.builder()
                .postalCode(a.getPostalCode())
                .state(a.getState())
                .city(a.getCity())
                .town(a.getTown())
                .line1(a.getLine1())
                .line2(a.getLine2())
                .stateKana(a.getStateKana())
                .cityKana(a.getCityKana())
                .townKana(a.getTownKana())
                .line1Kana(a.getLine1Kana())
                .line2Kana(a.getLine2Kana())
                .build())
            .orElse(AddressForm.builder().build());
    }

    /**
     * 本人確認にコンバートします
     *
     * @param form 本人確認フォーム
     * @return 本人確認
     */
    public Identification convert(@Nonnull final EditSeminarIdentificationForm form) {

        Identification.IdentificationBuilder builder = Identification.builder()
            .id(form.getId())
            .seminarId(form.getSeminarId())
            .businessType(form.getBusinessType())
            .ip(form.getIp())
            .address(convert(form.getId(), form.getAddressForm()));

        if (BusinessType.INDIVIDUAL.equals(form.getBusinessType())) {
            builder.individual(convert(form.getId(), form.getIndividualForm()));
        } else {
            builder.company(convert(form.getId(), form.getCompanyForm()));
        }
        return builder.build();
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

        Transliterator fullToHalf = Transliterator.getInstance("Fullwidth-Halfwidth");

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
            .line1Kana(HyphenConverter.convertHalf(fullToHalf.transliterate(form.getLine1Kana())))
            .line2Kana(HyphenConverter.convertHalf(fullToHalf.transliterate(form.getLine2Kana())))
            .build();
    }
}

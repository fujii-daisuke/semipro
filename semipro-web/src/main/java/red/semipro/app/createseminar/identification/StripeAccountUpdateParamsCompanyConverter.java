package red.semipro.app.createseminar.identification;

import com.stripe.param.AccountUpdateParams;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.common.PhoneNumberFormatter;

@Component
@RequiredArgsConstructor
public class StripeAccountUpdateParamsCompanyConverter {

    /**
     * Stripe法人にコンバートします
     *
     * @param form 法人フォーム
     * @return Stripe法人
     */
    public AccountUpdateParams.Company convert(@NotNull final CompanyForm form,
        @NotNull final AddressForm addressForm,
        @NotNull final AddressKanaForm addressKanaForm) {
        return AccountUpdateParams.Company.builder()
            .setName(form.getName())
            .setNameKanji(form.getName())
            .setNameKana(form.getNameKana())
            .setTaxId(form.getTax())
            .setPhone(PhoneNumberFormatter.formatE164(form.getPhone()))
            .setAddressKanji(convert(addressForm))
            .setAddressKana(convert(addressKanaForm))
            .build();
    }

    /**
     * Stripe法人住所（漢字）にコンバートします
     *
     * @param form 住所フォーム
     * @return Stripe法人住所（漢字）
     */
    private AccountUpdateParams.Company.AddressKanji convert(
        @NotNull final AddressForm form) {
        return AccountUpdateParams.Company.AddressKanji.builder()
            .setPostalCode(form.getPostalCode())
            .setState(form.getState())
            .setCity(form.getCity())
            .setTown(form.getTown())
            .setLine1(form.getLine1())
            .setLine2(form.getLine2())
            .build();
    }

    /**
     * Stripe法人住所（カタカナ）にコンバートします
     *
     * @param form 住所（カタカナ）フォーム
     * @return Stripe法人住所（カタカナ）
     */
    private AccountUpdateParams.Company.AddressKana convert(
        @NotNull final AddressKanaForm form) {
        return AccountUpdateParams.Company.AddressKana.builder()
            .setPostalCode(form.getPostalCode())
            .setState(form.getState())
            .setCity(form.getCity())
            .setTown(form.getTown())
            .setLine1(form.getLine1())
            .setLine2(form.getLine2())
            .build();
    }
}

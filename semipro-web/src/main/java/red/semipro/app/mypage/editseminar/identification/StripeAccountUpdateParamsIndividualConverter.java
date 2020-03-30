package red.semipro.app.mypage.editseminar.identification;

import com.stripe.param.AccountUpdateParams;
import com.stripe.param.AccountUpdateParams.Individual.AddressKana;
import com.stripe.param.AccountUpdateParams.Individual.AddressKanji;
import com.stripe.param.AccountUpdateParams.Individual.Dob;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.common.PhoneNumberFormatter;

@Component
@RequiredArgsConstructor
public class StripeAccountUpdateParamsIndividualConverter {

    /**
     * Stripe個人にコンバートします
     *
     * @param form 個人フォーム
     * @return Stripe個人
     */
    public AccountUpdateParams.Individual convert(@NotNull final IndividualForm form,
        @NotNull final AddressForm addressForm,
        @NotNull final AddressKanaForm addressKanaForm) {

        return AccountUpdateParams.Individual.builder()
            .setLastNameKanji(form.getLastName())
            .setFirstNameKanji(form.getFirstName())
            .setLastNameKana(form.getLastNameKana())
            .setFirstNameKana(form.getFirstNameKana())
            .setGender(form.getGender().getValue())
            .setPhone(PhoneNumberFormatter.formatE164(form.getPhone()))
            .setDob(Dob.builder()
                .setYear(form.getDobYear())
                .setMonth(form.getDobMonth())
                .setDay(form.getDobDay())
                .build())
            .setAddressKanji(convert(addressForm))
            .setAddressKana(convert(addressKanaForm))
            .build();
    }

    /**
     * Stripe個人住所（漢字）にコンバートします
     *
     * @param form 住所フォーム
     * @return Stripe個人住所（漢字）
     */
    private AccountUpdateParams.Individual.AddressKanji convert(@NotNull final AddressForm form) {
        return AddressKanji.builder()
            .setPostalCode(form.getPostalCode())
            .setState(form.getState())
            .setCity(form.getCity())
            .setTown(form.getTown())
            .setLine1(form.getLine1())
            .setLine2(form.getLine2())
            .build();
    }

    /**
     * Stripe個人住所（カタカナ）にコンバートします
     *
     * @param form 住所（カタカナ）フォーム
     * @return Stripe個人住所（カタカナ）
     */
    private AccountUpdateParams.Individual.AddressKana convert(@NotNull final AddressKanaForm form) {
        return AddressKana.builder()
            .setPostalCode(form.getPostalCode())
            .setState(form.getState())
            .setCity(form.getCity())
            .setTown(form.getTown())
            .setLine1(form.getLine1())
            .setLine2(form.getLine2())
            .build();
    }
}

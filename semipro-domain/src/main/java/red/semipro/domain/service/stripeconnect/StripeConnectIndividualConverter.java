package red.semipro.domain.service.stripeconnect;

import com.stripe.param.AccountCreateParams.Individual;
import com.stripe.param.AccountCreateParams.Individual.AddressKana;
import com.stripe.param.AccountCreateParams.Individual.AddressKanji;
import com.stripe.param.AccountCreateParams.Individual.Dob;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.common.PhoneNumberFormatter;
import red.semipro.domain.model.identification.IdentificationAddress;
import red.semipro.domain.model.identification.IdentificationIndividual;

/**
 * Stripe会員作成パラメーター（個人） - converter
 */
@Component
@RequiredArgsConstructor
public class StripeConnectIndividualConverter {

    /**
     * Stripe個人にコンバートします
     *
     * @param individual 個人
     * @return Stripe個人
     */
    public Individual convert(@NotNull final IdentificationIndividual individual,
        @NotNull final IdentificationAddress address) {
        return Individual.builder()
            .setLastNameKanji(individual.getLastName())
            .setFirstNameKanji(individual.getFirstName())
            .setLastNameKana(individual.getLastNameKana())
            .setFirstNameKana(individual.getFirstNameKana())
            .setGender(individual.getGender().getValue())
            .setPhone(PhoneNumberFormatter.formatE164(individual.getPhone()))
            .setDob(Dob.builder()
                .setYear(individual.getDobYear())
                .setMonth(individual.getDobMonth())
                .setDay(individual.getDobDay())
                .build())
            .setAddressKanji(convertKanji(address))
            .setAddressKana(convertKana(address))
            .build();
    }

    /**
     * Stripe個人住所（漢字）にコンバートします
     *
     * @param address 個人住所
     * @return Stripe個人住所（漢字）
     */
    private AddressKanji convertKanji(@NotNull final IdentificationAddress address) {
        return AddressKanji.builder()
            .setPostalCode(address.getPostalCode())
            .setState(address.getState())
            .setCity(address.getCity())
            .setTown(address.getTown())
            .setLine1(address.getLine1())
            .setLine2(address.getLine2())
            .build();
    }

    /**
     * Stripe個人住所（カタカナ）にコンバートします
     *
     * @param address 個人住所
     * @return Stripe個人住所（カタカナ）
     */
    private AddressKana convertKana(@NotNull final IdentificationAddress address) {
        return AddressKana.builder()
            .setPostalCode(address.getPostalCode())
            .setState(address.getStateKana())
            .setCity(address.getCityKana())
            .setTown(address.getTownKana())
            .setLine1(address.getLine1Kana())
            .setLine2(address.getLine2Kana())
            .build();
    }
}

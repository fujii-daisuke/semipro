package red.semipro.domain.service.stripeconnect;

import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountCreateParams.Company.AddressKana;
import com.stripe.param.AccountCreateParams.Company.AddressKanji;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.common.PhoneNumberFormatter;
import red.semipro.domain.model.identification.IdentificationAddress;
import red.semipro.domain.model.identification.IdentificationCompany;

/**
 * Stripe会員作成パラメーター（法人） - converter
 */
@Component
@RequiredArgsConstructor
public class StripeConnectCompanyConverter {

    /**
     * Stripe法人にコンバートします
     *
     * @param company 法人フォーム
     * @return Stripe法人
     */
    public AccountCreateParams.Company convert(@NotNull final IdentificationCompany company,
        @NotNull final IdentificationAddress address) {
        return AccountCreateParams.Company.builder()
            .setName(company.getCompanyName())
            .setNameKanji(company.getCompanyName())
            .setNameKana(company.getCompanyNameKana())
            .setTaxId(company.getTax())
            .setPhone(PhoneNumberFormatter.formatE164(company.getPhone()))
            .setAddressKanji(convertKanji(address))
            .setAddressKana(convertKana(address))
            .build();
    }

    /**
     * Stripe法人住所（漢字）にコンバートします
     *
     * @param address 法人住所
     * @return Stripe法人住所（漢字）
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
     * @param address 法人住所
     * @return Stripe法人住所（カタカナ）
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

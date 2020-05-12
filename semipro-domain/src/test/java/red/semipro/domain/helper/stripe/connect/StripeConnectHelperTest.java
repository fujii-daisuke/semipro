package red.semipro.domain.helper.stripe.connect;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountCreateParams.Individual;
import com.stripe.param.AccountCreateParams.Individual.AddressKana;
import com.stripe.param.AccountCreateParams.Individual.AddressKanji;
import com.stripe.param.AccountCreateParams.Individual.Dob;
import com.stripe.param.AccountCreateParams.RequestedCapability;
import com.stripe.param.AccountCreateParams.TosAcceptance;
import java.util.List;
import org.junit.Test;
import red.semipro.common.PhoneNumberFormatter;
import red.semipro.domain.enums.BusinessType;
import red.semipro.domain.enums.Gender;

public class StripeConnectHelperTest {

    @Test
    public void create() throws StripeException {

        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        AccountCreateParams.Builder builder = AccountCreateParams.builder()
            .setBusinessType(BusinessType.INDIVIDUAL.getValue())
            .addAllRequestedCapability(
                List.of(RequestedCapability.CARD_PAYMENTS, RequestedCapability.TRANSFERS))
            .setCountry("JP")
            .setType(AccountCreateParams.Type.CUSTOM)
            .setTosAcceptance(TosAcceptance.builder()
                .setIp("127.0.0.1")
                .setDate((long) System.currentTimeMillis() / 1000L)
                .build());

        AddressKanji kanji = AddressKanji.builder()
            .setPostalCode("3300802")
            .setState("埼玉県")
            .setCity("さいたま市")
            .setTown("大宮区　宮町")
            .setLine1("１２３４−５")
            .setLine2("銀座ビル７F")
            .build();

        AddressKana kana = AddressKana.builder()
            .setPostalCode("3300802")
            .setState("ｻｲﾀﾏｹﾝ")
            .setCity("ｻｲﾀﾏｼ")
            .setTown("ｵｵﾐﾔｸ ﾐﾔﾏﾁ")
            .setLine1("1234-5")
            .setLine1("1234−5")
            .setLine2("ｷﾞﾝｻﾞﾋﾞﾙ")
            .build();

        Individual individual = Individual.builder()
            .setLastNameKanji("高橋")
            .setFirstNameKanji("たけお")
            .setLastNameKana("タカハシ")
            .setFirstNameKana("タケオ")
            .setGender(Gender.MALE.getValue())
            .setPhone(PhoneNumberFormatter.formatE164("09012345678"))
            .setDob(Dob.builder()
                .setYear(1980l)
                .setMonth(9l)
                .setDay(29l)
                .build())
            .setAddressKanji(kanji)
            .setAddressKana(kana)
            .build();

        builder.setIndividual(individual);

        Account.create(builder.build());


    }
}

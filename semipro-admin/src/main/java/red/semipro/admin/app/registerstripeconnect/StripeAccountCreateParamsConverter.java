package red.semipro.admin.app.registerstripeconnect;

import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountCreateParams.RequestedCapability;
import com.stripe.param.AccountCreateParams.TosAcceptance;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.BusinessType;
import red.semipro.domain.model.identification.Identification;

/**
 * Stripe会員作成パラメーター - converter
 */
@Component
@RequiredArgsConstructor
public class StripeAccountCreateParamsConverter {

    private final StripeAccountCreateParamsIndividualConverter individualConverter;
    private final StripeAccountCreateParamsCompanyConverter companyConverter;

    public AccountCreateParams convert(@NotNull final Identification identification) {

        AccountCreateParams.Builder builder = AccountCreateParams.builder()
            .setBusinessType(identification.getBusinessType().getValue())
            .addAllRequestedCapability(
                List.of(RequestedCapability.CARD_PAYMENTS, RequestedCapability.TRANSFERS))
            .setCountry("JP")
            .setType(AccountCreateParams.Type.CUSTOM)
            .setTosAcceptance(TosAcceptance.builder()
                .setIp(identification.getIp())
                .setDate((long) System.currentTimeMillis() / 1000L)
                .build());

        if (BusinessType.INDIVIDUAL.equals(identification.getBusinessType())) {
            builder.setIndividual(individualConverter.convert(identification.getIndividual(), identification.getAddress()));
        } else {
            builder.setCompany(companyConverter.convert(identification.getCompany(), identification.getAddress()));
        }
        return builder.build();
    }

}

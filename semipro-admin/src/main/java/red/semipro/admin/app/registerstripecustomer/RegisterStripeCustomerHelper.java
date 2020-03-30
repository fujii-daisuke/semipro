package red.semipro.admin.app.registerstripecustomer;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.admin.share.stripe.StripeHelper;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.AccountStripeConnect;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.service.account.AccountStripeConnectService;
import red.semipro.domain.service.identification.IdentificationSharedService;
import red.semipro.domain.service.seminar.SeminarSharedService;

@Component
@RequiredArgsConstructor
@Transactional
public class RegisterStripeCustomerHelper {

    private final AccountStripeConnectService accountStripeConnectService;
    private final SeminarSharedService seminarSharedService;
    private final IdentificationSharedService identificationSharedService;
    private final StripeAccountCreateParamsConverter stripeAccountCreateParamsConverter;
    private final StripeHelper stripeHelper;

    /**
     * Stripeコネクトアカウント登録を行います
     *
     * @param seminarId セミナーID
     */
    public void register(Long seminarId) throws StripeException {

        Seminar seminar = seminarSharedService
            .findOneWithDetailsForUpdate(seminarId, OpeningStatus.APPLYING);

        if (Objects.nonNull(accountStripeConnectService.findOne(seminar.getAccount().getId()))) {
            seminarSharedService.save(seminar.getId(), OpeningStatus.STRIPE_REGISTERED);
        }

        Identification identification = identificationSharedService.findOneWithDetails(seminarId);

        Account stripeAccount = stripeHelper
            .create(stripeAccountCreateParamsConverter.convert(identification));

        accountStripeConnectService.insert(AccountStripeConnect.builder()
            .accountId(seminar.getAccount().getId())
            .stripeConnectAccountId(stripeAccount.getId())
            .build());

    }
}

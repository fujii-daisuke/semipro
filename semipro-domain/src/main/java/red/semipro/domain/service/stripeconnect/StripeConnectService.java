package red.semipro.domain.service.stripeconnect;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.AccountStripeConnect;
import red.semipro.domain.model.identification.Identification;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.account.AccountStripeConnectRepository;
import red.semipro.domain.service.identification.IdentificationSharedService;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.domain.stripe.repository.connect.ConnectRepositoryImpl;

@Component
@RequiredArgsConstructor
@Transactional
public class StripeConnectService {

    private final AccountStripeConnectRepository accountStripeConnectRepository;
    private final SeminarSharedService seminarSharedService;
    private final IdentificationSharedService identificationSharedService;
    private final StripeConnectConverter stripeConnectConverter;
    private final ConnectRepositoryImpl connectRepository;

    /**
     * Stripeコネクトアカウント登録を行います
     *
     * @param seminarId セミナーID
     */
    public void register(Long seminarId) throws StripeException {

        Seminar seminar = seminarSharedService
            .findOneWithDetailsForUpdate(seminarId, OpeningStatus.APPLYING);

        if (Objects.nonNull(accountStripeConnectRepository.findOne(seminar.getAccount().getId()))) {
            seminarSharedService.save(seminar.getId(), OpeningStatus.STRIPE_REGISTERED);
        }

        Identification identification = identificationSharedService.findOneWithDetails(seminarId);

        Account stripeAccount = connectRepository
            .create(stripeConnectConverter.convert(identification));

        accountStripeConnectRepository.insert(AccountStripeConnect.builder()
            .accountId(seminar.getAccount().getId())
            .stripeConnectAccountId(stripeAccount.getId())
            .build());

        seminarSharedService.save(seminarId, OpeningStatus.STRIPE_REGISTERED);
    }

}

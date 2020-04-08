package red.semipro.domain.service.entry;

import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import java.time.LocalDate;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.helper.stripe.charge.StripeChargeHelper;
import red.semipro.domain.helper.stripe.customercard.StripeCardHelper;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.account.AccountStripeCustomer;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarEntry;
import red.semipro.domain.model.seminar.SeminarTicket;
import red.semipro.domain.repository.account.AccountStripeCustomerRepository;
import red.semipro.domain.repository.seminar.SeminarEntryRepository;
import red.semipro.domain.repository.seminar.SeminarEntrySummaryRepository;
import red.semipro.domain.repository.seminar.SeminarRepository;
import red.semipro.domain.service.seminar.SeminarSharedService;

/**
 * セミナーエントリー - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EntrySeminarService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarRepository seminarRepository;
    private final AccountStripeCustomerRepository accountStripeCustomerRepository;
    private final SeminarEntryRepository seminarEntryRepository;
    private final SeminarEntrySummaryRepository seminarEntrySummaryRepository;
    private final StripeCardHelper stripeCardHelper;
    private final StripeChargeHelper stripeChargeHelper;

    public Seminar findSeminar(
        @Nonnull final Long seminarId,
        @Nonnull final OpeningStatus openingStatus,
        @Nonnull final Long seminarTicketId,
        @Nonnull final Long entryAccountId) {

        if (seminarEntryRepository.existsEntry(seminarId, entryAccountId)) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        Seminar seminar =
            seminarRepository.findOneWithDetailsByIdAndOpeningStatusAndSeminarTicketId(
                seminarId, openingStatus, seminarTicketId);

        if (Objects.isNull(seminar)) {
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0404);
            throw new BusinessException(message);
        }
        return seminar;
    }

    public void entry(@Nonnull final EntrySeminarInput input) throws StripeException {

        Seminar seminar = seminarSharedService
            .findOneWithDetailsForUpdate(input.getSeminarId(), OpeningStatus.OPENING);

        // check date
        if (LocalDate.now().isAfter(seminar.getGoal().getEntryEndingAt())) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        // card id check
        AccountStripeCustomer accountStripeCustomer =
            accountStripeCustomerRepository.findOne(input.getEntryAccountId());
        Card card =
            stripeCardHelper.retrieve(accountStripeCustomer.getStripeCustomerId(),
                input.getStripeCustomerCardId());
        if (Objects.isNull(card)) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        // exists entry
        if (seminarEntryRepository.existsEntry(input.getSeminarId(), input.getEntryAccountId())) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        //check capacity
        SeminarTicket ticket = seminar.getTicketList().stream()
            .filter(t -> t.getId().equals(input.getTicketId()))
            .findFirst()
            .orElseThrow();

        int entryCount = seminarEntryRepository
            .countBySeminarIdAndTicketId(input.getSeminarId(), input.getTicketId());
        if (entryCount > ticket.getCapacity()) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        // stripe charge
        Charge charge = stripeChargeHelper.charge(
            accountStripeCustomer.getStripeCustomerId(),
            card.getId(),
            ticket.getPrice(),
            ticket.getId());

        // entry
        seminarEntryRepository.insert(SeminarEntry.builder()
            .seminarId(input.getSeminarId())
            .account(Account.builder().id(input.getEntryAccountId()).build())
            .ticket(SeminarTicket.builder().id(input.getTicketId()).build())
            .stripeCustomerCardId(input.getStripeCustomerCardId())
            .stripeChargeId(charge.getId())
            .build());

        seminarEntrySummaryRepository.countUp(input.getSeminarId());

    }
}

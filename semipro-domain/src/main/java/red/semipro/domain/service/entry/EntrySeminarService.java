package red.semipro.domain.service.entry;

import java.time.LocalDate;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarEntry;
import red.semipro.domain.model.seminar.SeminarTicket;
import red.semipro.domain.repository.seminar.SeminarEntryRepository;
import red.semipro.domain.repository.seminar.SeminarEntrySummaryRepository;
import red.semipro.domain.repository.stripe.StripeCustomerCardRepository;
import red.semipro.domain.service.seminar.SeminarSharedService;

/**
 * セミナーエントリー - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EntrySeminarService {

    private final SeminarSharedService seminarSharedServiceService;
    private final SeminarEntryRepository seminarEntryRepository;
    private final SeminarEntrySummaryRepository seminarEntrySummaryRepository;
    private final StripeCustomerCardRepository stripeCustomerCardRepository;

    public void entry(@Nonnull final EntrySeminarInput input) {

        Seminar seminar = seminarSharedServiceService
            .findOneWithDetailsForUpdate(input.getSeminarId(), OpeningStatus.OPENING);

        // check date
        if (LocalDate.now().isAfter(seminar.getGoal().getEntryEndingAt())) {

            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        // card id check
        if (!stripeCustomerCardRepository.hasCard(
            input.getEntryAccountId(), input.getStripeCustomerCardId())) {

            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        // exists entry
        if (seminarEntryRepository.existsEntry(input.getSeminarId(), input.getEntryAccountId())) {

            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
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
            ResultMessages message = ResultMessages.error().add(
                MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        // entry
        seminarEntryRepository.insert(SeminarEntry.builder()
            .seminarId(input.getSeminarId())
            .accountId(input.getEntryAccountId())
            .seminarTicketId(input.getTicketId())
            .stripeCustomerCardId(input.getStripeCustomerCardId())
            .build());

        seminarEntrySummaryRepository.countUp(input.getSeminarId());

    }
}

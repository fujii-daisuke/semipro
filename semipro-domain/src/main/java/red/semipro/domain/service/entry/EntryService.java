package red.semipro.domain.service.entry;

import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.account.AccountStripeCustomer;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarEntry;
import red.semipro.domain.model.seminar.SeminarTicket;
import red.semipro.domain.repository.account.AccountRepository;
import red.semipro.domain.repository.account.AccountStripeCustomerRepository;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarEntryRepository;
import red.semipro.domain.repository.seminar.SeminarEntrySummaryRepository;
import red.semipro.domain.service.email.EmailDocumentType;
import red.semipro.domain.service.email.EmailInput;
import red.semipro.domain.service.email.EmailSharedService;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.domain.stripe.repository.charge.ChargeRepository;
import red.semipro.domain.stripe.repository.customercard.CardRepository;

/**
 * セミナーエントリー - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EntryService {

    private final SeminarSharedService seminarSharedService;
    private final AccountStripeCustomerRepository accountStripeCustomerRepository;
    private final SeminarEntryRepository seminarEntryRepository;
    private final SeminarEntrySummaryRepository seminarEntrySummaryRepository;
    private final CardRepository cardRepository;
    private final ChargeRepository chargeRepository;
    private final AccountRepository accountRepository;
    private final EmailSharedService emailSharedService;

    public EntrySeminar findEntrySeminar(
        @Nonnull final Long seminarId,
        @Nonnull final OpeningStatus openingStatus,
        @Nonnull final Long seminarTicketId,
        @Nonnull final Long entryAccountId) {

        if (seminarEntryRepository.existsEntry(seminarId, entryAccountId)) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
            throw new BusinessException(message);
        }

        final Seminar seminar = seminarSharedService.findOneWithDetails(
            SeminarCriteria.builder()
                .id(seminarId)
                .openingStatus(openingStatus)
                .seminarTicketId(seminarTicketId)
                .build());

        return EntrySeminar.builder()
            .seminar(seminar)
            .selectedTicket(seminar.getTicketList().get(0))
            .build();
    }

    public boolean validate(@Nullable final String stripeCustomerCardId,
        @Nonnull final SeminarTicket ticket) {

        // チケット料金が1円以上の場合、支払い方法は必須項目とする
        if (!ticket.isFreeTicket()
            && Objects.isNull(stripeCustomerCardId)) {
            return false;
        }
        return true;
    }

    public void entry(@Nonnull final EntryInput input) throws StripeException {

        final Seminar seminar = seminarSharedService.findOneWithDetails(
            SeminarCriteria.builder()
                .id(input.getSeminarId())
                .openingStatus(OpeningStatus.OPENING)
                .build());

        final SeminarTicket ticket = seminar.getTicketList().stream()
            .filter(t -> t.getId().equals(input.getTicketId()))
            .findFirst()
            .orElseThrow();

        if (!validate(input.getStripeCustomerCardId(), ticket)) {
            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
            throw new BusinessException(message);
        }

        // check date
        if (LocalDateTime.now().isAfter(seminar.getGoal().getEntryEndingAt())) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
            throw new BusinessException(message);
        }

        // exists entry
        if (seminarEntryRepository.existsEntry(input.getSeminarId(), input.getEntryAccountId())) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
            throw new BusinessException(message);
        }

        //check capacity
        int entryCount = seminarEntryRepository
            .countBySeminarIdAndTicketId(input.getSeminarId(), input.getTicketId());
        if (entryCount > ticket.getCapacity()) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
            throw new BusinessException(message);
        }

        Charge charge = null;
        if (Objects.nonNull(input.getStripeCustomerCardId())) {

            // card id check
            AccountStripeCustomer accountStripeCustomer =
                accountStripeCustomerRepository.findOne(input.getEntryAccountId());
            Card card =
                cardRepository.retrieve(accountStripeCustomer.getStripeCustomerId(),
                    input.getStripeCustomerCardId());

            if (Objects.isNull(card)) {
                ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
                throw new BusinessException(message);
            }

            // stripe charge
            charge = chargeRepository.charge(
                accountStripeCustomer.getStripeCustomerId(),
                card.getId(),
                ticket.getPrice(),
                ticket.getId());
        }

        // entry
        seminarEntryRepository.insert(SeminarEntry.builder()
            .seminarId(input.getSeminarId())
            .account(Account.builder().id(input.getEntryAccountId()).build())
            .ticket(SeminarTicket.builder().id(input.getTicketId()).build())
            .stripeCustomerCardId(input.getStripeCustomerCardId())
            .stripeChargeId(Optional.ofNullable(charge).map(Charge::getId).orElse(null))
            .build());

        seminarEntrySummaryRepository.countUp(input.getSeminarId());

        //申し込み者へメール送信
        final Account entryAccount = accountRepository.findOne(input.getEntryAccountId());
        emailSharedService.sendMail(EmailInput.builder()
            .emailDocumentType(EmailDocumentType.ENTRY_APPLICANT)
            .variableMap(Map.of("account", entryAccount,
                "seminar", seminar,
                "ticket", ticket,
                "entryCounts",
                seminarEntrySummaryRepository.findOne(seminar.getId()).getEntryCount()))
            .recipientEmail(entryAccount.getEmail())
            .locale(LocaleContextHolder.getLocale())
            .build());

        //主催者へメール送信
        emailSharedService.sendMail(EmailInput.builder()
            .emailDocumentType(EmailDocumentType.ENTRY_SPONSOR)
            .variableMap(Map.of("account", seminar.getAccount(),
                "seminar", seminar,
                "ticket", ticket,
                "entryCounts",
                seminarEntrySummaryRepository.findOne(seminar.getId()).getEntryCount()))
            .recipientEmail(seminar.getAccount().getEmail())
            .locale(LocaleContextHolder.getLocale())
            .build());

    }
}

package red.semipro.domain.service.seminar;

import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.SeminarTicket;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarTicketRepository;

/**
 * セミナーチケット - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarTicketService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarTicketRepository seminarTicketRepository;

    /**
     * セミナーチケットを登録します
     *
     * @param seminarId      セミナーID
     * @param seminarTickets セミナーチケットリスト
     * @param account        アカウント
     */
    public void save(@Nonnull Long seminarId,
        @Nonnull List<SeminarTicket> seminarTickets,
        @Nonnull final Account account) {

        seminarSharedService.findOneWithDetailsForUpdate(SeminarCriteria.builder()
            .id(seminarId)
            .openingStatus(OpeningStatus.DRAFT)
            .accountId(account.getId())
            .build());

        seminarTicketRepository.delete(seminarId);
        seminarTicketRepository.insert(seminarTickets);
    }
}

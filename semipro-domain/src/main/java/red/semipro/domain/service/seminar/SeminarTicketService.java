package red.semipro.domain.service.seminar;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.seminar.SeminarTicket;
import red.semipro.domain.repository.seminar.SeminarTicketRepository;

/**
 * セミナーチケット - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarTicketService {

    private final SeminarTicketRepository seminarTicketRepository;

    /**
     * セミナーチケットリストを取得します
     *
     * @param seminarId 　セミナーID
     * @return セミナーチケットリスト
     */
    public List<SeminarTicket> findAll(@NotNull final Long seminarId) {
        return seminarTicketRepository.findAll(seminarId);
    }

    /**
     * セミナーチケットを登録します
     *
     * @param seminarId      セミナーID
     * @param seminarTickets セミナーチケットリスト
     */
    public void register(Long seminarId, List<SeminarTicket> seminarTickets) {
        seminarTicketRepository.delete(seminarId);
        seminarTicketRepository.insert(seminarTickets);
    }

}

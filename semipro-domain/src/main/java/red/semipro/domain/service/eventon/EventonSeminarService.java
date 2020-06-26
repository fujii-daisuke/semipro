package red.semipro.domain.service.eventon;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.eventon.EventonSeminar;
import red.semipro.domain.repository.eventon.EventonSeminarRepository;
import red.semipro.domain.repository.eventon.EventonSeminarTicketRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EventonSeminarService {

    private final EventonSeminarRepository eventonSeminarRepository;
    private final EventonSeminarTicketRepository eventonSeminarTicketRepository;

    public EventonSeminar findOneWithDetails(@Nonnull final Integer eventId) {
        EventonSeminar eventonSeminar = eventonSeminarRepository.findOneWithDetails(eventId);
        //XXX なぜか上記で取得できないので再取得
        eventonSeminar.setTickets(eventonSeminarTicketRepository.findAllByEventId(eventId));
        return eventonSeminar;
    }

}

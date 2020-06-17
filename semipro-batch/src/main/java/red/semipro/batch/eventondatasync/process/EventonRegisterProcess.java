package red.semipro.batch.eventondatasync.process;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.eventon.EventonSeminar;
import red.semipro.domain.repository.eventon.EventonSeminarOwnerRepository;
import red.semipro.domain.repository.eventon.EventonSeminarRepository;
import red.semipro.domain.repository.eventon.EventonSeminarTicketRepository;

@Component
@RequiredArgsConstructor
public class EventonRegisterProcess {

    private final EventonSeminarRepository eventonSeminarRepository;
    private final EventonSeminarTicketRepository eventonSeminarTicketRepository;
    private final EventonSeminarOwnerRepository eventonSeminarOwnerRepository;

    @Transactional
    public void execute(@Nonnull final EventonSeminar eventonSeminar) {

        EventonSeminar old = eventonSeminarRepository.findOne(eventonSeminar.getEventId());
        if (Objects.isNull(old)) {
            eventonSeminarRepository.insert(eventonSeminar);
            eventonSeminarTicketRepository.insert(eventonSeminar.getTickets());
            eventonSeminar.getOwners().forEach(eventonSeminarOwnerRepository::insertOrUpdate);
            return;
        }

        if (eventonSeminar.getUpdatedAt().isEqual(old.getUpdatedAt())) {
            return;
        }

        eventonSeminarRepository.update(eventonSeminar);
        eventonSeminarTicketRepository.delete(eventonSeminar.getEventId());
        eventonSeminarTicketRepository.insert(eventonSeminar.getTickets());
        eventonSeminar.getOwners().forEach(eventonSeminarOwnerRepository::insertOrUpdate);

    }
}

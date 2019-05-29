package red.semipro.domain.service.seminar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import red.semipro.domain.model.SeminarTicket;
import red.semipro.domain.repository.seminar.SeminarTicketRepository;

@Service
public class SeminarTicketServiceImpl implements SeminarTicketService {

    @Autowired
    private SeminarTicketRepository seminarTicketRepository;
    
    @Transactional
    @Override
    public void register(Long seminarId, List<SeminarTicket> seminarTickets) {
        seminarTicketRepository.delete(seminarId);
        seminarTicketRepository.insert(seminarTickets);
    }

}

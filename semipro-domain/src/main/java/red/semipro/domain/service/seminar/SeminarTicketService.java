package red.semipro.domain.service.seminar;

import java.util.List;

import red.semipro.domain.model.SeminarTicket;

public interface SeminarTicketService {

    public void register(Long seminarId, List<SeminarTicket> seminarTickets);
}

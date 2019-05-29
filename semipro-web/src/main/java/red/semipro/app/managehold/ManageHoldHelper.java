package red.semipro.app.managehold;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import red.semipro.business.util.FileUploader;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.SeminarTicket;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.seminar.SeminarTicketService;

@Component
public class ManageHoldHelper {

    @Autowired
    private SeminarService seminarService;
    @Autowired
    private FileUploader fileUploader;
    @Autowired
    private SeminarTicketService seminarTicketService;
    
    @Transactional
    public void saveAdvanced(Long seminarId, ManageHoldAdvancedForm form) throws IOException {
        Seminar seminar = seminarService.findOneWithDetails(Long.valueOf(seminarId));
        seminar.setSummary(form.getSummary());
        seminar.setContents(form.getContents());
        seminar.setShootingSupported(form.getShootingEditSupported());
        seminar.setShootingEditSupported(form.getShootingEditSupported());
        seminar.setUpdatedAt(LocalDateTime.now());
        
        fileUploader.upload(form.getMainImage(), "seminar", seminar.getId().toString());
        seminarService.update(seminar);
    }
    
    @Transactional
    public void saveTicket(Long seminarId, ManageHoldTicketForm form) throws IOException {
        Seminar seminar = seminarService.findOneWithDetails(Long.valueOf(seminarId));
        seminar.setEntryStartingAt(LocalDateTime.of(LocalDate.parse(form.getEntryStartingDate()), LocalTime.parse(form.getEntryStartingTime())));
        seminar.setEntryEndingAt(LocalDateTime.of(LocalDate.parse(form.getEntryEndingDate()), LocalTime.parse(form.getEntryEndingTime())));
        seminar.setCapacity(form.getCapacity());
        seminar.setMinimumNumberHosts(form.getMinimumNumberHosts());
        seminar.setUpdatedAt(LocalDateTime.now());
        seminarService.update(seminar);
        
        List<SeminarTicket> seminarTickets = new ArrayList<SeminarTicket>();
        for (Ticket ticket: form.getTickets()) {
            seminarTickets.add(new SeminarTicket(seminarId, ticket.getName(), ticket.getPrice(), ticket.getNum()));
        }
        seminarTicketService.register(seminarId, seminarTickets);
    }
}

package red.semipro.domain.service.seminar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.eventon.Event;
import red.semipro.domain.model.eventon.Response;
import red.semipro.domain.repository.seminar.SeminarOwnerRepository;
import red.semipro.domain.repository.seminar.SeminarPaymentTypeRepository;
import red.semipro.domain.repository.seminar.SeminarRepository;
import red.semipro.domain.repository.seminar.SeminarTicketRepository;

@Transactional
@Service
@Slf4j
public class SeminarServiceImpl implements SeminarService {

    @Autowired
    private SeminarRepository seminarRepository;
    @Autowired
    private SeminarPaymentTypeRepository seminarPaymentTypeRepository;
    @Autowired
    private SeminarTicketRepository seminarTicketRepository;
    @Autowired
    private SeminarOwnerRepository seminarOwnerRepository;
    
    public List<Seminar> findAll() {
        return seminarRepository.findAll();
    }
    
    public Seminar findOneWithDetails(Long id) {
        Seminar seminar = seminarRepository.findOneWithDetails(id);
        if (seminar != null) {
            seminar.setSeminarTickets(seminarTicketRepository.findAll(seminar.getId()));
            seminar.setSeminarPaymentTypes(seminarPaymentTypeRepository.findAll(seminar.getId()));
            seminar.setSeminarOwners(seminarOwnerRepository.findAll(seminar.getId()));
        }
        return seminar;
    }
    
    public void eventonRegister(Response response) {
        for (Event event: response.getEvents()) {
            Seminar seminar = new Seminar(event);
            log.debug("seminar_id: " + seminar.getProviderSeminarId());
            this.register(seminar);
        }
    }
    
    public Seminar register(Seminar seminar) {
        Seminar oldSeminar = seminarRepository.findOneByProviderIdAndProviderSeminarId(seminar.getProviderId().getValue(), seminar.getProviderSeminarId());
        if (oldSeminar == null) {
            seminarRepository.insert(seminar);
            if (seminar.getSeminarPaymentTypes() != null && seminar.getSeminarPaymentTypes().size() > 0) {
                seminar.getSeminarPaymentTypes().forEach(pay -> pay.setSeminarId(seminar.getId()));
                seminarPaymentTypeRepository.insert(seminar.getSeminarPaymentTypes());
            }
            if (seminar.getSeminarTickets() != null && seminar.getSeminarTickets().size() > 0) {
                seminar.getSeminarTickets().forEach(ticket -> ticket.setSeminarId(seminar.getId()));
                seminarTicketRepository.insert(seminar.getSeminarTickets());
            }
            if (seminar.getSeminarOwners() != null && seminar.getSeminarOwners().size() > 0) {
                seminar.getSeminarOwners().forEach(owner -> owner.setSeminarId(seminar.getId()));
                seminarOwnerRepository.insert(seminar.getSeminarOwners());
            }
            return seminar;
        }
        
        if (seminar.getUpdatedAt().isEqual(oldSeminar.getUpdatedAt())) {
            return oldSeminar;
        } else {
            seminar.setId(oldSeminar.getId());
            seminarRepository.update(seminar);
            seminarPaymentTypeRepository.delete(seminar.getId());
            seminarTicketRepository.delete(seminar.getId());
            seminarOwnerRepository.delete(seminar.getId());
            if (seminar.getSeminarPaymentTypes() != null && seminar.getSeminarPaymentTypes().size() > 0) {
                seminar.getSeminarPaymentTypes().forEach(pay -> pay.setSeminarId(seminar.getId()));
                seminarPaymentTypeRepository.insert(seminar.getSeminarPaymentTypes());
            }
            if (seminar.getSeminarTickets() != null && seminar.getSeminarTickets().size() > 0) {
                seminar.getSeminarTickets().forEach(ticket -> ticket.setSeminarId(seminar.getId()));
                seminarTicketRepository.insert(seminar.getSeminarTickets());
            }
            if (seminar.getSeminarOwners() != null && seminar.getSeminarOwners().size() > 0) {
                seminar.getSeminarOwners().forEach(owner -> owner.setSeminarId(seminar.getId()));
                seminarOwnerRepository.insert(seminar.getSeminarOwners());
            }
        }
        return seminar;
    }

}

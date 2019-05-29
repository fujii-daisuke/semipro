package red.semipro.domain.service.seminar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import red.semipro.domain.model.Member;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.SeminarOwner;
import red.semipro.domain.model.SeminarSearchCriteria;
import red.semipro.domain.repository.seminar.SeminarOwnerRepository;
import red.semipro.domain.repository.seminar.SeminarPaymentTypeRepository;
import red.semipro.domain.repository.seminar.SeminarRepository;
import red.semipro.domain.repository.seminar.SeminarTicketRepository;

@Transactional
@Service
public class SeminarServiceImpl implements SeminarService {

    @Autowired
    private SeminarRepository seminarRepository;
    @Autowired
    private SeminarPaymentTypeRepository seminarPaymentTypeRepository;
    @Autowired
    private SeminarTicketRepository seminarTicketRepository;
    @Autowired
    private SeminarOwnerRepository seminarOwnerRepository;
    
    @Override
    public Page<Seminar> searchSeminar(SeminarSearchCriteria criteria, Pageable pageable) {

        long total = seminarRepository.countByCriteria(criteria);
        List<Seminar> content;
        if (0 < total) {
            content = seminarRepository.findPageByCriteria(criteria, pageable);
        } else {
            content = Collections.emptyList();
        }

        Page<Seminar> page = new PageImpl<Seminar>(content, pageable, total);
        return page;
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

    public void registerByProvider(Seminar seminar) {
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
            return;
        }
        
        if (seminar.getUpdatedAt().isEqual(oldSeminar.getUpdatedAt())) {
            return;
        }

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

    @Transactional
    public Seminar save(Seminar seminar, Member member) {
        if (seminar.getId() == null) {
            seminarRepository.insert(seminar);
            List<SeminarOwner> owners = new ArrayList<SeminarOwner>();
            owners.add(new SeminarOwner(seminar, member));
            seminarOwnerRepository.insert(owners);
        } else {
            seminarRepository.update(seminar);
        }
        return seminar;
    }
    
    @Transactional
    public void update(Seminar seminar) {
        seminarRepository.update(seminar);
    }

    @Override
    public List<Seminar> findAllByOwner(Long memberId) {
        return seminarRepository.findAllByOwner(memberId);
    }
}

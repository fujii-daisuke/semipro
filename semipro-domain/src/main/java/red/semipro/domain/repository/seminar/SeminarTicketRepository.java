package red.semipro.domain.repository.seminar;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import red.semipro.domain.model.SeminarTicket;

@Mapper
public interface SeminarTicketRepository {
    
    List<SeminarTicket> findAll(@Param("seminarId")Long seminarId);
    int insert(@Param("tickets")List<SeminarTicket> seminarTickets);
    int delete(@Param("seminarId")Long seminarId);

}

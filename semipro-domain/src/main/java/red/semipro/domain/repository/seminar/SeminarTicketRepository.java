package red.semipro.domain.repository.seminar;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import red.semipro.domain.model.SeminarTicket;

@Mapper
public interface SeminarTicketRepository {
    
    public List<SeminarTicket> findAll(@Param("seminarId")Long seminarId);
    public int insert(@Param("tickets")List<SeminarTicket> seminarTickets);
    public int delete(@Param("seminarId")Long seminarId);

}

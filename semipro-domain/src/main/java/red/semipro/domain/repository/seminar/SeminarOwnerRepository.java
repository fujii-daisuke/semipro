package red.semipro.domain.repository.seminar;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import red.semipro.domain.model.SeminarOwner;

@Mapper
public interface SeminarOwnerRepository {
    
    public List<SeminarOwner> findAll(@Param("seminarId")Long seminarId);
    public int insert(@Param("owners")List<SeminarOwner> seminarOwners);
    public int delete(@Param("seminarId")Long seminarId);

}

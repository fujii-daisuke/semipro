package red.semipro.domain.repository.seminar;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import red.semipro.domain.model.Seminar;

@Mapper
public interface SeminarRepository {

    public List<Seminar> findAll();

    public Seminar findOneByProviderIdAndProviderSeminarId(@Param("providerId") Integer providerId,
            @Param("providerSeminarId") Integer providerSeminarId);
    
    public Seminar findOneWithDetails(Long id);
    
    public int insert(Seminar seminar);
    
    public int update(Seminar seminar);
}

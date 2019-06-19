package red.semipro.domain.repository.seminar;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.SeminarSearchCriteria;

@Mapper
public interface SeminarRepository {

    long countByCriteria(
            @Param("criteria") SeminarSearchCriteria criteria);

    List<Seminar> findPageByCriteria(
            @Param("criteria") SeminarSearchCriteria criteria,
            @Param("pageable") Pageable pageable);
    
    Seminar findOneByProviderIdAndProviderSeminarId(@Param("providerId") Integer providerId,
            @Param("providerSeminarId") Integer providerSeminarId);
    
    Seminar findOneWithDetails(Long id);
    
    int insert(Seminar seminar);
    
    int update(Seminar seminar);
    
    Seminar findOneByOwner(@Param("id")Long id, @Param("memberId")Long memberId);
    
    List<Seminar> findAllByOwner(Long memberId);
    
    int publish(Long id);
}

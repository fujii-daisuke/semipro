package red.semipro.domain.repository.seminar;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import red.semipro.domain.model.SeminarPaymentType;

@Mapper
public interface SeminarPaymentTypeRepository {

    List<SeminarPaymentType> findAll(@Param("seminarId")Long seminarId);
    int insert(@Param("paymentTypes")List<SeminarPaymentType> seminarPaymentTypes);
    int delete(@Param("seminarId")Long seminarId);
}

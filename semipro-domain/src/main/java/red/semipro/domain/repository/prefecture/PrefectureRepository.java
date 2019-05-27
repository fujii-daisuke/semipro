package red.semipro.domain.repository.prefecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import red.semipro.domain.model.Prefecture;

@Mapper
public interface PrefectureRepository {

    List<Prefecture> findAll();
}

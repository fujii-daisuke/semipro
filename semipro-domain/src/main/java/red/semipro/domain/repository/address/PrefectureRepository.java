package red.semipro.domain.repository.address;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;
import red.semipro.domain.model.address.Prefecture;

/**
 * 都道府県 - repository
 */
@Repository
@Mapper
public interface PrefectureRepository {

    /**
     * 都道府県一覧を取得する
     *
     * @return 都道府県一覧
     */
    List<Prefecture> findAll();
}

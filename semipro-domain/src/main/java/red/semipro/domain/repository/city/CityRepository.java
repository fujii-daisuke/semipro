package red.semipro.domain.repository.city;

import java.util.List;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;
import red.semipro.domain.model.City;

/**
 * 市区町村 - repository
 */
@Repository
@Mapper
public interface CityRepository {

    /**
     * 都道府県IDから市区町村一覧を取得する
     *
     * @param prefectureId 都道府県ID
     * @return 市区町村リスト
     */
    List<City> findAllByPrefectureId(@Nonnull final Integer prefectureId);
}

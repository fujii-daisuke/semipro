package red.semipro.domain.service.seminar;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import red.semipro.domain.model.seminar.SeminarPlace;
import red.semipro.domain.repository.seminar.SeminarPlaceRepository;

/**
 * セミナー開催地 - service
 */
@Service
@RequiredArgsConstructor
public class SeminarPlaceService {

    private final SeminarPlaceRepository seminarPlaceRepository;

    /**
     * セミナー開催地を取得します
     *
     * @param seminarId 　セミナーID
     * @return セミナー開催地
     */
    public SeminarPlace findOne(@Nonnull final Long seminarId) {
        return seminarPlaceRepository.findOne(seminarId);
    }

    /**
     * セミナー開催地を登録or更新します
     *
     * @param seminarPlace セミナー開催地
     * @return 登録or更新件数
     */
    public int insertOrUpdate(@Nonnull final SeminarPlace seminarPlace) {
        if (Objects.isNull(seminarPlaceRepository.findOne(seminarPlace.getSeminarId()))) {
            return seminarPlaceRepository.insert(seminarPlace);
        }
        return seminarPlaceRepository.update(seminarPlace);
    }

    /**
     * セミナー開催地を登録します
     *
     * @param seminarPlace 　セミナー開催地
     * @return 登録件数
     */
    public int insert(@Nonnull final SeminarPlace seminarPlace) {
        return seminarPlaceRepository.insert(seminarPlace);
    }

    /**
     * セミナー開催地を更新します
     *
     * @param seminarPlace 　セミナー開催地
     * @return 更新件数
     */
    public int update(@Nonnull final SeminarPlace seminarPlace) {
        return seminarPlaceRepository.update(seminarPlace);
    }
}

package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.seminar.SeminarContents;
import red.semipro.domain.repository.seminar.SeminarContentsRepository;

/**
 * セミナーコンテンツ - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarContentsService {

    private final SeminarContentsRepository seminarContentsRepository;

    /**
     * セミナーコンテンツを取得します
     *
     * @param seminarId 　セミナーID
     * @return セミナーコンテンツ
     */
    public SeminarContents findOne(@Nonnull final Long seminarId) {
        return seminarContentsRepository.findOne(seminarId);
    }

    /**
     * セミナーコンテンツを登録します
     *
     * @param seminarId 　セミナーID
     * @return 登録件数
     */
    public int initialize(@Nonnull final Long seminarId) {
        return seminarContentsRepository.initialize(seminarId);
    }

    /**
     * セミナーコンテンツを更新します
     *
     * @param seminarContents 　セミナーコンテンツ
     * @return 更新件数
     */
    public int update(@Nonnull final SeminarContents seminarContents) {
        return seminarContentsRepository.update(seminarContents);
    }
}

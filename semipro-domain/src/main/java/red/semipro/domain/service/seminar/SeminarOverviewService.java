package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.seminar.SeminarOverview;
import red.semipro.domain.repository.seminar.SeminarOverviewRepository;

/**
 * セミナー概要 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarOverviewService {

    private final SeminarOverviewRepository seminarOverviewRepository;

    /**
     * セミナー概要を取得します
     *
     * @param seminarId 　セミナーID
     * @return セミナー概要
     */
    public SeminarOverview findOne(@Nonnull final Long seminarId) {
        return seminarOverviewRepository.findOne(seminarId);
    }

    /**
     * セミナー概要を登録します
     *
     * @param seminarId 　セミナーID
     * @return 登録件数
     */
    public int initialize(@Nonnull final Long seminarId) {
        return seminarOverviewRepository.initialize(seminarId);
    }

    /**
     * セミナー概要を更新します
     *
     * @param seminarOverview 　セミナー概要
     * @return 更新件数
     */
    public int update(@Nonnull final SeminarOverview seminarOverview) {
        return seminarOverviewRepository.update(seminarOverview);
    }

    /**
     * メイン画像拡張子を更新します
     *
     * @param seminarId          セミナーID
     * @param mainImageExtension メイン画像拡張子
     * @return 更新件数
     */
    public int updateMainImageExtension(@NotNull final Long seminarId,
        @Nullable final String mainImageExtension) {
        return seminarOverviewRepository.updateMainImageExtension(seminarId, mainImageExtension);
    }
}

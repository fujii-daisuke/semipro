package red.semipro.domain.service.seminar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Account;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.repository.seminar.SeminarRepository;

/**
 * セミナー - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeminarService {

    private final SeminarRepository seminarRepository;

    /**
     * セミナー目標を取得します
     *
     * @param seminarId セミナーID
     * @return セミナー目標
     */
    public Seminar findOne(@Nonnull final Long seminarId) {
        return seminarRepository.findOne(seminarId);
    }

    /**
     * アカウントIDからセミナー目標を取得します
     *
     * @param seminarId     セミナーID
     * @param accountId     アカウントID
     * @param openingStatus 公開ステータス
     * @return セミナー目標
     */
    public Seminar findOneBy(@Nonnull final Long seminarId,
        @Nonnull final Long accountId,
        @Nullable final OpeningStatus openingStatus) {
        return seminarRepository.findOneBy(seminarId, accountId, openingStatus);
    }

    /**
     * セミナー目標初期状態を作成します
     *
     * @param account アカウント
     * @return セミナー目標
     */
    public Seminar initialize(@Nonnull final Account account) {
        Seminar seminar = Seminar.builder()
            .account(account)
            .openingStatus(OpeningStatus.DRAFT)
            .build();
        seminarRepository.initialize(seminar);
        return seminar;
    }

    /**
     * セミナー目標を更新します
     *
     * @param seminar セミナー目標
     */
    public void update(@Nonnull final Seminar seminar) {
        seminarRepository.update(seminar);
    }

    /**
     * 公開ステータスを更新します
     *
     * @param seminarId セミナーID
     * @param openingStatus 公開ステータス
     */
    public void updateOpeningStatus(@Nonnull final Long seminarId,
        @NotNull final OpeningStatus openingStatus) {
        seminarRepository.updateOpeningStatus(seminarId, openingStatus);
    }
}

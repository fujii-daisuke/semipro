package red.semipro.domain.model.seminar;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.SeminarType;
import red.semipro.domain.enums.SuccessCondition;

/**
 * セミナー目標 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarGoal implements Serializable {

    private static final long serialVersionUID = 5719120670466605714L;

    private static final DateTimeFormatter entryEndingAtFormatter = DateTimeFormatter
        .ofPattern("yyyy/MM/dd 23:59:59");

    /**
     * セミナーID
     */
    @NotNull
    private Long seminarId;

    /**
     * セミナー種別
     */
    @NotNull
    private SeminarType seminarType;

    /**
     * 最低開催人数
     */
    @NotNull
    private Integer minimumNumber;

    /**
     * 申し込み開始日
     */
    private LocalDate entryStartingAt;

    /**
     * 申し込み終了日
     */
    @NotNull
    private LocalDate entryEndingAt;

    /**
     * 募集方式
     */
    @NotNull
    private SuccessCondition successCondition;

    /**
     * セミナー開始日時
     */
    private LocalDateTime seminarStartingAt;

    /**
     * セミナー終了日時
     */
    private LocalDateTime seminarEndingAt;

    /**
     * セミナー開催地
     */
    private SeminarPlace place;

    /**
     * 募集終了日までの残日数を取得します
     *
     * @return 残日数
     */
    public Long getEntryRemainingDays() {
        if (Objects.isNull(entryEndingAt)) {
            return 0L;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), entryEndingAt);
    }

    public String formattedEntryEndingAt() {
        return Optional.ofNullable(entryEndingAt)
            .map(at -> at.format(entryEndingAtFormatter))
            .orElse(null);
    }
}

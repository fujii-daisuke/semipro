package red.semipro.app.createseminar.goal;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import red.semipro.domain.enums.SeminarType;
import red.semipro.domain.enums.SuccessCondition;

/**
 * セミナー目標 - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeminarGoalForm implements Serializable {

    private static final long serialVersionUID = -4322153237351598284L;

    @NotNull
    private Long seminarId;

    @NotNull
    private SeminarType seminarType;

    @NotNull
    @Min(1)
    private Integer minimumNumber;

    @NotNull
    @Min(1)
    private Integer capacity;

    @NotNull
    @Future
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate entryEndingAt;

    @NotNull
    private SuccessCondition successCondition;

    @Future
    private LocalDateTime seminarStartingAt;

    @Future
    private LocalDateTime seminarEndingAt;

    private CreateSeminarPlaceForm placeForm;

}

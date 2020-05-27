package red.semipro.batch.endrecruitmentseminar;

import com.stripe.exception.StripeException;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarRepository;
import red.semipro.domain.service.establish.EstablishService;

@Component
@RequiredArgsConstructor
@Slf4j
public class EndRecruitmentSeminarHelper {

    private final SeminarRepository seminarRepository;
    private final EstablishService establishService;

    public void execute(@Nonnull final LocalDate executionDate) {

        List<Seminar> seminarList =
            seminarRepository.findAll(SeminarCriteria.builder()
                .openingStatus(OpeningStatus.OPENING)
                .executionDate(executionDate).build());

        log.info("募集終了件数は " + seminarList.size() + "件です");

        seminarList.forEach(seminar -> {
            try {
                establishService.establish(seminar.getId(), executionDate);
            } catch (StripeException e) {
                e.printStackTrace();
            }
        });
    }
}

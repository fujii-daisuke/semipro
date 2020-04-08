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
import red.semipro.domain.repository.seminar.SeminarRepository;
import red.semipro.domain.service.establish.EstablishSeminarService;

@Component
@RequiredArgsConstructor
@Slf4j
public class EndRecruitmentSeminarHelper {

    private final SeminarRepository seminarRepository;
    private final EstablishSeminarService establishSeminarService;

    public void execute(@Nonnull final LocalDate executionDate) {

        List<Seminar> seminarList =
            seminarRepository.findAllEndOfRecruitment(OpeningStatus.OPENING, executionDate);

        log.info("募集終了件数は " + seminarList.size() + "件です");

        seminarList.forEach(seminar -> {
            try {
                establishSeminarService.establish(seminar.getId(), executionDate);
            } catch (StripeException e) {
                e.printStackTrace();
            }
        });
    }
}
